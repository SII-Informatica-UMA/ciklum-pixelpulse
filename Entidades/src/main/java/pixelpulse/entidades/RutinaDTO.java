package pixelpulse.entidades;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class RutinaDTO {
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
    private List<FragmentoRutinaDTO> ejercicios;
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

    public List<FragmentoRutinaDTO> getEjercicios() {
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

    public void setEjercicios(final List<FragmentoRutinaDTO> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public void setIdEntrenador(final Long idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public RutinaDTO(final Long id, final String nombre, final String descripcion, final String observaciones, final List<FragmentoRutinaDTO> ejercicios, final Long idEntrenador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.ejercicios = ejercicios;
        this.idEntrenador = idEntrenador;
    }

    public RutinaDTO() {
    }

    public boolean equals( Object o) {
        if (o == this)  return true;
            
        if (!(o instanceof EjercicioDTO))  return false; 
               
            
        if(getClass() != o.getClass()) return false;
            RutinaDTO ot = (RutinaDTO) o;
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

