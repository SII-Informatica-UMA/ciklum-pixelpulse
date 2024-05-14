package Dtos;
import entidades.Ejercicio;
import java.util.List;

public class EjercicioNuevoDTO {
    private String nombre;
    private String descripcion;
    private String observaciones;
    private String tipo;
    private String musculosTrabajados;
    private String material;
    private String dificultad;
    private List<String> multimedia;

    public Ejercicio toEntity() {
        return Ejercicio.builder().nombre(this.nombre).descripcion(this.descripcion).observaciones(this.observaciones).tipo(this.tipo).musculosTrabajados(this.musculosTrabajados).material(this.material).dificultad(this.dificultad).multimedia(this.multimedia).build();
    }

    public static EjercicioNuevoDTOBuilder otro() {
        return new EjercicioNuevoDTOBuilder();
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

    public EjercicioNuevoDTO() {
    }

    public EjercicioNuevoDTO(final String nombre, final String descripcion, final String observaciones, final String tipo, final String musculosTrabajados, final String material, final String dificultad, final List<String> multimedia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.tipo = tipo;
        this.musculosTrabajados = musculosTrabajados;
        this.material = material;
        this.dificultad = dificultad;
        this.multimedia = multimedia;
    }

    public static class EjercicioNuevoDTOBuilder {
        private String nombre;
        private String descripcion;
        private String observaciones;
        private String tipo;
        private String musculosTrabajados;
        private String material;
        private String dificultad;
        private List<String> multimedia;

        EjercicioNuevoDTOBuilder() {
        }

        public EjercicioNuevoDTOBuilder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public EjercicioNuevoDTOBuilder descripcion(final String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public EjercicioNuevoDTOBuilder observaciones(final String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public EjercicioNuevoDTOBuilder tipo(final String tipo) {
            this.tipo = tipo;
            return this;
        }

        public EjercicioNuevoDTOBuilder musculosTrabajados(final String musculosTrabajados) {
            this.musculosTrabajados = musculosTrabajados;
            return this;
        }

        public EjercicioNuevoDTOBuilder material(final String material) {
            this.material = material;
            return this;
        }

        public EjercicioNuevoDTOBuilder dificultad(final String dificultad) {
            this.dificultad = dificultad;
            return this;
        }

        public EjercicioNuevoDTOBuilder multimedia(final List<String> multimedia) {
            this.multimedia = multimedia;
            return this;
        }

        public EjercicioNuevoDTO build() {
            return new EjercicioNuevoDTO(this.nombre, this.descripcion, this.observaciones, this.tipo, this.musculosTrabajados, this.material, this.dificultad, this.multimedia);
        }

        public String toString() {
            String var10000 = this.nombre;
            return "EjercicioNuevoDTO.EjercicioNuevoDTOBuilder(nombre=" + var10000 + ", descripcion=" + this.descripcion + ", observaciones=" + this.observaciones + ", tipo=" + this.tipo + ", musculosTrabajados=" + this.musculosTrabajados + ", material=" + this.material + ", dificultad=" + this.dificultad + ", multimedia=" + String.valueOf(this.multimedia) + ")";
        }
    }
}

