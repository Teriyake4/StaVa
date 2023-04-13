## StaVa: Stats for Valorant API

do version numbers start at 0???

Stava is a Java library for retrieving stats and information about players in Valorant. This is a personal project so support and development may be slow. Stava uses the Valorant Tracker API to retrieve all the information. 

Player stats are organized as objects seperated by the different types of stats as classes. For instance stats for a specific agent such as Omen will be as a PlayerAgent Object. Classes will have getter methods to retrieve information. 

The Player class is the recomended way to access the information of a player. It contains the respective stat objects of a player in one object. 


##  Example

Getting the Player object and getting stats from it using the retriever. The name varible can be set to any valid player name, but must include tagline. 

```java
String name = "example#tag";

Retriever ret = null;
Player p = null;
try {
    System.out.println("Creating connection");
    ret = new Retriever();
    System.out.println("Connected");
    p = ret.getPlayer(name);
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
