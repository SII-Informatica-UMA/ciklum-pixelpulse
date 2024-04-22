package pixelpulse.entidades;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Rutina {
    @Id
    @GeneratedValue
    private Long id;
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

    public static RutinaBuilder builder() {
        return new RutinaBuilder();
    }

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

    public Rutina() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Rutina)) {
            return false;
        } else {
            Rutina other = (Rutina)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }

                Object this$idEntrenador = this.getIdEntrenador();
                Object other$idEntrenador = other.getIdEntrenador();
                if (this$idEntrenador == null) {
                    if (other$idEntrenador != null) {
                        return false;
                    }
                } else if (!this$idEntrenador.equals(other$idEntrenador)) {
                    return false;
                }

                Object this$nombre = this.getNombre();
                Object other$nombre = other.getNombre();
                if (this$nombre == null) {
                    if (other$nombre != null) {
                        return false;
                    }
                } else if (!this$nombre.equals(other$nombre)) {
                    return false;
                }

                label62: {
                    Object this$descripcion = this.getDescripcion();
                    Object other$descripcion = other.getDescripcion();
                    if (this$descripcion == null) {
                        if (other$descripcion == null) {
                            break label62;
                        }
                    } else if (this$descripcion.equals(other$descripcion)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    Object this$observaciones = this.getObservaciones();
                    Object other$observaciones = other.getObservaciones();
                    if (this$observaciones == null) {
                        if (other$observaciones == null) {
                            break label55;
                        }
                    } else if (this$observaciones.equals(other$observaciones)) {
                        break label55;
                    }

                    return false;
                }

                Object this$ejercicios = this.getEjercicios();
                Object other$ejercicios = other.getEjercicios();
                if (this$ejercicios == null) {
                    if (other$ejercicios != null) {
                        return false;
                    }
                } else if (!this$ejercicios.equals(other$ejercicios)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Rutina;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $idEntrenador = this.getIdEntrenador();
        result = result * 59 + ($idEntrenador == null ? 43 : $idEntrenador.hashCode());
        Object $nombre = this.getNombre();
        result = result * 59 + ($nombre == null ? 43 : $nombre.hashCode());
        Object $descripcion = this.getDescripcion();
        result = result * 59 + ($descripcion == null ? 43 : $descripcion.hashCode());
        Object $observaciones = this.getObservaciones();
        result = result * 59 + ($observaciones == null ? 43 : $observaciones.hashCode());
        Object $ejercicios = this.getEjercicios();
        result = result * 59 + ($ejercicios == null ? 43 : $ejercicios.hashCode());
        return result;
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
            Long var10000 = this.id;
            return "Rutina.RutinaBuilder(id=" + var10000 + ", nombre=" + this.nombre + ", descripcion=" + this.descripcion + ", observaciones=" + this.observaciones + ", ejercicios=" + String.valueOf(this.ejercicios) + ", idEntrenador=" + this.idEntrenador + ")";
        }
    }
}
