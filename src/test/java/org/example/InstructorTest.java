package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class InstructorTest {

    Instructor prof = new Instructor("gunturi@iitrpr.ac.in");
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void isCourseFloatActive() {
        try{
            String query = "update current_session set status = 8";
            PreparedStatement preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();

            assertEquals(false,prof.isCourseFloatActive());

            query = "update current_session set status = 1";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();

            assertEquals(true,prof.isCourseFloatActive());


        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void floatCourse() {
        try{
            String query = "update current_session set status = 4 ";
            PreparedStatement preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();
            boolean ans = prof.floatCourse("DM101","CSE",4.5);

            assertEquals(false,ans);

            query = "update current_session set status = 4 ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();
            ans = prof.floatCourse("DM101","DM",5);

            assertEquals(false,ans);

//            query = "update current_session set status = 1 ";
//            preparedStatement = prof.con.prepareStatement(query);
//            preparedStatement.execute();
//            ans = prof.floatCourse("DM101","DM",5);
//
//            assertEquals(false,ans);

            query = "update current_session set status = 1 ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();
            ans = prof.floatCourse("DM102","CSE",5);

            assertEquals(true,ans);


            query = "delete from course_offering where course_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"DM102");
            preparedStatement.execute();

        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void deFloatCourse() {
        try{
            String query = "update current_session set status = 4 ";
            PreparedStatement preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();
            boolean ans = prof.floatCourse("DM101","CSE",4.5);

            assertEquals(false,ans);

            query = "update current_session set status = 1 ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();
            ans = prof.floatCourse("DM102","CSE",5);

            assertEquals(true,ans);


//            query = "delete from course_offering where course_id = ? ";
//            preparedStatement = prof.con.prepareStatement(query);
//            preparedStatement.setString(1,"DM102");
//            preparedStatement.execute();

//            query = "update current_session set status = 8 ";
//            preparedStatement = prof.con.prepareStatement(query);
//            preparedStatement.execute();

            ans = prof.deFloatCourse("DM102","DM");
            assertEquals(false,ans);

            query = "update current_session set status = 8 ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();
            ans = prof.deFloatCourse("DM102","CSE");

            assertEquals(false,ans);

            query = "update current_session set status = 1 ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();
            ans = prof.deFloatCourse("DM102","CSE");

            assertEquals(true,ans);


        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void isGradesSubmissionStarted() {
        try{
            String query = "update current_session set status = 8";
            PreparedStatement preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();

            assertEquals(false,prof.isGradesSubmissionStarted());

            query = "update current_session set status = 5";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();

            assertEquals(true,prof.isGradesSubmissionStarted());


        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void giveGrades() {
        try{
            String path = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/give_grades_test.csv";
            String query = "update current_session set status = 6";
            PreparedStatement preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();

            boolean ans = prof.giveGrades(path,2020);

            assertEquals(false,ans);

            query = "update current_session set status = 5";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();

            ans = prof.giveGrades(path,2000);
            assertEquals(false,ans);

            query = "update current_session set status = 5";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.execute();

            ans = prof.giveGrades(path,2020);
            assertEquals(true,ans);


            query = "delete from student_record_2020 where student_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"2020ceb1030@iitrpr.ac.in");
            int num = preparedStatement.executeUpdate();
            assertEquals(1,num);

//            query = "delete from student_record_2020 where student_id = ? and course_id = ?";
//            preparedStatement = prof.con.prepareStatement(query);
//            preparedStatement.setString(1,"2020csb1101@iitrpr.ac.in");
//            preparedStatement.setString(2,"DM102");
//            num = preparedStatement.executeUpdate();
//            assertEquals(1,num);


        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void seeGrades() {
        boolean ans = prof.seeGrades(2020);
        assertEquals(true,ans);

        ans = prof.seeGrades(2000);
        assertEquals(false,ans);

    }

    @Test
    void printMainMenu() {
        boolean ans = prof.printMainMenu();

        assertEquals(true,ans);
    }

    @Test
    void printSeeGradesMenu() {
        boolean ans = prof.printSeeGradesMenu();
        assertEquals(true,ans);
    }

    @Test
    void logOut() throws SQLException {

        Connection con = prof.con;
        boolean ans = prof.logOut();
        assertEquals(true,ans);

        prof.con = con;
    }
    @Test
    void runFaculty() {

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

            prof.runFaculty(prof.instructorId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runFaculty378910() {

        //change profile test
        String input = "3\n7\n8\n9\n10\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            prof.runFaculty(prof.instructorId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runFaculty2() {

        //change profile test
        String input = "2\nasdf\n1\n2020\nasdf\n2\n2020csb999@iitrpr.ac.in\nasdf\n3\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            prof.runFaculty(prof.instructorId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runFaculty4() throws SQLException {


        //change profile test
        String input = "4\nasdf\n2020\n/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/give_grades_test.csv\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            String query = "update current_session set status = ? ";
            PreparedStatement preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setInt(1,6);

            prof.runFaculty(prof.instructorId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runFaculty4_1() throws SQLException {


        //change profile test
        String input = "4\nasdf\n2020\n/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/java/org/example/seedData/give_grades_test.csv\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            String query = "update current_session set status = ? ";
            PreparedStatement preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setInt(1,5);

            prof.runFaculty(prof.instructorId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runFaculty5_1() throws SQLException {


        //change profile test
        String input = "5\nasdf\nLN201\nLN\n5.5\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            String query = "update current_session set status = ? ";
            PreparedStatement preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setInt(1,5);

            prof.runFaculty(prof.instructorId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runFaculty6_1() throws SQLException {


        //change profile test
        String input = "6\nasdf\nLN201\nLN\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            String query = "update current_session set status = ? ";
            PreparedStatement preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setInt(1,5);

            prof.runFaculty(prof.instructorId);


        } finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }

    //completed 1,3,7,8,9,10
}