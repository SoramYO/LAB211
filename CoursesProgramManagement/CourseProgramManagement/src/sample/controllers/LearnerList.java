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
import java.time.LocalDate;
import sample.dto.I_List;
import java.util.ArrayList;
import java.util.List;
import sample.dto.Course;
import sample.dto.Learner;
import sample.dto.Topic;
import sample.utils.Utils;

/**
 *
 * @author Thu Hien
 */
public class LearnerList extends ArrayList<Learner> implements I_List {
    private CourseList courseList; 
    
    public LearnerList(CourseList courseList) {
        this.courseList = courseList;
    }
    @Override
    public int find(String code) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getLearnerID().equals(code)) {
                return i;
            }
        }
        return -1;
    }

     @Override
    public boolean add() {
        boolean check = false;
        String learnerID, name, courseID;
        LocalDate dateofBirth;
        double score;
        try {
            // Generate unique learner ID
            int y = 1;
            while (true) {
                learnerID = "L" + "_" + y;
                if (isCodeUnique(learnerID) == false) {
                    y++;
                } else {
                    break;
                }
            }

            name = Utils.getString("Enter Learner's name: ");
            dateofBirth = Utils.getDate("Enter date of birth: ");
            score = 0;
            courseList.show();
            // Get course ID and validate
            while (true) {
                courseID = Utils.getString("Enter Course ID to enroll the learner: ");
                Course selectedCourse = null;
                
                // Find the course
                for (Course course : courseList) {
                    if (course.getCourseID().equals(courseID)) {
                        selectedCourse = course;
                        break;
                    }
                }

                if (selectedCourse == null) {
                    System.out.println("Course not found! Please enter a valid Course ID.");
                    boolean retry = Utils.confirmYesNo("Do you want to try another Course ID? (Y/N)");
                    if (!retry) {
                        return false;
                    }
                    continue;
                }

                // Check if course is active
                if (!selectedCourse.isActive()) {
                    System.out.println("This course is not active!");
                    boolean retry = Utils.confirmYesNo("Do you want to try another Course ID? (Y/N)");
                    if (!retry) {
                        return false;
                    }
                    continue;
                }

                // Check course capacity
                if (selectedCourse.getLearner() != null && 
                    selectedCourse.getLearner().size() >= selectedCourse.getSize()) {
                    System.out.println("Course is full! Maximum capacity reached.");
                    boolean retry = Utils.confirmYesNo("Do you want to try another Course ID? (Y/N)");
                    if (!retry) {
                        return false;
                    }
                    continue;
                }

                // Create new learner
                Learner newLearner = new Learner(learnerID, name, dateofBirth, score);
                
                // Add learner to the main list
                this.add(newLearner);
                
                // Initialize learner list if null
                if (selectedCourse.getLearner() == null) {
                    selectedCourse.setLearner(new ArrayList<>());
                }
                
                // Add learner to course
                selectedCourse.getLearner().add(newLearner);
                
                System.out.println("Learner successfully added and enrolled in course: " + selectedCourse.getName());
                check = true;
                break;
            }

        } catch (Exception e) {
            System.out.println("Add failed: " + e.getMessage());
        }

        return check;
    }

    private boolean isCodeUnique(String code) {
        for (Learner ram : this) {
            if (ram.getLearnerID().equals(code)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update() {
        String learnerID = Utils.getString("Enter Learner's ID to update score: ");
        int index = find(learnerID);
        if (index == -1) {
            System.out.println("Learner not found!");
        } else {
            Learner learnerUd = this.get(index);
            System.out.println("Current Learner's Score:");
            System.out.println(learnerUd.getScore());

            double newScore = Utils.getDouble("Enter new score (0 to keep current): ", 0, 10.0, learnerUd.getScore());
            if (newScore > 0) {
                learnerUd.setScore(newScore);
            }

            System.out.println("Updated information:");
            System.out.println("Điểm mới của" + learnerUd.getName() + "là" + learnerUd.getScore());
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
        System.out.println("No learners available to display.");
        return;
    }

    System.out.printf("%-10s %-20s %-15s %-10s %-10s %-10s\n", "LearnerID", "Name", "Date of Birth", "GPA", "Status", "Total Courses");
    System.out.println("-----------------------------------------------------------------------------------------");

    for (Learner learner : this) {

        double totalScore = 0;
        int courseCount = 0;
        
        for (Course course : courseList) {
            if (course.getLearner() != null && course.getLearner().contains(learner)) {
                totalScore += learner.getScore(); 
                courseCount++;
            }
        }


        double gpa = (courseCount > 0) ? totalScore / courseCount : 0;
        String status = gpa >= 5 ? "Pass" : "Fail"; 

        System.out.printf("%-10s %-20s %-15s %-10.2f %-10s %-10d\n",
                learner.getLearnerID(),
                learner.getName(),
                learner.getDateofBirth(),
                gpa,
                status,
                courseCount  
        );
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

            for (Learner learner : this) {
                os.writeObject(learner);
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
                    Learner learner = (Learner) objectInputStream.readObject();
                    this.add(learner);
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
        List<Learner> learnerList = this;
        if (!learnerList.isEmpty()) {
            this.clear();
            this.addAll(learnerList);
            System.out.println("Data loaded successfully from " + fileName);
        } else {
            System.out.println("No data found in the file.");
        }
    }

}
