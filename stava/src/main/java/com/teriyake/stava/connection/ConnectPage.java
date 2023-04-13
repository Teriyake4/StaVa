package com.teriyake.stava.connection;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.Response;
import com.teriyake.stava.HttpStatusException;

/**
 * {@code ConnectPage} class connects to the Valroant Tracker.gg API
 * to retrieve player data and matches while handaling HTTP
 * status exceptions. 
 */
public class ConnectPage {
    Playwright playwright;
    Browser process;
    BrowserContext browser;
    Page page;
    /**
     * Creates a new {@code ConnectPage} instance that can 
     * be used to retrieve player data and matches from the 
     * Valorant Tracker.gg API. 
     * @throws PlaywrightException
     */
    public ConnectPage() throws PlaywrightException {
        connect();
    }
    /**
     * loads page while handling any exceptions and stores in playerPage. 
     * @param url as a String. 
     * @return page as a Document. 
     */
    private Response loadPage(String url) throws HttpStatusException {
        int statusCode = -1;
        Response rep = null;
        try {
            Page page = browser.newPage();
            rep = page.navigate(url);
            statusCode = rep.status();
        }
             catch(PlaywrightException e) {
            e.printStackTrace();
        }
             switch(statusCode) {
            case 403:
                throw new HttpStatusException(
                    "Access denied", statusCode, url);
            case 404:
                throw new HttpStatusException(
                    "Profile or page does not exist", statusCode, url);
            case 451:
                throw new HttpStatusException(
                    "Profile is private", statusCode, url);
        }
        if(statusCode >= 300 || statusCode < 200) {
            throw new HttpStatusException("Http error", statusCode, url);
        }
        return rep;
    }
    /**
     * This method is called by the constructer. This method establishes a 
     * connection to the Valorant Tracker.gg API. 
     * @throws PlaywrightException
     */
    public void connect() throws PlaywrightException {
        playwright = Playwright.create();
        process = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setChannel("msedge"));
        Page tempPage = process.newPage();
        String userAgent = tempPage.evaluate("navigator.userAgent").toString();
        tempPage.close();

        userAgent = userAgent.replaceFirst("Headless", "");

        browser = process.newContext(new Browser.NewContextOptions()
            .setUserAgent(userAgent));
        page = browser.newPage();
        page.setDefaultNavigationTimeout(4000);
    }
    /**
     * This method closes the connection to free up resources.
     * This method should be called when finished using the 
     * {@code ConnectPage} instance. 
     */
    public void close() {
        page.close();
        browser.close();
        process.close();
        playwright.close();
    }

    /**
     * Loads the search results page for a given player name and returns the page's text as a string in JSON format.
     * @param name the player name to search for. Can include tagline, e.g. "example#NA1" or "example"
     * @return the search page as a string containing player IDs in JSON format
     * @throws HttpStatusException if an HTTP error occurs while loading the page, e.g. 403 or 404
     */
    public String getSearchPage(String name) throws HttpStatusException {
        String searchURL = 
        "https://api.tracker.gg/api/v2/valorant/standard/search?platform=riot&query=";
        name = name.replaceAll(" ", "%20");
        name = name.replaceAll("#", "%23");
        searchURL += name + "&autocomplete=true";

        String searchPage = loadPage(searchURL).text();

        return searchPage;
    }

    /**
     * Loads the player profile page for a given player name and returns the page's text as a string.
     * @param name the player name including tagline, e.g. "example#NA1"
     * @return the profile page as a string in JSON format
     * @throws HttpStatusException if an HTTP error occurs while loading the page, e.g. 403 or 404
     */
    public String getProfilePage(String name) throws HttpStatusException {
        String pageURL = "https://api.tracker.gg/api/v2/valorant/standard/profile/riot/";
        name = name.replaceAll(" ", "%20");
        name = name.replaceAll("#", "%23");
        pageURL += name + "?source=web";

        String profilePage = loadPage(pageURL).text();

        return profilePage;
    }

    /**
     * Loads the player's competitive matches page for a given player name and returns the page's text as a string.
     * @param name the player name including tagline, e.g. "example#NA1"
     * @return the competitive matches page as a string in JSON format
     * @throws HttpStatusException if an HTTP error occurs while loading the page, e.g. 403 or 404
     */
    public String getProfileMatchesPage(String name) throws HttpStatusException {
        String pageURL = "https://api.tracker.gg/api/v2/valorant/standard/matches/riot/";
        name = name.replaceAll(" ", "%20");
        name = name.replaceAll("#", "%23");
        pageURL += name + "?type=competitive";

        String profileMatchesPage = loadPage(pageURL).text();
             return profileMatchesPage;
    }

    // general match
    // https://api.tracker.gg/api/v2/valorant/standard/matches/7dce3e6d-63ce-4e97-914e-9a6e5542a8c7

    // leaderboard
    // https://api.tracker.gg/api/v1/valorant/standard/leaderboards?type=ranked&platform=all&board=ranked&region=global&act=9c91a445-4f78-1baa-a3ea-8f8aadf4914d&skip=0&take=100

    // general stats by either month or act including lifetime
    // https://api.tracker.gg/api/v2/valorant/standard/profile/riot/EL%20TRUCKO%23saucy/segments/season-report?playlist=competitive

    // teammates
    // https://api.tracker.gg/api/v1/valorant/matches/riot/EL%20TRUCKO%23saucy/aggregated?localOffset=480&playlist=competitive

    // best matches, different ways to sort
    // https://api.tracker.gg/api/v2/valorant/standard/profile/riot/EL%20TRUCKO%23saucy/stats/playlist/kills?playlist=competitive
}