package com.example.safedom.clases;

import java.io.Serializable;

public class Casa implements Serializable {

    private String  Paciente;
    //private User Familiar;
    private String  Medico;
    private String Direccion;
    private String Ciudad;
    private String CP;

    public Casa(String  paciente/*,User familiar*/, String medico, String direccion, String ciudad, String cP) {
        Paciente = paciente;
        //Familiar = familiar;
        Medico = medico;
        Direccion = direccion;
        Ciudad = ciudad;
        CP = cP;
    }
    public Casa()
    {
        Paciente = getPaciente();
        //Familiar = getFamiliar();
        Medico = getMedico();
        Direccion = getDireccion();
        Ciudad = getCiudad();
        CP = getCP();
    }
    public String  getPaciente() {
        return Paciente;
    }
    public void setPaciente(String paciente) {
        Paciente = paciente;
    }
    public String getMedico() {
        return Medico;
    }
    public void setMedico(String  medico) {
        Medico = medico;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
    public String getCiudad() {
        return Ciudad;
    }
    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }
    public String getCP() {
        return CP;
    }
    public void setCP(String CP) {
        this.CP = CP;
    }


}
