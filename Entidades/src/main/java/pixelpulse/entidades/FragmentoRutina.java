package pixelpulse.entidades;
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

    public static FragmentoRutinaBuilder builder() {
        return new FragmentoRutinaBuilder();
    }

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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FragmentoRutina)) {
            return false;
        } else {
            FragmentoRutina other = (FragmentoRutina)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label71;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label71;
                    }

                    return false;
                }

                Object this$series = this.getSeries();
                Object other$series = other.getSeries();
                if (this$series == null) {
                    if (other$series != null) {
                        return false;
                    }
                } else if (!this$series.equals(other$series)) {
                    return false;
                }

                label57: {
                    Object this$repeticiones = this.getRepeticiones();
                    Object other$repeticiones = other.getRepeticiones();
                    if (this$repeticiones == null) {
                        if (other$repeticiones == null) {
                            break label57;
                        }
                    } else if (this$repeticiones.equals(other$repeticiones)) {
                        break label57;
                    }

                    return false;
                }

                Object this$duracionMinutos = this.getDuracionMinutos();
                Object other$duracionMinutos = other.getDuracionMinutos();
                if (this$duracionMinutos == null) {
                    if (other$duracionMinutos != null) {
                        return false;
                    }
                } else if (!this$duracionMinutos.equals(other$duracionMinutos)) {
                    return false;
                }

                Object this$ejercicio = this.getEjercicio();
                Object other$ejercicio = other.getEjercicio();
                if (this$ejercicio == null) {
                    if (other$ejercicio == null) {
                        return true;
                    }
                } else if (this$ejercicio.equals(other$ejercicio)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FragmentoRutina;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $series = this.getSeries();
        result = result * 59 + ($series == null ? 43 : $series.hashCode());
        Object $repeticiones = this.getRepeticiones();
        result = result * 59 + ($repeticiones == null ? 43 : $repeticiones.hashCode());
        Object $duracionMinutos = this.getDuracionMinutos();
        result = result * 59 + ($duracionMinutos == null ? 43 : $duracionMinutos.hashCode());
        Object $ejercicio = this.getEjercicio();
        result = result * 59 + ($ejercicio == null ? 43 : $ejercicio.hashCode());
        return result;
    }

    public static class FragmentoRutinaBuilder {
        private Long id;
        private Integer series;
        private Integer repeticiones;
        private Integer duracionMinutos;
        private Ejercicio ejercicio;

        FragmentoRutinaBuilder() {
        }

        public FragmentoRutinaBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public FragmentoRutinaBuilder series(final Integer series) {
            this.series = series;
            return this;
        }

        public FragmentoRutinaBuilder repeticiones(final Integer repeticiones) {
            this.repeticiones = repeticiones;
            return this;
        }

        public FragmentoRutinaBuilder duracionMinutos(final Integer duracionMinutos) {
            this.duracionMinutos = duracionMinutos;
            return this;
        }

        public FragmentoRutinaBuilder ejercicio(final Ejercicio ejercicio) {
            this.ejercicio = ejercicio;
            return this;
        }

        public FragmentoRutina build() {
            return new FragmentoRutina(this.id, this.series, this.repeticiones, this.duracionMinutos, this.ejercicio);
        }

        public String toString() {
            Long var10000 = this.id;
            return "FragmentoRutina.FragmentoRutinaBuilder(id=" + var10000 + ", series=" + this.series + ", repeticiones=" + this.repeticiones + ", duracionMinutos=" + this.duracionMinutos + ", ejercicio=" + String.valueOf(this.ejercicio) + ")";
        }
    }
}
