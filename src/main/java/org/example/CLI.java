package org.example;
import de.vandermeer.asciitable.*;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CLI {
    public static int getMaxColumns(ArrayList<ArrayList<String>> arrayLists) {
        int maxColumns = 0;
        for (ArrayList<String> row : arrayLists) {
            if (row.size() > maxColumns) {
                maxColumns = row.size();
            }
        }
        return maxColumns;
    }
    public static boolean clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return true;
    }public static boolean inputTaker()
    {
        System.out.print(":~$ ");
        return true;
    }
    public static String removeSpacesCLI(String text)
    {
        Pattern pattern = Pattern.compile("(?m)^\\s*(.*?)\\s*$");
        Matcher matcher = pattern.matcher(text);
        String extractedString = null;
        if (matcher.find()) {
            extractedString = matcher.group(1);
        }
        return extractedString;
    }
//    private static void printGenericTable(String title, List<String> messages) {
//        AsciiTable table = new AsciiTable();
//        table.addRule();
//        table.addRow(null, title + ": " + messages.size()).getCells().get(1).getContext().setTextAlignment(TextAlignment.CENTER);
//        table.addRule();
//        int count = 1;
//        for (String mess : messages) {
//            table.addRow("#" + count, mess).getCells().get(0).getContext().setTextAlignment(TextAlignment.CENTER);
//            table.addRule();
//            count ++;
//        }
//        table.getContext().setGrid(A7_Grids.minusBarPlusEquals());
//        table.getRenderer().setCWC(new CWC_LongestWordMin(new int[]{5, 101}));
//        table.renderAsCollection().forEach(SDLogger::info);
//    }
//    public static boolean printMenu(ArrayList<ArrayList<String>> arr)
//    {
//        AsciiTable table = new AsciiTable();
//        int sz = arr.size();
//        int cnt = 0;
//        int maxColumns = getMaxColumns(arr);
////        System.out.println(sz);
//
//        for(ArrayList<String> row: arr)
//        {
//            cnt = 0;
//            table.addRule();
//            List<String> list = new ArrayList<>();
//            for(String s :row)
//            {
//                list.add(s);
//                cnt++;
//            }
//            while(cnt<maxColumns)
//            {
//                cnt++;
//                list.add("NULL");
//            }
////            System.out.println(list);
//            table.addRow(list).setTextAlignment(TextAlignment.CENTER);
//        }
//
//        table.addRule();
//
//        table.getRenderer().setCWC(new CWC_LongestLine());
//
//        System.out.println(table.render());
//        return true;
//    }
    public static boolean printMenu(String title,ArrayList<ArrayList<String>> arr)
    {
        AsciiTable table = new AsciiTable();
        int sz = arr.size();
        int cnt = 0;
        int maxColumns = getMaxColumns(arr);

        table.addRule();
        if(maxColumns == 2)
        {
            table.addRow(null,title).setTextAlignment(TextAlignment.CENTER);
        }
        if(maxColumns == 3)
        {
            table.addRow(null,null,title).setTextAlignment(TextAlignment.CENTER);
        }

//        System.out.println(sz);

        for(ArrayList<String> row: arr)
        {
            cnt = 0;
            table.addRule();
            List<String> list = new ArrayList<>();
            for(String s :row)
            {
                list.add(s);
                cnt++;
            }
            while(cnt<maxColumns)
            {
                cnt++;
                list.add("NULL");
            }
//            System.out.println(list);
            table.addRow(list).setTextAlignment(TextAlignment.CENTER);
        }
        table.addRule();
        table.getRenderer().setCWC(new CWC_LongestLine());
        System.out.println(table.render());
        return true;
    }

    public static String getFormedTable(String title,ArrayList<ArrayList<String>> arr)
    {
        AsciiTable table = new AsciiTable();
        int sz = arr.size();
        int cnt = 0;
        int maxColumns = getMaxColumns(arr);

        table.addRule();
        if(maxColumns == 2)
        {
            table.addRow(null,title).setTextAlignment(TextAlignment.CENTER);
        }
        if(maxColumns == 3)
        {
            table.addRow(null,null,title).setTextAlignment(TextAlignment.CENTER);
        }

//        System.out.println(sz);

        for(ArrayList<String> row: arr)
        {
            cnt = 0;
            table.addRule();
            List<String> list = new ArrayList<>();
            for(String s :row)
            {
                list.add(s);
                cnt++;
            }
            while(cnt<maxColumns)
            {
                cnt++;
                list.add("NULL");
            }
//            System.out.println(list);
            table.addRow(list).setTextAlignment(TextAlignment.CENTER);
        }
        table.addRule();
        table.getRenderer().setCWC(new CWC_LongestLine());
//        System.out.println(table.render());
        return table.render();
    }

//    public static void main(String[] args)
//    {
//        ArrayList<ArrayList<String>> arr = new ArrayList<>();
//        ArrayList<ArrayList<String>> data = new ArrayList<>();
//        ArrayList<String> row1 = new ArrayList<>();
//        row1.add("John");
//        row1.add("Doe");
//        row1.add("25");
//        data.add(row1);
//        ArrayList<String> row2 = new ArrayList<>();
//        row2.add("Jane");
//        row2.add("Doe");
//        row2.add("25");
//        data.add(row2);
//        printMenu("Title",data);
//
////        ArrayList<String> row3 = new ArrayList<>();
////        row3.add("John");
////        row3.add("Doe");
////        arr.add(row3);
////
////        printMenu(arr);
//
//    }

}
