package EntidadesApplication.controllers;

import java.net.URI;
import java.util.List;
import java.util.function.Function;


import EntidadesApplication.CustomExceptions.EjercicioNotFoundException;
import EntidadesApplication.Dtos.EjercicioDTO;
import EntidadesApplication.Dtos.EjercicioNuevoDTO;
import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.services.EjercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping({"/ejercicios"})
/*@Tag(
        name = "Gestión de ejercicios y rutinas",
        description = "Conjunto de operaciones para la gestión de ejercicios y rutinas"
)*/
public class GestionEjercicios {
    private EjercicioService ejercicioService;

    public GestionEjercicios(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping
    @Operation(
            description = "Permite consultar todos los ejercicios a un entrenador. Solo lo puede hacer el entrenador.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Devuelve la lista de ejercicios de un entrenador."
            ), @ApiResponse(
                    responseCode = "403",
                    description = "Acceso no autorizado",
                    content = {@Content(
                            schema = @Schema(
                                    implementation = Void.class
                            )
                    )}
            )}
    )
    public List<EjercicioDTO> obtenerEjercicios(@RequestParam(value = "entrenador",required = true) Long idEntrenador) {
        return this.ejercicioService.obtenerEjercicios(idEntrenador).stream().map(EjercicioDTO::fromEntity).toList();
    }

    @PostMapping
    /*@Operation(
            description = "Permite crear un ejercicio nuevo a un entrenador. ",
            responses = {@ApiResponse(
                    responseCode = "201",
                    description = "Se crea el ejercicio y lo devuelve",
                    headers = {@Header(
                            name = "Location",
                            description = "URI del nuevo recurso",
                            schema = @Schema(
                                    type = "string",
                                    subTypes = {URI.class}
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "403",
                    description = "Acceso no autorizado",
                    content = {@Content(
                            schema = @Schema(
                                    implementation = Void.class
                            )
                    )}
            )}
    )*/
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
    @Operation(
            description = "Obtiene un ejercicio concreto. Sollo pued ehacerlo el entrenador que lo ha creado y los clientes que entrena.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "El ejercicio existe"
            ), @ApiResponse(
                    responseCode = "404",
                    description = "El ejercicio no existe",
                    content = {@Content(
                            schema = @Schema(
                                    implementation = Void.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "403",
                    description = "Acceso no autorizado",
                    content = {@Content(
                            schema = @Schema(
                                    implementation = Void.class
                            )
                    )}
            )}
    )
    public ResponseEntity<EjercicioDTO> getEjercicio(@PathVariable Long idEjercicio) {
        return ResponseEntity.of(this.ejercicioService.getEjercicio(idEjercicio).map(EjercicioDTO::fromEntity));
    }

    @PutMapping({"/{idEjercicio}"})
    @Operation(
            description = "Actualiza un ejercicio. Solo puede hacerlo el entrenador que lo ha creado.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "El ejercicio se ha actualizado"
            ), @ApiResponse(
                    responseCode = "404",
                    description = "El ejercicio no existe",
                    content = {@Content(
                            schema = @Schema(
                                    implementation = Void.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "403",
                    description = "Acceso no autorizado",
                    content = {@Content(
                            schema = @Schema(
                                    implementation = Void.class
                            )
                    )}
            )}
    )
    public EjercicioDTO actualizarEjercicio(@PathVariable Long idEjercicio, @RequestBody EjercicioDTO ejercicio) throws Exception {
        Object EjercicioNotFoundException;
        this.ejercicioService.getEjercicio(idEjercicio).orElseThrow(EjercicioNotFoundException::new);
        ejercicio.setId(idEjercicio);
        Ejercicio g = this.ejercicioService.PutorPostEjercicios(ejercicio.toEntity());
        return EjercicioDTO.fromEntity(g);
    }

    @DeleteMapping({"/{idEjercicio}"})
    @Operation(
            description = "Elimina el ejercicio. Solo puede hacerlo el entrenador que lo ha creado.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "El ejercicio se ha elminado"
            ), @ApiResponse(
                    responseCode = "404",
                    description = "El ejercicio no existe",
                    content = {@Content(
                            schema = @Schema(
                                    implementation = Void.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "403",
                    description = "Acceso no autorizado",
                    content = {@Content(
                            schema = @Schema(
                                    implementation = Void.class
                            )
                    )}
            )}
    )
    public void eliminarEjercicio(@PathVariable Long idEjercicio) throws Exception {
        this.ejercicioService.getEjercicio(idEjercicio).orElseThrow(EjercicioNotFoundException::new);
        this.ejercicioService.EliminarEjercicio(idEjercicio);
    }

    @ExceptionHandler({EjercicioNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void ejercicioNoEncontrado() {
    }
}

