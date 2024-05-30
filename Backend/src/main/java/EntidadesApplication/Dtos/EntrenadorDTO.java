package EntidadesApplication.Dtos;

import java.sql.Date;

public class EntrenadorDTO  {
    private Long id;

    public EntrenadorDTO(Long id, Long idUsuario, String telefono, String direccion, String dni, Date fechaNacimiento, Date fechaAlta, Date fechaBaja, String especialidad, String titulacion, String experiencia, String observaciones) {
        this.id = id;
    }




    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public EntrenadorDTO() {
    }

    public static class EntrenadorDTOBuilder {
        private Long id;
        private Long idUsuario;
        private String telefono;
        private String direccion;
        private String dni;
        private Date fechaNacimiento;
        private Date fechaAlta;
        private Date fechaBaja;
        private String especialidad;
        private String titulacion;
        private String experiencia;
        private String observaciones;

        EntrenadorDTOBuilder() {
        }

        public EntrenadorDTOBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public EntrenadorDTOBuilder idUsuario(final Long idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public EntrenadorDTOBuilder telefono(final String telefono) {
            this.telefono = telefono;
            return this;
        }

        public EntrenadorDTOBuilder direccion(final String direccion) {
            this.direccion = direccion;
            return this;
        }

        public EntrenadorDTOBuilder dni(final String dni) {
            this.dni = dni;
            return this;
        }

        public EntrenadorDTOBuilder fechaNacimiento(final Date fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public EntrenadorDTOBuilder fechaAlta(final Date fechaAlta) {
            this.fechaAlta = fechaAlta;
            return this;
        }

        public EntrenadorDTOBuilder fechaBaja(final Date fechaBaja) {
            this.fechaBaja = fechaBaja;
            return this;
        }

        public EntrenadorDTOBuilder especialidad(final String especialidad) {
            this.especialidad = especialidad;
            return this;
        }

        public EntrenadorDTOBuilder titulacion(final String titulacion) {
            this.titulacion = titulacion;
            return this;
        }

        public EntrenadorDTOBuilder experiencia(final String experiencia) {
            this.experiencia = experiencia;
            return this;
        }

        public EntrenadorDTOBuilder observaciones(final String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public EntrenadorDTO build() {
            return new EntrenadorDTO(this.id, this.idUsuario, this.telefono, this.direccion, this.dni, this.fechaNacimiento, this.fechaAlta, this.fechaBaja, this.especialidad, this.titulacion, this.experiencia, this.observaciones);
        }

        public String toString() {
            Long var10000 = this.id;
            return "EntrenadorDTO.EntrenadorDTOBuilder(id=" + var10000 + ", idUsuario=" + this.idUsuario + ", telefono=" + this.telefono + ", direccion=" + this.direccion + ", dni=" + this.dni + ", fechaNacimiento=" + String.valueOf(this.fechaNacimiento) + ", fechaAlta=" + String.valueOf(this.fechaAlta) + ", fechaBaja=" + String.valueOf(this.fechaBaja) + ", especialidad=" + this.especialidad + ", titulacion=" + this.titulacion + ", experiencia=" + this.experiencia + ", observaciones=" + this.observaciones + ")";
        }
    }
}
