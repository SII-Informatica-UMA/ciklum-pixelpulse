package Dtos;
import pixelpulse.entidades.Rutina;
import java.util.List;

public class RutinaNuevaDTO {
    private String nombre;
    private String descripcion;
    private String observaciones;
    private List<FragmentoRutinaDTO> ejercicios;

    public Rutina toEntity() {
        return Rutina.builder().nombre(this.nombre).descripcion(this.descripcion).observaciones(this.observaciones).ejercicios(FragmentoRutinaDTO.toEntityList(this.ejercicios)).build();
    }

    public static RutinaNuevaDTOBuilder otro() {
        return new RutinaNuevaDTOBuilder();
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

    public RutinaNuevaDTO() {
    }

    public RutinaNuevaDTO(final String nombre, final String descripcion, final String observaciones, final List<FragmentoRutinaDTO> ejercicios) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.ejercicios = ejercicios;
    }

    public static class RutinaNuevaDTOBuilder {
        private String nombre;
        private String descripcion;
        private String observaciones;
        private List<FragmentoRutinaDTO> ejercicios;

        RutinaNuevaDTOBuilder() {
        }

        public RutinaNuevaDTOBuilder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public RutinaNuevaDTOBuilder descripcion(final String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public RutinaNuevaDTOBuilder observaciones(final String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public RutinaNuevaDTOBuilder ejercicios(final List<FragmentoRutinaDTO> ejercicios) {
            this.ejercicios = ejercicios;
            return this;
        }

        public RutinaNuevaDTO build() {
            return new RutinaNuevaDTO(this.nombre, this.descripcion, this.observaciones, this.ejercicios);
        }

        public String toString() {
            String var10000 = this.nombre;
            return "RutinaNuevaDTO.RutinaNuevaDTOBuilder(nombre=" + var10000 + ", descripcion=" + this.descripcion + ", observaciones=" + this.observaciones + ", ejercicios=" + String.valueOf(this.ejercicios) + ")";
        }
    }
}

