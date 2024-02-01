/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Son
 */
public class VehicleManager {

    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Vehicle> vehicleFile = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public boolean checkVehicleExists(int vehicleID) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getID_Vehicle() == vehicleID) {
                return true;
            }
        }
        return false;
    }

    public void updateVehicle(int vehicleID, Vehicle newVehicle) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getID_Vehicle() == vehicleID) {
                vehicles.set(i, newVehicle);
                break;
            }
        }
    }

    public void deleteVehicle(int vehicleID) {
        vehicles.removeIf(vehicle -> vehicle.getID_Vehicle() == vehicleID);
    }

    public Vehicle searchVehicleByID(int vehicleID) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getID_Vehicle() == vehicleID) {
                return vehicle;
            }
        }
        return null;
    }

    public List<Vehicle> searchVehicleByName(String name) {
        List<Vehicle> foundVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getName_Vehicle().equalsIgnoreCase(name)) {
                foundVehicles.add(vehicle);
            }
        }
        return foundVehicles;
    }

    public void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("\n List vehicle is empty\n");
        } else {
            System.out.println("List vehicle:");
            System.out.println("+---------------+----------------------+----------------------+----------------------+----------------------+------------------+------------------+");
            System.out.printf("| %-13s | %-20s | %-20s | %-20s | %-20s | %-16s | %-16s |%n",
                    "ID_Vehicle", "Name_Vehicle", "Color_Vehicle", "Price_Vehicle", "Brand_Vehicle", "Type", "ProductYear");

            for (Vehicle vehicle : vehicles) {
                System.out.println("+---------------+----------------------+----------------------+----------------------+----------------------+------------------+------------------+");
                System.out.printf("| %-13d | %-20s | %-20s | %-20f | %-20s | %-16s | %-16d |%n",
                        vehicle.getID_Vehicle(), vehicle.getName_Vehicle(), vehicle.getColor_Vehicle(),
                        vehicle.getPrice_Vehicle(), vehicle.getBrand_Vehicle(), vehicle.getType(), vehicle.getProductYear());
            }
            System.out.println("+---------------+----------------------+----------------------+----------------------+----------------------+------------------+------------------+");
        }
    }

    public void saveVehiclesToFile() {
        try (FileWriter vehicleTxt = new FileWriter("src/View/vehicle.txt")) {
            for (Vehicle vehicle : vehicles) {
                vehicleTxt.write(vehicle.toString() + "\n");
            }
            System.out.println("Dữ liệu đã được lưu vào các tệp txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printVehiclesFromFile() {
        try {
            FileReader fileReader = new FileReader("src/View/vehicle.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 7) {
                    int ID_Vehicle = Integer.parseInt(parts[0].trim());
                    String Name_Vehicle = parts[1].trim();
                    String Color_Vehicle = parts[2].trim();
                    double Price_Vehicle = Double.parseDouble(parts[3].trim());
                    String Brand_Vehicle = parts[4].trim();
                    String Type = parts[5].trim();
                    int ProductYear = Integer.parseInt(parts[6].trim());

                    Vehicle vehicleList = new Vehicle(ID_Vehicle, Name_Vehicle, Color_Vehicle, Price_Vehicle, Brand_Vehicle, Type, ProductYear);
                    boolean exists = false;
                    for (Vehicle vehicle : vehicleFile) {
                        if (vehicle.getID_Vehicle() == ID_Vehicle) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        vehicleFile.add(vehicleList);
                    }
                }
            }

            bufferedReader.close();

            if (vehicleFile.isEmpty()) {
                System.out.println("\nList vehicle is empty\n");
            } else {
                System.out.println("List vehicle:");
                System.out.println("+---------------+----------------------+----------------------+----------------------+----------------------+------------------+------------------+");
                System.out.printf("| %-13s | %-20s | %-20s | %-20s | %-20s | %-16s | %-16s |%n",
                        "ID_Vehicle", "Name_Vehicle", "Color_Vehicle", "Price_Vehicle", "Brand_Vehicle", "Type", "ProductYear");

                for (Vehicle vehicle : vehicleFile) {
                    System.out.println("+---------------+----------------------+----------------------+----------------------+----------------------+------------------+------------------+");
                    System.out.printf("| %-13d | %-20s | %-20s | %-20f | %-20s | %-16s | %-16d |%n",
                            vehicle.getID_Vehicle(), vehicle.getName_Vehicle(), vehicle.getColor_Vehicle(),
                            vehicle.getPrice_Vehicle(), vehicle.getBrand_Vehicle(), vehicle.getType(), vehicle.getProductYear());
                }
                System.out.println("+---------------+----------------------+----------------------+----------------------+----------------------+------------------+------------------+");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
