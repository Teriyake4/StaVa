package com.teriyake.stava;

import com.teriyake.stava.connection.ConnectPage;

public class ConnectionTest {
    public static void main(String[] args) {
        ConnectPage connection = null;
        String page = "";
        try {
            System.out.println("Creating connection...");
            connection = new ConnectPage();
            System.out.println("Connected!");
            System.out.println("Getting page...");
            page = connection.getProfilePage("xVeg#NA1");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Page: " + page);
        connection.close();

    }
}
