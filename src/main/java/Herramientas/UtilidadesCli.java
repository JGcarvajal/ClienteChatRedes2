/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;
import clientechatr2.Comandos;
import clientechatr2.Usuario;
import Conexiones.Conexion;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Array;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.List;
import org.sql2o.Connection;

/**
 *
 * @author gusta
 */
public class UtilidadesCli {

    public static HashMap<String, String> parametros(String mensaje) {
        HashMap<String, String> res = new HashMap<>();

        //System.out.println("[Parametros] str " + mensaje);
        StringTokenizer st = new StringTokenizer(mensaje, "||");
        String accion = st.nextToken();
        res.put("tipo", accion);
        res.put("token", st.nextToken());
        res.put("origen", st.nextToken());
        res.put("destino", st.nextToken());

        if (Comandos._MSJ.equals(accion)) {
            res.put("msj", st.nextToken());
        } else if (Comandos._ARCHIVO.equals(accion)) {
            res.put("n_archivo", st.nextToken());
            res.put("base64", st.nextToken());
        } else if (Comandos._LISTAUSUARIOS.equals(accion)) {
            res.put("listado", st.nextToken());
        } else if (Comandos._SOLICITUDES.equals(accion)) {
            res.put("listado", st.nextToken());
        }

        System.out.println("Parametros Recibidos " + res.size());

        return res;
    }

    

    public List<Usuario> login(String usuario, String clave) {
        List<Usuario> resultado;
        String ramdon = generateRandomText();
        try (Connection con = Conexion.getSql2o().open()) {
            final String query
                    = "SELECT id, nombre , apodo , correo, usuario, clave "
                    + "FROM usuarios WHERE usuario = :usuario3 AND clave = :clave";

            resultado = con.createQuery(query)
                    .addParameter("usuario3", usuario)
                    .addParameter("clave", clave)
                    .executeAndFetch(Usuario.class);
            if (resultado.size() == 0) {
                ingresarAutenticados(resultado.get(0).getId(), ramdon);
                actualizarEstado(resultado.get(0).getId(), 1, ramdon);
            }
        }
        return resultado;
    }

    public List<Usuario> BuscaUsuario(String usuario) {

        try (Connection con = Conexion.getSql2o().open()) {
            final String query
                    = "SELECT id, nombre , apodo , correo, usuario, clave "
                    + "FROM usuarios WHERE usuario = :usuario3 AND clave = :clave";

            return con.createQuery(query)
                    .addParameter("usuario3", usuario)
                    .executeAndFetch(Usuario.class);
        }

    }

    public int ingresarAutenticados(Integer id, String token) {

        try (Connection con = Conexion.getSql2o().open()) {
            final String query
                    = "insert into autenticados (id,token) values(:id,:token)";

            return con.createQuery(query)
                    .addParameter("id", id)
                    .addParameter("token", token)
                    .executeUpdate()
                    .getResult();
        }

    }
    
        public void BuscaAutenticados(String usuario, String clave) {

        
        }
        

    

    public int actualizarUsuario(Usuario usuario) {
        int res = -1;

        final String updateQuery
                = "UPDATE usuario SET :nombre, :apodo,:correo,:usuario,:clave,:token) WHERE id = :id";

        try (Connection con = Conexion.getSql2o().open()) {
            res = con.createQuery(updateQuery, true)
                    .bind(usuario)
                    .executeUpdate()
                    .getResult();
        }

        return res;
    }

    public int actualizarEstado(Integer id, int estado, String token) {
        int res = -1;

        final String updateQuery
                = "UPDATE usuario SET estado=:estado WHERE id = :id and token =:token";

        try (Connection con = Conexion.getSql2o().open()) {
            res = con.createQuery(updateQuery, true)
                    .addParameter("id", id)
                    .addParameter("estado", estado)
                    .addParameter("token", token)
                    .executeUpdate()
                    .getResult();
        }

        return res;
    }

    public ArrayList AmigosConectados(String id, String token) {
        ArrayList res = new ArrayList();

        final String updateQuery
                = "SELECT am.id FROM  usuarios us inner join amigos am on us.id=am.id "
                + "where us.conectado=true and us.id=:id";

        try (Connection con = Conexion.getSql2o().open()) {
            con.createQuery(updateQuery, true)
                    .addParameter("id", id)
                    .executeUpdate()
                    .getResult();
        }

        return res;
    }

    public static String generateRandomText() {
        SecureRandom random = new SecureRandom();
        String text = new BigInteger(130, random).toString(32);
        return text;
    }
}
