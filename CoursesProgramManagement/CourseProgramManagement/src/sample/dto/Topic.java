/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import java.io.Serializable;

/**
 *
 * @author Thu Hien
 */
public class Topic implements Serializable{
    private String topicID, name, type, title;
    private int duration;

    public Topic(String topicID, String name, String type, String title, int duration) {
        this.topicID = topicID;
        this.name = name;
        this.type = type;
        this.title = title;
        this.duration = duration;
    }
    
    @Override
    public boolean equals(Object obj) {
        Topic t = (Topic) obj;
        return this.topicID.equalsIgnoreCase(t.getTopicID());
    }
    
    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("%s_%s_%s_%s_%d\n "
                , topicID, name, type, title, duration);          
    }
    
    
    
}
