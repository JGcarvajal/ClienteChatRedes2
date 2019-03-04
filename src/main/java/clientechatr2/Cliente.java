
package clientechatr2;
 
// Java implementation for multithreaded chat client 
// Save file as Client.java 
import Conexiones.Loguin;
import clientechatr2.Recursos;
import Herramientas.TipoArchivo;
import Herramientas.UtilidadesCli;
import static clientechatr2.ClientePrueba.yo;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.Fields;
import org.eclipse.jetty.util.Fields.Field;

 
public class Cliente {
 
    final static int ServerPort = 8000;
 static UtilidadesCli util=new UtilidadesCli();
 static Usuario yo=new Usuario();
 static  String data="";
 static  String path="";
 static Loguin loguin;
 static String token="";
   
    public static void main(String args[]) throws UnknownHostException, IOException {


        
         //registrarUsuario();
         //EnviarSolicitud();
         //logOut();
         if( loguin().equals("1")){
         
            
        Scanner scn = new Scanner(System.in); 
        
        // getting localhost ip 
        InetAddress ip = InetAddress.getByName("127.0.0.1");
 
        // establish the connection 
        Socket s = new Socket(ip, ServerPort);
        // obtaining input and out streams 
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
 
        // sendMessage thread 
        Thread sendMessage = new Thread(new Runnable() {
            String[] dataMsj;
            
            @Override
            public void run() {
                while (true) {
 
                  
                    String msg = scn.nextLine();
 
                    try {
                        // write on the output stream 
                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
 
       
        
        // readMessage thread 
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
 
                while (true) {
                    try {
                        // read the message sent to this client 
                        String mensaje = dis.readUTF();
 
                        HashMap<String, String> p = Herramientas.UtilidadesCli.parametros(mensaje);
                         
                        String accion = p.get("tipo");
 
                        //si accion es Enviar Mensaje:
                        if (Comandos._MSJ.equals(accion)) {
                            System.out.println(p.get("origen") + " --- " + p.get("msj"));
                        } else if (Comandos._ARCHIVO.equals(accion)) {
                            System.out.println("Recibiendo Archivo ....");
                            String nombreArchivo = p.get("n_archivo");
                            byte[] archivoRecibido = Herramientas.Archivos.decodeArchivo(p.get("base64"));
                            FileOutputStream imageOutFile = new FileOutputStream(nombreArchivo);
                            imageOutFile.write(archivoRecibido);
                            imageOutFile.close();
                            System.out.println("Archivo Recibido ....");
                             
                            //TODO: Ruta de trabajo archivos
                            String ruta = Herramientas.Archivos.rutaAlmacenamiento(nombreArchivo, TipoArchivo.ARCHIVO);
                            System.out.println("Ruta: " + ruta);
                        }else if (Comandos._LISTAUSUARIOS.equals(accion)){
                            System.out.println(p.get("destino") + ") : usuarios conectados " + p.get("listado"));
                        }else if (Comandos._SOLICITUDES.equals(accion)){
                             System.out.println(p.get("destino") + ") : solicitudes " + p.get("listado"));
                        }
                        else if(Comandos._ERRORMSJ.equals(accion)){
                            System.out.println(p.get("msj"));
                        }
 
                    } catch (IOException e) {
 
                        e.printStackTrace();
                    }
                }
            }
        });
 
        sendMessage.start();
        readMessage.start();
 
    }
         else{
             System.out.println("No se pudo autenticar");
         }
    }
    
      private void startClient() throws Exception {
      
    }
      
       public static void registrarUsuario(){
            String nombre="Fernandito";
            String usuario="Fercho";
            String clave="12345";
            String correo="patilla3000@gmail.com";

           yo =new Usuario(nombre,correo,usuario,clave);
           
           path = "/RegistrarUsuario";
           data="nombre="+yo.getNombre()+"&correo="+yo.getCorreo()
                   +"&usuario="+yo.getUsuario()+"&clave="+yo.getClave();
           //data=codificarBase64(data);
     Recursos.peticion(path, data); 
        }
       
       public static void EnviarSolicitud(){
           String respuesta="";
           
            String id="1";
            String codUsurio="6";
            String mensaje="se mi amigo o te destripo cuando duermas";
           System.out.println("clientechatr2.Cliente.EnviarSolicitud()");
           path = "/EnviarSolicitud";
           data="id="+id+"&codUsuario="+codUsurio+"&mensaje="+mensaje;
           //data=codificarBase64(data);
     respuesta=loguin.peticion(path, data); 
           System.out.println("Respuestas: "+respuesta);
        }
       public static void logOut(){
           String respuesta="";
           
            String id="6";
            String token="okjfqg";
            
           path = "/LogOut";
           data="id="+id+"&token="+token;
           //data=codificarBase64(data);
     respuesta=Recursos.peticion(path, data); 
        }
       
       public static String loguin(){
             loguin= new Loguin();
  String res="";
  String respServ="";
 
          
          
           Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        System.out.println("Ingrese usuario");
        String usuario=entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
        System.out.println("Ingrese clave");
        String clave=entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
          
        path = "/autenticacion";
        data="usuario="+usuario+"&clave="+clave;
     res=loguin.peticion(path, data); 
     String[] data=res.split(";");
     respServ=data[0];
     token=data[1];
        System.out.println("repuesta del loguin "+res);
           //yo= util.BuscaAutenticados(usuario, clave);
         //System.out.println("Total de tokens " +yo.getToken());
         
         return respServ;
       }
public static String codificarBase64(String data){
    
    Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data.getBytes(StandardCharsets.UTF_8) );        
        
}
    
}

