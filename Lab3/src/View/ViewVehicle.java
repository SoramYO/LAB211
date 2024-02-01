/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Vehicle;
import Model.VehicleManager;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Son
 */
public class ViewVehicle {

    VehicleManager manager = new VehicleManager();
    Scanner scanner = new Scanner(System.in);

    public void addVehicle() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Color: ");
        String color = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter Type: ");
        String type = scanner.nextLine();
        System.out.print("Enter Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        Vehicle newVehicle = new Vehicle(id, name, color, price, brand, type, year);
        manager.addVehicle(newVehicle);
        System.out.println("Vehicle added.");
    }

    public void checkExitVehicle() {
        System.out.print("Enter Vehicle ID to check: ");
        int checkID = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        boolean exists = manager.checkVehicleExists(checkID);
        if (exists) {
            System.out.println("Vehicle exists.");
        } else {
            System.out.println("Vehicle does not exist.");
        }
    }

    public void updateVehicle() {
        System.out.print("Enter Vehicle ID to update: ");
        int updateID = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        if (manager.checkVehicleExists(updateID)) {
            System.out.print("Enter new ID: ");
            int newID = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter new Name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new Color: ");
            String newColor = scanner.nextLine();
            System.out.print("Enter new Price: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter new Brand: ");
            String newBrand = scanner.nextLine();
            System.out.print("Enter new Type: ");
            String newType = scanner.nextLine();
            System.out.print("Enter new Year: ");
            int newYear = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            Vehicle updatedVehicle = new Vehicle(newID, newName, newColor, newPrice, newBrand, newType, newYear);
            manager.updateVehicle(updateID, updatedVehicle);
            System.out.println("Vehicle updated.");
        } else {
            System.out.println("Vehicle not found for update.");
        }
    }

    public void deleteVehicle() {
        manager.displayAllVehicles();
        System.out.print("Enter Vehicle ID to delete: ");
        int deleteID = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (manager.checkVehicleExists(deleteID)) {
            System.out.println("Are you sure to delete (Y/N");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("y")) {
                manager.deleteVehicle(deleteID);

                System.out.println("Vehicle deleted.");
            } else {
                return;
            }
        } else {
            System.out.println("Vehicle not found for deletion.");
        }

    }

    public void searchVehicle() {
        System.out.println("Search vehicle by:");
        System.out.println("1. ID_Vehicle");
        System.out.println("2. Name_Vehicle");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (searchChoice) {
            case 1:
                System.out.print("Enter ID_Vehicle to search: ");
                int searchID = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                Vehicle foundByID = manager.searchVehicleByID(searchID);
                if (foundByID != null) {
                    System.out.println("Vehicle found:");
                    System.out.println(foundByID);
                } else {
                    System.out.println("Vehicle not found.");
                }
                break;
            case 2:
                System.out.print("Enter Name_Vehicle to search: ");
                String searchName = scanner.nextLine();
                List<Vehicle> foundByName = manager.searchVehicleByName(searchName);
                if (!foundByName.isEmpty()) {
                    System.out.println("Vehicles found:");
                    for (Vehicle vehicle : foundByName) {
                        System.out.println(vehicle);
                    }
                } else {
                    System.out.println("Vehicles not found.");
                }
                break;
            default:
                System.out.println("Invalid search choice.");
        }
    }

    public void displayListVehicle() {
        manager.displayAllVehicles();
    }

    public void saveToFile() {
        System.out.println("The data will be overwrite(Y/N)");
        String check = scanner.nextLine();
        if (check.equalsIgnoreCase("y")) {
            manager.saveVehiclesToFile();
        } else {
            return;
        }

    }

    public void printFromFile() {
        manager.printVehiclesFromFile();
    }
}
