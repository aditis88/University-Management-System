package org.example;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.CachedRowSet;
import javax.xml.transform.Result;

public class AbstractCommonFunctions {

    public final int SEMRUNNING = 0;
    public final int COURSEFLOATACTIVE =1;
    public final int COURSEFLOATEND = 2;
    public final int STUDENTREGBEGIN = 3;
    public final int STUDENTREGEND = 4;
    public final int GRADESUBMITSART = 5;
    public final int GRADESUBMITEND = 6;
    public final int SEMSTART = 7;
    public final int SEMEND = 8;
    public final int CREDITLIMIT = 18;
    public final String ENGCORETYPE = "ec";
    public final String ELECCORETYPE = "el";
    public final String PROCORETYPE = "pc";
    public static ArrayList<Double> getBtechGradCred(Connection con)
    {
        ArrayList<Double> arr = new ArrayList<>();
        String fetchCredQuery = "select * from gradcheck";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(fetchCredQuery);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                arr.add(rs.getDouble("program_core_credits"));
                arr.add(rs.getDouble("engineering_core_credits"));
                arr.add(rs.getDouble("elective_core_credits"));
            }
            rs.close();
            preparedStatement.close();

        }catch(SQLException er)
        {
            System.out.println(er.getMessage());
        }

        return arr;
    }
    public static String getBtechProjCode(Connection con)
    {
        String code = null;
        String fetchCredQuery = "select * from gradcheck";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(fetchCredQuery);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                code = rs.getString("must_complete_btp");
            }
            rs.close();
            preparedStatement.close();

        }catch(SQLException er)
        {
            System.out.println(er.getMessage());
        }

        return code;
    }


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
    public static int extractIntegral(String str) {
        int integralPart = 0;
        try {
            Pattern pattern = Pattern.compile("^\\d+");
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                integralPart = Integer.parseInt(matcher.group());
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return integralPart;
    }

    public boolean printResultTable(ResultSet rs)  {
            try{
                ResultSetMetaData rsmd = rs.getMetaData();
                int numColumns = rsmd.getColumnCount();

                AsciiTable table = new AsciiTable();

                List<String> tableHeader = new ArrayList<>();
                table.addRule();
                for(int i=1;i<=numColumns;i++)
                {
                    Object value = rsmd.getColumnName(i);
                    tableHeader.add(value.toString());

                }
                table.addRule();
                table.addRow(tableHeader);
                table.addRule();


                // Add the rows to the ASCII table
                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    for (int i = 1; i <= numColumns; i++) {
                        Object value = rs.getObject(i);
                        row.add(value.toString());

                    }
                    table.addRule();
                    table.addRow(row);
                }
                table.addRule();
                table.getRenderer().setCWC(new CWC_LongestLine());
                // Print the ASCII table
                System.out.println(table.render());
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }

        return true;
    }
    public boolean viewCourseCatalogue (Connection con)
    {
        String query = "select * from course_catalogue";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            printResultTable(rs);
            preparedStatement.close();
            // Close the result set, statement, and connection
            rs.close();

        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
        return true;
    }

    // Needs updation
    boolean checkCourseDepartment(Connection con,String course_id,String dept_id)
    {
        course_id = removeSpaces(course_id);
        course_id = course_id.toUpperCase();
        dept_id = removeSpaces(dept_id);
        dept_id = dept_id.toUpperCase();

        ResultSet rs = null;
        boolean correct = false;
        String query = "select * from course_catalogue   where course_id = ? and dept_id = ?";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,course_id);
            preparedStatement.setString(2,dept_id);
            rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                correct = true;
//                System.out.println("Result Set contains at least one row!");
            }
            else {
//                System.out.println("Result Set contain no rows!");
            }
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,course_id);
            preparedStatement.setString(2,dept_id);
            rs = preparedStatement.executeQuery();
            printResultTable(rs);

            rs.close();
            preparedStatement.close();

        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

        return correct;
    }
    double fetchCourseCredits(Connection con,String course_id,int year)
    {
        double credits = 0;
        String fetchQuery = "Select course_id,credits,batch from course_catalogue where course_id = ? group by course_id, credits, batch having batch <= ? order by batch desc ";
        course_id = removeSpaces(course_id);
        course_id = course_id.toUpperCase();

        try{
            PreparedStatement preparedStatement = con.prepareStatement(fetchQuery);
            preparedStatement.setString(1,course_id);
            preparedStatement.setInt(2,year);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                credits = rs.getDouble("credits");
            }
            else {
                System.out.println("No such course is available");
            }
//            printResultTable(rs);
            preparedStatement.close();
            rs.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

        return credits;
    }
    String fetchCourseType(Connection con,String course_id,int year)
    {
        String type = "none";
        String fetchQuery = "Select course_id,credits,batch,course_type from course_catalogue where course_id = ? group by course_id, credits, batch,course_type having batch <= ? order by batch desc ";
        course_id = removeSpaces(course_id);
        course_id = course_id.toUpperCase();

        try{
            PreparedStatement preparedStatement = con.prepareStatement(fetchQuery);
            preparedStatement.setString(1,course_id);
            preparedStatement.setInt(2,year);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                type = rs.getString("course_type");
            }
            else {
                System.out.println("No such course is available");
            }
//            printResultTable(rs);
            preparedStatement.close();
            rs.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

        return type;
    }
    int fetchMinReqSem(Connection con,String course_id,int year)
    {
        int req_sem = 0;
        String fetchQuery = "Select course_id,req_sem,batch from course_catalogue where course_id = ? group by course_id, req_sem, batch having batch <= ? order by batch desc ";
        course_id = removeSpaces(course_id);
        course_id = course_id.toUpperCase();

        try{
            PreparedStatement preparedStatement = con.prepareStatement(fetchQuery);
            preparedStatement.setString(1,course_id);
            preparedStatement.setInt(2,year);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                req_sem = rs.getInt("req_sem");
            }
            else {
                System.out.println("No such course is available");
            }
//            printResultTable(rs);
            preparedStatement.close();
            rs.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

        return req_sem;
    }
    protected boolean seeGrades(Connection con,int year,String student_id)
    {
        String findQuery = "Select * from student_record_"+String.valueOf(year)+" where student_id = ?";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(findQuery);
            preparedStatement.setString(1,removeSpaces(student_id));
            ResultSet rs = preparedStatement.executeQuery();
            printResultTable(rs);
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return true;
    }
    double getGradeValue(String grade)
    {
        double val = 0;
        if(grade.equals("A"))
        {
            val = 10;
        }
        else if(grade.equals("A-"))
        {
            val = 9;
        }else if(grade.equals("B"))
        {
            val = 8;
        } else if (grade.equals("B-"))
        {
            val = 7;
        }else if(grade.equals("C"))
        {
            val = 6;
        }
        else if(grade.equals("C-"))
        {
            val = 5;
        }
        else if(grade.equals("D"))
        {
            val = 4;
        }else if(grade.equals("E"))
        {
            val = 2;
        }
        else if(grade.equals("F"))
        {
            val = 0;
        }
        return val;
    }
    public double calculateCGPA(Connection con,String std_id, int year)
    {
        double cgpa = 0.0;
        double totalRegisteredCredits =0;
        double totalEarnedCredits = 0;
        double cumulativeProduct = 0;

        std_id = removeSpaces(std_id);
        std_id = std_id.toLowerCase();

        String findQuery = "Select * from student_record_"+String.valueOf(year)+" where student_id = ? and grades <> 'NA' ";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(findQuery);
            preparedStatement.setString(1,std_id);
            ResultSet rs = preparedStatement.executeQuery();
//            printResultTable(rs);
            double cred = 0;
            double gradePoints =0;
            while(rs.next())
            {
               cred = rs.getDouble("credits");
               gradePoints = getGradeValue(rs.getString("grades"));
                totalEarnedCredits += cred;
                cumulativeProduct += cred*gradePoints;
            }
            if(totalEarnedCredits>0)
            {
                cgpa = (cumulativeProduct/totalEarnedCredits);
            }


            preparedStatement.close();
            rs.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return cgpa;

    }
    public double calculateCGPA(Connection con,String std_id, int year,int sem)
    {
        double cgpa = 0.0;
        double totalRegisteredCredits =0;
        double totalEarnedCredits = 0;
        double cumulativeProduct = 0;

        std_id = removeSpaces(std_id);
        std_id = std_id.toLowerCase();

        String findQuery = "Select * from student_record_"+String.valueOf(year)+" where student_id = ? and semester <= ? and grades <> 'NA' ";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(findQuery);
            preparedStatement.setString(1,std_id);
            preparedStatement.setInt(2,sem);
            ResultSet rs = preparedStatement.executeQuery();
//            printResultTable(rs);
            double cred = 0;
            double gradePoints =0;
            while(rs.next())
            {
                cred = rs.getDouble("credits");
                gradePoints = getGradeValue(rs.getString("grades"));
                totalEarnedCredits += cred;
                cumulativeProduct += cred*gradePoints;
            }
            if(totalEarnedCredits>0)
            {
                cgpa = (cumulativeProduct/totalEarnedCredits);
            }


            preparedStatement.close();
            rs.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return cgpa;

    }
    public double calculateSGPA(Connection con,String std_id, int year,int sem)
    {
        double cgpa = 0.0;
        double totalRegisteredCredits =0;
        double totalEarnedCredits = 0;
        double cumulativeProduct = 0;

        std_id = removeSpaces(std_id);
        std_id = std_id.toLowerCase();

        String findQuery = "Select * from student_record_"+String.valueOf(year)+" where student_id = ? and semester = ? and grades <> 'NA' ";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(findQuery);
            preparedStatement.setString(1,std_id);
            preparedStatement.setInt(2,sem);
            ResultSet rs = preparedStatement.executeQuery();
//            printResultTable(rs);
            double cred = 0;
            double gradePoints =0;
            while(rs.next())
            {
                cred = rs.getDouble("credits");
                gradePoints = getGradeValue(rs.getString("grades"));
                totalEarnedCredits += cred;
                cumulativeProduct += cred*gradePoints;
            }
            if(totalEarnedCredits>0)
            {
                cgpa = (cumulativeProduct/totalEarnedCredits);
            }


            preparedStatement.close();
            rs.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return cgpa;

    }

    boolean isGraduated (Connection con, String student_id)
    {
        boolean graduated = false;
        boolean isBTP = false;
        String BTPCourseCode = "";
        student_id = removeSpaces(student_id);
        int studentAdmYear = extractIntegral(student_id);
//        System.out.println("student ADMN year" + studentAdmYear);
        String course_type = "";
        String semQuery = "select * from student where student_id = ?";
        String courseCompletedQuery = "select * from student_record_"+String.valueOf(studentAdmYear) + " where student_id = ? and grades <> 'NA' ";


        try{
            PreparedStatement preparedStatement = con.prepareStatement(semQuery);

            preparedStatement.setString(1,student_id);
            ResultSet rs = preparedStatement.executeQuery();

            int student_current_sem = 0;
            if(rs.next())
            {
                student_current_sem = rs.getInt("current_sem");
            }
            if(student_current_sem >= 8)
            {
//            System.out.println(studentAdmYear);
                System.out.println("Student's current semester is "+student_current_sem +" for gradution");
            }
            else {
                System.out.println("Not eligible for graduation due to less semesters passed");
                return false;
            }
            double cgpa = calculateCGPA(con,student_id,studentAdmYear);
            if(cgpa>=5.0)
            {
                System.out.println("CGPA is "+cgpa+ " which is enough for graduation");
            }
            else {
                System.out.println("CGPA is "+cgpa +" which is NOT enough for graduation");
                return false;
            }

            ArrayList<Double> arr = getBtechGradCred(con);
            BTPCourseCode = getBtechProjCode(con);

//            System.out.println(arr+"\n"+BTPCourseCode);

            preparedStatement = con.prepareStatement(courseCompletedQuery);

            preparedStatement.setString(1,student_id);
            rs = preparedStatement.executeQuery();
//            printResultTable(rs);

            Double programCoreCredtis = 0.0,electiveCredits = 0.0,engineeringCoreCredits = 0.0;
            String failGrade = "F";
            while(rs.next())
            {
                course_type = fetchCourseType(con,rs.getString("course_id"),studentAdmYear);

                if(course_type.equals(ELECCORETYPE))
                {
                    if(!failGrade.equals(rs.getString("grades")))
                    {
                        electiveCredits += rs.getDouble("credits");
                    }

                } else if (course_type.equals(ENGCORETYPE)) {
                    if(!failGrade.equals(rs.getString("grades")))
                    {
                        engineeringCoreCredits += rs.getDouble("credits");;
                    }
                }
                else if(course_type.equals(PROCORETYPE))
                {
                    if(!failGrade.equals(rs.getString("grades")))
                    {
                        programCoreCredtis += rs.getDouble("credits");;
                    }
                }

                if(BTPCourseCode.equals(rs.getObject("Course_id")))
                {
                    isBTP = true;
                }
            }
            String title = "CREDITS DISTRIBUTION";
            ArrayList<ArrayList<String>> printArr = new ArrayList<>();

            ArrayList<String> row = new ArrayList<>();
            row.add("Course Type");
            row.add("Required");
            row.add("Earned");
            printArr.add(row);

            row = new ArrayList<>();
            row.add("Program Core Credits");
            row.add(String.valueOf(arr.get(0)));
            row.add(String.valueOf(programCoreCredtis));
            printArr.add(row);

            row = new ArrayList<>();
            row.add("Engineering Core Credits");
            row.add(String.valueOf(arr.get(1)));
            row.add(String.valueOf(engineeringCoreCredits));
            printArr.add(row);

            row = new ArrayList<>();
            row.add("Elective  Credits");
            row.add(String.valueOf(arr.get(2)));
            row.add(String.valueOf(electiveCredits));
            printArr.add(row);

            CLI.printMenu(title,printArr);

//            System.out.println("Program Core Credits :" + programCoreCredtis + " required : " + arr.get(0));
//            System.out.println("Engineering Core Credits :" + engineeringCoreCredits + " required : " + arr.get(1));
//            System.out.println("Elective Credits :" + electiveCredits + " required : " + arr.get(2));

            if(programCoreCredtis >= arr.get(0) && engineeringCoreCredits >= arr.get(1) && electiveCredits >= arr.get(2))
            {
                if(isBTP)
                {
                    graduated = true;
                    System.out.println("Student is Eligible for Gradutaion!");
                }
            }

            rs.close();
            preparedStatement.close();

        }catch(SQLException err)
        {
            System.out.println();
        }

        if(graduated == false)
        {
            System.out.println("Student is NOT eligible for graduation!");
        }
        return graduated;
    }
    boolean printChangeProfileMenu()
    {
        ArrayList<ArrayList<String>> changeMenu = new ArrayList<>();
        String title = "Change Profile";
        ArrayList<String> row = new ArrayList<>();

        row.add("Options");
        row.add("Enter Choice");
        changeMenu.add(row);

        row = new ArrayList<>();
        row.add("Change Profile Name");
        row.add("1");
        changeMenu.add(row);

        row = new ArrayList<>();
        row.add("Change Profile Contact");
        row.add("2");
        changeMenu.add(row);

        CLI.printMenu(title,changeMenu);

        return true;
    }
    //        public final int SEMRUNNING = 0;
//        public final int COURSEFLOATACTIVE =1;
//        public final int COURSEFLOATEND = 2;
//        public final int STUDENTREGBEGIN = 3;
//        public final int STUDENTREGEND = 4;
//        public final int GRADESUBMITSART = 5;
//        public final int GRADESUBMITEND = 6;
//        public final int SEMSTART = 7;
//        public final int SEMEND = 8;
    String getCurrentEvent(int status)
    {

        String event = "";
        switch (status) {
            case  SEMRUNNING:
                event = "Semester is running";
                break;
            case COURSEFLOATACTIVE:
                event = "Course Float Active";
                break;
            case COURSEFLOATEND:
                event = "Course Float End";
                break;
            case STUDENTREGBEGIN:
                event = "Student Registration Begins";
                break;
            case STUDENTREGEND:
                event = "Student Registraion Ends";
                break;
            case GRADESUBMITSART:
                event = "Grades Submission Starts";
                break;
            case GRADESUBMITEND:
                event = "Grades Submission has ended";
                break;
            case SEMSTART:
                event = "Semester has Started";
                break;
            case SEMEND :
                event = "Semester has ended";
                break;
            default:
                event = "Event Not defined Yet";
        }

        return event;

    }

    public  boolean viewEvent(Connection con)
    {
        String query = "select * from current_session";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            String title = "EVENT INFORMATION";

            ArrayList<ArrayList<String>> arr = new ArrayList<>();
            ArrayList<String> row = new ArrayList<>();

            row.add("Current Year");
            row.add("Current Session");
            row.add("Current Event");
            arr.add(row);

            row = new ArrayList<>();
            row.add(String.valueOf(rs.getInt("current_year")));
            row.add(String.valueOf(rs.getInt("current_session")));
            row.add(getCurrentEvent(rs.getInt("status")));
            arr.add(row);

            CLI.printMenu(title,arr);

            preparedStatement.close();
            rs.close();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }

        return true;
    }
    boolean viewCourseOffering (Connection con)  {

        try{
            String query = "Select * from course_offering";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            printResultTable(rs);
        }catch(SQLException er)
        {
            System.out.println(er.getMessage());
        }

        return true;
    }
    boolean logInTimeStamp(String userId,Connection con)  {
        try{
            String query = "INSERT INTO user_sessions (user_id, login_time) VALUES (?, NOW())";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,userId);
            preparedStatement.execute();
        }catch (SQLException er)
        {
            System.out.println(er.getMessage());
        }
        return true;
    }
    boolean logOutTimeStamp(String userId,Connection con)  {
        try{
            String query = "UPDATE user_sessions\n" +
                    "SET logout_time = NOW()\n" +
                    "WHERE user_id = ? AND logout_time IS NULL;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,userId);
            preparedStatement.execute();
        }catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return true;
    }
}
