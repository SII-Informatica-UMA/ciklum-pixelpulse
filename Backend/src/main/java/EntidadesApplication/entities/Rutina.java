package EntidadesApplication.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Rutina {
    @Id
    @GeneratedValue Long id;
    private String nombre;
    private String descripcion;
    private String observaciones;
    @OneToMany(
        fetch = FetchType.EAGER,
        orphanRemoval = true,
        cascade = {CascadeType.ALL}
    )
    private List<FragmentoRutina> ejercicios;
    private Long idEntrenador;

    

    public Long getId() {
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

    public List<FragmentoRutina> getEjercicios() {
        return this.ejercicios;
    }

    public Long getIdEntrenador() {
        return this.idEntrenador;
    }

    public void setId(final Long id) {
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

    public void setEjercicios(final List<FragmentoRutina> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public void setIdEntrenador(final Long idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public Rutina(final Long id, final String nombre, final String descripcion, final String observaciones, final List<FragmentoRutina> ejercicios, final Long idEntrenador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.ejercicios = ejercicios;
        this.idEntrenador = idEntrenador;
    }

    public static class RutinaBuilder {
        private Long id;
        private String nombre;
        private String descripcion;
        private String observaciones;
        private List<FragmentoRutina> ejercicios;
        private Long idEntrenador;

        RutinaBuilder() {
        }

        public RutinaBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public RutinaBuilder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public RutinaBuilder descripcion(final String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public RutinaBuilder observaciones(final String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public RutinaBuilder ejercicios(final List<FragmentoRutina> ejercicios) {
            this.ejercicios = ejercicios;
            return this;
        }

        public RutinaBuilder idEntrenador(final Long idEntrenador) {
            this.idEntrenador = idEntrenador;
            return this;
        }

        public Rutina build() {
            return new Rutina(this.id, this.nombre, this.descripcion, this.observaciones, this.ejercicios, this.idEntrenador);
        }

        public String toString() {
            return "Rutina.RutinaBuilder(id=" + this.id + ", nombre=" + this.nombre + ", descripcion=" + this.descripcion + ", observaciones=" + this.observaciones + ", ejercicios=" + String.valueOf(this.ejercicios) + ", idEntrenador=" + this.idEntrenador + ")";
        }
    }

    public Rutina() {
    }

    public static RutinaBuilder builder() {
        return new RutinaBuilder();
    }

    public boolean equals( Object o) {
        if (o == this)  return true;
            
        if (!(o instanceof Ejercicio))  return false; 
               
            
        if(getClass() != o.getClass()) return false;
            Rutina ot = (Rutina) o;
            return Objects.equals(this.id, ot.id);
        
    }

   

    public int hashCode() {

        return Objects.hashCode(id);
    }
        

    

        public String toString() {
            Long var10000 = this.id;
            return "Rutina.RutinaBuilder(id=" + var10000 + ", nombre=" + this.nombre + ", descripcion=" + this.descripcion + ", observaciones=" + this.observaciones + ", ejercicios=" + String.valueOf(this.ejercicios) + ", idEntrenador=" + this.idEntrenador + ")";
        }
    }