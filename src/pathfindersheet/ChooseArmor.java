/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindersheet;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChooseArmor extends JFrame {

    JLabel name, acBonus, maxDex, penalty, spellFail;
    JTextField txname, txacBonus, txmaxDex, txpenalty, txspellFail;
    JButton ok;
    Handler h;

    public ChooseArmor(Handler h) {
        super("Add your armor features");
        setLayout(null);
        
        this.h = h;

        name = new JLabel("NAME: ");
        name.setFont(CharacterSheet.miniPFONT);
        name.setHorizontalAlignment(JLabel.RIGHT);
        name.setBounds(10, 10, 120, 20);
        add(name);

        txname = new JTextField(10);
        txname.setFont(CharacterSheet.miniPFONT);
        txname.setBounds(140, 10, 120, 20);
        add(txname);

        acBonus = new JLabel("AC BONUS: ");
        acBonus.setFont(CharacterSheet.miniPFONT);
        acBonus.setBounds(10, 35, 120, 20);
        acBonus.setHorizontalAlignment(JLabel.RIGHT);
        add(acBonus);

        txacBonus = new JTextField(10);
        txacBonus.setFont(CharacterSheet.miniPFONT);
        txacBonus.setBounds(140, 35, 120, 20);
        add(txacBonus);

        maxDex = new JLabel("MAX DEXTERITY: ");
        maxDex.setFont(CharacterSheet.miniPFONT);
        maxDex.setBounds(10, 60, 120, 20);
        maxDex.setHorizontalAlignment(JLabel.RIGHT);
        add(maxDex);

        txmaxDex = new JTextField(10);
        txmaxDex.setFont(CharacterSheet.miniPFONT);
        txmaxDex.setBounds(140, 60, 120, 20);
        add(txmaxDex);

        penalty = new JLabel("PENALTY: ");
        penalty.setFont(CharacterSheet.miniPFONT);
        penalty.setBounds(10, 85, 120, 20);
        penalty.setHorizontalAlignment(JLabel.RIGHT);
        add(penalty);

        txpenalty = new JTextField(10);
        txpenalty.setFont(CharacterSheet.miniPFONT);
        txpenalty.setBounds(140, 85, 120, 20);
        add(txpenalty);

        spellFail = new JLabel("SPELL FAILURE: ");
        spellFail.setFont(CharacterSheet.miniPFONT);
        spellFail.setBounds(10, 110, 120, 20);
        spellFail.setHorizontalAlignment(JLabel.RIGHT);
        add(spellFail);

        txspellFail = new JTextField(10);
        txspellFail.setFont(CharacterSheet.miniPFONT);
        txspellFail.setBounds(140, 110, 120, 20);
        add(txspellFail);

        ok = new JButton("OK");
        ok.addActionListener(h);
        ok.setBounds(100, 150, 60, 20);
        add(ok);
    }
}
