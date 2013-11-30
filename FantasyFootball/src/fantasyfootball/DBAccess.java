/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyfootball;
import java.sql.*;
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
         this.dbConnection = DriverManager.getConnection("jdbc:derby://localhost:1527/Fantasy");
         this.statement = this.dbConnection.createStatement();
}
    public ResultSet get_players() throws SQLException
    {
        ResultSet results = this.statement.executeQuery(GET_PLAYERS);
        return results;
    }
    
}
