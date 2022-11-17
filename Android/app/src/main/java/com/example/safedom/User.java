package com.example.safedom;

import android.view.Menu;

public class User {
    private String UserEmail;
    private String Pass;
    private String Nombre;
    private String Apellido;
    private int Telefono;
    private String Dob;
    private String Rol;



    public User(String userEmail, String pass, String nombre, String apellido) {
        UserEmail = userEmail;
        Pass = pass;
        Nombre = nombre;
        Apellido = apellido;
    }
    public  User(){
        UserEmail=getUserEmail();
        Pass=getPass();
        Nombre=getNombre();
        Apellido=getApellido();
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
    public int getTelefono() {
        return Telefono;
    }
    public void setTelefono(int telefono) {
        Telefono = telefono;
    }
    public String getDob() {
        return Dob;
    }
    public void setDob(String dob) {
        Dob = dob;
    }
    public String getRol() {
        return Rol;
    }
    public void setRol(String rol) {
        Rol = rol;
    }


}
