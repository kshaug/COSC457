/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyfootball;
import fantasyfootball.DBAccess;
import java.sql.*;
import java.util.Map;
import java.util.HashMap;
/**
 *
 * @author area51
 */
public class FantasyTester {
    
   public static void main(String[] args) throws SQLException
    {
       DBAccess dba = new DBAccess();
       
       Date bday = new Date(10000000);
       
       System.out.println(bday.getClass());
       HashMap<String, String> x = dba.returnAllNames();
       for(Map.Entry entry : x.entrySet())
       {
           System.out.println(entry.getKey() + "," + entry.getValue());
       }
       
       String check = dba.createUser("'Shaggy'", "'Kevin'", 20, "'cleddabest'", bday);
       
       String draft_QB = dba.draftPlayer("QB", 1, 1, "'cleddabest'");
       String draft_RB = dba.draftPlayer("RB", 1, 2, "'cleddabest'");
       String draft_DEF = dba.draftPlayer("DEFST", 1, 3, "'cleddabest'");
       String draft_K = dba.draftPlayer("K", 1, 4, "'cleddabest'");
       String draft_WR1 = dba.draftPlayer("WRTE", 1, 5, "'cleddabest'");
       String draft_WR2 = dba.draftPlayer("WRTE", 2, 6, "'cleddabest'");
       
       System.out.println(check);
       System.out.println(draft_QB);
       System.out.println(draft_RB);
       System.out.println(draft_DEF);
       System.out.println(draft_K);
       System.out.println(draft_WR1);
       System.out.println(draft_WR2);
       
       String drop_QB = dba.dropPlayer("DEFST", 1, "'cleddabest'");
       String drop_RB = dba.dropPlayer("RB", 1, "'cleddabest'");
       String drop_K = dba.dropPlayer("K", 1, "'cleddabest'");
       String drop_WR1 = dba.dropPlayer("WRTE", 1, "'cleddabest'");
       String drop_WR2 = dba.dropPlayer("WRTE", 2, "'cleddabest'");
       
       System.out.println(check);
       System.out.println(drop_QB);
       System.out.println(drop_RB);
       System.out.println(drop_K);
       System.out.println(drop_WR1);
       System.out.println(drop_WR2);
        
    }
}
