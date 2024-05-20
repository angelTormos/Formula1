package jpaswing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Escuderia {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String Nombre;
    private String Nacionalidad;
    private String Chasis;
    private String Motor;
    private String Imagen;
    public Escuderia(){

    }

    public Escuderia(String nombre, String nacionalidad, String chasis, String motor, String imagen) {
        Nombre = nombre;
        Nacionalidad = nacionalidad;
        Chasis = chasis;
        Motor = motor;
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        Nacionalidad = nacionalidad;
    }

    public String getChasis() {
        return Chasis;
    }

    public void setChasis(String chasis) {
        Chasis = chasis;
    }

    public String getMotor() {
        return Motor;
    }

    public void setMotor(String motor) {
        Motor = motor;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}

