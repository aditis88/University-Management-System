package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AcademicOfficeTest {

    AcademicOffice officer = new AcademicOffice("staffdeanoffice@iitrpr.ac.in");
    @BeforeEach
    void setUp() {


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBtechGradCred() {
        ArrayList<Double> arr = officer.getBtechGradCred(officer.con);
        ArrayList<Double> expectedOutput = new ArrayList<>();
        expectedOutput.add(20.0);
        expectedOutput.add(20.0);
        expectedOutput.add(20.0);
        assertArrayEquals(expectedOutput.toArray(),arr.toArray());
        Connection con = null;

    }

    @Test
    void getBtechProjCode() {
        Connection con = officer.con;
        String course = officer.getBtechProjCode(con);
        assertEquals("CP301",course);
        assertNotEquals("CP101",course);

    }

    @Test
    void removeSpaces() {
        String s = officer.removeSpaces("   asf    ");
        assertEquals("asf",s);
    }

    @Test
    void extractIntegral() {
        int a = officer.extractIntegral("2134543sdfasdf");
        assertEquals(a,2134543);
    }

    @Test
    void printResultTable() {
        try{
            PreparedStatement preparedStatement = officer.con.prepareStatement("select * from admin");
            ResultSet rs = preparedStatement.executeQuery();
            boolean ans = officer.printResultTable(rs);
            assertEquals(ans,true);
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

    }

    @Test
    void viewCourseCatalogue() {

        assertEquals(true,officer.viewCourseCatalogue(officer.con));
        Connection con = null;
//        assertEquals(false,officer.viewCourseCatalogue(con));
    }

    @Test
    void checkCourseDepartment() {
        String course = "CS305";
        String dept = "CSE";
        boolean ans = false;

        ans = officer.checkCourseDepartment(officer.con,course,dept);
        assertEquals(true,ans);

        ans = officer.checkCourseDepartment(officer.con,course,"HS");
        assertEquals(ans,false);
    }

    @Test
    void fetchCourseCredits() {
        String course = "DM101";
        double ans = officer.fetchCourseCredits(officer.con,course,2020);

        assertEquals(3.0,ans);
        ans = officer.fetchCourseCredits(officer.con,"LM101",2020);
        assertEquals(0,ans);

    }

    @Test
    void fetchCourseType() {

        String course = "DM101";
        String ans = officer.fetchCourseType(officer.con,course,2020);
        assertEquals(ans,"pc");

        ans = officer.fetchCourseType(officer.con,"LM101",2020);
        assertEquals(ans,"none");

    }

    @Test
    void fetchMinReqSem() {
        String course = "DM101";
        int ans = officer.fetchMinReqSem(officer.con,course,2020);
        assertEquals(ans,1);

        ans = officer.fetchMinReqSem(officer.con,"LM101",2020);
        assertEquals(ans,0);

    }

    @Test
    void seeGrades() {
//        (Connection con,int year,String student_id)

        boolean ans = officer.seeGrades(officer.con,2020,"2020csb1101@iitrpr.ac.in");
        assertEquals(ans,true);
    }

    @Test
    void getGradeValue() {
        String grade = "A";
        Double ans = officer.getGradeValue(grade);
        assertEquals(10,ans);
        grade = "A-";
        ans = officer.getGradeValue(grade);
        assertEquals(9,ans);
        grade = "B";
        ans = officer.getGradeValue(grade);
        assertEquals(8,ans);
        grade = "B-";
        ans = officer.getGradeValue(grade);
        assertEquals(7,ans);
        grade = "C";
        ans = officer.getGradeValue(grade);
        assertEquals(6,ans);
        grade = "C-";
        ans = officer.getGradeValue(grade);
        assertEquals(5,ans);
        grade = "C-";
        ans = officer.getGradeValue(grade);
        assertEquals(5,ans);
        grade = "D";
        ans = officer.getGradeValue(grade);
        assertEquals(4,ans);
        grade = "E";
        ans = officer.getGradeValue(grade);
        assertEquals(2,ans);
        grade = "F";
        ans = officer.getGradeValue(grade);
        assertEquals(0,ans);

    }

    @Test
    void calculateCGPA() {

        Double cgpa = officer.calculateCGPA(officer.con,"2020csb1101@iitrpr.ac.in",2020);
        assertEquals(cgpa,9.5);
    }

    @Test
    void testCalculateCGPA() {
        Double cgpa = officer.calculateCGPA(officer.con,"2020csb1101@iitrpr.ac.in",2020,2);
        assertEquals(cgpa,9.5);
    }

    @Test
    void calculateSGPA() {
        Double cgpa = officer.calculateSGPA(officer.con,"2020csb1101@iitrpr.ac.in",2020,2);
        assertEquals(cgpa,8);
        cgpa = officer.calculateSGPA(officer.con,"2020csb1101@iitrpr.ac.in",2020,1);
        assertEquals(cgpa,10);

    }

    @Test
    void isGraduated() {
        boolean ans = officer.isGraduated(officer.con,"2020csb1101@iitrpr.ac.in");
        assertEquals(ans,false);

        ans = officer.isGraduated(officer.con,"2020csb999@iitrpr.ac.in");
        assertEquals(ans,false);

        ans = officer.isGraduated(officer.con,"2020ceb1030@iitrpr.ac.in ");
        assertEquals(ans,false);

        ans = officer.isGraduated(officer.con,"2020csb9999@iitrpr.ac.in");
        assertEquals(ans,true);


    }

    @Test
    void printChangeProfileMenu() {
        assertEquals(true,officer.printChangeProfileMenu());
    }

    @Test
    void getCurrentEvent() {

        assertEquals("Semester is running", officer.getCurrentEvent(officer.SEMRUNNING));
        assertEquals("Course Float Active", officer.getCurrentEvent(officer.COURSEFLOATACTIVE));
        assertEquals("Course Float End", officer.getCurrentEvent(officer.COURSEFLOATEND));
        assertEquals("Student Registration Begins", officer.getCurrentEvent(officer.STUDENTREGBEGIN));
        assertEquals("Student Registraion Ends", officer.getCurrentEvent(officer.STUDENTREGEND));
        assertEquals("Grades Submission Starts", officer.getCurrentEvent(officer.GRADESUBMITSART));
        assertEquals("Grades Submission has ended", officer.getCurrentEvent(officer.GRADESUBMITEND));
        assertEquals("Semester has Started", officer.getCurrentEvent(officer.SEMSTART));
        assertEquals("Semester has ended", officer.getCurrentEvent(officer.SEMEND));
        assertEquals("Event Not defined Yet", officer.getCurrentEvent(10));
    }

    @Test
    void viewEvent() {

        assertEquals(true,officer.viewEvent(officer.con));
    }



}