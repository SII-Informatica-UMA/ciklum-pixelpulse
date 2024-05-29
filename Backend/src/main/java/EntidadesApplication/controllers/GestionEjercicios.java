package EntidadesApplication.controllers;

import java.net.URI;
import java.util.List;
import java.util.function.Function;


import EntidadesApplication.CustomExceptions.EjercicioNotFoundException;
import EntidadesApplication.Dtos.EjercicioDTO;
import EntidadesApplication.Dtos.EjercicioNuevoDTO;
import EntidadesApplication.Dtos.RutinaDTO;
import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.entities.Rutina;
import EntidadesApplication.services.EjercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@CrossOrigin
@RequestMapping({"/ejercicio"})
public class GestionEjercicios {
    private EjercicioService ejercicioService;

    public GestionEjercicios(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping
    public List<EjercicioDTO> obtenerEjercicios(@RequestParam(value = "entrenador",required = true) Long idEntrenador) {
        return this.ejercicioService.obtenerEjercicios(idEntrenador).stream().map(EjercicioDTO::fromEntity).toList();
    }

    @PostMapping
    //@PreAuthorize("hasRole('ENTRENADOR')")  // Solo los entrenadores pueden crear ejercicios
     public ResponseEntity<EjercicioDTO> crearEjercicio(@RequestParam(value = "entrenador",required = true) Long idEntrenador, @RequestBody EjercicioNuevoDTO ejercicioNuevoDTO, UriComponentsBuilder uriBuilder) throws Exception {
        Ejercicio g = ejercicioNuevoDTO.toEntity();
        g.setId((Long)null);
        g.setIdEntrenador(idEntrenador);
        g = this.ejercicioService.PutorPostEjercicios(g);
        return ResponseEntity.created((URI)this.generadorUri(uriBuilder.build()).apply(g)).body(EjercicioDTO.fromEntity(g));
    }

    private Function<Ejercicio, URI> generadorUri(UriComponents uriBuilder) {
        return (g) -> {
            return UriComponentsBuilder.newInstance().uriComponents(uriBuilder).path("/ejercicio").path(String.format("/%d", g.getId())).build().toUri();
        };
    }

    @GetMapping({"/{idEjercicio}"})
    public ResponseEntity<EjercicioDTO> obtenerEjercicio(@PathVariable Long idEjercicio) {
        return ResponseEntity.of(this.ejercicioService.getEjercicio(idEjercicio).map(EjercicioDTO::fromEntity));
    }

    @PutMapping({"/{idEjercicio}"})
    public EjercicioDTO actualizarEjercicio(@RequestParam(value = "entrenador",required = true) Long idEntrenador, @PathVariable Long idEjercicio, @RequestBody EjercicioDTO ejercicio) throws Exception {
        Ejercicio e = ejercicio.toEntity();
        e.setId(idEjercicio);
        e.setIdEntrenador(idEntrenador);
        e = this.ejercicioService.PutorPostEjercicios(e);
        return EjercicioDTO.fromEntity(e);

    }

    @DeleteMapping({"/{idEjercicio}"})
    public void eliminarEjercicio(@PathVariable Long idEjercicio) throws Exception {
        this.ejercicioService.getEjercicio(idEjercicio).orElseThrow(EjercicioNotFoundException::new);
        this.ejercicioService.EliminarEjercicio(idEjercicio);
    }

    @ExceptionHandler({EjercicioNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void ejercicioNoEncontrado() {
    }
}

