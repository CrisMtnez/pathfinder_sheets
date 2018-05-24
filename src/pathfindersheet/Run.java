/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindersheet;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Run {

    public static void main(String[] args) {
        
        Menu m = new Menu();       
        m.setSize(600,400);
        m.setVisible(true);
        m.setResizable(false);
        m.setDefaultCloseOperation(EXIT_ON_CLOSE);
        m.setLocationRelativeTo(null);
    }
    
}
