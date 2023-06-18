/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopProjet;

import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author glodi
 */
public class newMain {
       public static void main (String[] args){
    Connection Con = dbConnect.getConnect();
    if(Con == null){
    JOptionPane.showMessageDialog(null,"Connection failed");
    }else{
    JOptionPane.showMessageDialog(null,"SUCCESS");
    
    }
    }
    
}
