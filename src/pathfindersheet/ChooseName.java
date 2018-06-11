/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindersheet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ChooseName extends JFrame{
    
    JLabel name;
    JTextField txname;
    JButton ok;
    
    public ChooseName(Handler h){
        super("Character's name");
        setLayout(null);

        name = new JLabel("CHARACTER'S NAME:");
        name.setFont(CharacterSheet.miniPFONT);
        name.setHorizontalAlignment(JLabel.RIGHT);
        name.setBounds(40, 10, 120, 20);
        add(name);

        txname = new JTextField(10);
        txname.setFont(CharacterSheet.miniPFONT);
        txname.setBounds(40, 40, 120, 20);
        add(txname);
        
        ok = new JButton("OK");
        ok.addActionListener(h);
        ok.setBounds(70, 75, 60, 20);
        add(ok);
    }
}
