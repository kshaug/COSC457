/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyfootball;
import java.sql.*;

/**
 *
 * @author area51
 */
public class FantasyFootball {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            DBAccess dba = new DBAccess();
            
            System.out.println("Get Players");
            ResultSet players = dba.get_players();
            
            System.out.println(players.next());
        }
        catch(SQLException ex)
    {
        ex.printStackTrace();
    }
    }
    
}