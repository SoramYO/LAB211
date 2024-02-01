
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InventoryManagementSystem {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static List<Product> products = new ArrayList<>();
    private static List<ImportExportSlip> importSlips = new ArrayList<>();
    private static List<ImportExportSlip> exportSlips = new ArrayList<>();
    private static Warehouse warehouse = new Warehouse();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println("Inventory Management System:");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Warehouse");
            System.out.println("3. Report");
            System.out.println("4. Store Data to Files");
            System.out.println("5. Load data");
            System.out.println("6. Close the Application");
            System.out.print("Enter your choice: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (mainChoice) {
                case 1:
                    manageProductsMenu(scanner);
                    break;
                case 2:
                    manageWarehouseMenu(scanner);
                    break;
                case 3:
                    generateReportsMenu(scanner);
                    break;
                case 4:
                    saveDataToFile();
                    break;
                case 5:
                    loadDataFromFile();
                    break;
                case 6:
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void manageProductsMenu(Scanner scanner) {
        boolean continueManagingProducts = true;
        while (continueManagingProducts) {
            System.out.println("_______Manage Products_______");
            System.out.println("1. Add a Product");
            System.out.println("2. Update Product Information");
            System.out.println("3. Delete Product");
            System.out.println("4. Show All Products");
            System.out.println("5. Back to Main Menu");
            System.out.println("_____________________________");
            System.out.print("Enter your choice: ");
            int productsChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (productsChoice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    updateProduct(scanner);
                    break;
                case 3:
                    deleteProduct(scanner);
                    break;
                case 4:
                    displayProducts();
                    break;
                case 5:
                    continueManagingProducts = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Enter product code: ");
        String productCode = scanner.nextLine();
        if (isProductCodeDuplicate(productCode)) {
            System.out.println("Product code already exists.");
            return;
        }
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter product type (1 for Daily, 2 for Long Lasting): ");
        int productTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product;
        if (productTypeChoice == 1) {
            product = new DailyProduct(productCode, productName);
        } else if (productTypeChoice == 2) {
            product = new LongLastingProduct(productCode, productName);
        } else {
            System.out.println("Invalid product type choice.");
            return;
        }

        products.add(product);
        System.out.println("Product added successfully.");
    }

    private static boolean isProductCodeDuplicate(String productCode) {
        for (Product product : products) {
            if (product.getProductCode().equals(productCode)) {
                return true;
            }
        }
        return false;
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter product code to update: ");
        String productCode = scanner.nextLine();
        Product foundProduct = getProductByCode(productCode);
        if (foundProduct == null) {
            System.out.println("Product does not exist.");
            return;
        }

        System.out.println("Current Product Information:");
        foundProduct.displayInfo();

        System.out.println("Enter new product information (leave blank to keep current value):");
        System.out.print("Enter product name: ");
        String newProductName = scanner.nextLine();
        if (!newProductName.isEmpty()) {
            foundProduct.setProductName(newProductName);
        }

        System.out.print("Enter product type (DailyProduct/LongLastingProduct): ");
        String newProductType = scanner.nextLine();
        if (!newProductType.isEmpty()) {
            foundProduct.setProductType(newProductType);
        }

        System.out.println("Product information updated successfully.");
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Enter product code to delete: ");
        String productCode = scanner.nextLine();
        Product foundProduct = getProductByCode(productCode);
        if (foundProduct == null) {
            System.out.println("Product does not exist.");
            return;
        }

        // Remove the product from the products list
        products.remove(foundProduct);
        System.out.println("Product deleted successfully.");
    }

    private static void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.println("List of Products:");
            for (Product product : products) {
                product.displayInfo();
            }

        }
    }

    private static void manageWarehouseMenu(Scanner scanner) {
        boolean continueManagingWarehouse = true;
        while (continueManagingWarehouse) {
            System.out.println("_____Manage Warehouse_____");
            System.out.println("1. Import Products");
            System.out.println("2. Export Products");
            System.out.println("3. Show Warehouse Status");
            System.out.println("4. Back to Main Menu");
            System.out.println("_________________________");
            System.out.print("Enter your choice: ");
            int warehouseChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (warehouseChoice) {
                case 1:
                    importProducts(scanner);
                    break;
                case 2:
                    exportProducts(scanner);
                    break;
                case 3:
                    displayWarehouseStatus();
                    break;
                case 4:
                    continueManagingWarehouse = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void importProducts(Scanner scanner) {
        System.out.print("Enter product code to import: ");
        String productCode = scanner.nextLine();
        Product foundProduct = getProductByCode(productCode);
        if (foundProduct == null) {
            System.out.println("Product does not exist.");
            return;
        }

        System.out.print("Enter import code (7-digit number): ");
        String Slipcode = scanner.nextLine();
        if (!Slipcode.matches("\\d{7}")) {
            System.out.println("Invalid import code. It should be a 7-digit number.");
            return;
        }

        System.out.print("Enter import quantity: ");
        int importQuantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter import price per unit: ");
        double importPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter manufacturing date (yyyy-MM-dd): ");
        String manufacturingDateStr = scanner.nextLine();
        Date manufacturingDate = parseDate(manufacturingDateStr);

        System.out.print("Enter expiration date (yyyy-MM-dd): ");
        String expirationDateStr = scanner.nextLine();
        Date expirationDate = parseDate(expirationDateStr);

        // Update warehouse item
        WarehouseItem warehouseItem = warehouse.getWarehouseItem(productCode);
        if (warehouseItem == null) {
            warehouseItem = new WarehouseItem(productCode, importPrice, importQuantity, manufacturingDate, expirationDate);
            warehouse.addWarehouseItem(warehouseItem);
        } else {
            warehouseItem.setPrice(importPrice);
            warehouseItem.setQuantity(warehouseItem.getQuantity() + importQuantity);
            warehouseItem.setManufacturingDate(manufacturingDate);
            warehouseItem.setExpirationDate(expirationDate);
        }

        // Create import slip
        ImportExportSlip importSlip = new ImportExportSlip(productCode, Slipcode, importQuantity, importPrice, true);
        importSlips.add(importSlip);

        System.out.println("Product imported successfully.");
    }

    private static void exportProducts(Scanner scanner) {
        System.out.print("Enter product code to export: ");
        String productCode = scanner.nextLine();
        Product foundProduct = getProductByCode(productCode);
        if (foundProduct == null) {
            System.out.println("Product does not exist.");
            return;
        }

        System.out.print("Enter export code (7-digit number): ");
        String Slipcode = scanner.nextLine();
        if (!Slipcode.matches("\\d{7}")) {
            System.out.println("Invalid export code. It should be a 7-digit number.");
            return;
        }

        System.out.print("Enter export quantity: ");
        int exportQuantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter export price per unit: ");
        double exportPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter manufacturing date (yyyy-MM-dd): ");
        String manufacturingDateStr = scanner.nextLine();
        Date manufacturingDate = parseDate(manufacturingDateStr);

        System.out.print("Enter expiration date (yyyy-MM-dd): ");
        String expirationDateStr = scanner.nextLine();
        Date expirationDate = parseDate(expirationDateStr);

        // Update warehouse item
        WarehouseItem warehouseItem = warehouse.getWarehouseItem(productCode);
        if (warehouseItem == null) {
            warehouseItem = new WarehouseItem(productCode, exportPrice, exportQuantity, manufacturingDate, expirationDate);
            warehouse.addWarehouseItem(warehouseItem);
        } else {
            warehouseItem.setPrice(exportPrice);
            warehouseItem.setQuantity(warehouseItem.getQuantity() - exportQuantity);
            warehouseItem.setManufacturingDate(manufacturingDate);
            warehouseItem.setExpirationDate(expirationDate);
        }

        // Create export slip
        ImportExportSlip exportSlip = new ImportExportSlip(productCode, Slipcode, exportQuantity, exportPrice, true);
        exportSlips.add(exportSlip);

        System.out.println("Product exported successfully.");
    }

    private static void displayWarehouseStatus() {
        List<WarehouseItem> sortedItems = warehouse.getWarehouseItems().stream()
                .sorted(Comparator.comparing(WarehouseItem::getProductCode))
                .collect(Collectors.toList());

        System.out.println("Warehouse Status:");
        for (WarehouseItem item : sortedItems) {
            Product product = getProductByCode(item.getProductCode());
            System.out.println("Product Code: " + item.getProductCode());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Import Price: " + item.getPrice());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Manufacturing Date: " + dateFormat.format(item.getManufacturingDate()));
            System.out.println("Expiration Date: " + dateFormat.format(item.getExpirationDate()));
            System.out.println("_________________________");
        }
    }

    private static Product getProductByCode(String productCode) {
        for (Product product : products) {
            if (product.getProductCode().equals(productCode)) {
                return product;
            }
        }
        return null;
    }

    private static Date parseDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return null;
        }
    }

    private static void generateReportsMenu(Scanner scanner) {
        boolean continueGeneratingReports = true;
        while (continueGeneratingReports) {
            System.out.println("_________Generate Reports_________");
            System.out.println("1. Products that have expired");
            System.out.println("2. The products that the store is selling");
            System.out.println("3. Products that are running out of stock");
            System.out.println("4. List Import Slips");
            System.out.println("5. List Export Slips");
            System.out.println("6. Back to Main Menu");
            System.out.println("__________________________________");
            System.out.print("Enter your choice: ");
            int reportsChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (reportsChoice) {
                case 1:
                    displayExpiredProducts();
                    break;
                case 2:
                    displaySellingProducts();
                    break;
                case 3:
                    displayRunningOutProducts();
                    break;
                case 4:
                    listImportSlips();
                    break;
                case 5:
                    listExportSlips();
                    break;
                case 6:
                    continueGeneratingReports = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void listImportSlips() {
        if (importSlips.isEmpty()) {
            System.out.println("No import slips found.");
        } else {
            System.out.println("List of Import Slips:");
            for (ImportExportSlip slip : importSlips) {
                slip.displaySlipInfo();
            }
        }
    }

    private static void listExportSlips() {
        if (exportSlips.isEmpty()) {
            System.out.println("No export slips found.");
        } else {
            System.out.println("List of Export Slips:");
            for (ImportExportSlip slip : exportSlips) {
                slip.displaySlipInfo();
            }
        }
    }

    private static void saveDataToFile() {
        try {
            ObjectOutputStream productsOut = new ObjectOutputStream(new FileOutputStream("src/output/products.dat"));
            productsOut.writeObject(products);

            ObjectOutputStream importSlipsOut = new ObjectOutputStream(new FileOutputStream("src/output/importSlips.dat"));
            importSlipsOut.writeObject(importSlips);


            ObjectOutputStream exportSlipsOut = new ObjectOutputStream(new FileOutputStream("src/output/exportSlips.dat"));
            exportSlipsOut.writeObject(exportSlips);
            System.out.println("Data stored to files successfully.");
        } catch (IOException e) {
            System.out.println("Error while storing data to files.");
        }
    }

    private static void loadDataFromFile() {
        try {
            // Load products from file
            ObjectInputStream productsIn = new ObjectInputStream(new FileInputStream("src/output/products.dat"));
            products = (List<Product>) productsIn.readObject();
            productsIn.close();

            // Load import slips from file
            ObjectInputStream importSlipsIn = new ObjectInputStream(new FileInputStream("src/output/importSlips.dat"));
            importSlips = (List<ImportExportSlip>) importSlipsIn.readObject();
            importSlipsIn.close();

            // Load export slips from file
            ObjectInputStream exportSlipsIn = new ObjectInputStream(new FileInputStream("src/output/exportSlips.dat"));
            exportSlips = (List<ImportExportSlip>) exportSlipsIn.readObject();
            exportSlipsIn.close();

            System.out.println("Data loaded from files successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading data from files.");
        }
    }

    private static void displayRunningOutProducts() {
        System.out.println("Products Running Out:");
        for (Product product : products) {
            WarehouseItem warehouseItem = warehouse.getWarehouseItem(product.getProductCode());
            if (warehouseItem != null && warehouseItem.getQuantity() <= 10) {
                product.displayInfo();
                System.out.println("Quantity in Warehouse: " + warehouseItem.getQuantity());
                System.out.println("_________________________");
            }
        }
    }

    private static void displaySellingProducts() {
        System.out.println("Selling Products:");
        for (Product product : products) {
            WarehouseItem warehouseItem = warehouse.getWarehouseItem(product.getProductCode());
            if (warehouseItem != null && warehouseItem.getQuantity() > 10) {
                product.displayInfo();
                System.out.println("Quantity in Warehouse: " + warehouseItem.getQuantity());
                System.out.println("_________________________");
            }
        }
    }

    private static void displayExpiredProducts() {
        System.out.println("Expired Products:");
        Date currentDate = new Date();
        for (WarehouseItem warehouseItem : warehouse.getWarehouseItems()) {
            if (warehouseItem.getExpirationDate() != null && currentDate.after(warehouseItem.getExpirationDate())) {
                Product product = getProductByCode(warehouseItem.getProductCode());
                if (product != null) {
                    product.displayInfo();
                    System.out.println("Expiration Date: " + dateFormat.format(warehouseItem.getExpirationDate()));
                    System.out.println("_________________________");
                }
            }
        }
    }
}
