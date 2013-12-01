/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyfootball;

/**
 *
 * @author area51
 */
public class Player {
    public int UUID;
    public String FName;
    public String LName;
    public String Pos;
    public boolean Inj;
    public int DPick;
    public int Pos_UUID;
    

public Player()
{
    UUID = 0;
    FName = "";
    LName = "";
    Pos = "";
    Inj = false;
    DPick = 0;
    Pos_UUID = 0;
}

public String toString()
{
    String attrib = "";
    attrib += String.valueOf(UUID) + " ";
    attrib += FName + " ";
    attrib += LName + " ";
    attrib += Pos + " ";
    attrib += String.valueOf(Inj) + " ";
    attrib += String.valueOf(DPick) + " ";
    attrib += String.valueOf(Pos_UUID) + " ";
    
    return attrib;
}
}
