package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AcademicOfficeTest2 {

    AcademicOffice officer = new AcademicOffice("staffdeanoffice@iitrpr.ac.in");
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }



    @Test
    void isAddCourseActive() {
        String query = "";
        try{
            query = "update current_session set status = 8";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.execute();

            assertEquals(false,officer.isAddCourseActive());

            query = "update current_session set status = 7";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.execute();

            assertEquals(true,officer.isAddCourseActive());


        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

    }


    @Test
    void isGradesSubmissionEnded() {
        try{
            String query = "update current_session set status = 8";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.execute();

            assertEquals(false,officer.isGradesSubmissionEnded());

            query = "update current_session set status = 6";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.execute();

            assertEquals(true,officer.isGradesSubmissionEnded());


        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

    }

//    @Test
//    void addCourse() {
//        try{
//            String query = "update current_session set status = 8";
//            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.execute();
//
//            boolean ans = officer.addCourse("LAN101","LAN","CSE",1,3,3,3,3,"pc",2020);
//            assertEquals(false,ans);
//
//            query = "update current_session set status = 7";
//            preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.execute();
//
//            ans = officer.addCourse("LAN101","LAN","CSE",1,3,3,3,3,"pc",2020);
//            assertEquals(true,ans);
//
//            ans = officer.addCourse("CS305","software Engineering","CSE",6,3,3,3,3,"pc",2024);
//            assertEquals(true,ans);
//
//            query = "delete from course_catalogue where course_id = ?";
//            preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.setString(1,"LAN101");
//            preparedStatement.execute();
//
//            query = "delete from course where course_id = ?";
//            preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.setString(1,"LAN101");
//            preparedStatement.execute();
//
//            query = "delete from course_catalogue where course_id = ? and batch = ?";
//            preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.setString(1,"CS305");
//            preparedStatement.setInt(2,2024);
//            preparedStatement.execute();
//
//
//        }catch(SQLException err)
//        {
//            System.out.println(err.getMessage());
//        }
//    }




    @Test
    void logOut() throws SQLException {
        Connection con = officer.con;
        boolean ans = officer.logOut();
        assertEquals(ans,true);
        officer.con = con;
    }

    @Test
    void printMainMenu() {
        boolean ans = officer.printMainMenu();
        assertEquals(true,ans);
    }

    @Test
    void printActivityMenu() {
        boolean  ans = officer.printActivityMenu();
        assertEquals(true,ans);

    }

    @Test
    void printCourseTypeMenu() {
        boolean ans = officer.printCourseTypeMenu();
        assertEquals(true,ans);
    }

    @Test
    void printTranscriptMenu() {
        boolean ans = officer.printTranscriptMenu();
        assertEquals(true,ans);
    }

    @Test
    void runOffice1() {

        //change profile test
        String input = "1\nasdf\n1\nStaff\naddfa\n2\n999999999\nasdf\n3\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            officer.runOffice(officer.officerId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runOffice2() throws SQLException {

        //session  test
        String input = "2\n2022\n1\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            officer.runOffice(officer.officerId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            String query = "update current_session set current_year = ?";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setInt(1,2020);
            preparedStatement.execute();

            query = "drop table student_record_2022";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.execute();
        }

    }
    @Test
    void runOffice2_err() throws SQLException {

        //session  test
        String input = "2\nasdf\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            officer.runOffice(officer.officerId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            String query = "update current_session set current_year = ?";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setInt(1,2020);
            preparedStatement.execute();
        }

    }

    @Test
    void runOffice2_1() throws SQLException {

        //session  test
        String input = "2\n2022\n1\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            String query = "delete from current_session";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.execute();

            officer.runOffice(officer.officerId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            String query = "update current_session set current_year = ?";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setInt(1,2020);
            preparedStatement.execute();

            query = "drop table student_record_2022";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.execute();
        }

    }
    @Test
    void runOffice3() {

        //change profile test
        String input = "3\n8\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            officer.runOffice(officer.officerId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runOffice3_err() {

        //change profile test
        String input = "3\nasdf\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            officer.runOffice(officer.officerId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }

    @Test
    void runOffice4() {

        //change profile test
        String input = "4\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            officer.runOffice(officer.officerId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runOffice6() {

        //Generate Transcript
        String input = "6\nadsf\n1\n2020llb@llb\nasdf\n1\n2020csb9999@iitrpr.ac.in\nasdf\n2\n2020\nasdf\n3\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            officer.runOffice(officer.officerId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }

    @Test
    void runOffice7() {

        //generate transcript
        String input = "7\nadsf\n2020\n/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/promote.csv\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);
            String query = "update current_session set status = ?";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setInt(1,8);
            preparedStatement.execute();


            officer.runOffice(officer.officerId);
//        String path = "/home/ashish/hdd/study/java_proj/AIIMSPortal/src/main/java/org/example/seedData/promote.csv";
//        boolean ans = officer.promote(path,2020);
//
//        assertEquals(true,ans);
//
//        ans = officer.demote(path,2020);
//        assertEquals(true,ans);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }

    @Test
    void runOffice7_1() {

        //generate transcript
        String input = "7\nadsf\n2020\n/home/ashish/hdd/study/java_proj/AIIMSPortal/src/main/java/org/example/seedData/promote.csv\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);
            String query = "update current_session set status = ?";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setInt(1,6);
            preparedStatement.execute();


            officer.runOffice(officer.officerId);

            String path = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/demote.csv";
            query = "update current_session set status = ?";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setInt(1,8);
            preparedStatement.execute();

            boolean ans = officer.demote(path,2020);
            assertEquals(false,ans);

            query = "update current_session set status = ?";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setInt(1,6);
            preparedStatement.execute();
            ans = officer.demote(path,2020);
            assertEquals(true,ans);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runOffice8() {

        //generate transcript
        String input = "8\nasdf\nasdf\nasdf\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);
            String query = "update current_session set status = ?";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setInt(1,6);
            preparedStatement.execute();


            officer.runOffice(officer.officerId);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runOffice8_1() {

        //generate transcript
        String input = "8\nasdf\nCS305\nC1302\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            String query = "update current_session set status = ?";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setInt(1,7);
            preparedStatement.execute();


            officer.runOffice(officer.officerId);

//            query = "update current_session set status = ?";
//            preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.setInt(1,8);
//            preparedStatement.execute();

            query = "delete from prereq where course_id = ?";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setString(1,"CS305");
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

        }

    }
    @Test
    void runOffice9() {

        //generate transcript
        String input = "9\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            officer.runOffice(officer.officerId);
        }
        finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

        }

    }

    @Test
    void runOffice10() {

        //generate transcript
        String input = "9\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            officer.runOffice(officer.officerId);
        }
        finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

        }

    }
    @Test
    void runOffice5() {
//            boolean ans = officer.addCourse("LAN101","LAN","CSE",1,3,3,3,3,"pc",2020);
        //Add Course
        String input = "5\nasdf\nLAN101\nLAN\nCSE\n1\n3\n3\n3\n3\nlmb\npc\n2020\nasdf\nLAN101\nlan\nCSE\nd\nfda]n\nasdf\nadf\nadf\npc\nadsf\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);
            String query = "update current_session set status = 8";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.execute();

            officer.runOffice(officer.officerId);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

        }

    }
    @Test
    void runOffice5_1() {
//        ans = officer.addCourse("LAN101","LAN","CSE",1,3,3,3,3,"pc",2020);
//            ans = officer.addCourse("CS305","software Engineering","CSE",6,3,3,3,3,"pc",2024);

        //Add course
        String input = "5\nasdf\nLAN101\nLAN\nCSE\n1\n3\n3\n3\n3\nlmb\nec\n2020\nadasdf\nCS305\nsoftware Engineering\nCSE\n6\n3\n3\n3\n3\nel\n2024\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);
            String query = "update current_session set status = 7";
            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.execute();

            officer.runOffice(officer.officerId);

            query = "delete from course_catalogue where course_id = ?";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setString(1,"LAN101");
            preparedStatement.execute();

            query = "delete from course where course_id = ?";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setString(1,"LAN101");
            preparedStatement.execute();

            query = "delete from course_catalogue where course_id = ? and batch = ?";
            preparedStatement = officer.con.prepareStatement(query);
            preparedStatement.setString(1,"CS305");
            preparedStatement.setInt(2,2024);
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

        }

    }

//    @Test
//    void addCourse() {
//        try{
//            String query = "update current_session set status = 8";
//            PreparedStatement preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.execute();
//
//            boolean ans = officer.addCourse("LAN101","LAN","CSE",1,3,3,3,3,"pc",2020);
//            assertEquals(false,ans);
//
//            query = "update current_session set status = 7";
//            preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.execute();
//
//            ans = officer.addCourse("LAN101","LAN","CSE",1,3,3,3,3,"pc",2020);
//            assertEquals(true,ans);
//
//            ans = officer.addCourse("CS305","software Engineering","CSE",6,3,3,3,3,"pc",2024);
//            assertEquals(true,ans);
//
//            query = "delete from course_catalogue where course_id = ?";
//            preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.setString(1,"LAN101");
//            preparedStatement.execute();
//
//            query = "delete from course where course_id = ?";
//            preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.setString(1,"LAN101");
//            preparedStatement.execute();
//
//            query = "delete from course_catalogue where course_id = ? and batch = ?";
//            preparedStatement = officer.con.prepareStatement(query);
//            preparedStatement.setString(1,"CS305");
//            preparedStatement.setInt(2,2024);
//            preparedStatement.execute();
//
//
//        }catch(SQLException err)
//        {
//            System.out.println(err.getMessage());
//        }
//    }


//completed for 1,2,3,4,6,7,8
}