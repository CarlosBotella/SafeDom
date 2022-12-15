package com.example.safedom.clases;

import java.io.Serializable;

public class Medico implements Serializable {
    private String UserEmail;
    private String Pass;
    private String Nombre;
    private String Apellido;
    private String Rol;
    private String IdMedico;
    private String Genero;
    private String Foto;

    public Medico(String userEmail, String pass, String nombre, String apellido, String rol, String idMedico, String genero, String foto) {
        UserEmail = userEmail;
        Pass = pass;
        Nombre = nombre;
        Apellido = apellido;
        Rol = rol;
        IdMedico = idMedico;
        Genero = genero;
        Foto = foto;
    }

    public Medico(){
        UserEmail=getUserEmail();
        Pass=getPass();
        Nombre=getNombre();
        Apellido=getApellido();
        Rol=getRol();
        IdMedico=getIdMedico();
        Genero=getGenero();
        Foto=getFoto();
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

    public String getIdMedico() {
        return IdMedico;
    }

    public void setIdMedico(String idMedico) {
        IdMedico = idMedico;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
