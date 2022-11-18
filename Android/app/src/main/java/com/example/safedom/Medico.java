package com.example.safedom;

public class Medico {
    private String UserEmail;
    private String Pass;
    private String Nombre;
    private String Apellido;
    private String IdMedico;

    public Medico(){
        UserEmail=getUserEmail();
        Pass=getPass();
        Nombre=getNombre();
        Apellido=getApellido();
        IdMedico=getIdMedico();
    }

    public Medico(String useremail, String pass, String nombre, String apellido,String rol,String idmedico){
        UserEmail=useremail;
        Pass=pass;
        Nombre=nombre;
        Apellido=apellido;
        IdMedico=idmedico;
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
}
