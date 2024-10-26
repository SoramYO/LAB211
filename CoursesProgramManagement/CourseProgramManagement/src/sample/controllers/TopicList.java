/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sample.dto.I_List;
import sample.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import sample.dto.Topic;

/**
 *
 * @author Thu Hien
 */
public class TopicList extends ArrayList<Topic> implements I_List {

    @Override
    public int find(String code) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getTopicID().equals(code)) {
                return i;  // Return the index of the found RAMItem
            }
        }
        return -1;
    }

    @Override
    public boolean add() {
        boolean continuteAdd = true;
        boolean check = false;
        while (continuteAdd) {

            String topicID, name, type, title;
            int duration;
            try {
                int y = 1;
                while (true) {
                    topicID = "T" + "_" + y;
                    if (isCodeUnique(topicID) == false) {
                        y++;
                    } else {
                        break;
                    }
                }

                name = Utils.getString("Enter topic's name: ");
                type = Utils.getType("Enter topic's type (long/short): ", "long", "short");
                title = Utils.getString("Enter topic's title: ");
                duration = Utils.getInt("Enter topic's duration: ", 0, 1000);

                this.add(new Topic(topicID, name, type, title, duration));
                check = true;

            } catch (Exception e) {
                System.out.println("Add failed");
            }
            continuteAdd = Utils.confirmYesNo("Do you want to add more?(Y/N)");
        }

        return check;
    }

    private boolean isCodeUnique(String code) {
        for (Topic ram : this) {
            if (ram.getTopicID().equals(code)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update() {
        String topicCode = Utils.getString("Enter the topic's ID to update: ");
        int index = find(topicCode);
        if (index == -1) {
            System.out.println("The topic does not exist");
        } else {
            Topic topicUd = this.get(index);
            System.out.println("Current topic's Information:");
            System.out.printf("%-10s %-20s %-10s %-30s %-10s\n", "TopicID", "Name", "Type", "Title", "Duration");
            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-10s %-30s %-10d\n",
                    topicUd.getTopicID(),
                    topicUd.getName(),
                    topicUd.getType(),
                    topicUd.getTitle(),
                    topicUd.getDuration());

            String newName = Utils.getString("Enter new name: ", topicUd.getName());
            if (!newName.isEmpty()) {
                topicUd.setName(newName);
            }

            String newType = Utils.getType("Enter new type(long/short): ", "long", "short", topicUd.getType());
            if (!newType.isEmpty()) {
                topicUd.setType(newType);
            }

            String newTitle = Utils.getString("Enter new title: ", topicUd.getTitle());
            if (!newTitle.isEmpty()) {
                topicUd.setTitle(newTitle);
            }

            int newDuration = Utils.getInt("Enter new duration: ", 0, 1000, topicUd.getDuration());
            if (newDuration > 0) {
                topicUd.setDuration(newDuration);
            }
            System.out.println("New information: ");
            System.out.println(topicUd.toString());
        }
    }

    @Override
    public boolean delete() {
        boolean check = true;
        String code = Utils.getString("Enter topic's id you want to delete: ");
        int index = find(code);
        if (index == -1) {
            System.out.println("Topic not found!");
        } else {
            System.out.println(this.get(index).toString());
            boolean isDup = Utils.confirmYesNo("Do you wanna to remove this topic ??(Y/N)");
            if (isDup) {
                this.remove(index);
                System.out.println("Delete success");
            } else {
                check = false;
                System.out.println("Delete fail");
            }

        }
        return check;
    }

    @Override
    public void show() {
        if (this.isEmpty()) {
            System.out.println("No topics available to display.");
            return;
        }

        System.out.printf("%-10s %-20s %-10s %-30s %-10s\n", "TopicID", "Name", "Type", "Title", "Duration");
        System.out.println("--------------------------------------------------------------------------");

        for (Topic topic : this) {
            System.out.printf("%-10s %-20s %-10s %-30s %-10d\n",
                    topic.getTopicID(),
                    topic.getName(),
                    topic.getType(),
                    topic.getTitle(),
                    topic.getDuration());
        }
    }

    @Override
    public boolean saveToFile(String fileName) {
        try (FileOutputStream file = new FileOutputStream(fileName);
                ObjectOutputStream os = new ObjectOutputStream(file)) {
            if (this.isEmpty()) {
                System.out.println("No data to save.");
                return false;
            }
            for (Topic topic : this) {
                os.writeObject(topic);
            }

            System.out.println("Data saved successfully to " + fileName);
            return true;

        } catch (Exception e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }

        return false;
    }

    public void loadFromFile(String file) {

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);

            while (true) {
                try {
                    Topic topic = (Topic) objectInputStream.readObject();
                    this.add(topic);
                } catch (EOFException e) {
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File error" + e);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();

                }
            } catch (IOException e) {
                System.out.println("File error " + e);
            }
        }

    }

    @Override
    public void loadFileToShow(String fileName) {
        List<Topic> topicList = this;
        if (!topicList.isEmpty()) {
            this.clear();
            this.addAll(topicList);
            System.out.println("Data loaded successfully from " + fileName);
        } else {
            System.out.println("No data found in the file.");
        }
    }
    
    public void searchTopicByName() {
    String searchKeyword = Utils.getString("Enter topic name keyword to search: ").toLowerCase();

    boolean found = false;

    System.out.printf("%-10s %-20s %-15s %-15s %-15s\n", "TopicID", "Topic Name", "Type", "Title", "Duration");
    System.out.println("------------------------------------------------------");

    for (Topic topic : this) {  
        if (topic.getName().toLowerCase().contains(searchKeyword)) {
            System.out.printf("%-10s %-20s %-15s %-15s  %-15d\n",
                    topic.getTopicID(),
                    topic.getName(),
                    topic.getType(),
                    topic.getTitle(),
                    topic.getDuration());
            found = true;
        }
    }

    if (!found) {
        System.out.println("No topics found with the keyword: " + searchKeyword);
    }
}


}
