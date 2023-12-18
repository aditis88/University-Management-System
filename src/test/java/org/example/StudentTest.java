package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student std = new Student("2020csb1101@iitrpr.ac.in");
    Instructor prof = new Instructor("gunturi@iitrpr.ac.in");

    StudentTest() throws SQLException {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void viewCourseOffering() throws SQLException {
        boolean ans = std.viewCourseOffering(std.con);
        assertEquals(true,ans);
    }



    @Test
    void isCourseRegistraionActive() throws SQLException {

        String query = "update current_session set status = 4";
        PreparedStatement preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        assertEquals(false,std.isCourseRegistraionActive());

        query = "update current_session set status = 3";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        assertEquals(true,std.isCourseRegistraionActive());
    }

    @Test
    void checkIfFloated() throws SQLException {
        String query = "update current_session set status = 1 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();
        boolean ans = prof.floatCourse("DM102","CSE",5);
        assertEquals(true,ans);

        ans = std.checkIfFloated("DM102");
        assertEquals(true,ans);

        query = "update current_session set status = 1 ";
        preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();
        ans = prof.deFloatCourse("DM102","CSE");

        assertEquals(true,ans);

        ans = std.checkIfFloated("DM102");
        assertEquals(false,ans);

    }

    @Test
    void satisfyCGPACriteria() throws SQLException {

    }

    @Test
    void satisfyPrerequisite() {


    }

    @Test
    void satisfyRequiredSem() {
    }

    @Test
    void findCreditLimit() {
    }

    @Test
    void satisfyCreditLimitCUMUpdate() {
    }

    @Test
    void registerCourse() {

    }

    @Test
    void deRegisterCourse() {
    }

    @Test
    void printMainMenu() {
        std.printMainMenu();
    }

    @Test
    void logOut() {
    }

    @Test
    void runStudent()   {
        String input = "1\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runStudent1()   {

        String input = "1\nasdf\n1\nNavroop\nadsf\n2\n9999999\nasdf\n3\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }

    @Test
    void runStudent2()   {
        String input = "2\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runStudent3()   {
        String input = "3\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runStudent4()   {
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

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runStudent6()   {
        String input = "6\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runStudent7910()   {
        String input = "7\n9\n10\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }

    }
    @Test
    void runStudent5_1() throws SQLException {
        //Course Registration Not Active
        String query = "update current_session set status = 4";
        PreparedStatement preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();


        String input = "5\nasdf\nDM101\nexit\nexit"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);
        }
    }
    @Test
    void runStudent5_2() throws SQLException {

        //does not satisfy cgpa criteria
        String query = "update current_session set status = 1 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();
        boolean ans = prof.floatCourse("DM102","CSE",10.0);

        assertEquals(true,ans);


         query = "update current_session set status = 3";
         preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        String input = "5\nasdf\nLM102\nasdf\nDM102\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            query = "delete from course_offering where course_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"DM102");
            preparedStatement.execute();
        }
    }
    @Test
    void runStudent5_3() throws SQLException {
////does not satisfy prerquisite


        String query = "update current_session set status = 1 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();
        boolean ans = prof.floatCourse("CS302","CSE",5.0);

        assertEquals(true,ans);


        query = "update current_session set status = 3";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        String input = "5\nasdf\nCS302\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            query = "delete from course_offering where course_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"CS302");
            preparedStatement.execute();
        }
    }
    @Test
    void runStudent5_4() throws SQLException {

//// credit limit exceeded
        String query = "update current_session set status = 1 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();
        boolean ans = prof.floatCourse("DM102","CSE",5.0);

        assertEquals(true,ans);


        query = "update current_session set status = 3";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        String input = "5\nasdf\nDM102\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            query = "delete from course_offering where course_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"DM102");
            preparedStatement.execute();
        }
    }
    @Test
    void runStudent5_5() throws SQLException {


        String query = "update current_session set status = 1 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();
        boolean ans = prof.floatCourse("CS305","CSE",5.0);

        assertEquals(true,ans);


        query = "update current_session set status = 3";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        String input = "5\nasdf\nCS305\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            query = "delete from course_offering where course_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"CS305");
            preparedStatement.execute();
        }
    }
    @Test
    void runStudent5_6() throws SQLException {
//course_registered
//already enrolled courses
        String query = "update current_session set status = 1 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();

        query = "insert into course_offering(instructor_id,course_id,dept_id,min_cgpa) values(?,?,?,?)";
        preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.setString(1,"gunturi@iitrpr.ac.in");
        preparedStatement.setString(2,"CS101");
        preparedStatement.setString(3,"CSE");
        preparedStatement.setDouble(4,5.0);
        preparedStatement.execute();


        query = "update current_session set status = 3";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        query = "insert into enrolled_credits(student_id,credits) values(?,?)";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.setString(1,"2020csb1101@iitrpr.ac.in");
        preparedStatement.setDouble(2,0.0);
        preparedStatement.execute();


        String input = "5\nasdf\nCS101\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            query = "delete from course_offering where course_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"CS101");
            preparedStatement.execute();

            query = "delete from student_record_"+std.extractIntegral(std.student_id)+ " where course_id = ? and grades = ?";
            preparedStatement = std.con.prepareStatement(query);
            preparedStatement.setString(1,"CS101");
            preparedStatement.setString(2,"NA");
            preparedStatement.execute();

            query  ="delete from enrolled_credits";
            preparedStatement = std.con.prepareStatement(query);
            preparedStatement.execute();
        }
    }
    @Test
    void runStudent5_6_1() throws SQLException {
//course_registered
//already enrolled courses
        String query = "update current_session set status = 1 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();

        query = "insert into course_offering(instructor_id,course_id,dept_id,min_cgpa) values(?,?,?,?)";
        preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.setString(1,"gunturi@iitrpr.ac.in");
        preparedStatement.setString(2,"CS101");
        preparedStatement.setString(3,"CSE");
        preparedStatement.setDouble(4,5.0);
        preparedStatement.execute();


        query = "update current_session set status = 3";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        query  ="delete from enrolled_credits";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();


        String input = "5\nasdf\nCS101\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            query = "delete from course_offering where course_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"CS101");
            preparedStatement.execute();

            query = "delete from student_record_"+std.extractIntegral(std.student_id)+ " where course_id = ? and grades = ?";
            preparedStatement = std.con.prepareStatement(query);
            preparedStatement.setString(1,"CS101");
            preparedStatement.setString(2,"NA");
            preparedStatement.execute();

            query  ="delete from enrolled_credits";
            preparedStatement = std.con.prepareStatement(query);
            preparedStatement.execute();
        }
    }
    @Test
    void runStudent5_7() throws SQLException {
//        Student std1 = new Student("");


        String query = "update current_session set status = 1 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();


        query = "insert into course_offering(instructor_id,course_id,dept_id,min_cgpa) values(?,?,?,?)";
        preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.setString(1,"gunturi@iitrpr.ac.in");
        preparedStatement.setString(2,"CS101");
        preparedStatement.setString(3,"CSE");
        preparedStatement.setDouble(4,5.0);
        preparedStatement.execute();

        query = "insert into course_offering(instructor_id,course_id,dept_id,min_cgpa) values(?,?,?,?)";
        preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.setString(1,"gunturi@iitrpr.ac.in");
        preparedStatement.setString(2,"CS305");
        preparedStatement.setString(3,"CSE");
        preparedStatement.setDouble(4,5.0);
        preparedStatement.execute();


        query = "update current_session set status = 3";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        query = "update student set current_sem = 6 where student_id = ?";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.setString(1,"2020csb1101@iitrpr.ac.in");
        preparedStatement.execute();

        String input = "5\nasdf\nCS101\nasdf\nCS305\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            query = "delete from course_offering where course_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"CS101");
            preparedStatement.execute();

            query = "delete from student_record_"+std.extractIntegral(std.student_id)+ " where course_id = ? and grades = ?";
            preparedStatement = std.con.prepareStatement(query);
            preparedStatement.setString(1,"CS101");
            preparedStatement.setString(2,"NA");
            preparedStatement.execute();

            query  ="delete from enrolled_credits";
            preparedStatement = std.con.prepareStatement(query);
            preparedStatement.execute();

            query = "update student set current_sem = 2 where student_id = ?";
            preparedStatement = std.con.prepareStatement(query);
            preparedStatement.setString(1,"2020csb1101@iitrpr.ac.in");
            preparedStatement.execute();

            query = "delete from course_offering where course_id = ? ";
            preparedStatement = prof.con.prepareStatement(query);
            preparedStatement.setString(1,"CS305");
            preparedStatement.execute();

        }
    }

    @Test
    void runStudent11() throws SQLException {

        String query = "update current_session set status = 3";
        PreparedStatement preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        String input = "11\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

        }
    }
    @Test
    void runStudent8() throws SQLException {
        String query = "update current_session set status = 1 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();




        String input = "8\nasdf\nLM101\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

        }

    }
    @Test
    void runStudent8_1() throws SQLException {
        String query = "update current_session set status = 3 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();




        String input = "8\nasdf\nLM101\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

        }

    }@Test
    void runStudent8_2() throws SQLException {
        String query = "update current_session set status = 3 ";
        PreparedStatement preparedStatement = prof.con.prepareStatement(query);
        preparedStatement.execute();

        query = "insert into student_record_2020 (student_id,course_id,semester,grades,credits) " +
                "values(?,?,?,?,?)";
        preparedStatement = std.con.prepareStatement(query);
//        "2020csb1101@iitrpr.ac.in","CS101","NA",3
        preparedStatement.setString(1,"2020csb1101@iitrpr.ac.in");
        preparedStatement.setString(2,"CS101");
        preparedStatement.setInt(3,2);
        preparedStatement.setString(4,"NA");
        preparedStatement.setDouble(5,3.0);
        preparedStatement.execute();


        query  ="delete from enrolled_credits";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.execute();

        query = "insert into enrolled_credits(student_id,credits) values(?,?)";
        preparedStatement = std.con.prepareStatement(query);
        preparedStatement.setString(1,"2020csb1101@iitrpr.ac.in");
        preparedStatement.setDouble(2,3.0);




        String input = "8\nasdf\nCS101\nexit\nexit\n"; // Provide input to the program
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Save the original System.in and System.out streams
        InputStream originalInputStream = System.in;
        PrintStream originalPrintStream = System.out;

        try{
            System.setIn(inputStream);
            System.setOut(printStream);

            std.runStudent(std.student_id);


        }finally {

            System.setIn(originalInputStream);
            System.setOut(originalPrintStream);

            query  ="delete from enrolled_credits";
            preparedStatement = std.con.prepareStatement(query);
            preparedStatement.execute();

        }

    }
//completed 1,2,3,4,6,7,8,9,10,11
}