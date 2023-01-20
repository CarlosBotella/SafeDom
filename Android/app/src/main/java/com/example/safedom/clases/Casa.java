package com.example.safedom.clases;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Casa implements Serializable {
    private static final String[] usuariosLista = {};
    private String Paciente;
    //private User Familiar;
    private String Medico;
    private String Direccion;
    private String Ciudad;
    private String CP;
    private String Latitud;
    private String Longitud;


    public Casa(String paciente/*,User familiar*/, String medico, String direccion, String ciudad, String cP, String latitud, String longitud) {
        Paciente = paciente;
        //Familiar = familiar;
        Medico = medico;
        Direccion = direccion;
        Ciudad = ciudad;
        CP = cP;
        Latitud = latitud;
        Longitud = longitud;

    }

    public Casa() {
        Paciente = getPaciente();
        //Familiar = getFamiliar();
        Medico = getMedico();
        Direccion = getDireccion();
        Ciudad = getCiudad();
        CP = getCP();
        Latitud = getLatitud();
        Longitud = getLongitud();

    }

    public String getPaciente() {
        return Paciente;
    }

    public void setPaciente(String paciente) {
        Paciente = paciente;
    }

    public String getMedico() {
        return Medico;
    }

    public void setMedico(String medico) {
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

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }



}
