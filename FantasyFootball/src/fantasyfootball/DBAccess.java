/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyfootball;
import java.sql.*;
//import java.util.*;
import org.apache.derby.jdbc.ClientDriver;


/**
 *
 * @author area51
 */
public class DBAccess {
    
    static final String GET_PLAYERS = "SELECT * FROM PLAYER";
    private Connection dbConnection;
    private Statement statement;
    
    
    public DBAccess()throws SQLException
    {
        /* try{
        Class.forName("org.apache.derby.jdbc.ClientDriver");
    }catch(ClassNotFoundException e){
        System.out.println(e);}*/
         DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
         this.dbConnection = DriverManager.getConnection("jdbc:derby://localhost:1527/FantasyFootball");
         this.statement = this.dbConnection.createStatement();
}
    public ResultSet get_players() throws SQLException
    {
        ResultSet results = this.statement.executeQuery(GET_PLAYERS);
        return results;
    }
    
    public String createUser(String lname, String fname, int age, String username, Date bdate)
    {
        try
        {
        String useradd = "INSERT INTO FANTASYUSER VALUES(" + String.valueOf(age) +
                ", " + fname + ", "+ lname + ", " + username + ", '" + bdate.toString() + "')"; //insert command
        
        this.statement.executeUpdate(useradd); //execute insert
        }
        catch(SQLException e)
        {
            return e.getMessage();//if excepted return error
        }
        
        return "Successfully added user!";//else return success message
    }
        
        
    
}
