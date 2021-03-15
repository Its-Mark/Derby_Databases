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
                
                int userin = in.nextInt();
                
                if (userin == 1){ //list all writing groups
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
                    
                } else if (userin == 2){
                    /*
                    SELECT * FROM WritingGroups NATURAL JOIN Books NATURAL JOIN Publishers WHERE UPPER(WritingGroups.GROUPNAME) = UPPER('The Fellowship of Souther Writers');
                    */
                    System.out.println("Enter a Group's Name: ");
                    
                    
                } else if (userin == 3){ //list all publishers
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
                    
                } else if (userin == 10){
                    running = false;
                    
                } else {
                    System.out.println("Invalid entry try again.");
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
