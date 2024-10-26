package sample.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Course implements Serializable{

    private String courseID, name, type, title, topic;
    private LocalDate beginDate, endDate;
    private double tutionFee;
    private boolean active;
    private List<Learner> learner;
    private int size;


    public Course(String courseID, String name, String type, String title, String topic, LocalDate beginDate, LocalDate endDate, double tutionFee, boolean active,int size) {
        this.courseID = courseID;
        this.name = name;
        this.type = type;
        this.title = title;
        this.topic = topic;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.tutionFee = tutionFee;
        this.active = active;
        this.size = size;
    }

    public Course(String courseID, String name, String type, String title, String topic, LocalDate beginDate, LocalDate endDate, double tutionFee, boolean active, List<Learner> learner, int size) {
        this.courseID = courseID;
        this.name = name;
        this.type = type;
        this.title = title;
        this.topic = topic;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.tutionFee = tutionFee;
        this.active = active;
        this.learner = learner;
        this.size = size;
    }


    @Override
    public boolean equals(Object obj) {
        Course c = (Course) obj;
        return this.courseID.equalsIgnoreCase(c.getCourseID());
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTutionFee() {
        return tutionFee;
    }

    public void setTutionFee(double tutionFee) {
        this.tutionFee = tutionFee;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Learner> getLearner() {
        return learner;
    }

    public void setLearner(List<Learner> learner) {
        this.learner = learner;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    @Override
    public String toString() {
        return courseID + "_" + name + "_" + type + "_" + title + "_" + beginDate + "_"
                + endDate + "_" + tutionFee + "_" + topic + "_" + active;
    }

}
