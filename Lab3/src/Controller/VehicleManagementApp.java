package Controller;

import View.ViewVehicle;
import Model.VehicleManager;
import java.util.List;
import java.util.Scanner;

public class VehicleManagementApp {

    public static void main(String[] args) {
        ViewVehicle vehicleControll = new ViewVehicle();
        VehicleManager manager = new VehicleManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Vehicle Management Menu:");
            System.out.println("1. Add new vehicle");
            System.out.println("2. Check exists vehicle");
            System.out.println("3. Update vehicle");
            System.out.println("4. Delete vehicle");
            System.out.println("5. Search vehicle");
            System.out.println("6. Display all vehicles");
            System.out.println("7. Save all vehicles to file");
            System.out.println("8. Print all vehicles from file");
            System.out.println("9. Quit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    vehicleControll.addVehicle();
                    break;
                case 2:
                    vehicleControll.checkExitVehicle();
                    break;
                case 3:
                    vehicleControll.updateVehicle();
                    break;
                case 4:
                    vehicleControll.deleteVehicle();
                    break;
                case 5:
                    vehicleControll.searchVehicle();
                    break;
                case 6:
                    vehicleControll.displayListVehicle();
                    break;
                case 7:
                    vehicleControll.saveToFile();
                    break;
                case 8:
                    vehicleControll.printFromFile();
                    break;
                case 9:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
