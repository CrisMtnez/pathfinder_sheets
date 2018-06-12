package pathfindersheet;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Handler extends MouseAdapter implements ActionListener, KeyListener, ItemListener {

    Menu m;
    CharacterSheet cs;
    int mod;
    ChooseArmor ca;
    ChooseWeapon cw;
    ChooseName cn;
    String personaje;
    int maxDex = -10;

    public Handler() {
        m = new Menu(this);
        m.setSize(600, 400);
        m.setVisible(true);
        m.setResizable(false);
        m.setDefaultCloseOperation(EXIT_ON_CLOSE);
        m.setLocationRelativeTo(null);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (m != null) {
            if (e.getSource() == m.title) {
                m.opciones.show(m.title, 0, 83);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().getClass() == JMenuItem.class) {

            if (e.getSource() == m.salir) {
                System.exit(0);
            }

            personaje = "";

            if (e.getSource() == m.nuevaFicha) {
                chooseName();
            }

            if (((JMenuItem) e.getSource()).getName().equals("sheet")) {
                personaje = ((JMenuItem) (e.getSource())).getText().trim();
                String rutaFicha = m.ss.hojaPersonaje(((JMenuItem) (e.getSource())).getText().trim());
                m.ss.VaciarArmorsAndWeapons();
                m.ss.abrirFicha(rutaFicha);
                cargarHoja();
            }

            if (cs != null) {

                if (e.getSource() == cs.newf) {
                    chooseName();
                }

                if (e.getSource() == cs.openf) {
                }

                if (e.getSource() == cs.recentf) {
                }

                if (e.getSource() == cs.savef) {
                    //borrarPenArmadura();  NO funciona, tengo que borrar los modificadores en el guardado de la ficha, no en la 
                    //propia ficha. Siguen duplicandose armaduras y armas.
                    m.ss.guardarDatos(cs);
                    m.fichasGuardadas();
                }

                if (e.getSource() == cs.exit) {
                    cs.dispose();  //NO VA CUANDO ABRO OTRAS FICHAS DESDE UNA FICHA
                }

                if (e.getSource() == cs.resetAll) {
                }

                if (e.getSource() == cs.diceRoller) {
                }

                if (e.getSource() == cs.activateToolTips) {
                }

                if (e.getSource() == cs.about) {
                }
            }
        }
        if (e.getSource().getClass() == JComboBox.class) {
            JComboBox combo = (JComboBox) e.getSource();

            if (combo == cs.chooseArmor) {
                if (combo.getSelectedItem() == cs.chooseArmor.getModel().getElementAt(cs.chooseArmor.getModel().getSize() - 1)) {
                    ca = new ChooseArmor(this);
                    ca.setSize(280, 210);
                    ca.setResizable(false);
                    ca.setLocationRelativeTo(null);
                    ca.setVisible(true);
                    ca.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

                } else {
                    
                    borrarPenArmadura();
                    
                    int indx = combo.getSelectedIndex();
                    int x = cs.colArmor[0].getX() - 1;
                    int y = cs.colArmor[0].getY() + cs.colArmor[0].getHeight() + 1;
                    int w = 73;
                    int h = 20;

                    maxDex = -10;
                    modifiers(1, "stat");

                    for (int i = 0; i < cs.st.armorList.get(indx).length; i++, x += 26) {
                        cs.chosenArmor = cs.st.armorList.get(indx);
                        cs.chosenArmor[i].setBounds(x, y, w, h);
                        cs.chosenArmor[i].setText(cs.st.armorList.get(indx)[i].getText()
                                + (cs.st.armorList.get(indx)[i].getText().equals("") ? " " : ""));
                        cs.chosenArmor[i].setHorizontalAlignment(JTextField.CENTER);
                        cs.chosenArmor[i].setName("armor");
                        cs.chosenArmor[i].addKeyListener(this);
                        cs.panelArmor.add(cs.chosenArmor[i]);
                        if (i == 0) {
                            x += 48;
                            w = 25;
                        }
                    }
                    cs.chooseArmor.setBounds(cs.chooseArmor.getX(), cs.chosenArmor[0].getY() + cs.chosenArmor[0].getHeight()
                            + 15, cs.chooseArmor.getWidth(), cs.chooseArmor.getHeight());
                    cs.panelArmor.setSize(210, 121);
                    modifiers(0, "armor");

                }
            }

            if (combo == cs.chooseWeapon) {
                if (combo.getSelectedItem() == cs.chooseWeapon.getModel().getElementAt(cs.chooseWeapon.getModel().getSize() - 1)) {
                    cw = new ChooseWeapon(this);
                    cw.setSize(280, 185);
                    cw.setResizable(false);
                    cw.setLocationRelativeTo(null);
                    cw.setVisible(true);
                    cw.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

                } else {
                    int indx = combo.getSelectedIndex();
                    int x = cs.colWeapons[0].getX() - 1;
                    int y = cs.colWeapons[0].getY() + cs.colWeapons[0].getHeight() + 1;
                    int w = 73;
                    int h = 20;
                    // StringBuilder aux = cs.st.weaponNames.getElementAt(indx);
//                        ((String)(cs.st.weaponNames.getElementAt(indx))) = cs.st.weaponsList.get(indx)[0].getText();

                    if (cs.chosenWeapon != null) {
                        for (int i = cs.chosenWeapon.length - 1; i >= 0; i--) {
                            cs.panelWeapons.remove(cs.chosenWeapon[i]);
                        }
                    }

                    for (int i = 0; i < cs.st.weaponsList.get(indx).length; i++, x += 26) {
                        cs.chosenWeapon = cs.st.weaponsList.get(indx);
                        cs.chosenWeapon[i].setBounds(x, y, i == 3 ? w + 30 : w, h);
                        cs.chosenWeapon[i].setText(cs.st.weaponsList.get(indx)[i].getText()
                                + (cs.st.weaponsList.get(indx)[i].getText().equals("") ? " " : ""));
                        cs.chosenWeapon[i].setFont(new Font(cs.chosenWeapon[i].getFont().getFontName(), Font.PLAIN, 10));
                        cs.chosenWeapon[i].setHorizontalAlignment(JTextField.CENTER);
                        cs.chosenWeapon[i].addKeyListener(this);
                        cs.panelWeapons.add(cs.chosenWeapon[i]);
                        if (i == 0) {
                            x += 48;
                            w = 25;
                        }
                    }
                    cs.chooseWeapon.setBounds(cs.chooseWeapon.getX(), cs.chosenWeapon[0].getY() + cs.chosenWeapon[0].getHeight()
                            + 15, cs.chooseWeapon.getWidth(), cs.chooseWeapon.getHeight());
                    cs.panelWeapons.setSize(210, 121);
                    modifiers(0, "weapon");
                }
            }
        }

        if (ca != null && e.getSource() == ca.ok) {
            String[] newArmor = new String[]{ca.txname.getText(), ca.txacBonus.getText(), ca.txmaxDex.getText(),
                ca.txpenalty.getText(), ca.txspellFail.getText()};
            cs.st.addArmor(newArmor);
            ca.dispose();
        }
        if (cw != null && e.getSource() == cw.ok) {
            String[] newWeapon = new String[]{cw.txname.getText(), cw.txattackModifier.getText(), cw.txcritical.getText(),
                cw.txdamage.getText()};
            cs.st.addWeapon(newWeapon);
            cw.dispose();
        }

        if (cn != null && e.getSource() == cn.ok) {
            personaje = cn.txname.getText();
            if (personaje != null && !personaje.trim().equals("")) {
                cn.dispose();
                cargarHoja();
            }
        }
    }

    public void borrarPenArmadura() {
        if (cs.chosenArmor != null) {
            try {
                int pen = Integer.parseInt(cs.chosenArmor[3].getText());
                for (int i = 0; i < cs.miscSkills.length; i++) {
                    if (cs.mods[i + 9].getName().equals("0") || cs.mods[i + 9].getName().equals("1")) {
                        try {
                            cs.miscSkills[i].setText((Integer.parseInt(cs.miscSkills[i].getText().equals("") ? "0"
                                    : cs.miscSkills[i].getText()) + pen) + "");
                        } catch (NumberFormatException n) {
                        }
                    }
                }
            } catch (NumberFormatException n) {
            }
            for (int i = cs.chosenArmor.length - 1; i >= 0; i--) {
                cs.panelArmor.remove(cs.chosenArmor[i]);
            }
        }
    }

    public void chooseName() {
        cn = new ChooseName(this);
        cn.setSize(200, 140);
        cn.setResizable(false);
        cn.setLocationRelativeTo(null);
        cn.setVisible(true);
        cn.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    //THIS! KeyReleased es la mejor opción para que detecte automáticamente los valores introducidos. KeyTyped da problemas
    @Override
    public void keyReleased(KeyEvent ke) {
        int fila;

        if (ke.getComponent().getName() != null) {

            switch (ke.getComponent().getName()) {

                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    fila = Integer.parseInt(ke.getComponent().getName().charAt(0) + "");
                    modifiers(fila, "stat");
                    modifiers(0, "attack");
                    modifiers(0, "defense");
                    modifiers(0, "saves");
                    modifiers(0, "skills");
                    modifiers(0, "init");
                    break;

                case "20":
                case "21":
                case "22":
                case "23":
                case "60":
                case "61":
                case "62":
                case "63":
                    fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                    modifiers(fila, "attack");
                    break;

                case "00":
                case "01":
                case "02":
                    if (ke.getComponent() != cs.armorDefense[1]) {
                        fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                    } else {
                        fila = 2;
                    }
                    modifiers(fila, "defense");
                    break;

                case "10":
                case "11":
                case "12":
                    fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                    modifiers(fila, "saves");
                    break;

                case "hp":
                    calcularHp();
                    break;

                case "init":
                    modifiers(0, "init");
                    break;

                case "armor":
                    modifiers(0, "armor");
                    break;

                case "weapons":
                    modifiers(0, "weapons");
                    break;

                default:  //para los skills, 3-                   
                    try {
                        fila = Integer.parseInt(ke.getComponent().getName().charAt(1) + "");
                        modifiers(fila, "skills");
                    } catch (NumberFormatException n) {
                    }
                    break;
            }
        }
    }

    private void calcularHp() {
        int HP = 0, hit;
        String ouch = "";
        ArrayList<Integer> damage = new ArrayList();
        if (cs.damage.getText().length() > 5) {
            cs.damage.setSize(cs.damage.getText().length() * 10, 20);
        } else {
            cs.damage.setSize(50, 20);
        }
        for (int i = 0, j = 0; i < cs.damage.getText().length(); i++) {
            if (cs.damage.getText().charAt(i) != '+') {
                ouch += cs.damage.getText().charAt(i);
            } else {
                try {
                    hit = Integer.parseInt(ouch.trim());
                    damage.add(hit);
                    ouch = "";
                } catch (NumberFormatException n) {
                    ouch = "";
                }
            }
        }
        if (!ouch.equals("")) {
            try {
                hit = Integer.parseInt(ouch.trim());
                damage.add(hit);
            } catch (NumberFormatException n) {
            }
        }
        try {
            HP = Integer.parseInt(cs.totalHP.getText().trim());
        } catch (NumberFormatException n) {
        }
        for (int i = 0; i < damage.size(); i++) {
            HP -= damage.get(i);
        }
        cs.currentHP.setText(HP + "");
    }

    @Override
    public void mouseClicked(MouseEvent m) {

    }

    public void modifiers(int fila, String tipo) {

        int value;

        switch (tipo) {

            case "stat":

                value = 0;

                try {
                    value += Integer.parseInt(cs.baseStats[fila].getText().trim());
                } catch (NumberFormatException n) {
                    value += 0;
                }
                try {
                    value += Integer.parseInt(cs.enhStats[fila].getText().trim());
                } catch (NumberFormatException n) {
                    value += 0;
                }
                try {
                    value += Integer.parseInt(cs.miscStats[fila].getText().trim());
                } catch (NumberFormatException n) {
                    value += 0;
                }
                try {
                    value += Integer.parseInt(cs.tempStats[fila].getText().trim());
                } catch (NumberFormatException n) {
                    value += 0;
                }

                cs.totalStats[fila].setText(value + "");
                mod = (int) (((value - (value % 2)) - 10) / 2);
                cs.modStats[fila].setText(mod + "");

                for (int i = 0; i < cs.mods.length; i++) {
                    if (cs.mods[i].getName().equals(fila + "")) {
                        if ((i == 0 || i == 1) && maxDex != -10) {
                            cs.mods[i].setText(maxDex + "");
                            cs.mods[i].setForeground(Color.red);
                        } else {
                            cs.mods[i].setForeground(Color.black);
                            cs.mods[i].setText(mod + "");
                        }
                    }
                }

                int cmd = cs.modStats[0].getText().equals("") ? 0 : Integer.parseInt(cs.modStats[0].getText().trim());
                cmd += cs.modStats[1].getText().equals("") ? 0 : Integer.parseInt(cs.modStats[1].getText().trim());

                cs.mods[8].setText(cmd + "");

                break;

            case "attack":

                int modF,
                 modD;
                for (int i = 0; i < cs.BAB.length; i++) {
                    if (Integer.parseInt(cs.BAB[i].getName().charAt(1) + "") != fila) {
                        cs.BAB[i].setText(cs.BAB[fila].getText().trim());
                        int totalfila = cs.totalAttack[i].getText().equals("") ? 0 : Integer.parseInt(cs.totalAttack[i].getText().trim());
                        int babfila = cs.BAB[i].getText().equals("") ? 0 : Integer.parseInt(cs.BAB[i].getText().trim());
                        cs.totalAttack[i].setText((babfila + totalfila) + "");
                    }
                }

                for (int i = 0; i < cs.totalAttack.length; i++) {

                    value = 0;

                    try {
                        value += Integer.parseInt(cs.BAB[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.mods[i + 5].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.sizeAttack[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.miscAttack[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.tempAttack[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }

                    cs.totalAttack[i].setText(i == 3 ? value + 10 + "" : value + "");
                }
                break;

            case "defense":
                for (int i = 0; i < cs.totalDefense.length; i++) {

                    value = 0;

                    if (i == 0) {
                        try {
                            value += Integer.parseInt(cs.armorDefense[i].getText().trim());
                        } catch (NumberFormatException n) {
                            value += 0;
                        }
                    }

                    if (i == 2) {
                        try {
                            value += Integer.parseInt(cs.armorDefense[1].getText().trim());
                        } catch (NumberFormatException n) {
                            value += 0;
                        }
                    }

                    if (i != 2) {
                        try {
                            value += Integer.parseInt(cs.mods[i].getText().trim());
                        } catch (NumberFormatException n) {
                            value += 0;
                        }
                    }

                    try {
                        value += Integer.parseInt(cs.sizeDefense[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.miscDefense[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.tempDefense[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }

                    cs.totalDefense[i].setText(value + 10 + "");
                }
                break;

            case "saves":
                for (int i = 0; i < cs.totalSaves.length; i++) {

                    value = 0;

                    try {
                        value += Integer.parseInt(cs.baseSaves[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.mods[i + 2].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.enhSaves[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.miscSaves[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.tempSaves[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }

                    cs.totalSaves[i].setText(value + "");
                }
                break;

            case "skills":
                for (int i = 0; i < cs.totalSkills.length; i++) {

                    value = 0;

                    try {
                        value += Integer.parseInt(cs.mods[i + 9].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.rankSkills[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.trainedSkills[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }
                    try {
                        value += Integer.parseInt(cs.miscSkills[i].getText().trim());
                    } catch (NumberFormatException n) {
                        value += 0;
                    }

                    cs.totalSkills[i].setText(value + "");
                }
                break;

            case "init":
                value = 0;

                try {
                    value += Integer.parseInt(cs.modInit.getText().trim());
                } catch (NumberFormatException n) {
                    value += 0;
                }

                try {
                    value += Integer.parseInt(cs.mods[44].getText().trim());
                } catch (NumberFormatException n) {
                }

                cs.totalInit.setText(value + "");
                break;

            case "armor":

                try {
                    value = Integer.parseInt(cs.chosenArmor[1].getText().trim());
                } catch (NumberFormatException n) {
                    value = 0;
                }

                for (int i = 0; i < cs.armorDefense.length; i++) {
                    cs.armorDefense[i].setText(value + "");
                }

                try {
                    maxDex = Integer.parseInt(cs.chosenArmor[2].getText().trim());
                    if (maxDex >= Integer.parseInt(cs.modStats[1].getText().trim())) {
                        maxDex = -10;
                    }
                } catch (NumberFormatException n) {
                    maxDex = -10;
                }

                try {
                    value = Integer.parseInt(cs.chosenArmor[3].getText().trim());

                    for (int i = 0; i < cs.miscSkills.length; i++) {
                        if (cs.mods[i + 9].getName().equals("0") || cs.mods[i + 9].getName().equals("1")) {
                            try {
                                cs.miscSkills[i].setText((Integer.parseInt(cs.miscSkills[i].getText().trim().equals("") ? "0"
                                        : cs.miscSkills[i].getText().trim()) - value) + "");
                            } catch (NumberFormatException n) {
                            }
                        }
                    }

                } catch (NumberFormatException n) {
                    value = 0;
                }

                modifiers(1, "stat");
                modifiers(0, "attack");
                modifiers(0, "defense");
                modifiers(0, "saves");
                modifiers(0, "skills");
                modifiers(0, "init");
                break;

            case "weapons":

                break;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {

        Component check = (Component) ie.getSource();
        int fila = Integer.parseInt(((Component) (ie.getSource())).getName().substring(1));
        modifiers(fila, "chkSkills");

        switch (check.getName().charAt(0)) {
            case 'S':
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    cs.trainedSkills[fila].setText(3 + "");
                } else if (ie.getStateChange() == ItemEvent.DESELECTED) {
                    cs.trainedSkills[fila].setText("");
                }
                modifiers(fila, "skills");
                break;
        }
    }

    public void cargarHoja() {
        cs = new CharacterSheet(personaje, this, m.ss.stats, m.ss.defense, m.ss.saves, m.ss.attack, m.ss.skills,
                m.ss.hp, m.ss.init, m.ss.armor, m.ss.weapons);
        cs.setSize(935, 680);
        cs.setVisible(true);
        cs.setResizable(true);
        cs.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cs.setLocationRelativeTo(null);
        cs.chooseArmor.setSelectedIndex(0);
        recalcularTodo(true);
    }

    public void recalcularTodo(boolean primeraVez) {

        modifiers(0, "stat");
        modifiers(1, "stat");
        modifiers(2, "stat");
        modifiers(3, "stat");
        modifiers(4, "stat");
        modifiers(5, "stat");
        modifiers(0, "attack");
        modifiers(0, "defense");
        modifiers(0, "saves");
        modifiers(0, "init");

        calcularHp();

        if (primeraVez) {
            for (int i = 0; i < cs.chkSkills.length; i++) {
                if (cs.chkSkills[i].isSelected()) {
                    cs.trainedSkills[i].setText(3 + "");
                }
            }
        }

        modifiers(0, "skills");
    }
}
