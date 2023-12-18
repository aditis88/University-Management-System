package org.example;

import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Instructor extends AbstractCommonFunctions{
    protected Connection con = null;
    public String instructorId;
    Instructor(String id )
    {
        id = removeSpaces(id);
        this.instructorId = id;
        GetConnection gtCon = new GetConnection();
        this.con = gtCon.getConnection();
    }
    boolean isCourseFloatActive()
    {
        String query = "Select * from current_session";
        ResultSet rs ;
        boolean isActive = false;
        try
        {
            PreparedStatement preparedStatement = this.con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            int status = rs.getInt("status");
            if(status == this.COURSEFLOATACTIVE)
            {
                isActive = true;
            }

        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return isActive;
    }
    protected boolean floatCourse(String course_id,String dept_id,double min_cgpa)
    {
        boolean isActive = isCourseFloatActive();
        course_id = removeSpaces(course_id);
        course_id = course_id.toUpperCase();
        String instructor_id = this.instructorId;
        dept_id = removeSpaces(dept_id);
        dept_id = dept_id.toUpperCase();
//        System.out.println(isActive);

        if(!checkCourseDepartment(this.con,course_id,dept_id) )
        {
            System.out.println("The course and department that you have given are not compatible!");
            return false;
        }
        if(isActive)
        {
            String query = "insert into course_offering (instructor_id,course_id,dept_id,min_cgpa ) values (?,?,?,?)";
            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(query);

                preparedStatement.setString(1,instructor_id);
                preparedStatement.setString(2,course_id);
                preparedStatement.setString(3,dept_id);
                preparedStatement.setDouble(4,min_cgpa);

                preparedStatement.execute();
                System.out.println("Course floated successfully!");

            }
            catch(SQLException err)
            {
                System.out.println(err.getMessage());
                return false;
            }
        }
        else
        {
            System.out.println("Sorry you cannot float courses now!!");
            return false;
        }
        return true;
    }

    protected boolean deFloatCourse(String course_id,String dept_id)
    {
        boolean isActive = isCourseFloatActive();
        course_id = removeSpaces(course_id);
        String instructor_id = this.instructorId;
        dept_id = removeSpaces(dept_id);
//        System.out.println(isActive);
        if(!checkCourseDepartment(this.con,course_id,dept_id))
        {
            System.out.println("The course and department that you have given are not compatible!");
            return false;
        }
        if(isActive)
        {
            String query = "delete from course_offering where instructor_id = ? and  course_id = ? and dept_id = ? ";
            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(query);

                preparedStatement.setString(1,instructor_id);
                preparedStatement.setString(2,course_id);
                preparedStatement.setString(3,dept_id);
//                preparedStatement.setDouble(4,min_cgpa);

                preparedStatement.execute();
                System.out.println("Course defloated successfully!");

            }
            catch(SQLException err)
            {
                System.out.println(err.getMessage());
                return false;
            }
        }
        else
        {
            System.out.println("Sorry you cannot defloat courses now!!");
            return false;
        }
        return true;
    }
    private boolean changeProfileNumber(String contact)
    {
        contact = removeSpaces(contact);
        String updateQuery = "update instructor set contact = ? where instructor_id = ? ";
        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(updateQuery);
            preparedStatement.setString(1,removeSpaces(contact));
            preparedStatement.setString(2,removeSpaces(this.instructorId));

            preparedStatement.execute();
            preparedStatement.close();

        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
        return true;
    }

    private boolean changeProfileName(String name)
    {
        name = removeSpaces(name);
        String updateQuery = "update instructor set instructor_name = ? where instructor_id = ? ";
        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(updateQuery);
            preparedStatement.setString(1,removeSpaces(name));
            preparedStatement.setString(2,removeSpaces(this.instructorId));

            preparedStatement.execute();
            preparedStatement.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
        return true;

    }
    private boolean isGradesAlreadyAssigned(int year,String student_id,String course_id,String sem,Double credits)
    {
        boolean ans = false;
        student_id = removeSpaces(student_id);
        course_id = removeSpaces(course_id);
        int semester = Integer.parseInt(removeSpaces(sem));

        String findQuery = "Select * from student_record_"+String.valueOf(year)+" where student_id = ? and course_id = ? and semester = ? and credits = ?";
        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(findQuery);
            preparedStatement.setString(1,student_id);
            preparedStatement.setString(2,course_id);
            preparedStatement.setInt(3,semester);
            preparedStatement.setDouble(4,credits);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
            {
                ans = true;
            }
            rs.close();
            preparedStatement.close();

        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

        return ans;
    }
    boolean isGradesSubmissionStarted()
    {
        String query = String.format("select * from current_session");
        boolean isStarted = false;

        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int status = rs.getInt("status");
            if(status == this.GRADESUBMITSART)
            {
                isStarted = true;
            }

        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
        return isStarted;
    }

    protected boolean giveGrades(String csvFile,int year)
    {
        String insertQuery = "insert into student_record_"+String.valueOf(year)+" (student_id, course_id,semester,grades,credits) values (?,?,?,?,?)";
        String updateQuery = "update student_record_"+String.valueOf(year)+" set grades = ? where student_id = ? and course_id = ? and semester = ? and credits = ?";
        boolean submitGrade = isGradesSubmissionStarted();
        if(!submitGrade)
        {
            System.out.println("Grades submission has not started yet!");
            return false;
        }
        try {

            Connection connection = this.con;
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                String std_id = removeSpaces(data[0]);
                String course_id = removeSpaces(data[1]);
                int sem = Integer.parseInt(removeSpaces(data[2]));
                String grades = removeSpaces(data[3]);

                double cred = fetchCourseCredits(this.con, data[1],year);

                String query = String.format("insert into student_record_%d (student_id, course_id,semester,grades,credits) values ('%s','%s',%d,'%s',%.2f)",year,std_id,course_id,sem,grades,cred );
//                System.out.println(Arrays.toString(data));
//                System.out.println(query);

                preparedStatement = this.con.prepareStatement(query);

                if(!isGradesAlreadyAssigned(year,data[0],data[1],data[2],cred))
                {
                    preparedStatement.execute();
                }
                else {
                    //update the grades
                    preparedStatement = this.con.prepareStatement(updateQuery);

                    preparedStatement.setString(1,data[3]);

                    preparedStatement.setString(2, removeSpaces(data[0]));
                    preparedStatement.setString(3, removeSpaces(data[1]));
                    preparedStatement.setInt(4, Integer.parseInt(removeSpaces(data[2])));
                    preparedStatement.setDouble(5, cred);

                    preparedStatement.executeUpdate();
                }
            }

            preparedStatement.close();
            System.out.println("Grades UPDATED/INSERTED successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }


protected boolean seeGrades(int year)
{
    String findQuery = "Select * from student_record_"+String.valueOf(year)+" ";
    try{
        PreparedStatement preparedStatement = this.con.prepareStatement(findQuery);
        ResultSet rs = preparedStatement.executeQuery();
        printResultTable(rs);
    }catch(SQLException err)
    {
        System.out.println(err.getMessage());
        return false;
    }
    return true;
}

    boolean printMainMenu()
    {
        ArrayList<ArrayList<String>> mainMenu = new ArrayList<>();
        String title = "MAIN MENU";
        ArrayList<String> row = new ArrayList<>();

        row.add("Options");
        row.add("Enter Choice");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("Change Profile");
        row.add("1");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("See Grades");
        row.add("2");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("See Course Catalogue");
        row.add("3");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("Grade Upload through CSV file");
        row.add("4");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("Course Float");
        row.add("5");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("Course Defloat");
        row.add("6");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("View Event");
        row.add("7");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("See Course Offering");
        row.add("8");
        mainMenu.add(row);

        CLI.printMenu(title,mainMenu);

        return true;
    }
boolean printSeeGradesMenu()
{
    ArrayList<ArrayList<String>> gradesMenu = new ArrayList<>();
    String title = "GRADES MENU";
    ArrayList<String> row = new ArrayList<>();

    row.add("Options");
    row.add("Enter Choice");
    gradesMenu.add(row);

    row = new ArrayList<>();
    row.add("See grades by year");
    row.add("1");
    gradesMenu.add(row);

    row = new ArrayList<>();
    row.add("See Grades by entry number");
    row.add("2");
    gradesMenu.add(row);


    CLI.printMenu(title,gradesMenu);

    return true;

}



    boolean logOut()  {
        try{
            this.con.close();
            System.out.println("Instructor Successfully logged out!");
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return true;
    }
    public static  void runFaculty(String UserId)  {
        Instructor prof = new Instructor(UserId);
//        prof.viewCourseCatalogue(prof.con);
//        prof.floatCourse("CS555","gunturi@iitrpr.ac.in","CSE",7.0);
//        prof.changeProfileNumber("999999999");
//        prof.changeProfileName("Dr. Venkata M Viswanath Gunturi");
//        prof.deFloatCourse("CS555","gunturi@iitrpr.ac.in","CSE",7.0);
//        prof.giveGrades(csvFile,2020);
//        prof.seeGrades(2023,"2020ceb1033@iitrpr.ac.in");
//    prof.floatCourse("DM101",prof.instructorId,"CSE",7);

//        String csvFile = "/home/ashish/hdd/study/java_proj/AIIMSPortal/src/main/java/org/example/seedData/student_grades.csv";
        String csvFile = "";
        String input = "";
        String exitSymbol = "exit";
        Scanner scanner = new Scanner(System.in);

        while(true)
        {
            prof.printMainMenu();
            CLI.inputTaker();
            input = scanner.nextLine();
            input = removeSpaces(input);

            if (input.equals(exitSymbol)) {
                System.out.println("Exiting Faculty Menu..");
                break;
            }
            if(input.equals("1"))
            {

                String profile = "";
                prof.printChangeProfileMenu();
                while(true)
                {
                    prof.printChangeProfileMenu();
                    System.out.println("Enter exit to go back or any other string to proceed");
                    CLI.inputTaker();
                    profile = scanner.nextLine();
                    profile = removeSpaces(profile);
                    if(profile.equals(exitSymbol))
                    {
                        System.out.println("Exiting Change Profile Menu..");
                        break;
                    }
                    if(profile.equals("1"))
                    {
                        System.out.println("Enter New Name");
                        CLI.inputTaker();
                        String name = scanner.nextLine();
                        prof.changeProfileName(name);
                        System.out.println("Name Changed Successfully");

                    }else if(profile.equals("2"))
                    {
                        System.out.println("Enter New Phone Number");
                        CLI.inputTaker();
                        String number = scanner.nextLine();
                        prof.changeProfileNumber(number);
                        System.out.println("Number Changed Successfully");
                    }else {
                        System.out.println("Invalid Choice!");
                    }
                    CLI.clearScreen();
                }
            }
            else if(input.equals("2"))
            {
                String input1 = "";
                while(true)
                {
                    System.out.println("Enter exit to go back or any other string to proceed:");
                    prof.printSeeGradesMenu();
                    CLI.inputTaker();
                    input1 = scanner.nextLine();
                    input1 = removeSpaces(input1);
                    if(input1.equals(exitSymbol))
                    {
                        System.out.println("Exiting See Grades Menu..");
                        break;
                    }
                    CLI.inputTaker();
                    input1 = scanner.nextLine();

                    if(input1.equals("1"))
                    {
                        System.out.println("Enter Year:");
                        CLI.inputTaker();
                        String year = scanner.nextLine();
                        year = removeSpaces(year);
                        try{
                            prof.seeGrades(Integer.parseInt(year));
                        }catch(Exception err)
                        {
                            System.out.println(err.getMessage());
                        }

                    }else if(input1.equals("2"))
                    {
                        System.out.println("Enter Student ID");
                        CLI.inputTaker();
                        String id = scanner.nextLine();
                        id = removeSpaces(id);
                        int admYear = extractIntegral(id);
                        prof.seeGrades(prof.con,admYear,id);
                    }else {
                        System.out.println("Invalid Choice!");
                    }
                    CLI.clearScreen();

                }
            }
            else if(input.equals("3"))
            {
                prof.viewCourseCatalogue(prof.con);
            }
            else if(input.equals("4"))
            {
                String input1 = "";
               while(true)
               {
                   System.out.println("Enter exit to go back or any other string to move forward");
                   CLI.inputTaker();
                   input1 = scanner.nextLine();
                   if(input1.equals(exitSymbol))
                   {
                       System.out.println("Exiting give grades Menu");
                       break;
                   }
                   System.out.println("Enter the year of the students whom grades you want to upload:");
                   CLI.inputTaker();
                   String year = scanner.nextLine();
                   System.out.println("Give the path of the file");
                   CLI.inputTaker();
                   csvFile = scanner.nextLine();
                   try{
                       prof.giveGrades(csvFile,Integer.parseInt(year));
                   }catch(Exception err)
                   {
                       System.out.println(err.getMessage());
                   }
               }
            }
            else if(input.equals("5"))
            {
                String input1 = "";
                while(true)
                {
                    System.out.println("Enter exit to go back or any other string to move forward");
                    CLI.inputTaker();
                    input1 = scanner.nextLine();
                    if(input1.equals(exitSymbol))
                    {
                        System.out.println("Exiting Course Float Menu");
                        break;
                    }
//                    floatCourse(String course_id,String dept_id,double min_cgpa)
                    System.out.println("Enter the Course ID");
                    CLI.inputTaker();
                    String course_id = scanner.nextLine();

                    System.out.println("Enter the department of the Course");
                    CLI.inputTaker();
                    String dept_id = scanner.nextLine();

                    System.out.println("Enter the minimum CGPA Required to take this course");
                    CLI.inputTaker();
                    String min_cgpa = scanner.nextLine();

                    try{
                      prof.floatCourse(course_id,dept_id,Double.parseDouble(min_cgpa));

                    }catch(Exception err)
                    {
                        System.out.println("Course Not Floated!");
                        System.out.println(err.getMessage());
                    }

                }
            }
            else if(input.equals("6"))
            {
                String input1 = "";
                while(true)
                {
                    System.out.println("Enter exit to go back or any other string to move forward");
                    CLI.inputTaker();
                    input1 = scanner.nextLine();
                    if(input1.equals(exitSymbol))
                    {
                        System.out.println("Exiting Course Defloat Menu");
                        break;
                    }
//                    deFloatCourse(String course_id,String dept_id)
                    System.out.println("Enter the Course ID for defloating:");
                    CLI.inputTaker();
                    String course_id = scanner.nextLine();

                    System.out.println("Enter the department of the Course");
                    CLI.inputTaker();
                    String dept_id = scanner.nextLine();


                    try{
                        prof.deFloatCourse(course_id,dept_id);

                    }catch(Exception err)
                    {
                        System.out.println("Course Not Floated!");
                        System.out.println(err.getMessage());
                    }

                }

            }else if(input.equals("7"))
            {
                prof.viewEvent(prof.con);
            }
            else if(input.equals("8"))
            {
                prof.viewCourseOffering(prof.con);
            }
            else{
                System.out.println("Invalid Option! enter again!");
            }
            CLI.clearScreen();
        }


        prof.logOut();

    }

//    public static void main(String[] args) throws SQLException {
//        Instructor prof = new Instructor("gunturi@iitrpr.ac.in");
//
//        prof.runFaculty(prof.instructorId);
//
////        prof.giveGrades("/home/ashish/hdd/study/java_proj/AIIMSPortal/src/main/java/org/example/seedData/give_grades_test.csv",2020);
//        prof.logOut();
//    }
}
