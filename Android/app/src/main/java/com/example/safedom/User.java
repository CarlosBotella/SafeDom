package com.example.safedom;

import android.view.Menu;

public class User {
    private String UserEmail;
    private String Pass;
    private String Nombre;
    private String Apellido;
    private int Telefono;
    private String Dob;
    private String Direccion;
    private Enum Rol;



    public User(String userEmail, String pass, String nombre, String apellido, int telefono, String dob, String direccion, Enum rol) {
        UserEmail = userEmail;
        Pass = pass;
        Nombre = nombre;
        Apellido = apellido;
        Telefono = telefono;
        Dob = dob;
        Direccion = direccion;
        Rol = rol;
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

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public Enum getRol() {
        return Rol;
    }

    public void setRol(Enum rol) {
        Rol = rol;
    }


}
