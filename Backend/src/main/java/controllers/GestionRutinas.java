package controllers;

import CustomExceptions.RutinaNotFoundException;
import Dtos.RutinaDTO;
import Dtos.RutinaNuevaDTO;
import java.net.URI;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Tag;
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
import pixelpulse.entidades.Rutina;
import services.RutinaService;


@RestController
@CrossOrigin
@RequestMapping({"/rutina"})
/*@Tag(
        name = "Gestión de ejercicios y rutinas",
        description = "Conjunto de operaciones para la gestión de ejercicios y rutinas"
)*/
public class GestionRutinas {
    private RutinaService rutinaService;

    public GestionRutinas(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    @GetMapping
   /* @Operation(
            description = "Permite consultar todas las rutinas a un entrenador. Solo lo puede hacer el entrenador.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Devuelve la lista de rutinas de un entrenador."
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
    public List<RutinaDTO> obtenerRutinas(@RequestParam(value = "entrenador",required = true) Long idEntrenador) {
        return this.rutinaService.GetRutinas(idEntrenador).stream().map(RutinaDTO::fromEntity).toList();
    }

    @PostMapping
    /*@Operation(
            description = "Permite crear una rutina nueva a un entrenador. Los ejercicios usados deben existir previamente.",
            responses = {@ApiResponse(
                    responseCode = "201",
                    description = "Se crea la rutina y la devuelve",
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
    public ResponseEntity<RutinaDTO> crearRutina(@RequestParam(value = "entrenador",required = true) Long idEntrenador, @RequestBody RutinaNuevaDTO rutinaNuevaDTO, UriComponentsBuilder uriBuilder) {
        Rutina rutina = rutinaNuevaDTO.toEntity();
        rutina.setIdEntrenador(idEntrenador);
        rutina.setId((Long)null);
        rutina = this.rutinaService.PutOrPostRutina(rutina);
        return ResponseEntity.created((URI)this.generadorUri(uriBuilder.build()).apply(rutina)).body(RutinaDTO.fromEntity(rutina));
    }

    private Function<Rutina, URI> generadorUri(UriComponents uriBuilder) {
        return (g) -> {
            return UriComponentsBuilder.newInstance().uriComponents(uriBuilder).path("/rutina").path(String.format("/%d", g.getId())).build().toUri();
        };
    }

    @GetMapping({"/{idRutina}"})
   /* @Operation(
            description = "Obtiene una rutina concreta. Solo puede hacerlo el entrenador que la ha creado y los clientes que entrena.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "La rutina existe"
            ), @ApiResponse(
                    responseCode = "404",
                    description = "La rutina no existe",
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
    )*/
    public ResponseEntity<RutinaDTO> getRutina(@PathVariable Long idRutina) {
        return ResponseEntity.of(this.rutinaService.GetRutina(idRutina).map(RutinaDTO::fromEntity));
    }

    @PutMapping({"/{idRutina}"})
   /* @Operation(
            description = "Actualiza una rutina. Solo puede hacerlo el entrenador que lo ha creado.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "La rutina se ha actualizado"
            ), @ApiResponse(
                    responseCode = "404",
                    description = "La rutina no existe",
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
    )*/
    public RutinaDTO actualizarRutina(@PathVariable Long idRutina, @RequestBody RutinaDTO rutina) {
        Rutina r = rutina.toEntity();
        r.setId(idRutina);
        r = this.rutinaService.PutOrPostRutina(r);
        return RutinaDTO.fromEntity(r);
    }

    @DeleteMapping({"/{idRutina}"})
    /*@Operation(
            description = "Elimina la rutina. Solo puede hacerlo el entrenador que la ha creado.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "La rutina se ha elminado"
            ), @ApiResponse(
                    responseCode = "404",
                    description = "La rutina no existe",
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
    )*/
    public void eliminarRutina(@PathVariable Long idRutina) {
        this.rutinaService.DeleteRutina(idRutina);
    }

    @ExceptionHandler({RutinaNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void rutinaNoEncontrada() {
    }
}
