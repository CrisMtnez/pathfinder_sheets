/*
 */
package pathfindersheet;

import java.io.File;
import java.util.Scanner;

public class SavedSheets {
    String textoFicha;
    Scanner s;
    File f;
    
    public SavedSheets(){
        f = new File(System.getProperty("user.home")+"/Pathfinder_Sheets");
        f.mkdir();       
    }
    
    public String[] listaPersonajes(){
        return f.list();
    }
    
    public String hojaPersonaje(String nombre){
        return (f.getPath()+"/"+nombre);
    }
}
