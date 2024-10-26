package sample.view;

import java.util.Scanner;
import sample.dto.I_List;
import sample.dto.I_Menu;
import sample.controllers.Menu;
import sample.controllers.CourseList;
import sample.controllers.LearnerList;
import sample.controllers.TopicList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hoa Doan
 */
public class CourseManagement {

    public static void main(String args[]) {
        I_Menu menu = new Menu();
        menu.addItem("1. Manage the Topics");
        menu.addItem("2. Manage the Course");
        menu.addItem("3. Manage the Learner");
        menu.addItem("4. Search information");
        menu.addItem("5. Save Topics, Courses and Learner to file");
        menu.addItem("6. Load From file");
        menu.addItem("7. Others- Quit");
        int choice;
        boolean cont = true;

        TopicList topicList = new TopicList();
        CourseList courselist = new CourseList(topicList);
        LearnerList learnerList = new LearnerList(courselist);
        topicList.loadFromFile("Topic.dat");
        courselist.loadFromFile("Course.dat");
        learnerList.loadFromFile("Learner.dat");
        while (cont) {
            menu.showMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    manageTheTopic((TopicList) topicList);
                    break;
                case 2:
                    manageTheCourse((CourseList) courselist);
                    break;
                case 3:
                    manageTheLearner((LearnerList) learnerList);
                    break;
                case 4:
                    searchInformation((TopicList) topicList, (CourseList) courselist, (LearnerList) learnerList);
                    break;
                case 5:
                    //save
                    topicList.saveToFile("Topic.dat");
                    courselist.saveToFile("Course.dat");
                    learnerList.saveToFile("Learner.dat");
                    break;
                case 6:
                    topicList.loadFromFile("Topic.dat");
                    courselist.loadFromFile("Course.dat");
                    learnerList.loadFromFile("Learner.dat");
                    break;
                case 7:
                    cont = menu.confirmYesNo("Do you want to quit?(Y/N)");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void manageTheTopic(TopicList to) {
        boolean continueManagingTopic = true;
        while (continueManagingTopic) {
            Scanner sc = new Scanner(System.in);
            System.out.println("1.1. Add Topics to catalog");
            System.out.println("1.2. Update Topic");
            System.out.println("1.3. Delete Topic");
            System.out.println("1.4. Display all Topics");
            System.out.println("1.5. Back");
            System.out.print("Select your search option: ");
            int searchOption = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (searchOption) {
                case 1:
                    to.add();
                    break;
                case 2:
                    to.update();
                    break;
                case 3:
                    to.delete();
                    break;
                case 4:
                    to.show();
                    break;
                case 5:
                    continueManagingTopic = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void manageTheCourse(CourseList co) {
        boolean continueManageTheCourse = true;
        while (continueManageTheCourse) {
            Scanner sc = new Scanner(System.in);
            System.out.println("2.1. Add Course");
            System.out.println("2.2. Update Course");
            System.out.println("2.3. Delete Course");
            System.out.println("2.4. Display Course information");
            System.out.println("2.5. Back");
            System.out.print("Select your search option: ");
            int searchOption = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (searchOption) {
                case 1:
                    co.add();
                    break;
                case 2:
                    co.update();
                    break;
                case 3:
                    co.delete();
                    break;
                case 4:
                    co.show();
                    break;
                case 5:
                    continueManageTheCourse = false;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void manageTheLearner(LearnerList le) {
        boolean continueManageTheLearner = true;
        while (continueManageTheLearner) {
            Scanner sc = new Scanner(System.in);
            System.out.println("3.1. Add Learner to Course");
            System.out.println("3.2. Enter scores for learners");
            System.out.println("3.3. Display Learner information");
            System.out.println("3.4. Back");
            System.out.print("Select your search option: ");
            int searchOption = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (searchOption) {
                case 1:
                    le.add();
                    break;
                case 2:
                    le.update();
                    break;
                case 3:
                    le.show();
                    break;
                case 4:
                    continueManageTheLearner = false;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void searchInformation(TopicList to, CourseList co, LearnerList le) {
        boolean continueSearchInformation = true;
        while (continueSearchInformation) {
            Scanner sc = new Scanner(System.in);
            System.out.println("4.1. Search Topic");
            System.out.println("4.2. Search Course");
            System.out.print("Select your search option: ");
            int searchOption = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (searchOption) {
                case 1:
                    to.searchTopicByName();
                    break;
                case 2:
                    System.out.println("4.2.1. By Topic");
                    System.out.println("4.2.2. By Name");
                    System.out.print("Select your search option: ");
                    int searchSubOption = sc.nextInt();
                    switch (searchSubOption) {
                        case 1:
                            co.searchByTopicCode();
                            break;
                        case 2:
                            co.searchByCourseName();
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            return;
                    }
                    break;
                case 3:
                    continueSearchInformation = false;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
