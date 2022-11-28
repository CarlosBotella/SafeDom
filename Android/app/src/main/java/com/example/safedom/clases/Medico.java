package com.example.safedom.clases;

public class Medico {
    private String UserEmail;
    private String Pass;
    private String Nombre;
    private String Apellido;
    private String Rol;
    private String IdMedico;
    private String Genero;

    public Medico(){
        UserEmail=getUserEmail();
        Pass=getPass();
        Nombre=getNombre();
        Apellido=getApellido();
        Rol=getRol();
        IdMedico=getIdMedico();
        Genero=getGenero();
    }

    public Medico(String useremail, String pass, String nombre, String apellido,String rol,String idmedico,String genero){
        UserEmail=useremail;
        Pass=pass;
        Nombre=nombre;
        Apellido=apellido;
        Rol=rol;
        IdMedico=idmedico;
        Genero=genero;
    }

   
    public String getIdMedico() {
        return IdMedico;
    }

    public void setIdMedico(String idMedico) {
        IdMedico = idMedico;
    }
    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }
    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }
    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }
}
