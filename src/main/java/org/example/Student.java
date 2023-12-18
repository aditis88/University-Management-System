package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Student extends AbstractCommonFunctions {
    protected String student_id ;
    protected Connection con = null ;
    protected int studentAdmYear = 0;
    Student(String id)   {
        id = removeSpaces(id);
        id.toLowerCase();
        this.student_id = id;
        GetConnection gtCon = new GetConnection();
        this.con = gtCon.getConnection();
        studentAdmYear = extractIntegral(student_id);

        this.logInTimeStamp(this.student_id,this.con);


    }

   private boolean changeProfileNumber(String contact)  {
        contact = removeSpaces(contact);
        String updateQuery = "update student set contact = ? where student_id = ? ";

            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(updateQuery);
                preparedStatement.setString(1,removeSpaces(contact));
                preparedStatement.setString(2,removeSpaces(this.student_id));

                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }


        return true;
    }

    private boolean changeProfileName(String name)  {
        name = removeSpaces(name);
        String updateQuery = "update student set student_name = ? where student_id = ? ";
            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(updateQuery);
                preparedStatement.setString(1,removeSpaces(name));
                preparedStatement.setString(2,removeSpaces(this.student_id));

                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }

        return true;

    }
    protected boolean isCourseRegistraionActive()  {
        boolean isActive = false;
        String query = "select * from current_session";
            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery();

                if(rs.next())
                {
                    if(rs.getInt("status") == this.STUDENTREGBEGIN)
                    {
                        isActive = true;
                    }
                }


                rs.close();
                preparedStatement.close();
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }


        return isActive;
    }
    protected boolean checkIfFloated(String course_id)  {
        boolean isFloated = false;
        String query = "select * from course_offering where course_id = ?";

            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(query);
                preparedStatement.setString(1,course_id);

                ResultSet rs = preparedStatement.executeQuery();
//            printResultTable(rs);

                if(rs.next())
                {
                    isFloated = true;
                }
                rs.close();
                preparedStatement.close();
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }


        return isFloated;
    }
    protected boolean satisfyCGPACriteria(String course_id) {
        boolean isSatisfied = false;
        double cgpa = calculateCGPA(this.con,this.student_id, this.studentAdmYear);
        System.out.println("CGPA is "+cgpa);
        String findQuery = "select * from course_offering where course_id = ?";

            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(findQuery);
                preparedStatement.setString(1,course_id);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next())
                {
                    if(cgpa >= rs.getDouble("min_cgpa"))
                    {
                        isSatisfied = true;
                    }
                }

                rs.close();
                preparedStatement.close();
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }



        return isSatisfied;
    }
    protected boolean satisfyPrerequisite(String course_id)  {
        boolean isSatisfied = true;
        String fetchPrereq = "select * from prereq where course_id = ?";
        String fetchCompCourse = "select * from student_record_"+ String.valueOf(this.studentAdmYear)+" where student_id = ?";
           try{
               PreparedStatement preparedStatement = this.con.prepareStatement(fetchPrereq);
               preparedStatement.setString(1,course_id);

               ResultSet rs = preparedStatement.executeQuery();
//            printResultTable(rs);
               List<String> prereqList = new ArrayList<>();
               while (rs.next()) {
                   String str = rs.getString("prereq_id");
                   prereqList.add(str);
               }
               preparedStatement = this.con.prepareStatement(fetchCompCourse);
               preparedStatement.setString(1,this.student_id);

               rs = preparedStatement.executeQuery();

               Set<String> compCourseSet = new HashSet<>();
               while(rs.next())
               {
                   compCourseSet.add(rs.getString("course_id"));
               }
               for (String str : prereqList) {
                   if (compCourseSet.contains(str)) {
                       System.out.println(str + " is present in the Completed course set.");
                   } else {
                       System.out.println(str + " is not present in the Completed course set.");
                       isSatisfied = false;
                   }
               }

               rs.close();
               preparedStatement.close();
           }catch(SQLException err)
           {
               System.out.println(err.getMessage());
           }



        return isSatisfied;
    }
    protected boolean satisfyRequiredSem(String course_id)  {
        boolean isSatisfied = false;
        int minReqSem = fetchMinReqSem(this.con,course_id,this.studentAdmYear);
        System.out.println("Minimum required sem = "+minReqSem);
        String fetchSem = "select current_sem from student where student_id = ?";
        int sem = 0;
            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(fetchSem);
                preparedStatement.setString(1,this.student_id);
                ResultSet rs = preparedStatement.executeQuery();

                if(rs.next())
                {
                    sem = rs.getInt("current_sem");
                }
                if(sem >= minReqSem )
                {
                    isSatisfied = true;
                }

                preparedStatement.close();
                rs.close();
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }
        return isSatisfied;
    }
     public double findCreditLimit()  {
        double creditLimit = 0;
        int sem = 0;
        double earnedCredits = 0;

        String findSemQuery = "select current_sem from student where student_id = ?";
        String findAvgCreditQuery = "Select * from student_record_"+String.valueOf(this.studentAdmYear)+" where student_id = ? and (semester = ? or semester = ?) " ;


            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(findSemQuery);
                preparedStatement.setString(1,this.student_id);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next())
                {
                    sem = rs.getInt("current_sem");
                }
                else {
                    System.out.println("No such student");
                    return -1;
                }
                if(sem == 1 || sem == 2)
                {
                    creditLimit = this.CREDITLIMIT;
                }
                else {
                    preparedStatement = this.con.prepareStatement(findAvgCreditQuery);
                    preparedStatement.setString(1,this.student_id);
                    preparedStatement.setInt(2,sem-1);
                    preparedStatement.setInt(3,sem-2);

                    rs = preparedStatement.executeQuery();

                    while(rs.next())
                    {
                        earnedCredits += rs.getDouble("credits");
                    }
                    creditLimit = earnedCredits/2;
                    creditLimit = creditLimit*(1.25);
                }
                rs.close();
                preparedStatement.close();
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }

        return creditLimit;
    }
    private boolean satisfyCreditLimitCUMUpdate(String course_id)  {
        boolean isSatisfied = false;
        String findQuery = "Select * from enrolled_credits where student_id = ?" ;
        String insertQuery = "insert into enrolled_credits (student_id,credits) values(?,?)";
        String updateQuery = "update enrolled_credits set credits = ? where student_id = ?";
        String checkQuery = "select * from student_record_"+this.studentAdmYear + " where student_id = ? and course_id = ? and (grades = 'NA' or grades <>'F')";

        double course_credits = fetchCourseCredits(this.con,course_id,this.studentAdmYear);
        double creditLimit = findCreditLimit();


           try{
               PreparedStatement preparedStatement = this.con.prepareStatement(checkQuery);
               preparedStatement.setString(1,this.student_id);
               preparedStatement.setString(2,course_id);
               ResultSet rs = preparedStatement.executeQuery();
               if(rs.next())
               {
                   System.out.println("Course Already Taken!!");
                   return false;
               }


               preparedStatement = this.con.prepareStatement(findQuery);
               preparedStatement.setString(1,this.student_id);

               rs = preparedStatement.executeQuery();


               if(rs.next())
               {
                   double credits = rs.getInt(("credits"));

//                System.out.println("already enrolled in some courses");
//                System.out.println(credits +" credits") ;

                   if((credits+course_credits )<= creditLimit)
                   {
                       double totCredits = credits + course_credits;
                       preparedStatement = this.con.prepareStatement(updateQuery);
                       preparedStatement.setDouble(1,totCredits);
                       preparedStatement.setString(2,this.student_id);

                       preparedStatement.executeUpdate();
                       isSatisfied = true;
                   }

                   System.out.println("Total credits After Registration would be"+ (credits+course_credits));
               }
               else
               {

                   preparedStatement = this.con.prepareStatement(insertQuery);
                   preparedStatement.setString(1,this.student_id);
                   preparedStatement.setDouble(2,course_credits);

                   if(course_credits<=creditLimit)
                   {
                       preparedStatement.execute();
                       isSatisfied = true;
                   }
                   System.out.println("Total credits After Registration would be "+ (course_credits));
               }

               preparedStatement.close();
               rs.close();
           }catch(SQLException err)
           {
               System.out.println(err.getMessage());
           }



        return isSatisfied;
    }
    private boolean registerCourse(String course_id)  {
        boolean isRegistered = true;
        course_id = removeSpaces(course_id);
        course_id = course_id.toUpperCase();

        if(isCourseRegistraionActive())
        {
            System.out.println("Course registration active!");
        }else {
            System.out.println("Course Registration Not Active!");
            return false;
        }
        if(checkIfFloated(course_id))
        {
            System.out.println("Course "+course_id+" is Floated");
        }
        else {
            System.out.println("Course Not Floated!");
            return false;
        }
        if(satisfyCGPACriteria(course_id))
        {
            System.out.println("You satisfy cgpa criteria for this course!");
        }
        else {
            System.out.println("You does not satisfy cgpa criteria! Sorry you cannot register for this course!");
            return false;
        }
        if(satisfyPrerequisite(course_id))
        {
            System.out.println("You satisfy the prerquisite courses!");
        }else {
            System.out.println("You does not satisfy the prerquisite courses! Sorry you cannot register for this course!");
            return false;
        }
        if(satisfyRequiredSem(course_id))
        {
            System.out.println("You satisfy the minimum required semester for this course");

        }else {
            System.out.println("You does not satisfy the minimum required semester for this course!");
            return false;
        }
        if(satisfyCreditLimitCUMUpdate(course_id))
        {
            System.out.println("You Satisfy the credit limit criteria for the course chosen!\nCredits Registered");
        }
        else
        {
            System.out.println("Credits Limit exceeded!\nCourse not registered!");
            return false;
        }
        String registerQuery = "insert into student_record_"+String.valueOf(this.studentAdmYear)+" (student_id,course_id,semester,grades,credits) values(?,?,?,?,?)";
        String fetchSem = "select current_sem from student where student_id = ?";
        String updateCreditsQuery = "update set credits = ? where student_id = ?";
        int sem = 0;
        double credits = fetchCourseCredits(this.con,course_id,this.studentAdmYear);
        try{

            PreparedStatement preparedStatement = this.con.prepareStatement(fetchSem);
            preparedStatement.setString(1,this.student_id);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                sem = rs.getInt("current_sem");
            }


            preparedStatement = this.con.prepareStatement(registerQuery);
            preparedStatement.setString(1,this.student_id);
            preparedStatement.setString(2,course_id);
            preparedStatement.setInt(3,sem);
            preparedStatement.setString(4,"NA");
            preparedStatement.setDouble(5,credits);

            preparedStatement.execute();


            preparedStatement = this.con.prepareStatement(updateCreditsQuery);


            System.out.println("Course regisetered for student "+ this.student_id + " from the year "+ this.studentAdmYear);



            preparedStatement.close();

        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

        return isRegistered;
    }
    private boolean deRegisterCourse(String course_id)  {
        course_id = removeSpaces(course_id);
        course_id = course_id.toUpperCase();

        if(isCourseRegistraionActive())
        {
            System.out.println("Course registration active!");
        }else {
            System.out.println("Course Registration Not Active!");
            return false;
        }

        String checkQuery = "select * from student_record_"+this.studentAdmYear + " where student_id = ? and course_id = ? and grades = 'NA' ";
        String findPrevCred = "select * from enrolled_credits where student_id = ? ";
        String updateCred = "update enrolled_credits set credits = ? where student_id = ?";
        String deleteQuery = "delete from student_record_"+this.studentAdmYear+" where student_id = ? and course_id = ? and grades = 'NA' ";
        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(checkQuery);
            preparedStatement.setString(1,this.student_id);
            preparedStatement.setString(2,course_id);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                double prevCred = 0;
                preparedStatement = this.con.prepareStatement(findPrevCred);
                preparedStatement.setString(1,this.student_id);
                rs = preparedStatement.executeQuery();
                if(rs.next())
                {
                    prevCred = rs.getDouble("credits");
//                    System.out.println("Found prevCred");
                }
                Double course_credits = fetchCourseCredits(this.con,course_id,this.studentAdmYear);

                preparedStatement = this.con.prepareStatement(updateCred);
                preparedStatement.setDouble(1,prevCred-course_credits);
                preparedStatement.setString(2,this.student_id);
                preparedStatement.executeUpdate();

                preparedStatement = this.con.prepareStatement(deleteQuery);
                preparedStatement.setString(1,this.student_id);
                preparedStatement.setString(2,course_id);
                preparedStatement.executeUpdate();


                preparedStatement.close();
                rs.close();
                System.out.println("Course De Registered Succesfully!");

            }else {
                System.out.println("Course was never Registered!");
            }

        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

        return true;
    }

    public boolean printMainMenu()
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
        row.add("See Your Grades");
        row.add("2");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("Calculate CGPA till now");
        row.add("3");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("See Course Catalogue");
        row.add("4");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("Enroll a Course");
        row.add("5");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("View Current Event");
        row.add("6");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("Check for Graduation");
        row.add("7");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("De Registration of Course");
        row.add("8");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("View Course Offering");
        row.add("9");
        mainMenu.add(row);

        row = new ArrayList<>();
        row.add("View Enrolled Courses");
        row.add("10");
        mainMenu.add(row);


        CLI.printMenu(title,mainMenu);

        return true;
    }

    private boolean viewEnrolledCourses()
    {
        String findQuery = "Select * from student_record_"+String.valueOf(this.studentAdmYear)+" where student_id = ? and grades = ?";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(findQuery);
            preparedStatement.setString(1,removeSpaces(student_id));
            preparedStatement.setString(2,"NA");
            ResultSet rs = preparedStatement.executeQuery();
            printResultTable(rs);
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return true;

    }


    boolean logOut()  {

        try{
            this.logOutTimeStamp(this.student_id,this.con);
            this.con.close();
            System.out.println(this.student_id + " logged out Successfully!");
        }catch(SQLException er)
        {
            System.out.println(er.getMessage());
        }

        return true;
    }

    public static void runStudent(String userID)  {
        Student std = new Student(userID);
//        double cgpa = std.calculateCGPA(std.con,std.student_id,2020);
//        double cred = std.fetchCourseCredits(std.con,"CS305",2020);
//        System.out.println(cred);
//        System.out.println(cgpa);
//        std.changeProfileName("VIPIN");

//        String course = "HS507";
//        std.registerCourse(course);

//        double cred = std.findCreditLimit();
//        System.out.println(cred);
//        std.isGraduated(std.con,std.student_id);


        String csvFile = "";
        String input = "";
        String exitSymbol = "exit";
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            std.printMainMenu();
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
                std.printChangeProfileMenu();
                while(true)
                {
                    std.printChangeProfileMenu();
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
                        std.changeProfileName(name);
                        System.out.println("Name Changed Successfully");

                    }else if(profile.equals("2"))
                    {
                        System.out.println("Enter New Phone Number");
                        CLI.inputTaker();
                        String number = scanner.nextLine();
                        std.changeProfileNumber(number);
                        System.out.println("Number Changed Successfully");
                    }else {
                        System.out.println("Invalid Choice!");
                    }
                    CLI.clearScreen();
                }

            }else if(input.equals("2"))
            {
                std.seeGrades(std.con,std.studentAdmYear,std.student_id);
            }
            else if(input.equals("3"))
            {
                double cgpa = std.calculateCGPA(std.con,std.student_id,std.studentAdmYear);
                System.out.println("Your CGPA is "+cgpa+" till the current semester!");
            }
            else if(input.equals("4"))
            {
                std.viewCourseCatalogue(std.con);
            }
            else if(input.equals("5"))
            {
                String input1 = "";
                while(true) {
                    System.out.println("Enter exit to go back or any other string to proceed:");
                    CLI.inputTaker();
                    input1 = scanner.nextLine();
                    input1 = removeSpaces(input1);

                    if (input1.equals(exitSymbol)) {
                        System.out.println("Exiting Course Enrollment Menu..");
                        break;
                    }
                    System.out.println("Enter Course ID to enroll");
                    CLI.inputTaker();
                    String courseId = scanner.nextLine();

                    std.registerCourse(courseId);

                    CLI.clearScreen();
                }

            }
            else if(input.equals("6"))
            {
                std.viewEvent(std.con);
            }
            else if(input.equals("7"))
            {
                boolean isGrad = std.isGraduated(std.con,std.student_id);
                if(isGrad)
                {
                    System.out.println("You are ready for graduation");
                }
                else
                {
                    System.out.println("You are not ready for graduation");
                }
            }
            else if(input.equals("8"))
            {
                String input1 = "";
                while(true) {
                    System.out.println("Enter exit to go back or any other string to proceed:");
                    CLI.inputTaker();
                    input1 = scanner.nextLine();
                    input1 = removeSpaces(input1);

                    if (input1.equals(exitSymbol)) {
                        System.out.println("Exiting Course Enrollment Menu..");
                        break;
                    }
                    System.out.println("Enter Course ID to De Register Course:");
                    CLI.inputTaker();
                    String courseId = scanner.nextLine();

                    std.deRegisterCourse(courseId);

                    CLI.clearScreen();
                }
            }else if(input.equals("9"))
            {
                std.viewCourseOffering(std.con);
            }else if(input.equals("10"))
            {
                std.viewEnrolledCourses();
            }
            else {
                System.out.println("Wrong Choice!");
            }

            CLI.clearScreen();
        }


        std.logOut();
    }

//    public static void main(String[] args)
//    {
//        Student std = new Student("2020csb1101@iitrpr.ac.in");
//
////        System.out.println("course credits :" + std.fetchCourseCredits(std.con,"DM101",2020));
////        System.out.println(std.deRegisterCourse("DM101"));
////        System.out.println(std.findCreditLimit());
//
////        System.out.println(std.fetchCourseType(std.con,"HS507",2020));
//
//        std.logOut();
//    }

}
