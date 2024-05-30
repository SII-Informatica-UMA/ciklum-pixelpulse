package EntidadesApplication.services;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import EntidadesApplication.CustomExceptions.ForbbidenException;
import EntidadesApplication.Dtos.EntrenadorDTO;
import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.repositories.EjerciciosRepo;
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
    private EjerciciosRepo ejerciciosRepo;
    private JwtUtil jwtUtil;
    private RestTemplate restTemplate;
    private String jwt;











    @Autowired
    public EjercicioService(EjerciciosRepo er){
        this.ejerciciosRepo=er;
    }

    public Optional<Ejercicio> getEjercicio(Long id,String jwt) throws ForbbidenException{

        this.jwt = this.jwt;
        if(!comprobarGet(id,jwt)) throw new ForbbidenException();



        return ejerciciosRepo.findById(id);
    }


    public List<Ejercicio> obtenerEjercicios(Long idEntrenador, String substring) {
        return this.ejerciciosRepo.findByIdEntrenador(idEntrenador);
    }

    public void EliminarEjercicio(Long id) throws Exception{
            Optional<Ejercicio>aux=ejerciciosRepo.findById(id);
            if(aux.isEmpty()){

                throw new Exception();
            }
            ejerciciosRepo.deleteById(id);
    }
    public Ejercicio PutorPostEjercicios(Ejercicio ej) throws Exception{
             if (ej.getId() != null) {
            Optional<Ejercicio> existeejercicio = ejerciciosRepo.findById(ej.getId().longValue());
            if(existeejercicio.isPresent()){
                return ejerciciosRepo.save(ej);
            }else{
                throw new Exception();
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

    private RequestEntity<Void> get(String scheme, String host, int port, String path) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .build()
                .toUri();
        var peticion = RequestEntity.get(uri).header("Authorization", "Bearer "+jwt)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return peticion;
    }




    private boolean comprobarGet(Long id, String jwt) {
        String idusuario = jwtUtil.getUsernameFromToken(jwt);

        var peticion = get("http", "localhost", 9001, "/entrenador/id");
        var respuesta = restTemplate.exchange(peticion, EntrenadorDTO.class);


        String respuestaID = respuesta.getBody().getIdUsuario();


        return false;
    }


}