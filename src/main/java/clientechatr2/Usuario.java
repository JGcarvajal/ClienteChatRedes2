/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientechatr2;

/**
 *
 * @author jgcar
 */
public class Usuario {
    
    Integer id;
    String nombre;
    String apodo;
    String usuario;
    String clave;
    String token;
    String correo;
    boolean estado;

    
    
    public Usuario(String nombre,String correo, String usuario, String clave){
        this.usuario=usuario;
        this.nombre=nombre;
        this.clave=clave;  
        this.correo=correo;  
       

    }

    public Usuario() {
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String Usuario) {
        this.usuario = Usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

   
    
    
}
