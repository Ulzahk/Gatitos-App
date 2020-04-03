package com.mycompany.gatos_app;

import java.io.IOException;
import javax.swing.JOptionPane;

public class Inicio {
    
    public static void main (String[] args) throws IOException{
        int opcionMenu = -1;
        String[] botones = {"1. Ver Gatos","2. Ver Gatos Favoritos", "3. Salir"};
        
        do {    
            //Menú principal
            String opcion = (String) JOptionPane.showInputDialog(null, "Gatito Java", "Menu Principal", JOptionPane.INFORMATION_MESSAGE,
                    null, botones, botones[0]);
            //Validamos que opción selecciona el usuario
            for (int i = 0; i < botones.length; i++) {
                if(opcion.equals(botones[i])){
                    opcionMenu = i;
                }
            }
            
            switch(opcionMenu){
                case 0:
                    GatosServices.verGatos();
                    break;
                case 1:
                    Gatos gato = new Gatos();
                    GatosServices.verFavoritos(gato.getApiKey());
                    break;
                default:
                    break;
            }
            
            
        } while (opcionMenu != -1);
    }  
}
