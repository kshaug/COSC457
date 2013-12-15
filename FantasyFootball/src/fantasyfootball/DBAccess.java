/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasyfootball;

import java.util.HashMap;
import java.sql.*;
import java.util.ArrayList;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author area51
 */
public class DBAccess {

    static final String GET_PLAYERS = "SELECT * FROM PLAYER";
    private Connection dbConnection;
    private Statement statement;

    public DBAccess() throws SQLException {
        /* try{
         Class.forName("org.apache.derby.jdbc.ClientDriver");
         }catch(ClassNotFoundException e){
         System.out.println(e);}*/
        DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
        this.dbConnection = DriverManager.getConnection("jdbc:derby://localhost:1527/FantasyFootball");
        this.statement = this.dbConnection.createStatement();
    }

    public ResultSet get_players() throws SQLException {
        ResultSet results = this.statement.executeQuery(GET_PLAYERS);
        return results;
    }

    public String createUser(String lname, String fname, int age, String username, Date bdate) {
        try {
            
            String useradd = "INSERT INTO FANTASYUSER VALUES(" + String.valueOf(age)
                    + ", " + fname + ", " + lname + ", " + username + ", '" + bdate.toString() + "', null)"; //insert command

            this.statement.executeUpdate(useradd); //execute insert
            String exe = "INSERT INTO TEAM (QB_UUID, RB_UUID, WR1_UUID, WR2_UUID, K_UUID,  DEFST_UUID, USERNAME) VALUES(null, null, null, null, null, null," + username + ")";
            System.out.println(exe);
            this.statement.executeUpdate(exe);
            
        } catch (SQLException e) {
            return e.getMessage();//if excepted return error
        }

        return "Successfully added user!";//else return success message
    }

    public String draftPlayer(String position, int uuid, int pick, String username) //draft player
    {                                                                    //position MUST be the same as shown in DB
        try {
            String pos = position.toUpperCase();

            ResultSet is_drafted = this.statement.executeQuery("SELECT DPICK FROM " + pos + " WHERE " + pos + "_UUID=" + uuid);

            is_drafted.next();

            if (is_drafted.getInt("DPICK") == 0)//if player is currently undrafted, draft else return error
            {

                String draft_player = "UPDATE " + pos + " SET DPICK = " + pick
                        + " WHERE " + pos + "_UUID = " + uuid;//update the draft pick field for player

                this.statement.executeUpdate(draft_player);

                switch (pos) {//update position in user table based on position user selected
                    case "K":
                        this.statement.executeUpdate("UPDATE TEAM SET"
                                + " K_UUID =" + uuid + " WHERE USERNAME = " + username);
                        return "Success";
                    case "DEFST":
                        this.statement.executeUpdate("UPDATE TEAM SET"
                                + " DEFST_UUID =" + uuid + " WHERE USERNAME = " + username);
                        return "Success";
                    case "QB":
                        this.statement.executeUpdate("UPDATE TEAM SET"
                                + " QB_UUID =" + uuid + " WHERE USERNAME = " + username);
                        return "Success";
                    case "RB":
                        this.statement.executeUpdate("UPDATE TEAM SET"
                                + " RB_UUID =" + uuid + " WHERE USERNAME = " + username);
                        return "Success";
                    case "WRTE"://since there are two WR slots, if WR1 is null update, else if WR2 hasn't been updated update
                        this.statement.executeUpdate("UPDATE TEAM SET"//note: WR2 is updated when WR1 is updated
                                + " WR1_UUID = COALESCE(WR1_UUID," + uuid + "),"
                                + "WR2_UUID = (CASE WHEN WR2_UUID = WR1_UUID THEN " + uuid + " ELSE "
                                + uuid + "END)"
                                + " WHERE USERNAME = " + username);
                        return "Success";

                }

                return "Successfully drafted!";
            } else {
                return "Sorry this player is already drafted!";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String dropPlayer(String pos, int uuid, String username) {
        pos = pos.toUpperCase();

        try {
            String drop_player = "UPDATE " + pos + " SET DPICK = 0 WHERE " + pos + "_UUID = " + uuid;//reset the DraftPick number
            this.statement.executeUpdate(drop_player);

            switch (pos) {//update the uuid to null of the selected player, by UUID
                case "K":
                    this.statement.executeUpdate("UPDATE TEAM SET K_UUID=NULL WHERE K_UUID =" + uuid + " AND "
                            + "TEAM.USERNAME = " + username);
                    return "Success";
                case "DEFST":
                    this.statement.executeUpdate("UPDATE TEAM SET DEFST_UUID=NULL WHERE DEFST_UUID =" + uuid + " AND "
                            + "TEAM.USERNAME = " + username);
                    return "Success";
                case "QB":
                    this.statement.executeUpdate("UPDATE TEAM SET QB_UUID=NULL WHERE QB_UUID = " + uuid + " AND "
                            + "TEAM.USERNAME = " + username);
                    return "Success";
                case "RB":
                    this.statement.executeUpdate("UPDATE TEAM SET RB_UUID=NULL WHERE RB_UUID =" + uuid + " AND "
                            + "TEAM.USERNAME = " + username);
                    return "Success";
                case "WRTE":
                    ResultSet is_one = this.statement.executeQuery("SELECT WR1_UUID FROM TEAM WHERE USERNAME =" + username);
                    int rows = is_one.getRow();
                    is_one.next();//Get WR1_UUID
                    try {
                        if (is_one.getInt("WR1_UUID") == uuid)//if the selected WR is WR1 update WR1_UUID field
                        {
                            this.statement.executeUpdate("UPDATE TEAM SET WR1_UUID=NULL WHERE USERNAME =" + username);
                        } else//same as WR2
                        {
                            ResultSet is_two = this.statement.executeQuery("SELECT WR2_UUID FROM TEAM WHERE USERNAME =" + username);
                            is_two.next();

                            if (is_two.getInt("WR2_UUID") == uuid) {
                                this.statement.executeUpdate("UPDATE TEAM SET WR2_UUID=NULL WHERE USERNAME =" + username);
                            } else {
                                return "You dun goofed";//If none of the above conditions apply you dun goofed
                            }
                        }

                    } catch (SQLException err) {
                        return err.getMessage();
                    }
                    return "Success";
            }

        } catch (SQLException e) {
            return e.getMessage();
        }

        return "Something weird happened";//if none of the above conditions trigger, default error message cause I have no clue what happened
    }

    public void removePlayer(String username) throws SQLException {
        ResultSet data = this.statement.executeQuery("SELECT * FROM TEAM WHERE USERNAME='" + username + "'");

        this.statement.executeUpdate("UPDATE QB SET DPICK='0' WHERE QB_UUID='" + data.getInt("QB_UUID") + "'");
        this.statement.executeUpdate("UPDATE RB SET DPICK='0' WHERE RB_UUID='" + data.getInt("RB_UUID") + "'");
        this.statement.executeUpdate("UPDATE DEFST SET DPICK='0' WHERE DEFST_UUID='" + data.getInt("DEFST_UUID") + "'");
        this.statement.executeUpdate("UPDATE WRTE SET DPICK='0' WHERE WRTE_UUID='" + data.getInt("WR1_UUID") + "'");
        this.statement.executeUpdate("UPDATE WRTE SET DPICK='0' WHERE WRTE_UUID='" + data.getInt("WR2_UUID") + "'");
        this.statement.executeUpdate("UPDATE K SET DPICK='0' WHERE K_UUID='" + data.getInt("K_UUID") + "'");

        this.statement.executeUpdate("DELETE TEAM WHERE USERNAME='" + data.getInt("USERNAME") + "'");
        this.statement.executeUpdate("DELETE FANTASYUSER WHERE USERNAME='" + data.getInt("USERNAME") + "'");
    }

    public HashMap<String, String> returnAllNames() throws SQLException {
        HashMap<String, String> sorted_data = new HashMap<String, String>();
        ResultSet data = this.statement.executeQuery("SELECT QB.FNAME, QB.LNAME, "//get names of all players
                + "RB.FNAME, RB.LNAME, K.FNAME, K.LNAME, DEFST.FNAME, DEFST.LNAME, "
                + "WRTE.FNAME, WRTE.LNAME FROM WRTE, QB, RB, K, DEFST");
        while (data.next()) {//while names to get, store names in a hashmap in lastname:firstname format
            sorted_data.put(data.getString(2), data.getString(1));
            sorted_data.put(data.getString(4), data.getString(3));
            sorted_data.put(data.getString(6), data.getString(5));
            sorted_data.put(data.getString(8), data.getString(7));
            sorted_data.put(data.getString(10), data.getString(9));
        }
        System.out.println(data);
        return sorted_data;

    }
    
    
    public HashMap<String, String> returnRavens() throws SQLException {
        HashMap<String, String> sorted_data = new HashMap<String, String>();
        String[] positions = {"QB", "RB", "K", "WRTE", "DEFST"};//array with all position table names
        ResultSet data;
        for(int i=0; i<positions.length; i++)
        {
        data = this.statement.executeQuery("SELECT " + positions[i] + ".FNAME, " +positions[i] + ""
                + ".LNAME FROM " + positions[i] + " WHERE " + positions[i] + ".PROTEAM='Baltimore Ravens'");//select all players of a position from the Ravens
            while (data.next()) {//while names to get, store names in a hashmap in lastname:firstname format
                sorted_data.put(data.getString(2), data.getString(1));
                 }
        }
        return sorted_data;
    }
    
    public HashMap<String, String> returnSteelers() throws SQLException {
        HashMap<String, String> sorted_data = new HashMap<String, String>();
        String[] positions = {"QB", "RB", "K", "WRTE", "DEFST"};//array with all position table names
        ResultSet data;
        for(int i=0; i<positions.length; i++)
        {
        data = this.statement.executeQuery("SELECT " + positions[i] + ".FNAME, " +positions[i] + ""
                + ".LNAME FROM " + positions[i] + " WHERE " + positions[i] + ".PROTEAM='Pittsburgh Steelers'");//select all players of a position from the Ravens
            while (data.next()) {//while names to get, store names in a hashmap in lastname:firstname format
                sorted_data.put(data.getString(2), data.getString(1));
                 }
        }
        return sorted_data;
    }

    



   // public HashMap<String, String> returnPlayers(String teamname, String pos) throws SQLException { 
     //   HashMap<String, String> sorted_data = new HashMap<String, String>();

    //public HashMap<String, String> returnPlayers(String teamname, String pos) throws SQLException { 
     //   HashMap<String, String> sorted_data = new HashMap<String, String>();
      //  ResultSet data;


   



    public HashMap<String, String> returnPlayers(String teamname, String pos) throws SQLException { 
        HashMap<String, String> sorted_data = new HashMap<String, String>();
        ResultSet data;

        //returns players that belong to team teamname and are at position pos
        if(teamname=="PIT")//just use "PIT" or "BAL" in method call
        {
            teamname="'Pittsburgh Steelers'";
        }
        else if(teamname=="BAL")
        {
            teamname="'Baltimore Ravens'";
        }


     //   ResultSet data;


        else if(teamname=="ALL")
        {
            data = this.statement.executeQuery("SELECT " + pos + ".FNAME, " +pos + ""
                + ".LNAME FROM " + pos);//select all players of a position from the Ravens
            while (data.next()) {//while names to get, store names in a hashmap in lastname:firstname format
                sorted_data.put(data.getString(2), data.getString(1));
                 }
        return sorted_data;
        }
        

        
        data = this.statement.executeQuery("SELECT " + pos + ".FNAME, " +pos + ""
                + ".LNAME FROM " + pos + " WHERE " + pos + ".PROTEAM=" + teamname);//select all players of a position from the Ravens
            while (data.next()) {//while names to get, store names in a hashmap in lastname:firstname format
                sorted_data.put(data.getString(2), data.getString(1));
                 }
        return sorted_data;
    }



    
    public ArrayList<String> getUser() throws SQLException//returns an ArrayList of all usernames
    {
        ArrayList<String> usernames = new ArrayList();
        ResultSet data = this.statement.executeQuery("SELECT USERNAME FROM FANTASYUSER");
        
        while(data.next())
        {
            usernames.add(data.getString(1));
        }
        
        return usernames;
    }
    
   public String getUser(String username) throws SQLException
   {
       ResultSet data = this.statement.executeQuery("SELECT FNAME FROM FANTASYUSER WHERE USERNAME='" + username + "'");
       data.next();
       String name = data.getString(1);
       return name;
   }

}
