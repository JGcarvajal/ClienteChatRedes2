/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexiones;

import clientechatr2.Usuario;
import Herramientas.UtilidadesCli;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author jgcar
 */
public class Autenticacion extends HttpServlet {

    /**
     * protected void doGet(HttpServletRequest request, HttpServletResponse
     * response) throws ServletException, IOException {
     * System.out.println("HttpServletRequest : " + request);
     * System.out.println("request.getMethod() : " + request.getMethod());
     * System.out.println("request.getQueryString() " +
     * request.getQueryString()); response.setContentType("application/json");
     * response.setStatus(HttpServletResponse.SC_OK);
     * response.getWriter().println("{ \"status\": \"ok\"}"); }
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Parametro recibido = " + request.getParameter("usuario"));
        System.out.println("Parametro recibido = " + request.getParameter("clave"));

        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        String respuesta = "666";
        UtilidadesCli utilidades = new UtilidadesCli();
        List<Usuario> res = utilidades.login(usuario, clave);

        if (res.size() == 1) {
            respuesta = "333";
        } else {
            if (res.size() == 0) {
                respuesta = "444";
            } else {

                respuesta = "666";
            }
        }

        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(respuesta);
    }
}
