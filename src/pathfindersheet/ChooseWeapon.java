
package pathfindersheet;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChooseWeapon extends JFrame { 
    JLabel name, attackModifier, damage, critical, range;
    JTextField txname, txattackModifier, txdamage, txcritical, txrange;
    JButton ok;
    Handler h;

    public ChooseWeapon(Handler h) {
        super("Add your weapon features");
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

        attackModifier = new JLabel("ATTACK MODIFIER: ");
        attackModifier.setFont(CharacterSheet.miniPFONT);
        attackModifier.setBounds(10, 35, 120, 20);
        attackModifier.setHorizontalAlignment(JLabel.RIGHT);
        add(attackModifier);

        txattackModifier = new JTextField(10);
        txattackModifier.setFont(CharacterSheet.miniPFONT);
        txattackModifier.setBounds(140, 35, 120, 20);
        add(txattackModifier);

        damage = new JLabel("DAMAGE: ");
        damage.setFont(CharacterSheet.miniPFONT);
        damage.setBounds(10, 85, 120, 20);
        damage.setHorizontalAlignment(JLabel.RIGHT);
        add(damage);

        txdamage = new JTextField(10);
        txdamage.setFont(CharacterSheet.miniPFONT);        
        txdamage.setBounds(140, 85, 120, 20);
        add(txdamage);

        critical = new JLabel("CRITICAL: ");
        critical.setFont(CharacterSheet.miniPFONT);        
        critical.setBounds(10, 60, 120, 20);
        critical.setHorizontalAlignment(JLabel.RIGHT);
        add(critical);

        txcritical = new JTextField(10);
        txcritical.setFont(CharacterSheet.miniPFONT);
        txcritical.setBounds(140, 60, 120, 20);
        add(txcritical);

        ok = new JButton("OK");
        ok.addActionListener(h);
        ok.setBounds(100, 125, 60, 20);
        add(ok);
    }
}
