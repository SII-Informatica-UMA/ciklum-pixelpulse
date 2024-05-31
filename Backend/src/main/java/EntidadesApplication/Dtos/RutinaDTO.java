package EntidadesApplication.Dtos;

import EntidadesApplication.entities.Rutina;

import java.util.List;

public class RutinaDTO extends RutinaNuevaDTO {
    private Long id;

    public RutinaDTO(Long id, String nombre, String descripcion, String observaciones, List<FragmentoRutinaDTO> ejercicios) {
        super(nombre, descripcion, observaciones, ejercicios);
        this.id = id;
    }

    public  Rutina toEntity() {
        return Rutina.builder().id(this.id).nombre(this.getNombre()).descripcion(this.getDescripcion()).observaciones(this.getObservaciones()).ejercicios(FragmentoRutinaDTO.toEntityList(this.getEjercicios())).build();
    }

    public static RutinaDTO fromEntity(Rutina rutina) {
        return builder().id(rutina.getId()).nombre(rutina.getNombre()).descripcion(rutina.getDescripcion()).observaciones(rutina.getObservaciones()).ejercicios(FragmentoRutinaDTO.fromEntityList(rutina.getEjercicios())).build();
    }

    public static RutinaDTOBuilder builder() {
        return new RutinaDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public RutinaDTO() {
    }

    public static class RutinaDTOBuilder {
        private Long id;
        private String nombre;
        private String descripcion;
        private String observaciones;
        private List<FragmentoRutinaDTO> ejercicios;

        RutinaDTOBuilder() {
        }

        public RutinaDTOBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public RutinaDTOBuilder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public RutinaDTOBuilder descripcion(final String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public RutinaDTOBuilder observaciones(final String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public RutinaDTOBuilder ejercicios(final List<FragmentoRutinaDTO> ejercicios) {
            this.ejercicios = ejercicios;
            return this;
        }

        public RutinaDTO build() {
            return new RutinaDTO(this.id, this.nombre, this.descripcion, this.observaciones, this.ejercicios);
        }

        public String toString() {
            Long var10000 = this.id;
            return "RutinaDTO.RutinaDTOBuilder(id=" + var10000 + ", nombre=" + this.nombre + ", descripcion=" + this.descripcion + ", observaciones=" + this.observaciones + ", ejercicios=" + String.valueOf(this.ejercicios) + ")";
        }
    }
}

