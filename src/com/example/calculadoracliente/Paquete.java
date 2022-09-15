package com.example.calculadoracliente;

import java.io.Serializable;

public class Paquete implements Serializable {
    String cadena;

    int codigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
}