## StaVa: Stats for Valorant API


StaVa is a Java library for retrieving stats and information about players in Valorant. This is a personal project so support and development may be slow. Stava uses the Valorant Tracker API to retrieve all the information. 

Player stats are organized as objects seperated by the different types of stats as classes. For example, stats for a specific agent such as Omen will be as a PlayerAgent Object and stats for a map such as Ascent would be as a PlayerMap class. Classes will have getter methods to retrieve information. 

The Player class is the recomended way to access the information of a player. It contains the respective stat objects of a player in one object with methods to find information more easily. 


##  Example

Getting the Player object and getting stats from it using the retriever. The name varible to any valid player name, and must include tagline. 

```java
String name = "example#tag";
// replace with a real player. 
// make sure player profile is public on 
// Tracker.gg and with this code specifically, 
// should have one comp game

Player p = null;
try {
    System.out.println("Creating connection");
    Retriever ret = new Retriever(); // This can throw an error, but unlikely
    System.out.println("Connected");
    p = ret.getPlayer(name); // More likely to throw an error
    ret.closeConnection();
}
catch(Exception e) {
    e.printStackTrace();
}

PlayerMode pm = p.getMode("competitive");

System.out.println("Name: " + p.info().getName());
System.out.println("Rank: " + pm.getRank());
System.out.println("Winrate: " + pm.getMatchesWinPct());
System.out.println("KD Ratio: " + pm.getKDRatio());
```
