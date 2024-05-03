package pixelpulse.entidades;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class FragmentoRutina {
    @Id
    @GeneratedValue
    private Long id;
    private Integer series;
    private Integer repeticiones;
    private Integer duracionMinutos;
    @ManyToOne
    private Ejercicio ejercicio;

    

    public Long getId() {
        return this.id;
    }

    public Integer getSeries() {
        return this.series;
    }

    public Integer getRepeticiones() {
        return this.repeticiones;
    }

    public Integer getDuracionMinutos() {
        return this.duracionMinutos;
    }

    public Ejercicio getEjercicio() {
        return this.ejercicio;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setSeries(final Integer series) {
        this.series = series;
    }

    public void setRepeticiones(final Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public void setDuracionMinutos(final Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public void setEjercicio(final Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public FragmentoRutina(final Long id, final Integer series, final Integer repeticiones, final Integer duracionMinutos, final Ejercicio ejercicio) {
        this.id = id;
        this.series = series;
        this.repeticiones = repeticiones;
        this.duracionMinutos = duracionMinutos;
        this.ejercicio = ejercicio;
    }

    public FragmentoRutina() {
    }

    public boolean equals( Object o) {
        if (o == this)  return true;
            
        if (!(o instanceof Ejercicio))  return false; 
               
            
        if(getClass() != o.getClass()) return false;
            FragmentoRutina ot = (FragmentoRutina) o;
            return Objects.equals(this.id, ot.id);
        
    }
    
   

    public int hashCode() {
        return Objects.hashCode(id);
    }

    

        public String toString() {
            Long var10000 = this.id;
            return "FragmentoRutina.FragmentoRutinaBuilder(id=" + var10000 + ", series=" + this.series + ", repeticiones=" + this.repeticiones + ", duracionMinutos=" + this.duracionMinutos + ", ejercicio=" + String.valueOf(this.ejercicio) + ")";
        }
    
}
