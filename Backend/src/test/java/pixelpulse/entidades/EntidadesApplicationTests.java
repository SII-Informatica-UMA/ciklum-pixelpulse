package pixelpulse.entidades;

import EntidadesApplication.Dtos.*;
import EntidadesApplication.EntidadesApplication;
import EntidadesApplication.entities.FragmentoRutina;
import EntidadesApplication.entities.Rutina;
import EntidadesApplication.repositories.EjerciciosRepo;
import EntidadesApplication.repositories.RutinaRepo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import java.sql.Date;
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

public class EntidadesApplicationTests {

    Long idEjer1 = null;
    Long idEjer2 = null;
    Long idRut1 = null;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
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
    public class GetConMockitoNestedTest {

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
        public void GetEjercicioNoExistTest() {
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
        public void GetJWTConPermisoTest() throws JsonProcessingException {   //suponemos que el jwt60 es entrenador 1 y es dueño del ejercicio 1
            ObjectMapper mapper = new ObjectMapper();
            String tokenaux="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzE1ODA4MjU2LCJleHAiOjYwMDAxNzE1ODA4MjU2fQ.TlGrRwC7BI0maP--cNOepyOGneYY9bTI4Zke6DTVw-T59FhS4QjjFN7NTm6GhcfxDmPbD2jcLdebzS8TXvEnbQ";
            EntrenadorDTO ed= new EntrenadorDTO(60L,1L,"123456789","Calle Falsa 123","12345678A",null,null,null,"Especialidad","Titulacion","Experiencia","Observaciones");

            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(mapper.writeValueAsString(ed)));


            var peticion = getNoQuery("http", "localhost", port, "/ejercicio/" + idEjer1, tokenaux);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<EjercicioDTO>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }


        @Test
        @DisplayName("Devuelve Error del ejercicio JWT sin permiso")
        public void GetJWTSinPermisoTest() {   //intentamos el mismo ejercicio con JWT sin permiso
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

    @Nested
    @DisplayName("GET/ejercicio")
    public class GetGeneralConMockitoNestedTest {
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
        @DisplayName("Devuelve satisfactoriamente los ejercicios, JWT con permiso")
        public void GetJWTConPermisoTest() {   //suponemos que el jwt60 es entrenador 1 y es dueño del ejercicio 1
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));


            var peticion = getNoQuery("http", "localhost", port, "/ejercicio", jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Ejercicio>>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }


    @Nested
    @DisplayName("Put/ejercicio/{idejercicio}")
    public class PutConMockito {

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
        public void PutEjercicioNoExistTest() {
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setNombre("NuevoNombre");
            var peticion = put("http", "localhost", port, ejercicio,"/ejercicio/" + 43781234, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<EjercicioDTO>() {
                    });


            assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        }
        @Test
        @DisplayName("Actualiza satisfactoriamente el ejercicio JWT con permiso")
        public void PutJWTConPermisoTest() {   //suponemos que el jwt60 es entrenador 1 y es dueño del ejercicio 1
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setNombre("NuevoNombre");

            var peticion = put("http", "localhost", port, ejercicio,"/ejercicio/" + idEjer1, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<EjercicioDTO>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
           // assertThat(respuesta.getBody().getNombre()).isEqualTo("NuevoNombre");
        }
        @Test
        @DisplayName("Devuelve Error del ejercicio JWT sin permiso")
        public void PutJWTSinPermisoTest() {   //intentamos el mismo ejercicio con JWT sin permiso
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setNombre("NuevoNombre");
            var peticion = put("http", "localhost", port,ejercicio, "/ejercicio/" + idEjer1, jwt61);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<EjercicioDTO>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }
    }

    @Nested
    @DisplayName("Delete/ejercicio/{idejercicio}")
    public class DeleteConMockito {

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
        public void DeleteEjercicioNoExistTest() {
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            var peticion = delete("http", "localhost", port, "/ejercicio/" + 43781234, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<Void>() {
                    });


            assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        }

        @Test
        @DisplayName("Borra satisfactoriamente el ejercicio JWT con permiso")
        public void DeleteJWTConPermisoTest() {   //suponemos que el jwt60 es entrenador 1 y es dueño del ejercicio 1
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));


            var peticion = delete("http", "localhost", port,"/ejercicio/" + idEjer1, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<Void>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            // assertThat(respuesta.getBody().getNombre()).isEqualTo("NuevoNombre");
        }
        @Test
        @DisplayName("Devuelve Error del ejercicio JWT sin permiso")
        public void DeleteJWTSinPermisoTest() {   //intentamos el mismo ejercicio con JWT sin permiso
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));


            var peticion = delete("http", "localhost", port, "/ejercicio/" + idEjer1, jwt61);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<Void>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }
    }

    ////////////////////////////////////////////////////////////////RUTINAS////////////////////////////////////////////////////////////////
    @Nested
    @DisplayName("GET/rutina/{idrutina}")
    public class GetRutinaIdConMockitoNestedTest {

        @BeforeEach
        public void setup() {
            MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
            rutinaRepository.deleteAll();
            ejercicioRepository.deleteAll();

            Rutina rutina = new Rutina();

            rutina.setIdEntrenador(1L);


            Rutina rt1 = rutinaRepository.saveAndFlush(rutina);
            idRut1 = rt1.getId();

        }

        @Test
        @DisplayName("Devuelve error rutina no existe")
        public void GetRutinaNoExistTest() {
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            var peticion = getNoQuery("http", "localhost", port, "/rutina/" + 43781234, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<RutinaDTO>() {
                    });


            assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        }


        @Test
        @DisplayName("Devuelve satisfactoriamente la rutina JWT con permiso")
        public void GetRutinaJWTConPermisoTest() throws JsonProcessingException {   //suponemos que el jwt60 es entrenador 1 y es dueño del ejercicio 1
            ObjectMapper mapper = new ObjectMapper();
            EntrenadorDTO ed= new EntrenadorDTO(60L,1L,"123456789","Calle Falsa 123","12345678A",null,null,null,"Especialidad","Titulacion","Experiencia","Observaciones");

            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(mapper.writeValueAsString(ed)));


            var peticion = getNoQuery("http", "localhost", port, "/rutina/" + idRut1, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<RutinaDTO>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }


        @Test
        @DisplayName("Devuelve Error de la rutina JWT sin permiso")
        public void GetRutinaJWTSinPermisoTest() {   //intentamos el mismo ejercicio con JWT sin permiso
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));


            var peticion = getNoQuery("http", "localhost", port, "/rutina/" + idRut1, jwt61);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<RutinaDTO>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }



    }

    @Nested
    @DisplayName("GET/rutina")
    public class GetGeneralRutinaConMockitoNestedTest {
        @BeforeEach
        public void setup() {
            MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
            rutinaRepository.deleteAll();
            ejercicioRepository.deleteAll();

            Rutina rutina = new Rutina();

            rutina.setIdEntrenador(1L);


            Rutina rt1 = rutinaRepository.saveAndFlush(rutina);
            idRut1 = rt1.getId();

        }


        @Test
        @DisplayName("Devuelve satisfactoriamente las rutinas JWT con permiso")
        public void GetRutinaJWTConPermisoTest() {   //suponemos que el jwt60 es entrenador 1 y es dueño del ejercicio 1
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));


            var peticion = getNoQuery("http", "localhost", port, "/rutina", jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Rutina>>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }


    @Nested
    @DisplayName("Put/rutina/{idrutina}")
    public class PutRutinaConMockito {

        @BeforeEach
        public void setup() {
            MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
            rutinaRepository.deleteAll();
            ejercicioRepository.deleteAll();

            Rutina rutina = new Rutina();

            rutina.setIdEntrenador(1L);


            Rutina rt1 = rutinaRepository.saveAndFlush(rutina);
            idRut1 = rt1.getId();
        }

        @Test
        @DisplayName("Devuelve error Rutina no existe")
        public void PutRutinaNoExistTest() {
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            Rutina rutina = new Rutina();
            rutina.setNombre("NuevoNombre");
            var peticion = put("http", "localhost", port, rutina,"/ejercicio/" + 43781234, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<RutinaDTO>() {
                    });


            assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        }
        @Test
        @DisplayName("Actualiza satisfactoriamente la rutina JWT con permiso")
        public void PutRutinaJWTConPermisoTest() {   //suponemos que el jwt60 es entrenador 1 y es dueño del ejercicio 1
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            Rutina rutina = new Rutina();
            rutina.setNombre("NuevoNombre");

            var peticion = put("http", "localhost", port, rutina,"/ejercicio/" + idRut1, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<RutinaDTO>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            // assertThat(respuesta.getBody().getNombre()).isEqualTo("NuevoNombre");
        }
        @Test
        @DisplayName("Devuelve Error de la rutina JWT sin permiso")
        public void PutRutinaJWTSinPermisoTest() {   //intentamos el mismo ejercicio con JWT sin permiso
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            Rutina rutina = new Rutina();
            rutina.setNombre("NuevoNombre");
            var peticion = put("http", "localhost", port,rutina, "/ejercicio/" + idRut1, jwt61);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<RutinaDTO>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }
    }

    @Nested
    @DisplayName("Delete/rutina/{idrutina}")
    public class DeleteRutinaConMockito {

        @BeforeEach
        public void setup() {
            MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
            rutinaRepository.deleteAll();
            ejercicioRepository.deleteAll();

            Rutina rutina = new Rutina();

            rutina.setIdEntrenador(1L);


            Rutina rt1 = rutinaRepository.saveAndFlush(rutina);
            idRut1 = rt1.getId();

        }

        @Test
        @DisplayName("Devuelve error Rutina no existe")
        public void DeleteEjercicioNoExistTest() {
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));

            var peticion = delete("http", "localhost", port, "/rutina/" + 43781234, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<Void>() {
                    });


            assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        }

        @Test
        @DisplayName("Borra satisfactoriamente la rutina JWT con permiso")
        public void DeleteJWTConPermisoTest() {   //suponemos que el jwt60 es entrenador 1 y es dueño del ejercicio 1
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));



            var peticion = delete("http", "localhost", port,"/rutina/" + idRut1, jwt60);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<Void>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            // assertThat(respuesta.getBody().getNombre()).isEqualTo("NuevoNombre");
        }
        @Test
        @DisplayName("Devuelve Error de la rutina JWT sin permiso")
        public void DeleteJWTSinPermisoTest() {   //intentamos el mismo ejercicio con JWT sin permiso
            mockServer.expect(
                            requestTo(UriComponentsBuilder.fromUriString("http://localhost:9001/entrenador/1").build().toUri()))   //Vamos al microservicio de entrenadores a sacar los datos
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{ \"idUsuario\": 60, \"telefono\": \"string\", \"direccion\": \"string\", \"dni\": \"string\", \"fechaNacimiento\": \"2024-05-31T09:08:45.560Z\", \"fechaAlta\": \"2024-05-31T09:08:45.560Z\", \"fechaBaja\": \"2024-05-31T09:08:45.560Z\", \"especialidad\": \"string\", \"titulacion\": \"string\", \"experiencia\": \"string\", \"observaciones\": \"string\", \"id\": 1 }"));


            var peticion = delete("http", "localhost", port, "/rutina/" + idRut1, jwt61);
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<Void>() {
                    });


            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }
    }
}





