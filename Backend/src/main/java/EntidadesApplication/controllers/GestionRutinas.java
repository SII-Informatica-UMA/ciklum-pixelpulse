package EntidadesApplication.controllers;


import java.net.URI;
import java.util.List;
import java.util.function.Function;


import EntidadesApplication.CustomExceptions.RutinaNotFoundException;
import EntidadesApplication.Dtos.EjercicioDTO;
import EntidadesApplication.Dtos.RutinaDTO;
import EntidadesApplication.Dtos.RutinaNuevaDTO;
import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.entities.FragmentoRutina;
import EntidadesApplication.entities.Rutina;
import EntidadesApplication.repositories.EjerciciosRepo;
import EntidadesApplication.repositories.RutinaRepo;
import EntidadesApplication.services.RutinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@CrossOrigin
@RequestMapping({"/rutina"})
public class GestionRutinas {
    private final EjerciciosRepo ejerciciosRepo;
    private final RutinaRepo rutinaRepo;
    private RutinaService rutinaService;

    public GestionRutinas(RutinaService rutinaService, EjerciciosRepo ejerciciosRepo, RutinaRepo rutinaRepo) {
        this.rutinaService = rutinaService;
        this.ejerciciosRepo = ejerciciosRepo;
        this.rutinaRepo = rutinaRepo;
    }

    @GetMapping
    public ResponseEntity<List<RutinaDTO>>  obtenerRutinas(@RequestHeader(name="Authorization") String token, @RequestParam(value = "entrenador",required = true) Long idEntrenador) {
        if (!rutinaRepo.findByIdEntrenador(idEntrenador).isEmpty()) {
            List<RutinaDTO> lista = this.rutinaService.GetRutinas(idEntrenador,token.substring(7)).stream().map(RutinaDTO::fromEntity).toList();
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<RutinaDTO> crearRutina(@RequestHeader(name="Authorization") String token,@RequestParam(value = "entrenador",required = true) Long idEntrenador, @RequestBody RutinaNuevaDTO rutinaNuevaDTO, UriComponentsBuilder uriBuilder) {
        Rutina rutina = rutinaNuevaDTO.toEntity();
        rutina.setIdEntrenador(idEntrenador);
        rutina.setId((Long)null);

        List<FragmentoRutina> Ejercicios = rutina.getEjercicios();

        for(FragmentoRutina e : Ejercicios){
            if(ejerciciosRepo.findById(e.getEjercicio().getId()).isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

        rutina = this.rutinaService.PutOrPostRutina(rutina,token.substring(7));

        return ResponseEntity.created((URI)this.generadorUri(uriBuilder.build()).apply(rutina)).body(RutinaDTO.fromEntity(rutina));




    }

    private Function<Rutina, URI> generadorUri(UriComponents uriBuilder) {
        return (g) -> {
            return UriComponentsBuilder.newInstance().uriComponents(uriBuilder).path("/rutina").path(String.format("/%d", g.getId())).build().toUri();
        };
    }

    @GetMapping({"/{idRutina}"})
    public ResponseEntity<RutinaDTO> obtenerRutina(@RequestHeader(name="Authorization") String token,@PathVariable Long idRutina) {
        return ResponseEntity.of(this.rutinaService.GetRutina(idRutina,token.substring(7)).map(RutinaDTO::fromEntity));
    }

    @PutMapping({"/{idRutina}"})
    public ResponseEntity<RutinaDTO>  actualizarRutina(@RequestHeader(name="Authorization") String token, @PathVariable Long idRutina, @RequestBody RutinaDTO rutina) {
        Rutina e = rutina.toEntity();
        e.setId(idRutina);

        if(ejerciciosRepo.findById(idRutina).isPresent()){

            RutinaDTO rut = RutinaDTO.fromEntity(this.rutinaService.PutOrPostRutina(e, token.substring(7)));
            return ResponseEntity.ok(rut);

        }


        return ResponseEntity.notFound().build();
    }

    @DeleteMapping({"/{idRutina}"})
    public ResponseEntity<Void> eliminarRutina(@RequestHeader(name="Authorization") String token,@PathVariable Long idRutina) {
        if(rutinaRepo.findById(idRutina).isPresent()) {

            this.rutinaService.GetRutina(idRutina, token.substring(7));
            this.rutinaService.EliminarRutina(idRutina);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }


    @ExceptionHandler({RutinaNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void rutinaNoEncontrada() {
    }
}
