package com.example.safedom;

public class Medico extends User{


    private String IdMedico;

    public Medico(){
        super();
        IdMedico=getIdMedico();
    }

    public Medico(String userEmail, String pass, String nombre, String apellido,String rol,String idmedico){
        super(userEmail,pass,nombre,apellido,rol);
        IdMedico=idmedico;
    }

   
    public String getIdMedico() {
        return IdMedico;
    }

    public void setIdMedico(String idMedico) {
        IdMedico = idMedico;
    }
}
