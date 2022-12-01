package com.example.safedom.clases;

import java.io.Serializable;

public class Casa implements Serializable {
    private User Cliente;
    //private User Familiar;
    private Medico Doctor;
    private String Direccion;
    private String Ciudad;
    private String CP;

    public Casa(User cliente/*,User familiar*/, Medico doctor, String direccion, String ciudad, String cP) {
        Cliente = cliente;
        //Familiar = familiar;
        Doctor = doctor;
        Direccion = direccion;
        Ciudad = ciudad;
        CP = cP;
    }
    public Casa()
    {
        Cliente = getCliente();
        //Familiar = getFamiliar();
        Doctor = getDoctor();
        Direccion = getDireccion();
        Ciudad = getCiudad();
        CP = getCP();
    }
    public User getCliente() {
        return Cliente;
    }
    public void setCliente(User cliente) {
        Cliente = cliente;
    }
   /* public User getFamiliar() {
        return Familiar;
    }
    public void setFamiliar(User familiar) {
        Familiar = familiar;
    }*/
    public Medico getDoctor() {
        return Doctor;
    }
    public void setDoctor(Medico doctor) {
        Doctor = doctor;
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
