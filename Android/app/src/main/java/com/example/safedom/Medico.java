package com.example.safedom;

public class Medico extends User{
    private String IdMedico;

    public Medico(){
        super();
    }

    public Medico(String userEmail, String pass, String nombre, String apellido,String rol,String idmedico){
        super(userEmail,pass,nombre,apellido,rol);
        IdMedico=idmedico;
    }
}
