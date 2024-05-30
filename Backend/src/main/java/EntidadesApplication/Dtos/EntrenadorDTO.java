package EntidadesApplication.Dtos;

import java.sql.Date;

public class EntrenadorDTO {
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

    public EntrenadorDTO(Long id, Long idUsuario, String telefono, String direccion, String dni, Date fechaNacimiento, Date fechaAlta, Date fechaBaja, String especialidad, String titulacion, String experiencia, String observaciones) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.telefono = telefono;
        this.direccion = direccion;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.especialidad = especialidad;
        this.titulacion = titulacion;
        this.experiencia = experiencia;
        this.observaciones = observaciones;
    }

    public EntrenadorDTO() {
    }

    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

