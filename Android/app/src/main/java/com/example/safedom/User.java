package com.example.safedom;

import android.view.Menu;

import org.checkerframework.checker.units.qual.A;

public class User {
    private String UserEmail;
    private String Pass;
    private String Nombre;
    private String Apellido;
    private String  Telefono;
    private String Dob;
    private String Rol;
    private String Genero;
    private String Altura;
    private String Peso;


    public User(String userEmail, String pass, String nombre, String apellido, String rol,String telefono,String genero,String dob,String altura,String peso) {
        UserEmail = userEmail;
        Pass = pass;
        Nombre = nombre;
        Apellido = apellido;
        Rol = rol;
        Telefono=telefono;
        Genero=genero;
        Dob=dob;
        Altura=altura;
        Peso=peso;

    }

    public User() {
        UserEmail = getUserEmail();
        Pass = getPass();
        Nombre = getNombre();
        Apellido = getApellido();
        Rol = getRol();
        Telefono=getTelefono();
        Genero=getGenero();
        Dob=getDob();
        Altura=getAltura();
        Peso=getPeso();

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

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
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

    public String getAltura() {
        return Altura;
    }

    public void setAltura(String altura) {
        Altura = altura;
    }

    public String getPeso() {
        return Peso;
    }

    public void setPeso(String peso) {
        Peso = peso;
    }
    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }


}
