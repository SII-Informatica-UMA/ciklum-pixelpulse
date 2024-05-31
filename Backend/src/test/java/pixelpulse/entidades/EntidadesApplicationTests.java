package pixelpulse.entidades;

import EntidadesApplication.Dtos.*;
import EntidadesApplication.EntidadesApplication;
import EntidadesApplication.entities.FragmentoRutina;
import EntidadesApplication.entities.Rutina;
import EntidadesApplication.repositories.EjerciciosRepo;
import EntidadesApplication.repositories.RutinaRepo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.services.EjercicioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import org.mockito.Mock;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EntidadesApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de Gestion y Ejercicios")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EntidadesApplicationTests {

    Long idEjer1 = null;
    Long idEjer2 = null;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Mock
    private RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${local.server.port}")
    private int port;
    private String jwtToken;
    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private EjerciciosRepo ejercicioRepository;

    @Autowired
    private RutinaRepo rutinaRepository;

    String jwt60 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MCIsImlhdCI6MTcxNTgwODI1NiwiZXhwIjo2MDAwMTcxNTgwODI1Nn0.WEBElk3YnfFjFz3X9uyFevFVIHYPDSTI_8_B3ZsXq0oBe_43e8pNABfvgdpInQHttKeT33jl8aAvrV8SPT1T6w";
    String jwt61 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MSIsImlhdCI6MTcxNTgwODI1NiwiZXhwIjo2MDAwMTcxNTgwODI1Nn0.XVv_sy_VAbSfRhC4KVS2ygDOVlcnrILTXgDyAoKboK2UqLydCW5AUTmqFXKH1ig8TSIgYZt0xEDYdZLG5xpx9g";
    String jwt62 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MiIsImlhdCI6MTcxNTgwODI1NiwiZXhwIjo2MDAwMTcxNTgwODI1Nn0.DbcQ_qqJihfcX5jgdZ9glgihLaUIRRvnfnS3BVUwhVFPBSvNIRE7cF6c3pMpMp-OU18vZxzyubLVhSRTWV8Whg \n";


    MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);


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

    private RequestEntity<Void> get(String scheme, String host, int port, String path, String param, Long idParam, String jwt) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .queryParam(param, idParam)
                .build()
                .toUri();
        var peticion = RequestEntity.get(uri).header("Authorization", "Bearer " + jwt)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return peticion;
    }

    private RequestEntity<Void> getNoQuery(String scheme, String host, int port, String path, String jwt) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .build()
                .toUri();
        var peticion = RequestEntity.get(uri).header("Authorization", "Bearer " + jwt)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return peticion;
    }


    private <T> RequestEntity<T> post(String scheme, String host, int port, T body, String path, String param, Long idParam, String jwt) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .queryParam(param, idParam)
                .build()
                .toUri();
        return RequestEntity.post(uri).header("Authorization", "Bearer " + jwt)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, T body, String path, String jwt) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)

                .build()
                .toUri();
        return RequestEntity.put(uri).header("Authorization", "Bearer " + jwt)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path, String jwt) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)

                .build()
                .toUri();
        return RequestEntity.delete(uri).header("Authorization", "Bearer " + jwt)
                .accept(MediaType.APPLICATION_JSON).build();


    }

    @Nested
    @DisplayName("GET/ejercicio/{idejercicio}")
    public class GetConMockito {

        @BeforeEach
        public void setup() {
             MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
            rutinaRepository.deleteAll();
            ejercicioRepository.deleteAll();
            Ejercicio ejercicioID1 = new Ejercicio();
            Ejercicio ejercicioID2 = new Ejercicio();



            ejercicioID1.setIdEntrenador(1L);


            Ejercicio ej1 = ejercicioRepository.saveAndFlush(ejercicioID1);
            idEjer1 = ej1.getId();




            ejercicioID2.setIdEntrenador(2L);
            Ejercicio ej2 = ejercicioRepository.saveAndFlush(ejercicioID2);
            idEjer2 = ej2.getId();

        }

        @Test
        @DisplayName("Devuelve error ejercicio no existe")
        public void GetEjercicioNoExist() {
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            var peticion = getNoQuery("http", "localhost", port, "/ejercicio/" + 43781234, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<EjercicioDTO>() {
                    });


            assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        }


        @Test
        @DisplayName("Devuelve satisfactoriamente el ejercicio JWT con permiso")
        public void GetJWTConPermiso() {   //suponemos que el jwt60 es entrenador 1 y es dueño del ejercicio 1
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));


            var peticion = getNoQuery("http", "localhost", port, "/ejercicio/" + idEjer1, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<EjercicioDTO>() {
                    });


            assertThat(respuesta.getBody().getId()).isEqualTo(idEjer1);
        }


        @Test
        @DisplayName("Devuelve Error del ejercicio JWT sin permiso")
        public void GetJWTSinPermiso() {   //intentamos el mismo ejercicio con JWT sin permiso
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));


            var peticion = getNoQuery("http", "localhost", port, "/ejercicio/" + idEjer1, jwt61);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<EjercicioDTO>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }






    }
}




