package com.teriyake.stava;

public class SearchParserTest {
    public static void main(String[] args) {
        Retriever ret = null;
        String[] fullResults = null;
        String[] nonPrivateResults = null;
        String search = "el trucko";
        try {
            System.out.println("Creating connection");
            ret = new Retriever();
            System.out.println("Connected");
            fullResults = ret.getSearch(search);
            nonPrivateResults =  ret.getNonPrivateSearch(search);
            ret.closeConnection();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Full Results: \n");
        for(int i = 0; i < fullResults.length; i++) {
            System.out.println((i + 1) + ". " + fullResults[i]);
        }

        System.out.println("\nNon Private Results: \n");
        for(int i = 0; i < nonPrivateResults.length; i++) {
            System.out.println((i + 1) + ". " + nonPrivateResults[i]);
        }
    }    
}
