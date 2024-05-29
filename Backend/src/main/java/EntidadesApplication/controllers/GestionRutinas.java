package EntidadesApplication.controllers;


import java.net.URI;
import java.util.List;
import java.util.function.Function;


import EntidadesApplication.CustomExceptions.RutinaNotFoundException;
import EntidadesApplication.Dtos.RutinaDTO;
import EntidadesApplication.Dtos.RutinaNuevaDTO;
import EntidadesApplication.entities.Rutina;
import EntidadesApplication.services.RutinaService;
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
@RequestMapping({"/rutina"})
public class GestionRutinas {
    private RutinaService rutinaService;

    public GestionRutinas(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    @GetMapping
    public List<RutinaDTO> obtenerRutinas(@RequestParam(value = "entrenador",required = true) Long idEntrenador) {
        return this.rutinaService.GetRutinas(idEntrenador).stream().map(RutinaDTO::fromEntity).toList();
    }

    @PostMapping
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
    public ResponseEntity<RutinaDTO> obtenerRutina(@PathVariable Long idRutina) {
        return ResponseEntity.of(this.rutinaService.GetRutina(idRutina).map(RutinaDTO::fromEntity));
    }

    @PutMapping({"/{idRutina}"})
    public RutinaDTO actualizarRutina(@RequestParam(value = "entrenador",required = true) Long idEntrenador, @PathVariable Long idRutina, @RequestBody RutinaDTO rutina) {
        Rutina r = rutina.toEntity();
        r.setId(idRutina);
        r.setIdEntrenador(idEntrenador);
        r = this.rutinaService.PutOrPostRutina(r);
        return RutinaDTO.fromEntity(r);
    }

    @DeleteMapping({"/{idRutina}"})
    public void eliminarRutina(@PathVariable Long idRutina) {
        this.rutinaService.DeleteRutina(idRutina);
    }

    @ExceptionHandler({RutinaNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void rutinaNoEncontrada() {
    }
}
