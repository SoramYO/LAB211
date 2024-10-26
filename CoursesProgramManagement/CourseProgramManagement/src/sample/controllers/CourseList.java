package sample.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hoa Doan
 */
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import sample.dto.I_List;
import sample.dto.Course;
import sample.dto.Topic;

import sample.utils.Utils;
import sample.dto.Learner;

public class CourseList extends ArrayList<Course> implements I_List {

    private TopicList topicList;

    public CourseList(TopicList topicList) {
        this.topicList = topicList;
    }

    @Override
    public int find(String code) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getCourseID().equals(code)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void update() {
        String courseID = Utils.getString("Enter Course ID to update: ");
        int index = find(courseID);
        if (index == -1) {
            System.out.println("Course not found!");
        } else {
            Course courseUd = this.get(index);
            System.out.println("Current Course Information:");
            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s %-20s %-20s %-15s\n",
                    "CourseID", "Name", "Type", "Title", "Begin Date", "End Date", "Topic", "Fee", "Active", "Size");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");

            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s %-20.2f %-20s %-15d\n",
                    courseUd.getCourseID(),
                    courseUd.getName(),
                    courseUd.getType(),
                    courseUd.getTitle(),
                    courseUd.getBeginDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    courseUd.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    courseUd.getTopic(),
                    courseUd.getTutionFee(),
                    courseUd.isActive() ? "Yes" : "No",
                    courseUd.getSize());

            String newName = Utils.getString("Enter new name: ", courseUd.getName());
            if (!newName.isEmpty()) {
                courseUd.setName(newName);
            }

            String newType = Utils.getType("Enter new type (online/offline): ", "online", "offline", courseUd.getType());
            if (!newType.isEmpty()) {
                courseUd.setType(newType);
            }

            String newTitle = Utils.getString("Enter new title: ", courseUd.getTitle());
            if (!newTitle.isEmpty()) {
                courseUd.setTitle(newTitle);
            }

            String newTopic = Utils.getString("Enter new topic: ", courseUd.getTopic());
            if (!newTopic.isEmpty()) {
                courseUd.setTopic(newTopic);
            }

            LocalDate newBeginDate = Utils.getDate("Enter new begin date: ", courseUd.getBeginDate());
            if (newBeginDate != null) {
                courseUd.setBeginDate(newBeginDate);
            }
            LocalDate newEndDate;
            while (true) {
                newEndDate = Utils.getDate("Enter end date: ");
                if (isEndDateValid(newBeginDate, newEndDate)) {
                    break;
                }
                System.out.println("End date cannot be before begin date. Please enter again.");

            }

            if (newEndDate != null) {
                courseUd.setEndDate(newEndDate);
            }

            double newFee = Utils.getDouble("Enter new tution fee: ", 0, 1000000000, courseUd.getTutionFee());
            if (newFee > 0) {
                courseUd.setTutionFee(newFee);
            }

            boolean newActive = Utils.confirmYesNo("Do you want to activate this course? (Y/N)", courseUd.isActive());
            courseUd.setActive(newActive);

            System.out.println("Updated Course Information:");
            System.out.println(courseUd.toString());
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
            System.out.println("No courses available to display.");
            return;
        }
        System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s %-20s %-15s %-15s %-15s %-15s %-15s\n",
                "CourseID", "Name", "Type", "Title", "Begin Date", "End Date", "Topic", "Fee", "Active", "Total", "Passed", "Failed", "Income");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Course course : this) {
            List<Learner> learners = course.getLearner();
            int passedCount = 0;
            int failedCount = 0;
            double income = 0;
            int total = 0;

            if (learners != null) {
                passedCount = (int) learners.stream().filter(learner -> learner != null && learner.getScore() > 5).count();
                failedCount = learners.size() - passedCount;
                income = learners.size() * course.getTutionFee();
                total = learners.size();
            }

            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s %-20.2f %-15s %-15d %-15d %-15d %-15.2f\n",
                    course.getCourseID(),
                    course.getName(),
                    course.getType(),
                    course.getTitle(),
                    course.getBeginDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    course.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    course.getTopic(),
                    course.getTutionFee(),
                    course.isActive() ? "Yes" : "No",
                    total,
                    passedCount,
                    failedCount,
                    income
            );
        }
    }

    @Override
    public boolean add() {
        boolean check = false;
        boolean continuteAdd = true;
        while (continuteAdd) {
            String courseID, name, type, title, topic;
            LocalDate beginDate, endDate;
            double tutionFee;
            int sizeCourse;
            boolean active;
            try {
                int y = 1;
                while (true) {
                    courseID = "C" + "_" + y;
                    if (isCodeUnique(courseID) == false) {
                        y++;
                    } else {
                        break;
                    }
                }

                name = Utils.getString("Enter course's name: ");
                type = Utils.getType("Enter course's type (online/offline): ", "online", "offline");
                title = Utils.getString("Enter course's title: ");
                topicList.show();
                while (true) {
                    topic = Utils.getString("Enter course's topic id: ");
                    int topicId = topicList.find(topic);
                    if (topicId == -1) {
                        System.out.println("Please enter valid topic id");
                    } else {
                        break;
                    }
                }
                beginDate = Utils.getDate("Enter begin date: ");
                while (true) {
                    endDate = Utils.getDate("Enter end date: ");
                    if (isEndDateValid(beginDate, endDate)) {
                        break;
                    } else {
                        System.out.println("End date cannot be before begin date. Please enter again.");
                    }
                }
                tutionFee = Utils.getDouble("Enter tution fee: ", 0, 1000000000);
                sizeCourse = Utils.getInt("Enter size member of course: ", 1, 10000);
                active = Utils.confirmYesNo("Do you want to active this course now? (Y/N)");

                this.add(new Course(courseID, name, type, title, topic, beginDate, endDate, tutionFee, active, sizeCourse));
                check = true;

            } catch (Exception e) {
                System.out.println("Add failed");
            }
            continuteAdd = Utils.confirmYesNo("Do you want to add more?(Y/N)");
        }
        return check;
    }

    public static boolean isEndDateValid(LocalDate beginDate, LocalDate endDate) {
        return !endDate.isBefore(beginDate);
    }

    private boolean isCodeUnique(String code) {
        for (Course ram : this) {
            if (ram.getCourseID().equals(code)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveToFile(String fileName) {
        try (FileOutputStream file = new FileOutputStream(fileName);
                ObjectOutputStream os = new ObjectOutputStream(file)) {

            if (this.isEmpty()) {
                System.out.println("No data to save.");
                return false;
            }

            for (Course course : this) {
                os.writeObject(course);
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
                    Course course = (Course) objectInputStream.readObject();
                    this.add(course);
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
        List<Course> courseList = this;
        if (!courseList.isEmpty()) {
            this.clear();
            this.addAll(courseList);
            System.out.println("Data loaded successfully from " + fileName);
        } else {
            System.out.println("No data found in the file.");
        }
    }

    public void searchByTopicCode() {
        String topicCode = Utils.getString("Enter topic code to search for courses: ");

        boolean found = false;
        System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s %-20s %-15s\n",
                "CourseID", "Name", "Type", "Title", "Begin Date", "End Date", "Topic", "Fee", "Active");
        System.out.println("------------------------------------------------------------------------------------------------------");

        for (Course course : this) {
            if (course.getTopic().equalsIgnoreCase(topicCode)) {
                System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s %-20.2f %-15s\n",
                        course.getCourseID(),
                        course.getName(),
                        course.getType(),
                        course.getTitle(),
                        course.getBeginDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        course.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        course.getTopic(),
                        course.getTutionFee(),
                        course.isActive() ? "Yes" : "No");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No courses found with the topic code: " + topicCode);
        }
    }

    public void searchByCourseName() {
        String courseName = Utils.getString("Enter course name or keyword to search: ").toLowerCase();

        boolean found = false;
        System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s %-20s %-15s\n",
                "CourseID", "Name", "Type", "Title", "Begin Date", "End Date", "Topic", "Fee", "Active");
        System.out.println("------------------------------------------------------------------------------------------------------");

        for (Course course : this) {
            if (course.getName().toLowerCase().contains(courseName)) {
                System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s %-20.2f %-15s\n",
                        course.getCourseID(),
                        course.getName(),
                        course.getType(),
                        course.getTitle(),
                        course.getBeginDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        course.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        course.getTopic(),
                        course.getTutionFee(),
                        course.isActive() ? "Yes" : "No");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No courses found containing the name: " + courseName);
        }
    }

}
