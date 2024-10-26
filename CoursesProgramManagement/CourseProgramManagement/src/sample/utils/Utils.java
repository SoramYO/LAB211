/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author hd
 */
public class Utils {

    public static String getString(String welcome) {
        boolean check = true;
        String result = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println("Input text!!!");
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getString(String welcome, String oldData) {
        String result = oldData;
        Scanner sc = new Scanner(System.in);
        System.out.print(welcome);
        String tmp = sc.nextLine();
        if (!tmp.isEmpty()) {
            result = tmp;
        }
        return result;
    }

    public static int getInt(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                check = false;
            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number > max || number < min);
        return number;
    }

    public static int getInt(String welcome, int min, int max, int oldData) {
        boolean check = true;
        int number = oldData;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print(welcome);
                String tmp = sc.nextLine();
                if (tmp.isEmpty()) {
                    check = false;
                } else {
                    number = Integer.parseInt(tmp);
                    check = false;
                }
            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number > max || number < min);
        return number;
    }

    public static LocalDate getDate(String welcome) {
        LocalDate result = null;
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (result == null) {
            System.out.print(welcome);
            String input = sc.nextLine().trim();
            try {
                result = LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Enter valid date (dd/MM/yyyy)!!");
            }
        }

        return result;
    }

    public static LocalDate getDate(String welcome, LocalDate oldData) {
        LocalDate result = oldData;
        boolean check = true;
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tmp;
        while (check) {
            System.out.print(welcome);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                check = false;
            } else {
                try {
                    tmp = LocalDate.parse(input, formatter);
                    result = tmp;
                    check = false;
                } catch (DateTimeParseException e) {
                    System.out.println("Enter valid date (dd/MM/yyyy)!!");
                }
            }
        }
        return result;
    }

    public static String getType(String welcome, String type1, String type2) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);

        while (check) {
            System.out.print(welcome);
            result = sc.nextLine();

            if (result.equalsIgnoreCase(type1) || result.equalsIgnoreCase(type2)) {
                check = false;
            } else {
                System.out.println("Enter valid type: " + type1 + " or " + type2);
            }
        }

        return result;
    }

    public static String getType(String welcome, String type1, String type2, String oldData) {
        boolean check = true;
        String result = oldData;
        while (check) {

            Scanner sc = new Scanner(System.in);
            System.out.print(welcome);
            String tmp = sc.nextLine();
            if (tmp.isEmpty()) {
                check = false;
            } else {
                if (tmp.equalsIgnoreCase(type1) || tmp.equalsIgnoreCase(type2)) {
                    result = tmp;
                    check = false;
                } else {
                    System.out.println("Enter valid type :" + type1 + " or " + type2);
                }
            }
        }

        return result;

    }

    public static boolean confirmYesNo(String welcome) {
        boolean result = false;
        String confirm = Utils.getString(welcome);
        if ("Y".equalsIgnoreCase(confirm)) {
            result = true;
        }
        return result;
    }

    public static double getDouble(String welcome, double min, double max) {
        boolean check = true;
        double number = 0.0;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print(welcome);
                number = Double.parseDouble(sc.nextLine());
                check = false;
            } catch (Exception e) {
                System.out.println("Input real number!!!");
            }
        } while (check || number > max || number < min);
        return number;
    }

    public static double getDouble(String welcome, double min, double max, double oldData) {
        boolean check = true;
        double number = oldData;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print(welcome);
                String tmp = sc.nextLine();
                if (tmp.isEmpty()) {
                    check = false;
                } else {
                    number = Double.parseDouble(tmp);
                    check = false;
                }
            } catch (Exception e) {
                System.out.println("Input real number!!!");
            }
        } while (check || number > max || number < min);
        return number;
    }

    public static boolean confirmYesNo(String welcome, boolean defaultValue) {
        String defaultStr = defaultValue ? "(Y)" : "(N)";
        String confirm = Utils.getString(welcome + " " + defaultStr + ": ");

        if (confirm.isEmpty()) {
            return defaultValue;  // Trả về giá trị mặc định nếu không nhập gì
        } else if ("Y".equalsIgnoreCase(confirm)) {
            return true;
        } else if ("N".equalsIgnoreCase(confirm)) {
            return false;
        } else {
            System.out.println("Invalid input, please enter Y or N.");
            return confirmYesNo(welcome, defaultValue);  // Nhắc lại nếu nhập sai
        }
    }

}
