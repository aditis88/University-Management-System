package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvToDatabase {

    public static String removeSpaces(String text)
    {
        Pattern pattern = Pattern.compile("(?m)^\\s*(.*?)\\s*$");
        Matcher matcher = pattern.matcher(text);
        String extractedString = null;
        if (matcher.find()) {
            extractedString = matcher.group(1);
        }
        return extractedString;
    }
    public static boolean fillUserTable()
    {
        String csvFile = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/user_data.csv";

        String insertQuery = "insert into user_table (user_id, role,contact, password) values (?,?, ?, ?)";
        GetConnection gtCon = new GetConnection();
        try (Connection connection = gtCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            String line;

            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                preparedStatement.setString(1, removeSpaces(data[4]));
                preparedStatement.setString(2, removeSpaces(data[2]));
                preparedStatement.setString(3, removeSpaces(data[5]));
                preparedStatement.setString(4, removeSpaces(data[6]));

//                preparedStatement.executeUpdate();
            }
            connection.close();
            System.out.println("Data inserted successfully into the database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean fillDepartmentTable()
    {
        String csvFile = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/department.csv";

        String insertQuery = "insert into department (dept_name,dept_id,building) values (?,?,?)";
        GetConnection gtCon = new GetConnection();
        try (Connection connection = gtCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                preparedStatement.setString(1, removeSpaces(data[0]));
                preparedStatement.setString(2, removeSpaces(data[1]));
                preparedStatement.setString(3, removeSpaces(data[2]));


//                preparedStatement.executeUpdate();
            }
            connection.close();
            System.out.println("Data inserted successfully into the database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean fillInstructorTable()
    {
        String csvFile = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/instructor.csv";

        String insertQuery = "insert into instructor (instructor_id,instructor_name,dept_id,salary) values (?,?,?,?)";
        GetConnection gtCon = new GetConnection();
        try (Connection connection = gtCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                preparedStatement.setString(1, removeSpaces(data[0]));
                preparedStatement.setString(2, removeSpaces(data[1]));
                preparedStatement.setString(3, removeSpaces(data[2]));
                preparedStatement.setFloat(4, Float.parseFloat(removeSpaces(data[3])));


//                preparedStatement.executeUpdate();
            }
            connection.close();
            System.out.println("Data inserted successfully into the database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean fillCourseCatalogueTable()
    {
        String csvFile = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/course_catalogue.csv";


        String insertQueryCat = "insert into course_catalogue (course_id, title,dept_id, req_sem,l,t,p,credits,course_type,batch) values (?,?,?,?,?,?,?,?,?,?)";
        GetConnection gtCon = new GetConnection();
        try (Connection connection = gtCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQueryCat);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
//                System.out.println(Arrays.toString(data));
                preparedStatement.setString(1, removeSpaces(data[0]));
                preparedStatement.setString(2, removeSpaces(data[1]));
                preparedStatement.setString(3, removeSpaces(data[2]));
                preparedStatement.setInt(4, Integer.parseInt(removeSpaces(data[3])));
                preparedStatement.setFloat(5, Float.parseFloat(removeSpaces(data[4])));
                preparedStatement.setFloat(6, Float.parseFloat(removeSpaces(data[5])));
                preparedStatement.setFloat(7, Float.parseFloat(removeSpaces(data[6])));
                preparedStatement.setFloat(8, Float.parseFloat(removeSpaces(data[7])));
                preparedStatement.setString(9, removeSpaces(data[8]));
                preparedStatement.setInt(10,Integer.parseInt(removeSpaces(data[9])));


//                preparedStatement.execute();
            }
            connection.close();
            System.out.println("Data inserted successfully into the database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean fillCourseTable()
    {
        String csvFile = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/course.csv";


        String insertQueryCat = "insert into course (course_id, title,dept_id, req_sem,l,t,p,credits) values (?,?,?,?,?,?,?,?)";
        GetConnection gtCon = new GetConnection();
        try (Connection connection = gtCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQueryCat);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
//                System.out.println(Arrays.toString(data));
                preparedStatement.setString(1, removeSpaces(data[0]));
                preparedStatement.setString(2, removeSpaces(data[1]));
                preparedStatement.setString(3, removeSpaces(data[2]));
                preparedStatement.setInt(4, Integer.parseInt(removeSpaces(data[3])));
                preparedStatement.setFloat(5, Float.parseFloat(removeSpaces(data[4])));
                preparedStatement.setFloat(6, Float.parseFloat(removeSpaces(data[5])));
                preparedStatement.setFloat(7, Float.parseFloat(removeSpaces(data[6])));
                preparedStatement.setFloat(8, Float.parseFloat(removeSpaces(data[7])));


//                preparedStatement.execute();
            }
            connection.close();
            System.out.println("Data inserted successfully into the database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    static boolean fillPrereqTable()
    {
        String csvFile = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/prereq.csv";

        String insertQuery = "insert into prereq (course_id,prereq_id) values (?,?)";
        GetConnection gtCon = new GetConnection();
        try (Connection connection = gtCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            String line;
            String course_id,prerq_id;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                course_id =  removeSpaces(data[0]).toUpperCase();
                prerq_id =  removeSpaces(data[1]).toUpperCase();
                preparedStatement.setString(1,course_id);
                preparedStatement.setString(2,prerq_id);
//                preparedStatement.execute();
            }
            connection.close();
            preparedStatement.close();
            System.out.println("Data inserted successfully into the database.");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    public static boolean fillStudentTable()
    {
        String csvFile = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/student.csv";

        String insertQuery = "insert into student (student_id, student_name,dept_id, current_sem,contact) values (?,?,?,?,?)";
        GetConnection gtCon = new GetConnection();
        try (Connection connection = gtCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                preparedStatement.setString(1, removeSpaces(data[4]));
                preparedStatement.setString(2, removeSpaces(data[0]));
                preparedStatement.setString(3, removeSpaces(data[3]));
                preparedStatement.setInt(4, Integer.parseInt(removeSpaces(data[5])));
//                preparedStatement.setFloat(5, Float.parseFloat(removeSpaces(data[6])));
                preparedStatement.setString(5, removeSpaces(data[7]));


//                preparedStatement.executeUpdate();
            }
            connection.close();
            System.out.println("Data inserted successfully into the database.");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    static boolean fillEnrolledCredits()
//    {
//
//    }

    public static void main(String[] args) {

        fillUserTable();
        fillDepartmentTable();
        fillCourseCatalogueTable();
        fillCourseTable();
        fillInstructorTable();
        fillStudentTable();
        fillPrereqTable();


    }


}
