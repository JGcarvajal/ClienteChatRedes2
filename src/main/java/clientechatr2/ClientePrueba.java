/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientechatr2;

import Conexiones.Loguin;
import Herramientas.UtilidadesCli;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author jgcar
 */
public class ClientePrueba {
   static UtilidadesCli util=new UtilidadesCli();
   static Usuario yo;
     public static void main(String args[]) throws IOException, Exception {
         Loguin loguin= new Loguin();
          String data="";
          String path="";
          
        String usuario="Gabo";
        String clave="12345";
          
        path = "/autenticacion";
        data="usuario="+usuario+"&clave="+clave;
     loguin.peticion(path, data); 
           //yo= util.BuscaAutenticados(usuario, clave);
         //System.out.println("Total de tokens " +yo.getToken());
     }
     
   public void agregarAmigos(Integer yo, Integer amigo){
       String respuesta="";
       
       //respuesta = util.AgregarAmigo(yo, amigo);
   }
    
   
}
