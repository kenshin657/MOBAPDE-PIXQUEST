package com.example.pixquest;

public class Quest {

    private String id;
    private String title;
    private String description;
    private String dateCreated;
    private String lastCompleted;
    private String owner;
    private int reward;
    private int type;   //0 = single, 1 = daily, 2 = weekly
    private boolean isComplete;

    public Quest(){}

    public Quest(String id, String title, String description, String dateCreated, String lastCompleted, String owner, int reward, int type){
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.lastCompleted = lastCompleted;
        this.owner = owner;
        this.reward = reward;
        this.type = type;
        isComplete = false;
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

    public String getLastCompleted() {
        return lastCompleted;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public String getOwner(){
        return owner;
    }

    public int getReward() {
        return reward;
    }

    public int getType() {
        return type;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
