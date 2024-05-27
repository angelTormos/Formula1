package jpaswing.entity;
import jakarta.persistence.*;

@Entity
public class Circuito {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String Nombre;
    private String FirstRace;
    private String VueltaRapida;
    private String Localizacion;
    private String Distancia;
    private int Vueltas;
    private String Imagen;

    public Circuito() {
    }

    public Circuito(String nombre, String firstRace, String vueltaRapida, String localizacion, String distancia, int vueltas, String imagen) {
        Nombre = nombre;
        FirstRace = firstRace;
        VueltaRapida = vueltaRapida;
        Localizacion = localizacion;
        Distancia = distancia;
        Vueltas = vueltas;
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFirstRace() {
        return FirstRace;
    }

    public void setFirstRace(String firstRace) {
        FirstRace = firstRace;
    }

    public String getVueltaRapida() {
        return VueltaRapida;
    }

    public void setVueltaRapida(String vueltaRapida) {
        VueltaRapida = vueltaRapida;
    }

    public String getLocalizacion() {
        return Localizacion;
    }

    public void setLocalizacion(String localizacion) {
        Localizacion = localizacion;
    }

    public String getDistancia() {
        return Distancia;
    }

    public void setDistancia(String distancia) {
        Distancia = distancia;
    }

    public int getVueltas() {
        return Vueltas;
    }

    public void setVueltas(int vueltas) {
        Vueltas = vueltas;
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

