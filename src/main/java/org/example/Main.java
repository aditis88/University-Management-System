package org.example;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

//    public static boolean inputTaker()
//    {
//        System.out.print(":~$ ");
//        return true;
//    }

    public static boolean printMainMenu() {

        String title = "LOGIN ROLE";
        ArrayList<ArrayList<String>> arr = new ArrayList<>();

        ArrayList<String> row = new ArrayList<>();
        ArrayList<String> row1 = new ArrayList<>();
        ArrayList<String> row2 = new ArrayList<>();
        ArrayList<String> row3 = new ArrayList<>();

        row.add("Login as");
        row.add("role");
        arr.add(row);

        row1.add("Student");
        row1.add("student");

        arr.add(row1);

        row2.add("Instructor");
        row2.add("faculty");

        arr.add(row2);

        row3.add("Admin");
        row3.add("office");
        arr.add(row3);

        CLI.printMenu(title,arr);

        return true;

    }

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        String exitSymbol = "exit";
        String input;
        Authenticator authenticator = new Authenticator();
        String userId = "";
        String password = "";
        while (true) {
            System.out.println("For login Select Role...");
            printMainMenu();

//            System.out.print(":~$ ");
            CLI.inputTaker();
            input = scanner.nextLine();
            input = CLI.removeSpacesCLI(input);

            if (input.equals(exitSymbol)) {
                System.out.println("Exiting AIMS Portal...");
                break;
            }

            System.out.println("Enter UserName");
            CLI.inputTaker();

            userId = scanner.nextLine();
            userId = CLI.removeSpacesCLI(userId);
//            userId = "gunturi@iitrpr.ac.in";
//            userId = "2020csb1101@iitrpr.ac.in";
//            userId = "staffdeanoffice@iitrpr.ac.in";
            System.out.println(userId);


            System.out.println("Enter Password");
            CLI.inputTaker();
//                scanner.next();
            password = scanner.nextLine();
//            password = "*+r_4RJ:%qMHE.-K";-
            password = CLI.removeSpacesCLI(password);
//            password = "7(G])@&r";
//            password = "6AC(rWZk";

            System.out.println(password);

            boolean isCorrect = authenticator.authenticate(userId, input, password);

            if (input.equals("student")) {
                if (isCorrect) {
                    System.out.println("Student Logged in succesfully!");

                    try {
                        Student.runStudent(userId);
                    } catch (Exception err) {
                        System.out.println(err.getMessage());
                    }
                    }
            }
            else if (input.equals("faculty")) {
                if (isCorrect) {
                    System.out.println("Faculty Logged in succesfully!");

                    try {
                        Instructor.runFaculty(userId);

                    } catch (Exception err) {
                        System.out.println(err.getMessage());
                    }
                }
            }
            else if (input.equals("office")) {
                if (isCorrect) {
                    System.out.println("Officer Logged in succesfully!");

                    try {
                        AcademicOffice.runOffice(userId);
                    } catch (Exception err) {
                        System.out.println(err.getMessage());
                    }

                }
                else {
                    System.out.println("Wrong Login!");
                }
            } else {
                System.out.println("Invalid Role!");
            }


                    // Do something with the input
//            System.out.println("You entered: " + input);

                    CLI.clearScreen();
        }


    }
}