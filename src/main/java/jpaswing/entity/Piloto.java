package jpaswing.entity;
import jakarta.persistence.*;

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
    private String Imagen;
    public void Piloto(){

    }

    public void Piloto(Long id, int numero, String nombre, String nacimiento, String escuderia, String nacionalidad, String debut, String imagen) {
        this.id = id;
        Numero = numero;
        Nombre = nombre;
        Nacimiento = nacimiento;
        Escuderia = escuderia;
        Nacionalidad = nacionalidad;
        Debut = debut;
        Imagen = imagen;
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

