package pathfindersheet;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class CharacterSheet extends JFrame {

    //A BAB DARLE NOMBRE 6
    
    SheetText bc;
    JPanel panelStats, panelAtAndDef, panelSkills;
    JLabel[] colStats = new JLabel[7], tStats = new JLabel[6],
             colDefense = new JLabel [7], tDefense = new JLabel[3], 
             colSaves = new JLabel[7], tSaves = new JLabel[3], 
             colAttack = new JLabel [7], tAttack = new JLabel[4],             
             colSkills = new JLabel [5], tSkills = new JLabel[35];
    JTextField[] totalStats = new JTextField[6], modStats = new JTextField[6], baseStats = new JTextField[6],
            enhStats = new JTextField[6], miscStats = new JTextField[6], tempStats = new JTextField[6],
            totalDefense = new JTextField[3], miscDefense = new JTextField[3], tempDefense = new JTextField[3],
            totalSaves = new JTextField[3], baseSaves = new JTextField[3], enhSaves = new JTextField[3],
            miscSaves = new JTextField[3], tempSaves = new JTextField[3],
            totalAttack = new JTextField[4], miscAttack = new JTextField[4], tempAttack = new JTextField[4],
            totalSkills = new JTextField[35], rankSkills = new JTextField[35], trainedSkills = new JTextField[35],
            miscSkills = new JTextField[35], mods = new JTextField[44];  //OJO, ir aumentando el tamaño segun los use!!!!!!!!
    JTextField languages, currentHP, damage, BAB, armor, size, armorPenalty, maxDex, conMod;
    JLabel totalHP, labcurrentHP, labdamage;
    ArrayList<JLabel[]> weapons;
    ArrayList<JLabel> feats;
    ArrayList<JLabel> features;
    ArrayList<JLabel> equipment;
    int x, y, w, h;
    int modsCount=0;
//    JTextField prueba;

    public CharacterSheet(boolean nuevoPersonaje, String personaje) {

        super(personaje);               //mover todo según sea nuevopersonaje o cargar
        setLayout(null);

        bc = new SheetText();

        stats();
        hp();
                
        panelAtAndDef = new JPanel();
        panelAtAndDef.setLayout(null);        
        panelAtAndDef.setBounds(panelStats.getX(),panelStats.getY()+panelStats.getHeight()+25,290,325);
        panelAtAndDef.setBackground(Color.LIGHT_GRAY);
        
        ac();
        saves();
        attacks();
        add(panelAtAndDef);
        skills();
        
        if (nuevoPersonaje) {  //nah, hacerlo todo en uno pasando parametros

        } else {

        }
    }
    
    private void stats(){
        
        panelStats = new JPanel();
        panelStats.setLayout(null);        
        panelStats.setBounds(20,15,290,142);
        
        x = 11; 
        y = 0; 
        w = 50;
        h = 15;
        
        for (int i = 0; i < colStats.length; i++, x += 35) {
            colStats[i] = new JLabel("<html><h6>" + bc.colStats[i] + "</h6></html>");
            colStats[i].setBounds(x, y, i == 0 ? w + 5 : w, h);            
            colStats[i].setHorizontalAlignment(JLabel.CENTER);
            panelStats.add(colStats[i]);
            if (i == 0) {
                x += 20;
            }
        }
        
        x = colStats[0].getX()-1;
        y = colStats[0].getY()+15;
        w = 60;
        h = 20;

        for (int i = 0; i < tStats.length; i++, y += 21) {
            tStats[i] = new JLabel(" " + bc.tStats[i]);
            tStats[i].setBounds(x, y, w, h);
            tStats[i].setForeground(Menu.KHAKI);
            tStats[i].setBackground(Menu.DARKER_GRAY);
            tStats[i].setOpaque(true);            
            tStats[i].setHorizontalAlignment(JLabel.CENTER);
            panelStats.add(tStats[i]);
        }
        
        w = 30;
        y = colStats[0].getY()+15;

        for (int i = 0; i < totalStats.length; i++, y += 21) {
            x = tStats[i].getX()+tStats[i].getWidth()+5;            
            totalStats[i] = new JTextField(0);
            totalStats[i].setBounds(x, y, w, h);
            totalStats[i].setEditable(false);
            totalStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(totalStats[i]);
        }

        y = colStats[0].getY()+15;
        
        for (int i = 0; i < modStats.length; i++, y += 21) {
            x = totalStats[i].getX()+totalStats[i].getWidth()+5;
            modStats[i] = new JTextField(0);
            modStats[i].setBounds(x, y, w, h);
            modStats[i].setEditable(false);
            modStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(modStats[i]);
        }
        
        y = colStats[0].getY()+15;

        for (int i = 0; i < baseStats.length; i++, y += 21) {
            x = modStats[i].getX()+modStats[i].getWidth()+5;
            baseStats[i] = new JTextField(0);
            baseStats[i].setBounds(x, y, w, h);
            baseStats[i].addKeyListener(new Handler(this));
            baseStats[i].setName(i + "");
            baseStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(baseStats[i]);
        }
        
        y = colStats[0].getY()+15;

        for (int i = 0; i < enhStats.length; i++, y += 21) {
            x = baseStats[i].getX()+baseStats[i].getWidth()+5;
            enhStats[i] = new JTextField(0);
            enhStats[i].setBounds(x, y, w, h);
            enhStats[i].addKeyListener(new Handler(this));
            enhStats[i].setName(i + "");
            enhStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(enhStats[i]);
        }
        
        y = colStats[0].getY()+15;

        for (int i = 0; i < miscStats.length; i++, y += 21) {
            x = enhStats[i].getX()+enhStats[i].getWidth()+5;
            miscStats[i] = new JTextField(0);
            miscStats[i].setBounds(x, y, w, h);
            miscStats[i].addKeyListener(new Handler(this));
            miscStats[i].setName(i + "");
            miscStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(miscStats[i]);
        }
        
        y = colStats[0].getY()+15;

        for (int i = 0; i < tempStats.length; i++, y += 21) {            
            x = miscStats[i].getX()+miscStats[i].getWidth()+5;
            tempStats[i] = new JTextField(0);
            tempStats[i].setBounds(x, y, w, h);
            tempStats[i].addKeyListener(new Handler(this));
            tempStats[i].setName(i + "");
            tempStats[i].setHorizontalAlignment(JTextField.CENTER);
            panelStats.add(tempStats[i]);
        }
        
        add(panelStats);
    }
    
    private void hp(){
        
        x = panelStats.getX()+panelStats.getWidth()+65;
        
        totalHP = new JLabel("<html><h5>TOTAL: </h5></html>");
        totalHP.setBounds(x, panelStats.getY()+6, 70, 10);
        totalHP.setHorizontalAlignment(JTextField.CENTER);
        totalHP.setBackground(Color.WHITE);
        add(totalHP);

        labcurrentHP = new JLabel("<html><h6>CURRENT HP</h6></html>");
        labcurrentHP.setBounds(x, totalHP.getY()*2, 70, 10);
        labcurrentHP.setHorizontalAlignment(JTextField.CENTER);
        add(labcurrentHP);

        currentHP = new JTextField();
        currentHP.setBounds(x+10, labcurrentHP.getY()+10, 50, 50);
        currentHP.setHorizontalAlignment(JTextField.CENTER);
        add(currentHP);

        labdamage = new JLabel("<html><h6>DAMAGE</h6></html>");
        labdamage.setBounds(x, currentHP.getY()+50, 70, 10);
        labdamage.setHorizontalAlignment(JTextField.CENTER);
        add(labdamage);

        damage = new JTextField();
        damage.setBounds(x+10, labdamage.getY()+10, 50, 20);
        damage.setHorizontalAlignment(JTextField.CENTER);
        add(damage);
    }
    
    private void ac(){
                
        x = colStats[0].getX();
        y = colStats[0].getY()+10;
        w = colStats[0].getWidth();
        h = colStats[0].getHeight();
        
        for (int i = 0; i < colDefense.length; i++, x += 35) {
            colDefense[i] = new JLabel("<html><h6>" + bc.colDefense[i] + "</h6></html>");
            colDefense[i].setBounds(x, y, i == 0 ? w + 5 : w, h);            
            colDefense[i].setHorizontalAlignment(JLabel.CENTER);
            panelAtAndDef.add(colDefense[i]);
            if (i == 0) {
                x += 20;
            }
        }
        
        x = colDefense[0].getX()-1;
        y = colDefense[0].getY()+15;
        w = tStats[0].getWidth();
        h = tStats[0].getHeight();

        for (int i = 0; i < tDefense.length; i++, y += 21) {
            tDefense[i] = new JLabel(bc.tDefense[i]);
            tDefense[i].setBounds(x, y, w, h);
            tDefense[i].setForeground(Menu.KHAKI);
            tDefense[i].setBackground(Menu.DARKER_GRAY);
            tDefense[i].setHorizontalAlignment(JLabel.CENTER);
            tDefense[i].setOpaque(true);
            panelAtAndDef.add(tDefense[i]);
        }
        
        w = totalStats[0].getWidth();
        y = colDefense[0].getY()+15;

        for (int i = 0; i < totalDefense.length; i++, y += 21) {
            x = tDefense[i].getX()+tDefense[i].getWidth()+5;            
            totalDefense[i] = new JTextField(0);
            totalDefense[i].setBounds(x, y, w, h);
            totalDefense[i].setEditable(false);
            totalDefense[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(totalDefense[i]);
        }
        
        y = colDefense[0].getY()+15;
        
        for (int i = 0; i < 3; i++, y += 21) {
            if (i!=1) {
                x = totalDefense[i].getX() + totalDefense[i].getWidth() + 5;                
                armor = new JTextField(0);
                armor.setBounds(x, y, w, h);
                armor.setName("0"+i);
                armor.setHorizontalAlignment(JTextField.CENTER);
                panelAtAndDef.add(armor);
            }
        }
        
        y = colDefense[0].getY()+15;

        for (int i = 0; i < 2; i++, y += 21) {
            x = armor.getX()+armor.getWidth()+5;            
            mods[modsCount] = new JTextField();
            mods[modsCount].setBounds(x,y,w,h);
            mods[modsCount].setEditable(false);
            mods[modsCount].setName(1+"");
            mods[modsCount].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(mods[modsCount]);
            modsCount++;
        }
        
        y = colDefense[0].getY()+15;

        for (int i = 0; i < 3; i++, y += 21) {
            x = mods[1].getX()+mods[1].getWidth()+5;            
            size = new JTextField(0);
            size.setBounds(x,y,w,h);
            size.setName("0"+i); 
            size.setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(size);
        }
        
        y = colDefense[0].getY()+15;

        for (int i = 0; i < miscDefense.length; i++, y += 21) {
            x = size.getX()+size.getWidth()+5;
            miscDefense[i] = new JTextField(0);
            miscDefense[i].setBounds(x, y, w, h);
            miscDefense[i].addKeyListener(new Handler(this));
            miscDefense[i].setName("0"+i);
            miscDefense[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(miscDefense[i]);
        }
        
        y = colDefense[0].getY()+15;

        for (int i = 0; i < tempDefense.length; i++, y += 21) {
            x = miscDefense[i].getX()+miscDefense[i].getWidth()+5;
            tempDefense[i] = new JTextField(0);
            tempDefense[i].setBounds(x, y, w, h);
            tempDefense[i].addKeyListener(new Handler(this));
            tempDefense[i].setName("0"+i);
            tempDefense[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(tempDefense[i]);
        }
    }
    
    private void saves(){
        
        x = colDefense[0].getX();
        y = tDefense[2].getY()+tDefense[2].getHeight()+25;
        w = colStats[0].getWidth();
        h = colStats[0].getHeight();
        
        for (int i = 0; i < colSaves.length; i++, x += 35) {
            colSaves[i] = new JLabel("<html><h6>" + bc.colSaves[i] + "</h6></html>");
            colSaves[i].setBounds(x, y, i == 0 ? w + 5 : w, h);            
            colSaves[i].setHorizontalAlignment(JLabel.CENTER);
            panelAtAndDef.add(colSaves[i]);
            if (i == 0) {
                x += 20;
            }
        }
        
        x = colSaves[0].getX()-1;
        y = colSaves[0].getY()+15;
        w = tStats[0].getWidth();
        h = tStats[0].getHeight();

        for (int i = 0; i < tSaves.length; i++, y += 21) {
            tSaves[i] = new JLabel(bc.tSaves[i]);
            tSaves[i].setBounds(x, y, w, h);
            tSaves[i].setForeground(Menu.KHAKI);
            tSaves[i].setBackground(Menu.DARKER_GRAY);
            tSaves[i].setHorizontalAlignment(JLabel.CENTER);
            tSaves[i].setOpaque(true);
            panelAtAndDef.add(tSaves[i]);
        }
        
        w = totalStats[0].getWidth();
        y = colSaves[0].getY()+15;

        for (int i = 0; i < totalSaves.length; i++, y += 21) {
            x = tSaves[i].getX()+tSaves[i].getWidth()+5;            
            totalSaves[i] = new JTextField(0);
            totalSaves[i].setBounds(x, y, w, h);
            totalSaves[i].setEditable(false);
            totalSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(totalSaves[i]);
        }
        
        y = colSaves[0].getY()+15;
        
        for (int i = 0; i < baseSaves.length; i++, y += 21) {
            x = totalSaves[i].getX()+totalSaves[i].getWidth()+5;            
            baseSaves[i] = new JTextField(0);
            baseSaves[i].setBounds(x, y, w, h);
            baseSaves[i].setName("1"+i);
            baseSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(baseSaves[i]);
        }
        
        y = colSaves[0].getY()+15;

        for (int i = 0; i < 3; i++, y += 21) {
            x = baseSaves[i].getX()+baseSaves[i].getWidth()+5;            
            mods[modsCount] = new JTextField();
            mods[modsCount].setBounds(x,y,w,h);
            mods[modsCount].setEditable(false);
            mods[modsCount].setName((i==0?2:(i==1?1:4))+"");
            mods[modsCount].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(mods[modsCount]);
            modsCount++;
        }
        
        y = colSaves[0].getY()+15;

        for (int i = 0; i < enhSaves.length; i++, y += 21) {
            x = mods[modsCount-1].getX()+mods[modsCount-1].getWidth()+5;
            enhSaves[i] = new JTextField(0);
            enhSaves[i].setBounds(x, y, w, h);
            enhSaves[i].addKeyListener(new Handler(this));
            enhSaves[i].setName("1"+i);
            enhSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(enhSaves[i]);
        }
        
        y = colSaves[0].getY()+15;

        for (int i = 0; i < miscSaves.length; i++, y += 21) {
            x = size.getX()+size.getWidth()+5;
            miscSaves[i] = new JTextField(0);
            miscSaves[i].setBounds(x, y, w, h);
            miscSaves[i].addKeyListener(new Handler(this));
            miscSaves[i].setName("1"+i);
            miscSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(miscSaves[i]);
        }
        
        y = colSaves[0].getY()+15;

        for (int i = 0; i < tempSaves.length; i++, y += 21) {
            x = miscDefense[i].getX()+miscDefense[i].getWidth()+5;
            tempSaves[i] = new JTextField(0);
            tempSaves[i].setBounds(x, y, w, h);
            tempSaves[i].addKeyListener(new Handler(this));
            tempSaves[i].setName("1"+i);
            tempSaves[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(tempSaves[i]);
        }
    }
    
    private void attacks(){
        
        x = colSaves[0].getX();
        y = tSaves[2].getY()+tSaves[2].getHeight()+25;
        w = colStats[0].getWidth();
        h = colStats[0].getHeight();
        
        for (int i = 0; i < colAttack.length; i++, x += 35) {
            colAttack[i] = new JLabel("<html><h6>" + bc.colAttack[i] + "</h6></html>");
            colAttack[i].setBounds(x, y, i == 0 ? w + 5 : w, h);            
            colAttack[i].setHorizontalAlignment(JLabel.CENTER);
            panelAtAndDef.add(colAttack[i]);
            if (i == 0) {
                x += 20;
            }
        }
        
        x = colAttack[0].getX()-1;
        y = colAttack[0].getY()+15;
        w = tStats[0].getWidth();
        h = tStats[0].getHeight();

        for (int i = 0; i < tAttack.length; i++, y += 21) {
            tAttack[i] = new JLabel(bc.tAttack[i]);
            tAttack[i].setBounds(x, y, w, h);
            tAttack[i].setForeground(Menu.KHAKI);
            tAttack[i].setBackground(Menu.DARKER_GRAY);
            tAttack[i].setHorizontalAlignment(JLabel.CENTER);
            tAttack[i].setOpaque(true);
            panelAtAndDef.add(tAttack[i]);
        }
        
        w = totalStats[0].getWidth();
        y = colAttack[0].getY()+15;

        for (int i = 0; i < totalAttack.length; i++, y += 21) {
            x = tAttack[i].getX()+tAttack[i].getWidth()+5;            
            totalAttack[i] = new JTextField(0);
            totalAttack[i].setBounds(x, y, w, h);
            totalAttack[i].setEditable(false);
            totalAttack[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(totalAttack[i]);
        }
        
        y = colAttack[0].getY()+15;
        
        for (int i = 0; i < 4; i++, y += 21) {
            x = totalAttack[i].getX()+totalAttack[i].getWidth()+5;            
            BAB = new JTextField(0);
            BAB.setBounds(x, y, w, h);
            BAB.setName(6+"");                     //OJO!! pillar name 6 junto a los de columnas (20,21,22,23)
            BAB.setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(BAB);
        }
        
        y = colAttack[0].getY()+15;

        for (int i = 0; i < 4; i++, y += 21) {
            x = BAB.getX()+BAB.getWidth()+5;            
            mods[modsCount] = new JTextField();
            mods[modsCount].setBounds(x,y,w,h);
            mods[modsCount].setEditable(false);
            mods[modsCount].setName((i<2?i:(i-2))+"");  //OJO! AQUI HACER UN CALCULO A MAYORES cuando mods[8], sumar STR!
            mods[modsCount].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(mods[modsCount]);
            modsCount++;
        }
        
        y = colAttack[0].getY()+15;

        for (int i = 0; i < 4; i++, y += 21) {
            x = mods[modsCount-1].getX()+mods[modsCount-1].getWidth()+5;
            size = new JTextField(0);
            size.setBounds(x, y, w, h);
            size.addKeyListener(new Handler(this));
            size.setName("2"+i);
            size.setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(size);
        }
        
        y = colAttack[0].getY()+15;

        for (int i = 0; i < miscAttack.length; i++, y += 21) {
            x = size.getX()+size.getWidth()+5;
            miscAttack[i] = new JTextField(0);
            miscAttack[i].setBounds(x, y, w, h);
            miscAttack[i].addKeyListener(new Handler(this));
            miscAttack[i].setName("1"+i);
            miscAttack[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(miscAttack[i]);
        }
        
        y = colAttack[0].getY()+15;

        for (int i = 0; i < tempAttack.length; i++, y += 21) {
            x = miscAttack[i].getX()+miscAttack[i].getWidth()+5;
            tempAttack[i] = new JTextField(0);
            tempAttack[i].setBounds(x, y, w, h);
            tempAttack[i].addKeyListener(new Handler(this));
            tempAttack[i].setName("1"+i);
            tempAttack[i].setHorizontalAlignment(JTextField.CENTER);
            panelAtAndDef.add(tempAttack[i]);
        }
    }
    
    private void skills(){
        //nombres de 2 num => 2ºnum es la fila
        
        panelSkills = new JPanel();
        panelSkills.setLayout(null);        
        panelSkills.setBounds(panelStats.getX()+panelStats.getWidth()+170,panelStats.getY(),340,760);
        
        x = colStats[0].getX();   //ajustar
        y = colStats[0].getY();
        w = 50;
        h = 15;
        
        for (int i = -1; i < colSkills.length; i++, x += 35) {
            if (i!=-1){
                colSkills[i] = new JLabel("<html><h6>" + bc.colSkills[i] + "</h6></html>");
                colSkills[i].setBounds(x, y, i == 0 ? w + 5 : w, h);            
                colSkills[i].setHorizontalAlignment(JLabel.CENTER);
                panelSkills.add(colSkills[i]);
            } else {            
                x += 140;  //ajustar
            }
        }
        
        x = colSkills[0].getX()-141;
        y = colSkills[0].getY()+15;
        w = 150;
        h = 20;

        for (int i = 0; i < tSkills.length; i++, y += 21) {
            tSkills[i] = new JLabel(" " + bc.tSkills[i]);
            tSkills[i].setBounds(x, y, w, h);
            tSkills[i].setForeground(Menu.KHAKI);
            tSkills[i].setBackground(i%2==0?Color.DARK_GRAY:Menu.DARKER_GRAY);
            tSkills[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tSkills[i].setOpaque(true);            
            tSkills[i].setHorizontalAlignment(JLabel.LEFT);
            panelSkills.add(tSkills[i]);
        }
        
        w = 30;
        y = colSkills[0].getY()+15;

        for (int i = 0; i < totalSkills.length; i++, y += 21) {
            x = tSkills[i].getX()+tSkills[i].getWidth()+5;            
            totalSkills[i] = new JTextField(0);
            totalSkills[i].setBounds(x, y, w, h);
            totalSkills[i].setEditable(false);
            totalSkills[i].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(totalSkills[i]);
        }

        y = colSkills[0].getY()+15;
        
        for (int i = 0; i < 35; i++, y += 21) {
            x = totalSkills[i].getX()+totalSkills[i].getWidth()+5;
            mods[modsCount] = new JTextField();
            mods[modsCount].setBounds(x,y,w,h);
            mods[modsCount].setEditable(false);
            switch(i){
                case 3:
                case 33:
                    mods[modsCount].setName(0+"");  
                    break;
                case 0:
                case 6:
                case 8:
                case 9:
                case 27:
                case 29:
                case 31:
                    mods[modsCount].setName(1+"");  
                    break;                
                case 11:
                case 24:
                case 26:
                case 28:
                case 32:
                    mods[modsCount].setName(4+"");
                    break;
                case 2:
                case 5:
                case 7:
                case 10:
                case 12:
                case 25:
                case 34:
                    mods[modsCount].setName(5+"");
                    break;
                    case 1:
                default:
                    mods[modsCount].setName(3+"");
                    break;                    
            }            
            mods[modsCount].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(mods[modsCount]);
            modsCount++;
        }
        
        y = colSkills[0].getY()+15;

        for (int i = 0; i < rankSkills.length; i++, y += 21) {
            x = mods[modsCount-1].getX()+mods[modsCount-1].getWidth()+5;
            rankSkills[i] = new JTextField(0);
            rankSkills[i].setBounds(x, y, w, h);
            rankSkills[i].addKeyListener(new Handler(this));
            rankSkills[i].setName("3"+i);
            rankSkills[i].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(rankSkills[i]);
        }
        
        y = colSkills[0].getY()+15;

        for (int i = 0; i < trainedSkills.length; i++, y += 21) {
            x = rankSkills[i].getX()+rankSkills[i].getWidth()+5;
            trainedSkills[i] = new JTextField(0);
            trainedSkills[i].setBounds(x, y, w, h);
            trainedSkills[i].addKeyListener(new Handler(this));
            trainedSkills[i].setName("3"+i);
            trainedSkills[i].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(trainedSkills[i]);
        }
        
        y = colSkills[0].getY()+15;

        for (int i = 0; i < miscSkills.length; i++, y += 21) {
            x = trainedSkills[i].getX()+trainedSkills[i].getWidth()+5;
            miscSkills[i] = new JTextField(0);
            miscSkills[i].setBounds(x, y, w, h);
            miscSkills[i].addKeyListener(new Handler(this));
            miscSkills[i].setName("3"+i);
            miscSkills[i].setHorizontalAlignment(JTextField.CENTER);
            panelSkills.add(miscSkills[i]);
        }

        add(panelSkills);
    }
    
    private void armorAndWeapons(){
        
    }
    
    private void featsAndFeatures(){
        //escribir las pasivas en labels y aplicarlas, poner las activas en un checkBox
    }
}
