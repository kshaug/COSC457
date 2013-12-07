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
                ", " + fname + ", "+ lname + ", " + username + ", '" + bdate.toString() + "', "
                + "null, null, null, null, null, null)"; //insert command
        
        this.statement.executeUpdate(useradd); //execute insert
        }
        catch(SQLException e)
        {
            return e.getMessage();//if excepted return error
        }
        
        return "Successfully added user!";//else return success message
    }

    public String draftPlayer(String position, int uuid, int pick, String username) //draft player
    {                                                                    //position MUST be the same as shown in DB
        try
        {
            String pos = position.toUpperCase();
            
            String draft_player = "UPDATE " + pos + " SET DPICK = " + pick
                    + " WHERE " + pos +"_UUID = " + uuid;//update the draft pick field for player
            
            this.statement.executeUpdate(draft_player);
            
            switch(pos){//update position in user table based on position user selected
                case "K":
                    this.statement.executeUpdate("UPDATE FANTASYUSER SET"
                            + " K_UUID =" + uuid + " WHERE USERNAME = " + username);
                    return "Success";
                case "DEFST":
                    this.statement.executeUpdate("UPDATE FANTASYUSER SET"
                            + " DEFST_UUID =" + uuid + " WHERE USERNAME = " + username);
                    return "Success";
                case "QB":
                    this.statement.executeUpdate("UPDATE FANTASYUSER SET"
                            + " QB_UUID =" + uuid + " WHERE USERNAME = " + username);
                    return "Success";
                case "RB":
                    this.statement.executeUpdate("UPDATE FANTASYUSER SET"
                            + " RB_UUID =" + uuid + " WHERE USERNAME = " + username);
                    return "Success";
                case "WRTE"://since there are two WR slots, if WR1 is null update, else if WR2 hasn't been updated update
                    this.statement.executeUpdate("UPDATE FANTASYUSER SET"//note: WR2 is updated when WR1 is updated
                            + " WR1_UUID = COALESCE(WR1_UUID," + uuid + "),"
                            + "WR2_UUID = (CASE WHEN WR2_UUID = WR1_UUID THEN " + uuid + " ELSE " +
                            uuid + "END)"
                            + " WHERE USERNAME = " + username );
                    return "Success";
                           
            }
                        
            
            return "Successfully drafted!";
        }
        catch(SQLException e)
        {
            return e.getMessage();
        }
    }
        
    
}
