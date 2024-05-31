package EntidadesApplication.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import EntidadesApplication.CustomExceptions.ForbbidenException;
import EntidadesApplication.Dtos.EjercicioDTO;
import EntidadesApplication.Dtos.EntrenadorDTO;
import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.repositories.EjerciciosRepo;
import EntidadesApplication.repositories.RutinaRepo;
import EntidadesApplication.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@Transactional
public class EjercicioService {
    private final EjerciciosRepo ejerciciosRepo;


    private final RestTemplate restTemplate = new RestTemplate();

    private String jwt;



    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RutinaRepo rutinaRepo;


    @Autowired
    public EjercicioService(EjerciciosRepo er){

        this.ejerciciosRepo=er;
    }

    public Optional<Ejercicio> getEjercicio(Long id,String jwt) throws ForbbidenException{

        if(!(ejerciciosRepo.findById(id).isPresent())){
            return Optional.empty();
        }

        if(!(comprobarGet(id,jwt)))  throw new ForbbidenException();


        return ejerciciosRepo.findById(id);
    }


    public List<Ejercicio> obtenerEjercicios(Long idEntrenador, String jwt) throws ForbbidenException {

        String idUsuario = jwtUtil.getUsernameFromToken(jwt);
        if(!(esEntrenador(idEntrenador,idUsuario,jwt))) throw new ForbbidenException();

        return ejerciciosRepo.findByIdEntrenador(idEntrenador);




    }



    public void EliminarEjercicio(Long id )throws ForbbidenException{

            ejerciciosRepo.deleteById(id);
    }
    public Ejercicio PutorPostEjercicios(Ejercicio ej, String jwt) throws ForbbidenException{
             if (ej.getId() != null) {
            Optional<Ejercicio> existeejercicio = ejerciciosRepo.findById(ej.getId());
            if(existeejercicio.isPresent()){
                if (!comprobarGet(ej.getId(), jwt))  throw  new ForbbidenException();

                ej.setIdEntrenador(existeejercicio.get().getIdEntrenador());
                return ejerciciosRepo.save(ej);
            }else{
                throw new ForbbidenException();
            }
    }else{
        return ejerciciosRepo.save(ej);
    }
    }

   //CREADOR DE URIS PARA CONECTAR AL MICROSERVICIO

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




    public boolean comprobarGet(Long id_ejercicio, String jwt) {
        String idusuario = jwtUtil.getUsernameFromToken(jwt);
        String idEntrenador = ejerciciosRepo.findById(id_ejercicio).get().getIdEntrenador().toString();



        var peticion = get("http", "localhost", 9001, "/entrenador/" + idEntrenador, jwt);
        var respuesta = restTemplate.exchange(peticion, EntrenadorDTO.class);



        String respuestaUsuario = respuesta.getBody().getIdUsuario().toString();
        String respuestaEntrenador = respuesta.getBody().getId().toString();

        if( (respuesta.getStatusCode().value() != 200 || respuesta.getStatusCode().value() != 201) && (!(idusuario.equals(respuestaUsuario)))){  //Si el usuario no es un entrenador
            return false;
        }
        if(!(respuestaEntrenador.equals(idEntrenador))){  //Si el ejercicio no pertenece al entrenador, devuelve false
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


