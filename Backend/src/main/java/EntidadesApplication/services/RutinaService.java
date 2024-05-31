package EntidadesApplication.services;


import EntidadesApplication.CustomExceptions.ForbbidenException;
import EntidadesApplication.CustomExceptions.RutinaNotFoundException;
import EntidadesApplication.Dtos.EntrenadorDTO;
import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.entities.Rutina;
import EntidadesApplication.repositories.RutinaRepo;
import EntidadesApplication.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RutinaService {
    private final RestTemplate restTemplate = new RestTemplate();

    private String jwt;



    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RutinaRepo rutinaRepo;

    public  RutinaService(RutinaRepo rutinaRepo){
        this.rutinaRepo=rutinaRepo;
    }



    public List<Rutina> GetRutinas(Long idEntrenador,String jwt) {


        String idUsuario = jwtUtil.getUsernameFromToken(jwt);

        if(!(esEntrenador(idEntrenador,idUsuario,jwt))) throw new ForbbidenException();

        return rutinaRepo.findByIdEntrenador(idEntrenador);

    }

    public Optional<Rutina> GetRutina(Long idRutina, String jwt){

        if(!(rutinaRepo.findById(idRutina).isPresent())){
            return Optional.empty();
        }

        if(!(comprobarGet(idRutina,jwt)))  throw new ForbbidenException();


        return rutinaRepo.findById(idRutina);
    }

    public void EliminarRutina(Long id )throws ForbbidenException{

        rutinaRepo.deleteById(id);
    }



    public Rutina PutOrPostRutina(Rutina rut, String jwt) throws ForbbidenException {
        if (rut.getId() != null) {
            Optional<Rutina> existeRutina = rutinaRepo.findById(rut.getId());
            if(existeRutina.isPresent()){
                if (!comprobarGet(rut.getId(), jwt))  throw  new ForbbidenException();

                rut.setIdEntrenador(existeRutina.get().getIdEntrenador());
                return rutinaRepo.save(rut);
            }else{
                throw new ForbbidenException();
            }
        }else{
            return rutinaRepo.save(rut);
        }
    }






    //CREADOR DE URIS PARA CONECTAR AL MICROSERVICIO, lo voy a copiar al de ejercicios por si en el futuro deben de ir a parte


    private URI uri(String scheme, String host, int port, Map<String, String> queryParams, String... paths) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
                .scheme(scheme)
                .host(host)
                .port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        if (queryParams != null && !queryParams.isEmpty()) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                ub = ub.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return ub.build();
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path, String jwt) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .build()
                .toUri();
        var peticion = RequestEntity.get(uri)
                .header("Authorization", "Bearer " + jwt)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return peticion;
    }




    public boolean comprobarGet(Long id_ejercicio, String jwt) {String idusuario = jwtUtil.getUsernameFromToken(jwt);
        String idEntrenador = rutinaRepo.findById(id_ejercicio).get().getIdEntrenador().toString();



        var peticion = get("http", "localhost", 9001, "/entrenador/" + idEntrenador, jwt);
        var respuesta = restTemplate.exchange(peticion, EntrenadorDTO.class);



        String respuestaUsuario = respuesta.getBody().getIdUsuario().toString();
        String respuestaEntrenador = respuesta.getBody().getId().toString();

        if( (respuesta.getStatusCode().value() != 200 || respuesta.getStatusCode().value() != 201) && (!(idusuario.equals(respuestaUsuario)))){  //Si el usuario no es un entrenador
            return false;
        }
        if(!(respuestaEntrenador.equals(rutinaRepo.findById(id_ejercicio).get().getIdEntrenador().toString()))){  //Si el ejercicio no pertenece al entrenador, devuelve false
            return false;
        }

        return true;
    }

    public boolean esEntrenador(Long idEntrenador, String idUsuario,String jwt) {

        var peticion = get("http", "localhost", 9001, "/entrenador/" + idEntrenador, jwt);
        var respuesta = restTemplate.exchange(peticion, EntrenadorDTO.class);

        String respuestaUsuario = respuesta.getBody().getIdUsuario().toString();

        if( respuesta.getStatusCode().value() != 200 || (!(idUsuario.equals(respuestaUsuario)))) {  //Si el usuario no es un entrenador
            return false;
        }
        return true;
    }








}
