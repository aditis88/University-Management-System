package org.example;
import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class AcademicOffice extends AbstractCommonFunctions{
    public String officerId;
    protected Connection con = null;
    AcademicOffice(String id )
    {
        id = removeSpaces(id);
        this.officerId = id;
        GetConnection gtCon = new GetConnection();
        this.con = gtCon.getConnection();
    }

    private boolean changeProfileNumber(String contact) {
        contact = removeSpaces(contact);
//        insert into admin (admin_name,admin_id,contact) values ('Staff Dean’s office','staffdeanoffice@iitrpr.ac.in','NA');
        String updateQuery = "update admin set contact = ? where admin_id = ? ";
            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(updateQuery);
                preparedStatement.setString(1,contact);
                preparedStatement.setString(2,removeSpaces(this.officerId));

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
//        insert into admin (admin_name,admin_id,contact) values ('Staff Dean’s office','staffdeanoffice@iitrpr.ac.in','NA');
        String updateQuery = "update admin set admin_name = ? where admin_id = ? ";
            try{
                PreparedStatement preparedStatement = this.con.prepareStatement(updateQuery);
                preparedStatement.setString(1,removeSpaces(name));
                preparedStatement.setString(2,removeSpaces(this.officerId));

                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }

        return true;

    }

    private boolean setSession(int year,int session,int status) {
        String delQuery = "delete from current_session";
        String startQuery = "insert into current_session (current_year, current_session, status) values (?, ?, ?)";
        String takesQuery = "create table student_record_"+String.valueOf(year) +" (" +
                "student_id varchar(50) NOT NULL," +
                "course_id varchar(50) NOT NULL," +
                "semester INT NOT NULL," +
                "grades varchar NOT NULL,"+
                "credits numeric(10,2) NOT NULL," +
                "primary key(student_id,course_id,semester)," +
                "foreign key(student_id) references student(student_id)," +
                "foreign key(course_id) references course(course_id)" +
                "); ";
        String isNewYear = "Select * from current_session";
        boolean newYear = false;
        try
        {
            PreparedStatement preparedStatement = this.con.prepareStatement(isNewYear);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                if(rs.getInt("current_year") != year)
                {
                    newYear = true;
                }
            }
            else {
                newYear = true;
            }
            if(newYear)
            {
                preparedStatement = this.con.prepareStatement(takesQuery);
                preparedStatement.execute();
            }
           preparedStatement = this.con.prepareStatement(delQuery);
            preparedStatement.execute();
            preparedStatement = this.con.prepareStatement(startQuery);
            preparedStatement.setInt(1,year);
            preparedStatement.setInt(2,session);
            preparedStatement.setInt(3,status);
            preparedStatement.execute();

            if(status == 8)
            {
                String str = "delete from enrolled_credits";
                preparedStatement = this.con.prepareStatement(str);
                preparedStatement.execute();

                String query = "delete from course_offering ";
                preparedStatement = this.con.prepareStatement(query);
                preparedStatement.execute();
            }
            preparedStatement.close();



        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Session created succesfully!");
        return true;
    }
    private boolean createActivity(int status)
    {
        String updateQuery = "update current_session set status = ?";
        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(updateQuery);
            preparedStatement.setInt(1,status);

            preparedStatement.execute();
            preparedStatement.close();

        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
        return true;
    }
    public boolean isAddCourseActive()
    {
        String query = "select * from current_session";
        boolean isActive = false;
        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
            {
                if(rs.getInt("status") == this.SEMSTART )
                {
                    isActive = true;
                }
            }


            preparedStatement.close();
            rs.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }

        return isActive;
    }
    private boolean addCourse(String course_id,String title,String dept_id,int req_sem,double l,double t , double p,double credits,String course_type,int batch)
    {

        String insertQuery = "insert into course_catalogue (course_id, title,dept_id, req_sem,l,t,p,credits,course_type,batch) values (?,?,?,?,?,?,?,?,?,?)";
        course_id = course_id.toUpperCase();
        dept_id = dept_id.toUpperCase();
        String findCourse = "select * from course where course_id = ?";
        if(!isAddCourseActive())
        {
            System.out.println("Cannot Add course Now as current Activity is not allowing it!");
            return false;
        }
        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(insertQuery);
            preparedStatement.setString(1, removeSpaces(course_id));
            preparedStatement.setString(2, removeSpaces(title));
            preparedStatement.setString(3, removeSpaces(dept_id));
            preparedStatement.setInt(4, req_sem);
            preparedStatement.setDouble(5, l);
            preparedStatement.setDouble(6, t);
            preparedStatement.setDouble(7, p);
            preparedStatement.setDouble(8, credits);
            preparedStatement.setString(9, course_type);
            preparedStatement.setInt(10, batch);
            preparedStatement.execute();

            preparedStatement = this.con.prepareStatement(findCourse);
            preparedStatement.setString(1,course_id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
            {
                 String updateQuery = "update course set dept_id = ?,req_sem = ?,l = ?,t = ?,p = ?,credits = ?  where course_id = ? ";
                 preparedStatement = this.con.prepareStatement(updateQuery);

                 preparedStatement.setString(1,dept_id);
                 preparedStatement.setInt(2,req_sem);
                 preparedStatement.setDouble(3,l);
                preparedStatement.setDouble(4,t);
                preparedStatement.setDouble(5,p);
                preparedStatement.setDouble(6,credits);
                preparedStatement.setString(7,course_id);

                preparedStatement.execute();
            }
            else
            {
                String insertCourseQuery = "insert into course (course_id, title,dept_id, req_sem,l,t,p,credits) values (?,?,?,?,?,?,?,?)";
                preparedStatement = this.con.prepareStatement(insertCourseQuery);


                preparedStatement.setString(1, removeSpaces(course_id));
                preparedStatement.setString(2, removeSpaces(title));
                preparedStatement.setString(3, removeSpaces(dept_id));
                preparedStatement.setInt(4, req_sem);
                preparedStatement.setDouble(5, l);
                preparedStatement.setDouble(6, t);
                preparedStatement.setDouble(7, p);
                preparedStatement.setDouble(8, credits);

                preparedStatement.execute();
            }



            preparedStatement.close();
            rs.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return true;
    }
    public boolean isGradesSubmissionEnded()
    {
        String query = String.format("select * from current_session");
        boolean isStarted = false;

        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int status = rs.getInt("status");
            if(status == this.GRADESUBMITEND)
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
    private boolean promote(String csvFile,int year)
    {
        String updateQuery = "update student set current_sem = current_sem +1 where student_id = ?";
        boolean submitGradeEnd = isGradesSubmissionEnded();
        if(!submitGradeEnd)
        {
            System.out.println("Grades submission has not ended yet! So no promotion");
            return false;
        }

        try{
            Connection connection = this.con;
            PreparedStatement preparedStatement = null ;
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String std_id = removeSpaces(data[0]);
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1,std_id);
                preparedStatement.executeUpdate();

            }

            preparedStatement.close();
            System.out.println("Promoted Successfully!");

        }catch(Exception err)
        {
            System.out.println(err.getMessage());
        }


        return true;
    }
    public boolean demote(String csvFile,int year)
    {
        String updateQuery = "update student set current_sem = current_sem -1 where student_id = ?";
        boolean submitGradeEnd = isGradesSubmissionEnded();
        if(!submitGradeEnd)
        {
            System.out.println("Grades submission has not ended yet! So no promotion");
            return false;
        }

        try{
            Connection connection = this.con;
            PreparedStatement preparedStatement = null ;
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String std_id = removeSpaces(data[0]);
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1,std_id);
                preparedStatement.executeUpdate();

            }

            preparedStatement.close();
            System.out.println("demoted Successfully!");

        }catch(Exception err)
        {
            System.out.println(err.getMessage());
        }


        return true;
    }

    private boolean generateTranscript(String student_id)
    {
        student_id = removeSpaces(student_id);
        student_id = student_id.toLowerCase();

        String stdSemQuery = "select * from student where student_id = ?";

        int stdSem = 0;
        int stdAdmYear = extractIntegral(student_id);

        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(stdSemQuery);
            preparedStatement.setString(1,student_id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
            {
                stdSem = rs.getInt("current_sem");
//                System.out.println(stdSem);
            }else {
                System.out.println("NO Student with this ID Found!");
                return false;
            }
            String filePath = "/home/ashish/hdd/study/java_proj/UniversityManagementSystem/src/main/Transcripts/";

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath +student_id + "_transcript.txt"));

            writer.write("Transcript for Student ID " + student_id + "\n");

//            System.out.println(calculateCGPA(this.con,student_id,extractIntegral(student_id)));
            String query = "SELECT * FROM student_record_"+stdAdmYear+" WHERE student_id = ? and semester = ?  ";
            preparedStatement = null;
            String title = "";
            double totCumulativeCredits = 0.0;
            for(int i =1 ;i<=stdSem;i++)
            {
                title = "SEMESTER " + i;
                preparedStatement = this.con.prepareStatement(query);
                preparedStatement.setString(1, student_id);
                preparedStatement.setInt(2,i);
                rs = preparedStatement.executeQuery();
//                printResultTable(rs);

                double totCredits = 0;
                ArrayList<ArrayList<String>> arr = new ArrayList<>();
                ArrayList<String> row = new ArrayList<>();
                row.add("CourseID");
                row.add("Grades");
                row.add("Credits");
                arr.add(row);

                while (rs.next()) {
                    String courseId = rs.getString("course_id");
                    String grade = rs.getString("grades");
                    Double creditsEarned = rs.getDouble("credits");

                    row = new ArrayList<>();
                    row.add(courseId);
                    row.add(grade);
                    row.add(String.valueOf(creditsEarned));
                    arr.add(row);

                    totCredits += creditsEarned;
                }
                double cgpa = this.calculateCGPA(this.con,student_id,stdAdmYear,i);
                double sgpa = this.calculateSGPA(this.con,student_id,stdAdmYear,i);

                totCumulativeCredits += totCredits;
                row = new ArrayList<>();
                row.add("CGPA = "+cgpa);
                row.add("SGPA = "+sgpa);
                row.add("Total earned credits = " +totCredits);
                arr.add(row);

                System.out.println("data for sem "+i+ " added");

                writer.write(CLI.getFormedTable(title,arr));

                writer.write("\n\n\n\n");

            }
            writer.close();

            rs.close();
            preparedStatement.close();

        }catch(Exception err)
        {
            System.out.println(err.getMessage());
            System.out.println("Transcript NOT Generated!");
            return false;
        }

        System.out.println("Transcript Generated!");
        return true;
    }
    private boolean generateTranscriptForAll(int year)
    {
        String query = "select student_id from student_record_"+year+" group by student_id";
        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                generateTranscript(rs.getString("student_id"));
            }

        }catch(Exception err)
        {
            System.out.println(err.getMessage());
        }
        return true;
    }
    boolean logOut() {
           try{
               this.con.close();
               System.out.println("Admin Successfully logged out!");
           }catch(SQLException err)
           {
               System.out.println(err.getMessage());
           }
        return true;
    }

    boolean printMainMenu()
    {
        ArrayList<ArrayList<String>> officerMainMenu = new ArrayList<>();
        String title = "MAIN MENU";
        ArrayList<String> row = new ArrayList<>();

        row.add("Options");
        row.add("Enter Choice");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Change Profile");
        row.add("1");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Create Session");
        row.add("2");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Create New Activity");
        row.add("3");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("See Course Catalogue");
        row.add("4");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Add new Course");
        row.add("5");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Generate Student Transcript");
        row.add("6");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Promote Students");
        row.add("7");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Add Course Prerequisites of Courses");
        row.add("8");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("View Event");
        row.add("9");
        officerMainMenu.add(row);

        CLI.printMenu(title,officerMainMenu);

        return true;
    }
    private boolean addCoursePrerequisite(String course_id,String prereq_id)
    {
        if(!isAddCourseActive())
        {
            System.out.println("Course Add or drop not active so cannot add prerequisite");
            return false;
        }
        course_id = removeSpaces(course_id);
        prereq_id = removeSpaces(prereq_id);
        course_id = course_id.toUpperCase();
        prereq_id = prereq_id.toUpperCase();

        String query = "insert into prereq(course_id,prereq_id) values(?,?)";
        try{
            PreparedStatement preparedStatement = this.con.prepareStatement(query);
            preparedStatement.setString(1,course_id);
            preparedStatement.setString(2,prereq_id);

        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
        return true;
    }

    boolean printActivityMenu()
    {
        ArrayList<ArrayList<String>> officerMainMenu = new ArrayList<>();
        String title = "ACTIVITY MENU";
        ArrayList<String> row = new ArrayList<>();

        row.add("Options");
        row.add("Enter Choice");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Semester Running");
        row.add("0");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Course Float Starts");
        row.add("1");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Course Float Ends");
        row.add("2");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Student Registration Begins");
        row.add("3");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Student Registration Ends");
        row.add("4");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Grade Submission Begins");
        row.add("5");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Grade Submissin Ends");
        row.add("6");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Semester Starts");
        row.add("7");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Semester Ends");
        row.add("8");
        officerMainMenu.add(row);


        CLI.printMenu(title,officerMainMenu);

        return true;
    }
    boolean printCourseTypeMenu()
    {
        ArrayList<ArrayList<String>> officerMainMenu = new ArrayList<>();
        String title = "COURSE TYPE MENU";
        ArrayList<String> row = new ArrayList<>();

        row.add("Options");
        row.add("Enter Choice");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Program core");
        row.add("pc");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Engineering Core");
        row.add("ec");
        officerMainMenu.add(row);

        row = new ArrayList<>();
        row.add("Elective Core");
        row.add("el");
        officerMainMenu.add(row);


        CLI.printMenu(title,officerMainMenu);

        return true;
    }

    boolean printTranscriptMenu()
    {
        ArrayList<ArrayList<String>> transcriptMenu = new ArrayList<>();
        String title = "TRANSCRIPT MENU";
        ArrayList<String> row = new ArrayList<>();

        row.add("Options");
        row.add("Enter Choice");
        transcriptMenu.add(row);

        row = new ArrayList<>();
        row.add("Generate for single student");
        row.add("1");
        transcriptMenu.add(row);

        row = new ArrayList<>();
        row.add("Generate for a batch");
        row.add("2");
        transcriptMenu.add(row);

        CLI.printMenu(title,transcriptMenu);

        return true;
    }
    public static void runOffice(String userId) {

        AcademicOffice officer = new AcademicOffice(userId);
        String input = "";
        String exitSymbol = "exit";
        Scanner scanner = new Scanner(System.in);



        while(true)
        {
            officer.printMainMenu();
            CLI.inputTaker();
            input = scanner.nextLine();
            input = removeSpaces(input);

            if (input.equals(exitSymbol)) {
                System.out.println("Exiting Officer Menu..");
                break;
            }
            if(input.equals("1"))
            {
                String profile = "";
                officer.printChangeProfileMenu();
                while(true)
                {
                    officer.printChangeProfileMenu();
                    CLI.inputTaker();
                    profile = scanner.nextLine();
                    profile = removeSpaces(profile);
                    if(profile.equals("exit"))
                    {
                        System.out.println("Exiting Change Profile Menu..");
                        break;
                    }
                    if(profile.equals("1"))
                    {
                        System.out.println("Enter New Name");
                        CLI.inputTaker();
                        String name = scanner.nextLine();
                        officer.changeProfileName(name);
                        System.out.println("Name Changed Successfully");

                    }else if(profile.equals("2"))
                    {
                        System.out.println("Enter New Phone Number");
                        CLI.inputTaker();
                        String number = scanner.nextLine();
                        officer.changeProfileNumber(number);
                        System.out.println("Number Changed Succesfully");
                    }else {
                        System.out.println("Invalid Choice!");
                    }
                    CLI.clearScreen();
                }

            }else if (input.equals("2"))
            {
                String input1 = "";
                System.out.println("Enter New Year of Academic Session: ");
                CLI.inputTaker();

               try{
                   input1 = scanner.nextLine();
                   input1 = removeSpaces(input1);
                   int year = Integer.parseInt(input1);

                   System.out.println("Enter New Session of Academic Session: ");
                   CLI.inputTaker();
                   input1 = scanner.nextLine();

                   int session = Integer.parseInt(input1);
                   officer.setSession(year,session,officer.SEMEND);
               }catch(Exception err)
               {
                   err.getMessage();
               }

                CLI.clearScreen();


            }else if (input.equals("3"))
            {
                String status = "";
                while(true)
                {
                    officer.printActivityMenu();
                    CLI.inputTaker();
                    status = scanner.nextLine();
                    if(status.equals(exitSymbol))
                    {
                        System.out.println("Exiting Activity Create Menu...");
                        break;
                    }

                    try{
                        int session = Integer.parseInt(status);
                        officer.createActivity(session);
                    }catch(Exception err)
                    {
                        err.getMessage();
                    }

                    System.out.println("Activity Created Successfully!");

                    CLI.clearScreen();

                }

            }else if (input.equals("4"))
            {
                officer.viewCourseCatalogue(officer.con);

            }else if (input.equals("5"))
            {
                String input1 = "";
                System.out.println("Add Course: ");
                while(true)
                {
                    System.out.println("To exit this menu enter exit or press any key to continue");
                    CLI.inputTaker();
                    input1 = scanner.nextLine();
                    if(input1.equals(exitSymbol))
                    {
                        System.out.println("Exiting Adding Course Menu");
                        break;
                    }
                    try{
                        System.out.println("Enter Course ID: ");
                        CLI.inputTaker();
                        String course_id = scanner.nextLine();

                        System.out.println("Enter course Title: ");
                        CLI.inputTaker();
                        String title = scanner.nextLine();

                        System.out.println("Enter course department: ");
                        CLI.inputTaker();
                        String dept_id = scanner.nextLine();

                        System.out.println("Enter Minimum required Semester");
                        CLI.inputTaker();
                        String req_sem = scanner.nextLine();

                        System.out.println("Enter Number of lectures per week :");
                        CLI.inputTaker();
                        String l = scanner.nextLine();

                        System.out.println("Enter Number of tutorial per week :");
                        CLI.inputTaker();
                        String t = scanner.nextLine();

                        System.out.println("Enter Number of practicals per week :");
                        CLI.inputTaker();
                        String p = scanner.nextLine();

                        System.out.println("Enter Number of credits :");
                        CLI.inputTaker();
                        String credits = scanner.nextLine();

                        System.out.println("Enter Course Type:");
                        String type = "";
                        while(true)
                        {
                            officer.printCourseTypeMenu();
                            CLI.inputTaker();
                            type = scanner.nextLine();
                            if(type.equals(officer.ENGCORETYPE))
                            {
                                type = officer.ENGCORETYPE;
                                break;
                            }else if(type.equals(officer.ELECCORETYPE))
                            {
                                type = officer.ELECCORETYPE;
                                break;
                            }
                            else if(type.equals(officer.PROCORETYPE))
                            {
                                type = officer.PROCORETYPE;break;
                            }
                            else {
                                System.out.println("Invalid Course Type! Enter Again:");
                            }
                        }

                        System.out.println("Enter Batch year above which this course configuration is valid :");
                        CLI.inputTaker();
                        String batch = scanner.nextLine();

                        officer.addCourse(course_id,title,dept_id,Integer.parseInt(req_sem),Double.parseDouble(l),
                                Double.parseDouble(t),Double.parseDouble(p),
                                Double.parseDouble(credits),type,Integer.parseInt(batch));

                        System.out.println("Course Added Successfully!");

                    }catch(Exception err)
                    {
                        System.out.println(err.getMessage());
                    }
                }

            }else if (input.equals("6"))
            {
                String input1 = "";
                while(true)
                {
                    System.out.println("Enter exit to go back or any other string to move forward");
                    CLI.inputTaker();
                    input1 = scanner.nextLine();

                    if(input1.equals(exitSymbol))
                    {
                        System.out.println("Exiting Generate Transcript Menu");
                        break;
                    }
                    officer.printTranscriptMenu();
                    CLI.inputTaker();
                    input1 = scanner.nextLine();
                    if(input1.equals("1"))
                    {
                        System.out.println("Enter the ID of the student to generate his transcript");
                        CLI.inputTaker();
                        String student_id = scanner.nextLine();
                        officer.generateTranscript(student_id);
                    }
                    else if(input1.equals("2"))
                    {
                        System.out.println("Enter the Year of the whose transcript you want to generate ");
                        CLI.inputTaker();
                        String year = scanner.nextLine();
                        year = removeSpaces(year);
                        officer.generateTranscriptForAll(Integer.parseInt(year));

                    }else{
                        System.out.println("wrong Choice");
                    }
                }



            }else if(input.equals("7"))
            {
                String input1 = "";
                String csvFile = "";
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
                    System.out.println("Enter the year of the students whom you want to promote to next sem");
                    CLI.inputTaker();
                    String year = scanner.nextLine();
                    System.out.println("Give the path of the CSV file");
                    CLI.inputTaker();
                    csvFile = scanner.nextLine();
                    try{
                        officer.promote(csvFile,Integer.parseInt(year));
                    }catch(Exception err)
                    {
                        System.out.println(err.getMessage());
                    }
                }
            }else if(input.equals("8"))
            {
                String course_id = "",prereq_id = "";
                while(true)
                {
                    String input1 = "";
                    System.out.println("Enter exit to go back or any other string to proceed");
                    CLI.inputTaker();
                    input1 = scanner.nextLine();
                    if(input1.equals(exitSymbol))
                    {
                        System.out.println("Exiting add prerequisite Menu");
                        break;
                    }
                    System.out.println("Enter the course Id of the course whose prerequisite you want to add");
                    CLI.inputTaker();
                    course_id = scanner.nextLine();

                    System.out.println("Enter the course Id of the prerequisite course");
                    CLI.inputTaker();
                    prereq_id = scanner.nextLine();


                    officer.addCoursePrerequisite(course_id,prereq_id);
                }

            }else if(input.equals("9"))
            {
                officer.viewEvent(officer.con);
            }
            else
            {
                System.out.println("Invalid Choice!");
            }



        }
        officer.logOut();

//        officer.setSession(2020,1, officer.GRADESUBMITSART);
//        officer.changeProfileNumber("7667247058");
//        officer.createActivity(1);

//        cs302,Algorithm Design,CSE,5,3,1,0,3,PC,2020
//        cs101,Discrete Mathematics,CSE,3,1,0		PC	2020
//        cs201	Data Structures	3,1,2	ge103	PC	2020
//        GE103	Introduction to Computing and Data Structures	3,0,3		EC	2020
//
//        officer.addCourse("CS201","Data Structures","CSE",2,3.0,1.0,2.0,3.0,"pc",2020);

//        officer.viewCourseCatalogue(officer.con);
//        officer.viewCourseCatalogue(officer.con);


    }
//    public static void main(String[] args) throws SQLException {
//        AcademicOffice officer = new AcademicOffice("staffdeanoffice@iitrpr.ac.in");
//
////        officer.generateTranscriptForAll(2020);
//        officer.setSession(2020,1,5);
////        String path = "/home/ashish/hdd/study/java_proj/AIIMSPortal/src/main/java/org/example/seedData/give_grades_test.csv";
////        officer.giveGrades(path,2020);
////        officer.changeProfileNumber("999999");
//            officer.logOut();
//
//
//    }

}
