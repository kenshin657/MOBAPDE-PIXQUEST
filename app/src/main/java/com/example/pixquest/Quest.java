package com.example.pixquest;

public class Quest {

    private String id;
    private String title;
    private String description;
    private String dateCreated;
    private int reward;
    private int type;   //0 = single, 1 = daily, 2 = weekly

    public Quest(){}

    public Quest(String id, String title, String description, String dateCreated, int reward, int type){
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.reward = reward;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getReward() {
        return reward;
    }

    public int getType() {
        return type;
    }
}
