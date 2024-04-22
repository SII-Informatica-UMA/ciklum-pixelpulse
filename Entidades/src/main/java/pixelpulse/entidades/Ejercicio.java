package pixelpulse.entidades;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.List;

@Entity
public class Ejercicio {
    @Id
    @GeneratedValue
    private Long id;
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

    public static EjercicioBuilder builder() {
        return new EjercicioBuilder();
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

    public Ejercicio(final Long id, final String nombre, final String descripcion, final String observaciones, final String tipo, final String musculosTrabajados, final String material, final String dificultad, final List<String> multimedia, final Long idEntrenador) {
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

    public Ejercicio() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Ejercicio)) {
            return false;
        } else {
            Ejercicio other = (Ejercicio)o;
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

                label110: {
                    Object this$descripcion = this.getDescripcion();
                    Object other$descripcion = other.getDescripcion();
                    if (this$descripcion == null) {
                        if (other$descripcion == null) {
                            break label110;
                        }
                    } else if (this$descripcion.equals(other$descripcion)) {
                        break label110;
                    }

                    return false;
                }

                label103: {
                    Object this$observaciones = this.getObservaciones();
                    Object other$observaciones = other.getObservaciones();
                    if (this$observaciones == null) {
                        if (other$observaciones == null) {
                            break label103;
                        }
                    } else if (this$observaciones.equals(other$observaciones)) {
                        break label103;
                    }

                    return false;
                }

                Object this$tipo = this.getTipo();
                Object other$tipo = other.getTipo();
                if (this$tipo == null) {
                    if (other$tipo != null) {
                        return false;
                    }
                } else if (!this$tipo.equals(other$tipo)) {
                    return false;
                }

                label89: {
                    Object this$musculosTrabajados = this.getMusculosTrabajados();
                    Object other$musculosTrabajados = other.getMusculosTrabajados();
                    if (this$musculosTrabajados == null) {
                        if (other$musculosTrabajados == null) {
                            break label89;
                        }
                    } else if (this$musculosTrabajados.equals(other$musculosTrabajados)) {
                        break label89;
                    }

                    return false;
                }

                label82: {
                    Object this$material = this.getMaterial();
                    Object other$material = other.getMaterial();
                    if (this$material == null) {
                        if (other$material == null) {
                            break label82;
                        }
                    } else if (this$material.equals(other$material)) {
                        break label82;
                    }

                    return false;
                }

                Object this$dificultad = this.getDificultad();
                Object other$dificultad = other.getDificultad();
                if (this$dificultad == null) {
                    if (other$dificultad != null) {
                        return false;
                    }
                } else if (!this$dificultad.equals(other$dificultad)) {
                    return false;
                }

                Object this$multimedia = this.getMultimedia();
                Object other$multimedia = other.getMultimedia();
                if (this$multimedia == null) {
                    if (other$multimedia != null) {
                        return false;
                    }
                } else if (!this$multimedia.equals(other$multimedia)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Ejercicio;
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
        Object $tipo = this.getTipo();
        result = result * 59 + ($tipo == null ? 43 : $tipo.hashCode());
        Object $musculosTrabajados = this.getMusculosTrabajados();
        result = result * 59 + ($musculosTrabajados == null ? 43 : $musculosTrabajados.hashCode());
        Object $material = this.getMaterial();
        result = result * 59 + ($material == null ? 43 : $material.hashCode());
        Object $dificultad = this.getDificultad();
        result = result * 59 + ($dificultad == null ? 43 : $dificultad.hashCode());
        Object $multimedia = this.getMultimedia();
        result = result * 59 + ($multimedia == null ? 43 : $multimedia.hashCode());
        return result;
    }

    public static class EjercicioBuilder {
        private Long id;
        private String nombre;
        private String descripcion;
        private String observaciones;
        private String tipo;
        private String musculosTrabajados;
        private String material;
        private String dificultad;
        private List<String> multimedia;
        private Long idEntrenador;

        EjercicioBuilder() {
        }

        public EjercicioBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public EjercicioBuilder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public EjercicioBuilder descripcion(final String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public EjercicioBuilder observaciones(final String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public EjercicioBuilder tipo(final String tipo) {
            this.tipo = tipo;
            return this;
        }

        public EjercicioBuilder musculosTrabajados(final String musculosTrabajados) {
            this.musculosTrabajados = musculosTrabajados;
            return this;
        }

        public EjercicioBuilder material(final String material) {
            this.material = material;
            return this;
        }

        public EjercicioBuilder dificultad(final String dificultad) {
            this.dificultad = dificultad;
            return this;
        }

        public EjercicioBuilder multimedia(final List<String> multimedia) {
            this.multimedia = multimedia;
            return this;
        }

        public EjercicioBuilder idEntrenador(final Long idEntrenador) {
            this.idEntrenador = idEntrenador;
            return this;
        }

        public Ejercicio build() {
            return new Ejercicio(this.id, this.nombre, this.descripcion, this.observaciones, this.tipo, this.musculosTrabajados, this.material, this.dificultad, this.multimedia, this.idEntrenador);
        }

        public String toString() {
            Long var10000 = this.id;
            return "Ejercicio.EjercicioBuilder(id=" + var10000 + ", nombre=" + this.nombre + ", descripcion=" + this.descripcion + ", observaciones=" + this.observaciones + ", tipo=" + this.tipo + ", musculosTrabajados=" + this.musculosTrabajados + ", material=" + this.material + ", dificultad=" + this.dificultad + ", multimedia=" + String.valueOf(this.multimedia) + ", idEntrenador=" + this.idEntrenador + ")";
        }
    }
}
