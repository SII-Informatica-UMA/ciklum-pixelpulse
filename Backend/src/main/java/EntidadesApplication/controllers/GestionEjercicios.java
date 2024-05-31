package EntidadesApplication.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


import EntidadesApplication.CustomExceptions.EjercicioNotFoundException;
import EntidadesApplication.Dtos.EjercicioDTO;
import EntidadesApplication.Dtos.EjercicioNuevoDTO;
import EntidadesApplication.Dtos.EntrenadorDTO;
import EntidadesApplication.Dtos.RutinaDTO;
import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.entities.Rutina;
import EntidadesApplication.repositories.EjerciciosRepo;
import EntidadesApplication.services.EjercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;


@RestController
@CrossOrigin
@RequestMapping({"/ejercicio"})
public class GestionEjercicios {
    private final EjerciciosRepo ejerciciosRepo;
    private EjercicioService ejercicioService;

    public GestionEjercicios(EjercicioService ejercicioService, EjerciciosRepo ejerciciosRepo) {
        this.ejercicioService = ejercicioService;
        this.ejerciciosRepo = ejerciciosRepo;
    }

    @GetMapping
    public ResponseEntity<List<EjercicioDTO>>obtenerEjercicios(@RequestParam(value = "entrenador",required = true) Long idEntrenador,@RequestHeader(name="Authorization") String token) {

        if (!ejerciciosRepo.findByIdEntrenador(idEntrenador).isEmpty()) {
            List<EjercicioDTO> lista = this.ejercicioService.obtenerEjercicios(idEntrenador,token.substring(7)).stream().map(EjercicioDTO::fromEntity).toList();
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.notFound().build();


    }

    @PostMapping
     public ResponseEntity<EjercicioDTO> crearEjercicio(@RequestHeader(name="Authorization") String token,@RequestParam(value = "entrenador",required = true) Long idEntrenador, @RequestBody EjercicioNuevoDTO ejercicioNuevoDTO, UriComponentsBuilder uriBuilder) throws Exception {

        Ejercicio g = ejercicioNuevoDTO.toEntity();
        g.setId((Long)null);
        g.setIdEntrenador(idEntrenador);
        g = this.ejercicioService.PutorPostEjercicios(g,token.substring(7));
        return ResponseEntity.created((URI)this.generadorUri(uriBuilder.build()).apply(g)).body(EjercicioDTO.fromEntity(g));
    }

    private Function<Ejercicio, URI> generadorUri(UriComponents uriBuilder) {
        return (g) -> {
            return UriComponentsBuilder.newInstance().uriComponents(uriBuilder).path("/ejercicio").path(String.format("/%d", g.getId())).build().toUri();
        };
    }

    @GetMapping({"/{idEjercicio}"})
    public ResponseEntity<EjercicioDTO> obtenerEjercicio(@PathVariable Long idEjercicio,@RequestHeader(name="Authorization") String token) {
        return ResponseEntity.of(this.ejercicioService.getEjercicio(idEjercicio,token.substring(7)).map(EjercicioDTO::fromEntity));
    }

    @PutMapping({"/{idEjercicio}"})
    public ResponseEntity<EjercicioDTO> actualizarEjercicio( @PathVariable Long idEjercicio, @RequestBody EjercicioDTO ejercicio,@RequestHeader(name="Authorization") String token) {
        Ejercicio e = ejercicio.toEntity();
        e.setId(idEjercicio);

        if(ejerciciosRepo.findById(idEjercicio).isPresent()){

                EjercicioDTO ejer = EjercicioDTO.fromEntity(this.ejercicioService.PutorPostEjercicios(e, token.substring(7)));
                return ResponseEntity.ok(ejer);

        }


        return ResponseEntity.notFound().build();


    }

    @DeleteMapping({"/{idEjercicio}"})
    public ResponseEntity<Void> eliminarEjercicio(@PathVariable Long idEjercicio,@RequestHeader(name="Authorization") String token) throws Exception {

           if(ejerciciosRepo.findById(idEjercicio).isPresent()) {

                   this.ejercicioService.getEjercicio(idEjercicio, token.substring(7));
                   this.ejercicioService.EliminarEjercicio(idEjercicio);
                   return ResponseEntity.ok().build();
               }

         return ResponseEntity.notFound().build();

    }

    @ExceptionHandler({EjercicioNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void ejercicioNoEncontrado() {
    }



}

