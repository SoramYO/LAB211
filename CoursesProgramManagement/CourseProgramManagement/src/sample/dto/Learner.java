/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import java.io.Serializable;
import java.time.LocalDate;



/**
 *
 * @author Thu Hien
 */
public class Learner implements Serializable{
    private String learnerID, name; 
    private LocalDate dateofBirth;
 
    private double score;

    public Learner(String learnerID, String name, LocalDate dateofBirth, double score) {
        this.learnerID = learnerID;
        this.name = name;
        this.dateofBirth = dateofBirth;
        this.score = score;
    }

    public Learner(double score) {
        this.score = score;
    }
    
     @Override
    public boolean equals(Object obj) {
        Learner l = (Learner) obj;
        return this.learnerID.equalsIgnoreCase(l.getLearnerID());
    }

    public String getLearnerID() {
        return learnerID;
    }

    public void setLearnerID(String learnerID) {
        this.learnerID = learnerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(LocalDate dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return learnerID +"_"+name+"_"+dateofBirth +"_"+score;
    }
}
