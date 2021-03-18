/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcproject;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mark Garcia
 */
public class JDBCProject {
//  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormat="%-5s%-15s%-15s%-15s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to 
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        String sql;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            
            boolean running = true;
            
            while (running) {
                System.out.println("CHOOSE FROM OPTIONS BELOW:");
                System.out.println("1. List all writing groups");
                System.out.println("2. List all the data for a group");
                System.out.println("3. List all publishers");
                System.out.println("4. List all the data for a publisher");
                System.out.println("5. List all book titles");
                System.out.println("6. List all data for a single book");
                System.out.println("7. Insert new book");
                System.out.println("8. Insert new publisher");
                System.out.println("9. Remove a single book");
                System.out.println("10. Exit");
                
                try {
                    String userin = in.nextLine().stripTrailing();
                
                    if (userin.equals("1")){ //list all writing groups
                        System.out.println("LIST OF WRITING GROUPS: ");
                        stmt = conn.createStatement();
                        sql = "SELECT GroupName FROM WritingGroups";
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            String gn = rs.getString("GroupName");
                            System.out.println(dispNull(gn));
                        }

                        rs.close();
                        stmt.close();
                        System.out.println();

                    } else if (userin.equals("2")){
                        //Scanner n = new Scanner(System.in);
                        System.out.println("Enter a Group's Name: ");
                        String g = in.nextLine();
                        sql = "SELECT * FROM WritingGroups NATURAL JOIN Books NATURAL JOIN Publishers WHERE UPPER(WritingGroups.GROUPNAME) = UPPER(?)";
                        try {
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setString(1, g);
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()){
                                System.out.println("PRINTING ALL DATA FOR " + g);
                                System.out.printf("%-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s\n", "Publisher Name", "Group Name", "Head Writer", "Year Formed", "Subject", "Book Title", "Year Published", "Number of Pages", "Publisher Address", "Publisher Phone", "Publisher EMAIL");

                                String pubname = rs.getString("PublisherName");
                                String gname = rs.getString("GroupName");
                                String hwriter = rs.getString("HeadWriter");
                                String yformed = rs.getString("YearFormed");
                                String sub = rs.getString("Subject");
                                String btitle = rs.getString("BookTitle");
                                String ypub = rs.getString("YearPublished");
                                String np = rs.getString("NumberPages");
                                String pubadd = rs.getString("PublisherAddress");
                                String pubphone = rs.getString("PublisherPhone");
                                String pubemail = rs.getString("PublisherEmail");

                                System.out.printf("%-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s\n", dispNull(pubname), dispNull(gname), dispNull(hwriter), dispNull(yformed), dispNull(sub), dispNull(btitle), dispNull(ypub), dispNull(np), dispNull(pubadd), dispNull(pubphone), dispNull(pubemail));
                            }

                            rs.close();
                            ps.close();
                            //n.close();
                            System.out.println();
                        } catch (SQLException sqle){
                            System.out.println(sqle.getMessage());
                        }


                    } else if (userin.equals("3")){ //list all publishers
                        System.out.println("LIST OF PUBLISHERS: ");
                        stmt = conn.createStatement();
                        sql = "SELECT PublisherName FROM Publishers";
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()){
                            String pn = rs.getString("PublisherName");
                            System.out.println(dispNull(pn));
                        }

                        rs.close();
                        stmt.close();
                        System.out.println();

                    } else if (userin.equals("4")){
                        //Scanner n = new Scanner(System.in);
                        System.out.println("Enter a publisher's name: ");
                        String p = in.nextLine();
                        System.out.println("PRINTING ALL DATA FOR: " + p);
                        sql = "SELECT DISTINCT * FROM publishers INNER JOIN books ON books.PUBLISHERNAME = publishers.PUBLISHERNAME INNER JOIN writingGroups ON writinggroups.GROUPNAME = books.GROUPNAME WHERE UPPER(publishers.PUBLISHERNAME) = UPPER(?)";
                        try {
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setString(1, p);
                            ResultSet rs = ps.executeQuery();
                            System.out.println();
                            System.out.printf("%-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s|\n", "Publisher Name", "Publisher Address", "Publisher Phone Number", "Publisher Email", "Group Name", "Book Title", "Publisher Name", "Year Published", "Number of Pages", "Group Name", "Head Writer", "Year Formed", "Subject");
                            while (rs.next()){
                                String pn = rs.getString("publisherName");
                                String pa = rs.getString("publisherAddress");
                                String pp = rs.getString("publisherPhone");
                                String pe = rs.getString("publisherEmail");
                                String gn = rs.getString("groupName");
                                String bt = rs.getString("bookTitle");
                                String yp = rs.getString("yearPublished");
                                String np = rs.getString("numberPages");
                                String hw = rs.getString("headWriter");
                                String yf = rs.getString("yearFormed");
                                String s = rs.getString("subject");
                                
                                System.out.printf("%-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s|\n", pn, pa, pp, pe, gn, bt, pn, yp, np, gn, hw, yf, s);

                            }
                            rs.close();
                            ps.close();
                            System.out.println();
                            
                        } catch (SQLException sqle) {
                            System.out.println(sqle.getMessage());
                        }

                    } else if (userin.equals("5")){
                        System.out.println("LIST OF BOOKS: ");
                        stmt = conn.createStatement();
                        sql = "SELECT bookTitle FROM Books";
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()){
                            String bt = rs.getString("bookTitle");
                            System.out.println(dispNull(bt));
                        }

                        rs.close();
                        stmt.close();
                        System.out.println();                        

                    } else if (userin.equals("6")){
                        System.out.println("ENTER BOOK TITLE: ");
                        String b = in.nextLine();
                        System.out.println("PRINTING ALL DATA FOR: " + b);
                        sql = "SELECT * FROM BOOKS NATURAL JOIN PUBLISHERS NATURAL JOIN WRITINGGROUPS WHERE UPPER(BOOKTITLE) LIKE UPPER(?)";
                        try {
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setString(1, b);
                            ResultSet rs = ps.executeQuery();
                            System.out.println();
                            System.out.printf("%-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s|\n", "Group Name", "Publisher Name", "Book Title", "Year Published", "Number of Pages", "Publisher Address", "Publisher Phone", "Publisher Email", "Head Writer", "Year Formed", "Subject");
                            while (rs.next()){
                                String pn = rs.getString("publisherName");
                                String pa = rs.getString("publisherAddress");
                                String pp = rs.getString("publisherPhone");
                                String pe = rs.getString("publisherEmail");
                                String gn = rs.getString("groupName");
                                String bt = rs.getString("bookTitle");
                                String yp = rs.getString("yearPublished");
                                String np = rs.getString("numberPages");
                                String hw = rs.getString("headWriter");
                                String yf = rs.getString("yearFormed");
                                String s = rs.getString("subject");
                                
                                System.out.printf("%-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s| %-5s|\n", gn, pn, bt, yp, np, pa, pp, pe, hw, yf, s);

                            }
                            rs.close();
                            ps.close();
                            System.out.println();
                            
                        } catch (SQLException sqle) {
                            System.out.println(sqle.getMessage());
                        }
                        

                    } else if (userin.equals("7")){
                        System.out.println("Enter a Writing Group's name: ");
                        String group = in.nextLine();
                        System.out.println("Enter a Book Title: ");
                        String title = in.nextLine();
                        System.out.println("Enter a Publisher's Name: ");
                        String pubName = in.nextLine();
                        System.out.println("Enter the Year Published: ");
                        String yearPub = in.nextLine();
                        System.out.println("Enter the Number of Pages: ");
                        String numPages = in.nextLine();
                        sql = "INSERT INTO books(groupName, bookTitle, publisherName, yearPublished, numberPages) VALUES (?, ?, ?, ?, ?)";
                        try {
                           PreparedStatement ps = conn.prepareStatement(sql);
                           ps.setString(1, group);
                           ps.setString(2, title);
                           ps.setString(3, pubName);
                           ps.setInt(4, Integer.valueOf(yearPub));
                           ps.setInt(5, Integer.valueOf(numPages));
                           
                           ps.executeUpdate();
                           
                           System.out.println("Book " + title + " inserted...");
                           
                           ps.close();
                           System.out.println();
                           
                        } catch(SQLException sqle){
                            System.out.println(sqle.getMessage());
                        }
                        

                    } else if (userin.equals("8")){
                        System.out.println("Enter a Publisher's Name: ");
                        String newpName = in.nextLine();
                        System.out.println("Enter a Publisher's Address: ");
                        String newpAd = in.nextLine();
                        System.out.println("Enter the Publisher's Phone Number: ");
                        String newpPhone = in.nextLine();
                        System.out.println("Enter the Publisher's Email: ");
                        String newpEmail = in.nextLine();
                        sql = "INSERT INTO Publishers (publisherName, publisherAddress, publisherPhone, publisherEmail) VALUES (?, ?, ?, ?)";
                        
                        try{
                            //insert new publisher
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setString(1, newpName);
                            ps.setString(2, newpAd);
                            ps.setString(3, newpPhone);
                            ps.setString(4, newpEmail);
                            
                            ps.executeUpdate();
                            
                            ps.close();
                            
                            System.out.println("Publisher " + newpName + " added...");
                            
                        } catch(SQLException sqle){
                            System.out.println(sqle.getMessage());
                        }
                        
                        String sql2 = "UPDATE books SET books.publisherName = ? WHERE books.publisherName = ?";
                        System.out.println("Enter the name of the old publisher: ");
                        String oldpName = in.nextLine();
                        
                        try {
                            PreparedStatement ps2 = conn.prepareStatement(sql2);
                            ps2.setString(1, newpName);
                            ps2.setString(2, oldpName);
                            
                            ps2.executeUpdate();
                            
                            ps2.close();
                            System.out.println();
                            
                            
                        } catch (SQLException sqle2){
                            System.out.println(sqle2.getMessage());
                        }

                    } else if (userin.equals("9")){
                        sql = "DELETE FROM books where books.bookTitle = ?";
                        System.out.println("Enter a books name: ");
                        String bookToRemove =  in.nextLine();
                        
                        try{
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setString(1, bookToRemove);
                            
                            ps.executeUpdate();
                            
                            ps.close();
                            System.out.println();
                            
                        } catch (SQLException sqle){
                            System.out.println(sqle.getMessage());
                        }
                        
                        

                    } else if (userin.equals("10")){
                        running = false;

                    } else {
                        System.out.println("Invalid entry try again.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Invalid Entry... Restart Program");
                    break;
                }
                        
            }

//            //STEP 4: Execute a query
//            System.out.println("Creating statement...");
//            stmt = conn.createStatement();
//            String sql;
//            sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            //STEP 5: Extract data from result set
//            System.out.printf(displayFormat, "ID", "First Name", "Last Name", "Phone #");
//            while (rs.next()) {
//                //Retrieve by column name
//                String id = rs.getString("au_id");
//                String phone = rs.getString("phone");
//                String first = rs.getString("au_fname");
//                String last = rs.getString("au_lname");
//
//                //Display values
//                System.out.printf(displayFormat, 
//                        dispNull(id), dispNull(first), dispNull(last), dispNull(phone));
//            }
//            //STEP 6: Clean-up environment
//            rs.close();
//            stmt.close();
//            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
        
}//end FirstExample}
