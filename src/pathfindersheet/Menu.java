package pathfindersheet;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Menu extends JFrame {

    JButton title;
    JPopupMenu opciones;
    JMenuItem abrirFicha, nuevaFicha, salir;
    public static final Font PFONT = new Font("Monospaced", Font.BOLD, 18);
    public static final Color KHAKI = new Color(240, 230, 140);
    public static final Color DARKER_GRAY = new Color(32, 32, 32);
    SavedSheets ss;
    String[] personajes;
    JMenuItem[] sheets;

    /*TO-DO:
    - leer y guardar fichas
    - layout con pestañas para equipment and magic items + pestaña para magia
    - feats, armor and weapons
    - boton dice roller
     */
    public Menu() {

        super("Pathfinder sheets");
        setLayout(null);
        setContentPane(new JLabel(new ImageIcon(Menu.class.getResource("img/background.png"))));

        ss = new SavedSheets();

        abrirFicha = new JMenu("      Open sheet          ");
        abrirFicha.setFont(PFONT);
        abrirFicha.setForeground(KHAKI);
        abrirFicha.setBackground(DARKER_GRAY);
        abrirFicha.setOpaque(true);
        abrirFicha.setFocusPainted(false);
        abrirFicha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        //abrirFicha.addMouseListener(new Handler(this));

        fichasGuardadas();
        
        nuevaFicha = new JMenuItem("      New sheet          ");
        nuevaFicha.setFont(PFONT);
        nuevaFicha.setForeground(KHAKI);
        nuevaFicha.setBackground(DARKER_GRAY);
        nuevaFicha.setFocusPainted(false);
        nuevaFicha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        nuevaFicha.addActionListener(new Handler(this));

        salir = new JMenuItem("      Exit         ");
        salir.setFont(PFONT);
        salir.setForeground(KHAKI);
        salir.setBackground(DARKER_GRAY);
        salir.setFocusPainted(false);
        salir.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        salir.addActionListener(new Handler(this));

        opciones = new JPopupMenu();
        opciones.addSeparator();
        opciones.add(abrirFicha);
        opciones.add(nuevaFicha);
        opciones.add(salir);
        opciones.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        title = new JButton(new ImageIcon(Menu.class.getResource("img/logo.png")));
        title.setLocation(0, 0);
        title.setBounds(0, 0, 310, 85);
        title.setOpaque(false);
        title.setContentAreaFilled(false);
        title.setFocusPainted(false);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
        title.addMouseListener(new Handler(this));
        add(title);

    }
    
    public void fichasGuardadas(){
        personajes = ss.listaPersonajes();
        if (personajes.length!=0 && personajes!=null) {                
                sheets = new JMenuItem[personajes.length];

                for (int i = 0; i < personajes.length; i++) {

                    sheets[i] = new JMenuItem("  "+personajes[i]+"  ");
                    sheets[i].setFont(PFONT);
                    sheets[i].setForeground(KHAKI);
                    sheets[i].setBackground(DARKER_GRAY);
                    sheets[i].setFocusPainted(false);
                    sheets[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    sheets[i].addActionListener(new Handler(this));
                    abrirFicha.add(sheets[i]);
                }
            } else {
                JMenuItem empty = new JMenuItem("  No saved sheets  ");
                empty.setFont(PFONT);
                empty.setForeground(KHAKI);
                empty.setBackground(DARKER_GRAY);
                empty.setFocusPainted(false);
                empty.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                abrirFicha.add(empty);
            }    
    }

}
