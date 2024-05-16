package jpaswing.entity;
import jakarta.persistence.*;

import javax.swing.*;

@Entity
public class Piloto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private int Numero;
    private String Nombre;
    private String Nacimiento;
    private String Escuderia;
    private String Nacionalidad;
    private String Debut;
    public Piloto(){

    }

    public Piloto(int numero, String nombre, String nacimiento, String escuderia, String nacionalidad, String debut) {
        Numero = numero;
        Nombre = nombre;
        Nacimiento = nacimiento;
        Escuderia = escuderia;
        Nacionalidad = nacionalidad;
        Debut = debut;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNacimiento() {
        return Nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        Nacimiento = nacimiento;
    }

    public String getEscuderia() {
        return Escuderia;
    }

    public void setEscuderia(String escuderia) {
        Escuderia = escuderia;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        Nacionalidad = nacionalidad;
    }

    public String getDebut() {
        return Debut;
    }

    public void setDebut(String debut) {
        Debut = debut;
    }

    @Override
    public String toString() {
        return Numero + " - " + Nombre + " - " + Nacimiento + " - " + Escuderia + " - " + Nacionalidad + " - " + Debut;
    }
}

