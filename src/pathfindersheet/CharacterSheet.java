package pathfindersheet;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CharacterSheet extends JFrame {

    JScrollPane scroll;
    JPanel totalPanel;
    SheetText st;
    JMenuBar bar;
    JMenu file, options, help;
    JMenuItem newf, openf, recentf, savef, exit, resetAll, diceRoller, activateToolTips, about;
    JComboBox conditions, chooseArmor, chooseWeapon;
    JPanel panelStats, panelHP, panelAtAndDef, panelArmor, panelWeapons, borderPanelFeats, titlePanelFeats, panelFeats, panelSkills;
    JLabel[] colStats = new JLabel[7], tStats = new JLabel[6],
            colDefense = new JLabel[7], tDefense = new JLabel[3],
            colSaves = new JLabel[7], tSaves = new JLabel[3],
            colAttack = new JLabel[7], tAttack = new JLabel[4],
            colSkills = new JLabel[5], tSkills = new JLabel[35],
            colArmor = new JLabel[5], colWeapons = new JLabel[5];
    JTextField[] totalStats = new JTextField[6], modStats = new JTextField[6], baseStats = new JTextField[6],
            enhStats = new JTextField[6], miscStats = new JTextField[6], tempStats = new JTextField[6],
            sizeDefense = new JTextField[3], totalDefense = new JTextField[3], armorDefense = new JTextField[2],
            miscDefense = new JTextField[3], tempDefense = new JTextField[3],
            totalSaves = new JTextField[3], baseSaves = new JTextField[3], enhSaves = new JTextField[3],
            miscSaves = new JTextField[3], tempSaves = new JTextField[3], BAB = new JTextField[4], sizeAttack = new JTextField[4],
            totalAttack = new JTextField[4], miscAttack = new JTextField[4], tempAttack = new JTextField[4],
            totalSkills = new JTextField[35], rankSkills = new JTextField[35], trainedSkills = new JTextField[35],
            miscSkills = new JTextField[35], mods = new JTextField[45]; //OJO, ir aumentando el tamaño segun los use!!!!!!!!

    JTextField totalHP, languages, currentHP, damage, armorPenalty, maxDex, conMod, totalInit, modInit;
    JButton addWeapon, addArmor, seeEquipment, seeSpells;
    JLabel labtotalHP, labcurrentHP, labdamage, labConditions, labinit, equalInit, plusInit;
    ArrayList<JTextField[]> weapons, armor;
    ArrayList<JLabel> feats;
    ArrayList<JLabel> features;
    ArrayList<JLabel> equipment;
    int x, y, w, h;
    int modsCount = 0;

    /*TO-DO:
    - leer y guardar fichas
    - hacer suma fila init
    - cambiar botones addWeapon y addArmor por JComboBox chooseWeapon y chooseArmor
    - jtextfield dentro del label prof de skills
    - El espacio muerto se llena al pulsar equipment o spells
    - feats
    - acabar armor and weapons
    - boton dice roller
    - texfield damage responsive: aumenta cuando los daños no quepan, disminuye al reducirse
    - current HP se calcula al meter damage (en positivo)
    - MenuBar con opciones (Nueva ficha, cargar ficha, salir...)
    - checkboxes a las skills
    - añadir texfield totalHP para que el texfield de damages baje más
    - aumentar el tamaño del...scroll?totalPanel? para poder meter el panel spells/equipment
     */
    public CharacterSheet(boolean nuevoPersonaje, String personaje) {

        super(personaje);
        setLayout(null);

        menubar();

        st = new SheetText();

        totalPanel = new JPanel();
        totalPanel.setLayout(null);
        totalPanel.setPreferredSize(new Dimension(915, 790)); //THIS!!

        panelStats = new JPanel();
        panelStats.setLayout(null);
        panelStats.setBounds(20, 15, 290, 142);
        stats();
        totalPanel.add(panelStats);

        panelHP = new JPanel();
        panelHP.setLayout(null);
        panelHP.setBounds(panelStats.getX() + panelStats.getWidth() + 20, panelStats.getY() + 6, 75, 145);
        hp();
        totalPanel.add(panelHP);

        labConditions = new JLabel("CONDITIONS:");
        labConditions.setBounds(panelHP.getX() + panelHP.getWidth() + 20, panelHP.getY(), 120, 15);
        totalPanel.add(labConditions);

        conditions = new JComboBox(st.conditions);
        conditions.setBounds(panelHP.getX() + panelHP.getWidth() + 20, panelHP.getY() + 20, 120, 20);
        conditions.setSelectedIndex(-1);
        conditions.addActionListener(new Handler(this));
        totalPanel.add(conditions);

        panelAtAndDef = new JPanel();
        panelAtAndDef.setLayout(null);
        panelAtAndDef.setBounds(panelStats.getX(), panelStats.getY() + panelStats.getHeight() + 25, 290, 325);
        panelAtAndDef.setBackground(Color.LIGHT_GRAY);
        ac();
        saves();
        attacks();
        totalPanel.add(panelAtAndDef);

        panelSkills = new JPanel();
        panelSkills.setLayout(null);
        panelSkills.setBounds(panelHP.getX() + panelHP.getWidth() + 165, panelStats.getY(), 330, 760);
        skills();
        totalPanel.add(panelSkills);
        
        init();

        panelArmor = new JPanel();
        panelArmor.setLayout(null);
        panelArmor.setBounds(panelAtAndDef.getX() + panelAtAndDef.getWidth() + 25, totalInit.getY() + totalInit.getHeight() + 20,
                            210, 100);
        panelArmor.setBorder(BorderFactory.createTitledBorder(null, "ARMOR", TitledBorder.CENTER, TitledBorder.TOP, Menu.PFONT));
        armor();
        totalPanel.add(panelArmor);

        panelWeapons = new JPanel();
        panelWeapons.setLayout(null);
        panelWeapons.setBounds(panelArmor.getX(), panelArmor.getY() + panelArmor.getHeight() + 20, 210, 100);
        panelWeapons.setBorder(BorderFactory.createTitledBorder(null, "WEAPONS", TitledBorder.CENTER, TitledBorder.TOP, Menu.PFONT));
        weapons();
        totalPanel.add(panelWeapons);
        
        seeEquipment = new JButton("<html><h4>EQUIPMENT</h4></html>");
        seeEquipment.setFont(Menu.PFONT);
        seeEquipment.setBackground(Color.LIGHT_GRAY);
        seeEquipment.setForeground(Menu.DARKER_GRAY);
        seeEquipment.setBounds(panelWeapons.getX()+2, panelAtAndDef.getY() + panelAtAndDef.getHeight() -30, 95, 30);
//        seeEquipment.setIcon(new ImageIcon(CharacterSheet.class.getResource("img/backpack.png")));
        totalPanel.add(seeEquipment);

        seeSpells = new JButton("<html><h4>SPELLS</h4></html>");
        seeSpells.setFont(Menu.PFONT);
        seeSpells.setBackground(Color.LIGHT_GRAY);
        seeSpells.setForeground(Menu.DARKER_GRAY);
        seeSpells.setBounds(seeEquipment.getX() + seeEquipment.getWidth() + 20, seeEquipment.getY(), 90, 30);
//        seeSpells.setIcon(new ImageIcon(CharacterSheet.class.getResource("img/spells.png")));
        totalPanel.add(seeSpells);

        borderPanelFeats = new JPanel();
        borderPanelFeats.setLayout(null);
        borderPanelFeats.setBounds(panelAtAndDef.getX(), panelAtAndDef.getY() + panelAtAndDef.getHeight() + 20, 523, 237);
        borderPanelFeats.setBackground(Color.DARK_GRAY);

        titlePanelFeats = new JPanel();
        titlePanelFeats.setLayout(null);
        titlePanelFeats.setBounds(5, 0, borderPanelFeats.getWidth() - 10, borderPanelFeats.getHeight() - 5);
        titlePanelFeats.setOpaque(false);
        titlePanelFeats.setBorder(BorderFactory.createTitledBorder(null, "FEATS & FEATURES", TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP, Menu.PFONT, Menu.KHAKI));

        panelFeats = new JPanel();
        panelFeats.setLayout(null);
        panelFeats.setBounds(5, 25, titlePanelFeats.getWidth() - 10, titlePanelFeats.getHeight() - 30);

        titlePanelFeats.add(panelFeats);
        borderPanelFeats.add(titlePanelFeats);
        totalPanel.add(borderPanelFeats);

        add(totalPanel);

        scroll = new JScrollPane(totalPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(0, 0, 855, 830);
        setContentPane(scroll);  //THIS!!

        if (!nuevoPersonaje) {
            //setText de todos los textfield con los datos guardados. En el caso de armor&weapons guardar una fila con un int 
            //y crear ese numero de JTextFields[5] dentro del ArrayList armor y lo mismo en el caso de weapons y luego llamar a
            //la función armor y weapons y después setText.
        }
    }

    private void menubar() {

        bar = new JMenuBar();
        setJMenuBar(bar);

        file = new JMenu("File");
        file.setMnemonic('F');

        newf = new JMenuItem("New");
        newf.setMnemonic('N');
        newf.addActionListener(new Handler(this));
        file.add(newf);

        openf = new JMenuItem("Open...");
        openf.setMnemonic('O');
        openf.addActionListener(new Handler(this));
        file.add(openf);

        recentf = new JMenuItem("Recent Sheets");
        recentf.setMnemonic('R');
        recentf.addActionListener(new Handler(this));
        file.add(recentf);

        file.addSeparator();

        savef = new JMenuItem("Save");
        savef.setMnemonic('S');
        savef.addActionListener(new Handler(this));
        file.add(savef);

        file.addSeparator();

        exit = new JMenuItem("Exit");
        exit.setMnemonic('X');
        exit.addActionListener(new Handler(this));
        file.add(exit);

        bar.add(file);

        options = new JMenu("Options");
        options.setMnemonic('O');

        resetAll = new JMenuItem("Reset All");   //pedir confirmación
        resetAll.setMnemonic('E');
        resetAll.addActionListener(new Handler(this));
        options.add(resetAll);

        diceRoller = new JMenuItem("Dice Roller");
        diceRoller.setMnemonic('D');
        diceRoller.addActionListener(new Handler(this));
        options.add(diceRoller);

        bar.add(options);

        help = new JMenu("Help");
        help.setMnemonic('H');

        activateToolTips = new JMenuItem("ToolTips");  //hacerlo checkbox
        activateToolTips.setMnemonic('T');
        help.add(activateToolTips);

        about = new JMenuItem("About");
        about.setMnemonic('A');
        help.add(about);

        bar.add(help);

    }

    private void stats() {

        x = 11;
        y = 0;
        w = 50;
        h = 15;

        for (int i = 0; i < colStats.length; i++, x += 35) {
            colStats[i] = new JLabel("<html><h6>" + st.colStats[i] + "</h6></html>");
            colStats[i].setBounds(x, y, i == 0 ? w + 5 : w, h);
            colStats[i].setHorizontalAlignment(JLabel.CENTER);
            panelStats.add(colStats[i]);
            if (i == 0) {
                x += 20;
            }
        }

        x = colStats[0].getX() - 1;
        y = colStats[0].getY() + 15;
        w = 60;
        h = 20;

        for (int i = 0; i < tStats.length; i++, y += 21) {
            tStats[i] = new JLabel(" " + st.tStats[i]);
            tStats[i].setBounds(x, y, w, h);
            tStats[i].setForeground(Menu.KHAKI);
            tStats[i].setBackground(Menu.DARKER_GRAY);
            tStats[i].setOpaque(true);
            tStats[i].setHorizontalAlignment(JLabel.CENTER);
            panelStats.add(tStats[i]);
        }

        w = 30;
        y = colStats[0].getY() + 15;

        for (int i = 0; i < totalStats.length; i++, y += 21) {
            x = tStats[i].getX() + tStats[i].getWidth() + 5;
            totalStats[i] = new JTextField(0);
            totalStats[i].setBounds(x, y, w, h);
            totalStats[i].setEditable(false);
            totalStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(totalStats[i]);
        }

        y = colStats[0].getY() + 15;

        for (int i = 0; i < modStats.length; i++, y += 21) {
            x = totalStats[i].getX() + totalStats[i].getWidth() + 5;
            modStats[i] = new JTextField(0);
            modStats[i].setBounds(x, y, w, h);
            modStats[i].setEditable(false);
            modStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(modStats[i]);
        }

        y = colStats[0].getY() + 15;

        for (int i = 0; i < baseStats.length; i++, y += 21) {
            x = modStats[i].getX() + modStats[i].getWidth() + 5;
            baseStats[i] = new JTextField(0);
            baseStats[i].setBounds(x, y, w, h);
            baseStats[i].addKeyListener(new Handler(this));
            baseStats[i].setName(i + "");
            baseStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(baseStats[i]);
        }

        y = colStats[0].getY() + 15;

        for (int i = 0; i < enhStats.length; i++, y += 21) {
            x = baseStats[i].getX() + baseStats[i].getWidth() + 5;
            enhStats[i] = new JTextField(0);
            enhStats[i].setBounds(x, y, w, h);
            enhStats[i].addKeyListener(new Handler(this));
            enhStats[i].setName(i + "");
            enhStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(enhStats[i]);
        }

        y = colStats[0].getY() + 15;

        for (int i = 0; i < miscStats.length; i++, y += 21) {
            x = enhStats[i].getX() + enhStats[i].getWidth() + 5;
            miscStats[i] = new JTextField(0);
            miscStats[i].setBounds(x, y, w, h);
            miscStats[i].addKeyListener(new Handler(this));
            miscStats[i].setName(i + "");
            miscStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(miscStats[i]);
        }

        y = colStats[0].getY() + 15;

        for (int i = 0; i < tempStats.length; i++, y += 21) {
            x = miscStats[i].getX() + miscStats[i].getWidth() + 5;
            tempStats[i] = new JTextField(0);
            tempStats[i].setBounds(x, y, w, h);
            tempStats[i].addKeyListener(new Handler(this));
            tempStats[i].setName(i + "");
            tempStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(tempStats[i]);
        }
    }

    private void hp() {

        labtotalHP = new JLabel("<html><h5>TOTAL</h5></html>");
        labtotalHP.setBounds(0, 0, 70, 10);
        labtotalHP.setHorizontalAlignment(JTextField.CENTER);
        labtotalHP.setBackground(Color.WHITE);
        panelHP.add(labtotalHP);

        totalHP = new JTextField();
        totalHP.setBounds(20, labtotalHP.getHeight(), 30, 30);
        totalHP.setHorizontalAlignment(JTextField.CENTER);
        panelHP.add(totalHP);

        labcurrentHP = new JLabel("<html><h6>CURRENT HP</h6></html>");
        labcurrentHP.setBounds(0, totalHP.getY() + totalHP.getHeight() + 2, labtotalHP.getWidth(), labtotalHP.getHeight());
        labcurrentHP.setHorizontalAlignment(JTextField.CENTER);
        panelHP.add(labcurrentHP);

        currentHP = new JTextField();
        currentHP.setBounds(10, labcurrentHP.getY() + labcurrentHP.getHeight(), 50, 50);
        currentHP.setHorizontalAlignment(JTextField.CENTER);
        panelHP.add(currentHP);

        labdamage = new JLabel("<html><h6>DAMAGE</h6></html>");
        labdamage.setBounds(0, currentHP.getY() + 50, labtotalHP.getWidth(), labtotalHP.getHeight());
        labdamage.setHorizontalAlignment(JTextField.CENTER);
        panelHP.add(labdamage);

        damage = new JTextField();
        damage.setBounds(10, labdamage.getY() + 10, 50, 20);
        damage.setHorizontalAlignment(JTextField.CENTER);
        damage.setName("damage");
        damage.addKeyListener(new Handler(this)); //cuando texto.length sea mayor a x, aumentar, si no restaura
        panelHP.add(damage);
    }

    private void ac() {

        x = colStats[0].getX();
        y = colStats[0].getY() + 10;
        w = colStats[0].getWidth();
        h = colStats[0].getHeight();

        for (int i = 0; i < colDefense.length; i++, x += 35) {
            colDefense[i] = new JLabel("<html><h6>" + st.colDefense[i] + "</h6></html>");
            colDefense[i].setBounds(x, y, i == 0 ? w + 5 : w, h);
            colDefense[i].setHorizontalAlignment(JLabel.CENTER);
            panelAtAndDef.add(colDefense[i]);
            if (i == 0) {
                x += 20;
            }
        }

        x = colDefense[0].getX() - 1;
        y = colDefense[0].getY() + 15;
        w = tStats[0].getWidth();
        h = tStats[0].getHeight();

        for (int i = 0; i < tDefense.length; i++, y += 21) {
            tDefense[i] = new JLabel(st.tDefense[i]);
            tDefense[i].setBounds(x, y, w, h);
            tDefense[i].setForeground(Menu.KHAKI);
            tDefense[i].setBackground(Menu.DARKER_GRAY);
            tDefense[i].setHorizontalAlignment(JLabel.CENTER);
            tDefense[i].setOpaque(true);
            panelAtAndDef.add(tDefense[i]);
        }

        w = totalStats[0].getWidth();
        y = colDefense[0].getY() + 15;

        for (int i = 0; i < totalDefense.length; i++, y += 21) {
            x = tDefense[i].getX() + tDefense[i].getWidth() + 5;
            totalDefense[i] = new JTextField(0);
            totalDefense[i].setBounds(x, y, w, h);
            totalDefense[i].setEditable(false);
            totalDefense[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(totalDefense[i]);
        }

        y = colDefense[0].getY() + 15;

        for (int i = 0, j = 0; i < 3; i++, y += 21) {
            if (i != 1) {
                x = totalDefense[i].getX() + totalDefense[i].getWidth() + 5;
                armorDefense[j] = new JTextField(0);
                armorDefense[j].setBounds(x, y, w, h);
                armorDefense[j].setName("0" + i);                            //nombres de 2 cifras => 1ª=tabla, 2ª=fila
                armorDefense[j].addKeyListener(new Handler(this));
                armorDefense[j].setHorizontalAlignment(JTextField.CENTER);
                panelAtAndDef.add(armorDefense[j]);
                j++;    //añadimos j porque hay que dejar un hueco entre armor[0] y armor [1]
            }
        }

        y = colDefense[0].getY() + 15;

        for (int i = 0; i < 2; i++, y += 21) {
            x = armorDefense[0].getX() + armorDefense[0].getWidth() + 5;
            mods[modsCount] = new JTextField();    //mods 0,1
            mods[modsCount].setBounds(x, y, w, h);
            mods[modsCount].setEditable(false);
            mods[modsCount].setName(1 + "");
            mods[modsCount].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(mods[modsCount]);
            modsCount++;
        }

        y = colDefense[0].getY() + 15;

        for (int i = 0; i < sizeDefense.length; i++, y += 21) {
            x = mods[1].getX() + mods[1].getWidth() + 5;
            sizeDefense[i] = new JTextField(0);
            sizeDefense[i].setBounds(x, y, w, h);
            sizeDefense[i].setName("0" + i);
            sizeDefense[i].addKeyListener(new Handler(this));
            sizeDefense[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(sizeDefense[i]);
        }

        y = colDefense[0].getY() + 15;

        for (int i = 0; i < miscDefense.length; i++, y += 21) {
            x = sizeDefense[i].getX() + sizeDefense[i].getWidth() + 5;
            miscDefense[i] = new JTextField(0);
            miscDefense[i].setBounds(x, y, w, h);
            miscDefense[i].addKeyListener(new Handler(this));
            miscDefense[i].setName("0" + i);
            miscDefense[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(miscDefense[i]);
        }

        y = colDefense[0].getY() + 15;

        for (int i = 0; i < tempDefense.length; i++, y += 21) {
            x = miscDefense[i].getX() + miscDefense[i].getWidth() + 5;
            tempDefense[i] = new JTextField(0);
            tempDefense[i].setBounds(x, y, w, h);
            tempDefense[i].addKeyListener(new Handler(this));
            tempDefense[i].setName("0" + i);
            tempDefense[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(tempDefense[i]);
        }
    }

    private void saves() {

        x = colDefense[0].getX();
        y = tDefense[2].getY() + tDefense[2].getHeight() + 25;
        w = colStats[0].getWidth();
        h = colStats[0].getHeight();

        for (int i = 0; i < colSaves.length; i++, x += 35) {
            colSaves[i] = new JLabel("<html><h6>" + st.colSaves[i] + "</h6></html>");
            colSaves[i].setBounds(x, y, i == 0 ? w + 5 : w, h);
            colSaves[i].setHorizontalAlignment(JLabel.CENTER);
            panelAtAndDef.add(colSaves[i]);
            if (i == 0) {
                x += 20;
            }
        }

        x = colSaves[0].getX() - 1;
        y = colSaves[0].getY() + 15;
        w = tStats[0].getWidth();
        h = tStats[0].getHeight();

        for (int i = 0; i < tSaves.length; i++, y += 21) {
            tSaves[i] = new JLabel(st.tSaves[i]);
            tSaves[i].setBounds(x, y, w, h);
            tSaves[i].setForeground(Menu.KHAKI);
            tSaves[i].setBackground(Menu.DARKER_GRAY);
            tSaves[i].setHorizontalAlignment(JLabel.CENTER);
            tSaves[i].setOpaque(true);
            panelAtAndDef.add(tSaves[i]);
        }

        w = totalStats[0].getWidth();
        y = colSaves[0].getY() + 15;

        for (int i = 0; i < totalSaves.length; i++, y += 21) {
            x = tSaves[i].getX() + tSaves[i].getWidth() + 5;
            totalSaves[i] = new JTextField(0);
            totalSaves[i].setBounds(x, y, w, h);
            totalSaves[i].setEditable(false);
            totalSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(totalSaves[i]);
        }

        y = colSaves[0].getY() + 15;

        for (int i = 0; i < baseSaves.length; i++, y += 21) {
            x = totalSaves[i].getX() + totalSaves[i].getWidth() + 5;
            baseSaves[i] = new JTextField();
            baseSaves[i].setBounds(x, y, w, h);
            baseSaves[i].setName("1" + i);
            baseSaves[i].addKeyListener(new Handler(this));
            baseSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(baseSaves[i]);
        }

        y = colSaves[0].getY() + 15;

        for (int i = 0; i < 3; i++, y += 21) {
            x = baseSaves[i].getX() + baseSaves[i].getWidth() + 5;        //mods 2,3,4
            mods[modsCount] = new JTextField();
            mods[modsCount].setBounds(x, y, w, h);
            mods[modsCount].setEditable(false);
            mods[modsCount].setName((i == 0 ? 2 : (i == 1 ? 1 : 4)) + "");
            mods[modsCount].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(mods[modsCount]);
            modsCount++;
        }

        y = colSaves[0].getY() + 15;

        for (int i = 0; i < enhSaves.length; i++, y += 21) {
            x = mods[modsCount - 1].getX() + mods[modsCount - 1].getWidth() + 5;
            enhSaves[i] = new JTextField();
            enhSaves[i].setBounds(x, y, w, h);
            enhSaves[i].addKeyListener(new Handler(this));
            enhSaves[i].setName("1" + i);
            enhSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(enhSaves[i]);
        }

        y = colSaves[0].getY() + 15;

        for (int i = 0; i < miscSaves.length; i++, y += 21) {
            x = enhSaves[i].getX() + enhSaves[i].getWidth() + 5;
            miscSaves[i] = new JTextField(0);
            miscSaves[i].setBounds(x, y, w, h);
            miscSaves[i].addKeyListener(new Handler(this));
            miscSaves[i].setName("1" + i);
            miscSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(miscSaves[i]);
        }

        y = colSaves[0].getY() + 15;

        for (int i = 0; i < tempSaves.length; i++, y += 21) {
            x = miscDefense[i].getX() + miscDefense[i].getWidth() + 5;
            tempSaves[i] = new JTextField(0);
            tempSaves[i].setBounds(x, y, w, h);
            tempSaves[i].addKeyListener(new Handler(this));
            tempSaves[i].setName("1" + i);
            tempSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(tempSaves[i]);
        }
    }

    private void attacks() {

        x = colSaves[0].getX();
        y = tSaves[2].getY() + tSaves[2].getHeight() + 25;
        w = colStats[0].getWidth();
        h = colStats[0].getHeight();

        for (int i = 0; i < colAttack.length; i++, x += 35) {
            colAttack[i] = new JLabel("<html><h6>" + st.colAttack[i] + "</h6></html>");
            colAttack[i].setBounds(x, y, i == 0 ? w + 5 : w, h);
            colAttack[i].setHorizontalAlignment(JLabel.CENTER);
            panelAtAndDef.add(colAttack[i]);
            if (i == 0) {
                x += 20;
            }
        }

        x = colAttack[0].getX() - 1;
        y = colAttack[0].getY() + 15;
        w = tStats[0].getWidth();
        h = tStats[0].getHeight();

        for (int i = 0; i < tAttack.length; i++, y += 21) {
            tAttack[i] = new JLabel(st.tAttack[i]);
            tAttack[i].setBounds(x, y, w, h);
            tAttack[i].setForeground(Menu.KHAKI);
            tAttack[i].setBackground(Menu.DARKER_GRAY);
            tAttack[i].setHorizontalAlignment(JLabel.CENTER);
            tAttack[i].setOpaque(true);
            panelAtAndDef.add(tAttack[i]);
        }

        w = totalStats[0].getWidth();
        y = colAttack[0].getY() + 15;

        for (int i = 0; i < totalAttack.length; i++, y += 21) {
            x = tAttack[i].getX() + tAttack[i].getWidth() + 5;
            totalAttack[i] = new JTextField(0);
            totalAttack[i].setBounds(x, y, w, h);
            totalAttack[i].setEditable(false);
            totalAttack[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(totalAttack[i]);
        }

        y = colAttack[0].getY() + 15;

        for (int i = 0; i < BAB.length; i++, y += 21) {
            x = totalAttack[i].getX() + totalAttack[i].getWidth() + 5;
            BAB[i] = new JTextField(0);
            BAB[i].setBounds(x, y, w, h);
            BAB[i].setName(6 + (i + ""));
            BAB[i].addKeyListener(new Handler(this));
            BAB[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(BAB[i]);
        }

        y = colAttack[0].getY() + 15;

        for (int i = 0; i < 4; i++, y += 21) {
            x = BAB[i].getX() + BAB[i].getWidth() + 5;
            mods[modsCount] = new JTextField();  //mods 5,6,7,8
            mods[modsCount].setBounds(x, y, w, h);
            mods[modsCount].setEditable(false);
            mods[modsCount].setName((i < 2 ? i : (i - 2)) + "");  //mods[8]=DEX+STR
            mods[modsCount].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(mods[modsCount]);
            modsCount++;
        }

        y = colAttack[0].getY() + 15;

        for (int i = 0; i < sizeAttack.length; i++, y += 21) {
            x = mods[modsCount - 1].getX() + mods[modsCount - 1].getWidth() + 5;
            sizeAttack[i] = new JTextField(0);
            sizeAttack[i].setBounds(x, y, w, h);
            sizeAttack[i].setName("2" + i);
            sizeAttack[i].setHorizontalAlignment(JTextField.CENTER);
            sizeAttack[i].addKeyListener(new Handler(this));
            panelAtAndDef.add(sizeAttack[i]);
        }

        y = colAttack[0].getY() + 15;

        for (int i = 0; i < miscAttack.length; i++, y += 21) {
            x = sizeAttack[i].getX() + sizeAttack[i].getWidth() + 5;
            miscAttack[i] = new JTextField(0);
            miscAttack[i].setBounds(x, y, w, h);
            miscAttack[i].setName("2" + i);
            miscAttack[i].setHorizontalAlignment(JTextField.CENTER);
            miscAttack[i].addKeyListener(new Handler(this));
            panelAtAndDef.add(miscAttack[i]);
        }

        y = colAttack[0].getY() + 15;

        for (int i = 0; i < tempAttack.length; i++, y += 21) {
            x = miscAttack[i].getX() + miscAttack[i].getWidth() + 5;
            tempAttack[i] = new JTextField(0);
            tempAttack[i].setBounds(x, y, w, h);
            tempAttack[i].setName("2" + i);
            tempAttack[i].setHorizontalAlignment(JTextField.CENTER);
            tempAttack[i].addKeyListener(new Handler(this));
            panelAtAndDef.add(tempAttack[i]);
        }
    }

    private void skills() {

        x = colStats[0].getX();
        y = colStats[0].getY();
        w = 50;
        h = 15;

        for (int i = -1; i < colSkills.length; i++, x += 35) {
            if (i != -1) {
                colSkills[i] = new JLabel("<html><h6>" + st.colSkills[i] + "</h6></html>");
                colSkills[i].setBounds(x, y, i == 0 ? w + 5 : w, h);
                colSkills[i].setHorizontalAlignment(JLabel.CENTER);
                panelSkills.add(colSkills[i]);
            } else {
                x += 100;
            }
        }

        x = colSkills[0].getX() - 145;
        y = colSkills[0].getY() + 15;
        w = 150;
        h = 20;

        for (int i = 0; i < tSkills.length; i++, y += 21) {
            tSkills[i] = new JLabel(" " + st.tSkills[i]);
            tSkills[i].setBounds(x, y, w, h);
            tSkills[i].setForeground(Menu.KHAKI);
            tSkills[i].setBackground(i % 2 == 0 ? Color.DARK_GRAY : Menu.DARKER_GRAY);
            tSkills[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tSkills[i].setOpaque(true);
            tSkills[i].setHorizontalAlignment(JLabel.LEFT);
            panelSkills.add(tSkills[i]);
        }

        w = 30;
        y = colSkills[0].getY() + 15;

        for (int i = 0; i < totalSkills.length; i++, y += 21) {
            x = tSkills[i].getX() + tSkills[i].getWidth() + 5;
            totalSkills[i] = new JTextField(0);
            totalSkills[i].setBounds(x, y, w, h);
            totalSkills[i].setEditable(false);
            totalSkills[i].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(totalSkills[i]);
        }

        y = colSkills[0].getY() + 15;

        for (int i = 0; i < 35; i++, y += 21) {
            x = totalSkills[i].getX() + totalSkills[i].getWidth() + 5;
            mods[modsCount] = new JTextField();
            mods[modsCount].setBounds(x, y, w, h);    // mods 9-43
            mods[modsCount].setEditable(false);
            switch (i) {
                case 3:
                case 33:
                    mods[modsCount].setName(0 + "");
                    break;
                case 0:
                case 6:
                case 8:
                case 9:
                case 27:
                case 29:
                case 31:
                    mods[modsCount].setName(1 + "");
                    break;
                case 11:
                case 24:
                case 26:
                case 28:
                case 32:
                    mods[modsCount].setName(4 + "");
                    break;
                case 2:
                case 5:
                case 7:
                case 10:
                case 12:
                case 25:
                case 34:
                    mods[modsCount].setName(5 + "");
                    break;
                case 1:
                default:
                    mods[modsCount].setName(3 + "");
                    break;
            }
            mods[modsCount].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(mods[modsCount]);
            modsCount++;
        }

        y = colSkills[0].getY() + 15;

        for (int i = 0; i < rankSkills.length; i++, y += 21) {
            x = mods[modsCount - 1].getX() + mods[modsCount - 1].getWidth() + 5;
            rankSkills[i] = new JTextField(0);
            rankSkills[i].setBounds(x, y, w, h);
            rankSkills[i].addKeyListener(new Handler(this));
            rankSkills[i].setName("3" + i);
            rankSkills[i].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(rankSkills[i]);
        }

        y = colSkills[0].getY() + 15;

        for (int i = 0; i < trainedSkills.length; i++, y += 21) {
            x = rankSkills[i].getX() + rankSkills[i].getWidth() + 5;
            trainedSkills[i] = new JTextField(0);
            trainedSkills[i].setBounds(x, y, w, h);
            trainedSkills[i].addKeyListener(new Handler(this));
            trainedSkills[i].setName("3" + i);
            trainedSkills[i].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(trainedSkills[i]);
        }

        y = colSkills[0].getY() + 15;

        for (int i = 0; i < miscSkills.length; i++, y += 21) {
            x = trainedSkills[i].getX() + trainedSkills[i].getWidth() + 5;
            miscSkills[i] = new JTextField(0);
            miscSkills[i].setBounds(x, y, w, h);
            miscSkills[i].addKeyListener(new Handler(this));
            miscSkills[i].setName("3" + i);
            miscSkills[i].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(miscSkills[i]);
        }
    }

    private void armor() {

        x = 17;
        y = 30;
        w = 73;
        h = 15;

        for (int i = 0; i < colArmor.length; i++, x += 26) {
            colArmor[i] = new JLabel("<html><h6>" + st.colArmor[i] + "</h6></html>");
            colArmor[i].setBounds(x, y, w, h);
            colArmor[i].setHorizontalAlignment(JLabel.CENTER);
            panelArmor.add(colArmor[i]);
            if (i == 0) {
                x += 48;
                w = 25;
            }
        }

        x = colArmor[0].getX() - 1;
        y = colArmor[0].getY() + 15;
        w = 73;
        h = 20;

        addArmor = new JButton("<html><h4>ADD ARMOR</h4></html>");
        addArmor.addActionListener(new Handler(this));

        if (armor != null) {
            for (int j = 0; j < armor.size(); j++) {
                for (int i = 0; i < armor.get(j).length; i++, x += 26) {
                    armor.get(j)[i] = new JTextField();
                    armor.get(j)[i].setBounds(x, y, w, h);
                    armor.get(j)[i].setHorizontalAlignment(JLabel.CENTER);
                    panelArmor.add(armor.get(j)[i]);
                    if (i == 0) {
                        x += 48;
                        w = 25;
                    }
                }
                x = colArmor[0].getX() - 27;
                y = armor.get(j)[0].getY() + armor.get(j)[0].getHeight() + 1;
                w = 73;
                h = 20;

                panelArmor.setSize(panelArmor.getWidth(), panelArmor.getHeight() + armor.get(0)[0].getHeight());
            }
            addArmor.setBounds((panelArmor.getWidth() / 2) - 50, armor.get(armor.size() - 1)[0].getY() + armor.get(0)[0].getHeight()
                    + 20, 100, 20);
        } else {
            addArmor.setBounds((panelArmor.getWidth() / 2) - 55, colArmor[0].getY() + colArmor[0].getHeight() + 20, 110, 20);
        }
        addArmor.setFont(Menu.PFONT);
        panelArmor.add(addArmor);
    }

    private void weapons() {

        x = 17;
        y = 30;
        w = 73;
        h = 15;

        for (int i = 0; i < colWeapons.length; i++, x += 26) {
            colWeapons[i] = new JLabel("<html><h6>" + st.colWeapon[i] + "</h6></html>");
            colWeapons[i].setBounds(x, y, w, h);
            colWeapons[i].setHorizontalAlignment(JLabel.CENTER);
            panelWeapons.add(colWeapons[i]);
            if (i == 0) {
                x += 46; //48
                w = 25;
            }
        }

        x = colWeapons[0].getX() - 1;
        y = colWeapons[0].getY() + 15;
        w = 73;
        h = 20;

        addWeapon = new JButton("<html><h4>ADD WEAPON</h4></html>");
        addWeapon.addActionListener(new Handler(this));

        if (weapons != null) {
            for (int i = 0; i < weapons.size(); i++, x += 26) {
                for (int j = 0; j < weapons.get(i).length; j++) {
                    weapons.get(i)[j] = new JTextField();
                    weapons.get(i)[j].setBounds(x, y, w, h);
                    weapons.get(i)[j].setHorizontalAlignment(JLabel.CENTER);
                    panelWeapons.add(weapons.get(i)[j]);
                    if (i == 0) {
                        x += 48;
                        w -= 48;
                    }
                }
                x = colWeapons[0].getX() - 27;
                y = weapons.get(i)[0].getY() + weapons.get(i)[0].getHeight() + 1;
                w = 73;
                h = 20;

                panelWeapons.setSize(panelWeapons.getWidth(), panelWeapons.getHeight() + weapons.get(0)[0].getHeight());
            }

            addWeapon.setBounds((panelWeapons.getWidth() / 2) - 50, weapons.get(weapons.size() - 1)[0].getY() + weapons.get(0)[0].getHeight()
                    + 20, 100, 20);
        } else {
            addWeapon.setBounds((panelWeapons.getWidth() / 2) - 55, colWeapons[0].getY() + colWeapons[0].getHeight() + 20, 110, 20);
        }
        addWeapon.setFont(Menu.PFONT);
        panelWeapons.add(addWeapon);
    }

    private void init() {
        
        h = 20;

        labinit = new JLabel(" INIT ");
//        labinit.setBounds(panelWeapons.getX(), panelWeapons.getY() + panelWeapons.getHeight() + 20, 60, h);
        labinit.setBounds(panelAtAndDef.getX() + panelAtAndDef.getWidth() + 30, panelAtAndDef.getY()-15, 60, h);
        labinit.setForeground(Menu.KHAKI);
        labinit.setBackground(Menu.DARKER_GRAY);
        labinit.setOpaque(true);
        labinit.setHorizontalAlignment(JLabel.CENTER);
        totalPanel.add(labinit);

        w = 30;
        x = labinit.getX() + labinit.getWidth() + 5;
        y = labinit.getY();

        totalInit = new JTextField();
        totalInit.setBounds(x, y, w, h);
        totalInit.setEditable(false);
        totalInit.setHorizontalAlignment(JTextField.CENTER);
        totalPanel.add(totalInit);
        
        equalInit = new JLabel("=");
        equalInit.setBounds(totalInit.getX()+totalInit.getWidth()+5,y,10,h);
        totalPanel.add(equalInit);
        
        mods[44] = new JTextField();
        mods[44].setBounds(equalInit.getX()+equalInit.getWidth()+5,y,w,h);
        mods[44].setHorizontalAlignment(JTextField.CENTER);
        mods[44].setEditable(false);
        mods[44].setName(1+"");
        totalPanel.add(mods[44]);
        
        plusInit = new JLabel("+");
        plusInit.setBounds(mods[44].getX()+mods[44].getWidth()+2,y,10,h);
        totalPanel.add(plusInit);
        
        modInit = new JTextField();
        modInit.setBounds(plusInit.getX()+plusInit.getWidth()+2, y, w, h);
        modInit.setHorizontalAlignment(JTextField.CENTER);
        totalPanel.add(modInit);

    }

    private void featsAndFeatures() {
        //escribir las pasivas en labels y aplicarlas, poner las activas en un checkBox
    }
}

//
//for (int i = 0; i < armorFeatures.length; i++, x += 26) {
//            armorFeatures[i] = new JTextField();
//            armorFeatures[i].setBounds(x, y, w, h);
//            armorFeatures[i].setHorizontalAlignment(JLabel.CENTER);
//            panelArmor.add(armorFeatures[i]);
//            if (i == 0 || i == 5) {
//                x += 48;
//                w = 25;
//            }
//            if (i == 4) {
//                x = colArmor[0].getX() - 27;
//                y = armorFeatures[0].getY() + armorFeatures[0].getHeight() + 1;
//                w = 73;
//                h = 20;
//            }
//        }
