package pixelpulse.entidades;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Objects;

@Entity
public class EjercicioDTO {
    @Id
    @GeneratedValue
    private Integer id;
    private String nombre;
    private String descripcion;
    private String observaciones;
    private String tipo;
    private String musculosTrabajados;
    private String material;
    private String dificultad;
    @ElementCollection
    private List<String> multimedia;
    private Long idEntrenador;

    

    public Integer getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getMusculosTrabajados() {
        return this.musculosTrabajados;
    }

    public String getMaterial() {
        return this.material;
    }

    public String getDificultad() {
        return this.dificultad;
    }

    public List<String> getMultimedia() {
        return this.multimedia;
    }

    public Long getIdEntrenador() {
        return this.idEntrenador;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    public void setObservaciones(final String observaciones) {
        this.observaciones = observaciones;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    public void setMusculosTrabajados(final String musculosTrabajados) {
        this.musculosTrabajados = musculosTrabajados;
    }

    public void setMaterial(final String material) {
        this.material = material;
    }

    public void setDificultad(final String dificultad) {
        this.dificultad = dificultad;
    }

    public void setMultimedia(final List<String> multimedia) {
        this.multimedia = multimedia;
    }

    public void setIdEntrenador(final Long idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public EjercicioDTO(final Integer id, final String nombre, final String descripcion, final String observaciones, final String tipo, final String musculosTrabajados, final String material, final String dificultad, final List<String> multimedia, final Long idEntrenador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.tipo = tipo;
        this.musculosTrabajados = musculosTrabajados;
        this.material = material;
        this.dificultad = dificultad;
        this.multimedia = multimedia;
        this.idEntrenador = idEntrenador;
    }

    public EjercicioDTO() {
    }

    
    public boolean equals( Object o) {
        if (o == this)  return true;
            
        if (!(o instanceof EjercicioDTO))  return false; 
               
            
        if(getClass() != o.getClass()) return false;
            EjercicioDTO ot = (EjercicioDTO) o;
            return Objects.equals(this.id, ot.id);
        
    }

   

    public int hashCode() {
        
        return Objects.hashCode(id);
    }

    

        public String toString() {
            Integer var10000 = this.id;
            return "Ejercicio.EjercicioBuilder(id=" + var10000 + ", nombre=" + this.nombre + ", descripcion=" + this.descripcion + ", observaciones=" + this.observaciones + ", tipo=" + this.tipo + ", musculosTrabajados=" + this.musculosTrabajados + ", material=" + this.material + ", dificultad=" + this.dificultad + ", multimedia=" + String.valueOf(this.multimedia) + ", idEntrenador=" + this.idEntrenador + ")";
        }
    }

