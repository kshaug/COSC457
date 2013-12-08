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
            
            ResultSet is_drafted = this.statement.executeQuery("SELECT DPICK FROM " + pos + " WHERE " + pos +"_UUID=" + uuid);
            
            is_drafted.next();
            
            if(is_drafted.getInt("DPICK")==0)//if player is currently undrafted, draft else return error
            {
            
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
            else
            {
                return "Sorry this player is already drafted!";
            }
        }
        catch(SQLException e)
        {
            return e.getMessage();
        }
    }
        
        public String dropPlayer(String pos, int uuid, String username)
        {
           pos = pos.toUpperCase();
            
            try
            {
                 String drop_player = "UPDATE " + pos + " SET DPICK = 0 WHERE " + pos +"_UUID = " + uuid;//reset the DraftPick number
                 this.statement.executeUpdate(drop_player);
                 
            switch(pos){//update the uuid to null of the selected player, by UUID
                case "K":
                    this.statement.executeUpdate("UPDATE FANTASYUSER SET K_UUID=NULL WHERE K_UUID =" + uuid + " AND "
                            + "FANTASYUSER.USERNAME = " + username);
                    return "Success";
                case "DEFST":
                    this.statement.executeUpdate("UPDATE FANTASYUSER SET DEFST_UUID=NULL WHERE DEFST_UUID =" + uuid+ " AND "
                            + "FANTASYUSER.USERNAME = " + username);
                    return "Success";
                case "QB":
                    this.statement.executeUpdate("UPDATE FANTASYUSER SET QB_UUID=NULL WHERE QB_UUID = " + uuid+ " AND "
                            + "FANTASYUSER.USERNAME = " + username);
                    return "Success";
                case "RB":
                    this.statement.executeUpdate("UPDATE FANTASYUSER SET RB_UUID=NULL WHERE RB_UUID =" + uuid+ " AND "
                            + "FANTASYUSER.USERNAME = " + username);
                    return "Success";
                case "WRTE":
                    ResultSet is_one = this.statement.executeQuery("SELECT WR1_UUID FROM FANTASYUSER WHERE USERNAME =" + username);
                    is_one.next();//Get WR1_UUID
                    try{
                    if(is_one.getInt("WR1_UUID")==uuid)//if the selected WR is WR1 update WR1_UUID field
                    {
                        this.statement.executeUpdate("UPDATE FANTASYUSER SET WR1_UUID=NULL WHERE USERNAME =" + username);
                    }
                    else//same as WR2
                    {
                        ResultSet is_two = this.statement.executeQuery("SELECT WR2_UUID FROM FANTASYUSER WHERE USERNAME =" + username);
                        is_two.next();
                        
                        if(is_two.getInt("WR2_UUID")==uuid)
                        {
                            this.statement.executeUpdate("UPDATE FANTASYUSER SET WR2_UUID=NULL WHERE USERNAME =" + username);
                        }
                        else
                        {
                            return "You dun goofed";//If none of the above conditions apply you dun goofed
                        }
                    }
                    
                    }
                    catch(SQLException err)
                    {
                        break;
                    }
                    return "Success";
            }
            
            }
            catch(SQLException e)
                    {
                    return e.getMessage();
                    }
            
            return "Something weird happened";//if none of the above conditions trigger, default error message cause I have no clue what happened
            }
        }
        
