package com.teriyake.stava.stats.player;

import com.teriyake.stava.stats.Metadata;

public class PlayerData extends Metadata {
    private String subType; // or null
    private String name;
    private String userID;
    private String avatarURL; // profile picture url
    private String itemImageURL;

    public String getSubType() {
        return subType;
    }
    
    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getItemImageURL() {
        return itemImageURL;
    }
}
