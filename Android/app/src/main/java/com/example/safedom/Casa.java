package com.example.safedom;

public class Casa {
    private User Cliente;
    private User Familiar;
    private User Doctor;
    private String Direccion;
    private String Ciudad;
    private int CP;

    public Casa(User cliente, User familiar, User doctor, String direccion, String ciudad, int CP) {
        Cliente = cliente;
        Familiar = familiar;
        Doctor = doctor;
        Direccion = direccion;
        Ciudad = ciudad;
        this.CP = CP;
    }

    public User getCliente() {
        return Cliente;
    }

    public void setCliente(User cliente) {
        Cliente = cliente;
    }

    public User getFamiliar() {
        return Familiar;
    }

    public void setFamiliar(User familiar) {
        Familiar = familiar;
    }

    public User getDoctor() {
        return Doctor;
    }

    public void setDoctor(User doctor) {
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

    public int getCP() {
        return CP;
    }

    public void setCP(int CP) {
        this.CP = CP;
    }


}
