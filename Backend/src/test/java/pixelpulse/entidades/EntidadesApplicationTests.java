package pixelpulse.entidades;

import EntidadesApplication.Dtos.*;
import EntidadesApplication.EntidadesApplication;
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

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Mock
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    @Value(value = "${local.server.port}")
    private int port;
    private String jwtToken;
    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private EjerciciosRepo ejercicioRepository;

    @Autowired
    private RutinaRepo rutinaRepository;

    @BeforeEach
    public void initializeDatabase() {
        mockServer=MockRestServiceServer.createServer(testRestTemplate.getRestTemplate());
        ejercicioRepository.deleteAll();
        rutinaRepository.deleteAll();
        jwtToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzE1ODA4MjU2LCJleHAiOjYwMDAxNzE1ODA4MjU2fQ.TlGrRwC7BI0maP--cNOepyOGneYY9bTI4Zke6DTVw-T59FhS4QjjFN7NTm6GhcfxDmPbD2jcLdebzS8TXvEnbQ";

    }

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
            .queryParam("entrenador", 1)
            .build()
            .toUri();
        var peticion = RequestEntity.get(uri).header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return peticion;
    }



    private <T> RequestEntity<T> post(String scheme, String host, int port, T body, String path) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .queryParam("entrenador", 1)
                .build()
                .toUri();
        return RequestEntity.post(uri).header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, T body, String path) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .queryParam("entrenador", 1)
                .build()
                .toUri();
        return RequestEntity.put(uri).header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .queryParam("entrenador", 1)
                .build()
                .toUri();
        return RequestEntity.delete(uri).header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON).build();


    }

    @Nested
    @DisplayName("cuando no hay ejercicios")
    public class EjerciciosVacios {

        @BeforeEach
        public void setup() throws URISyntaxException {
            String idEntrenadorValido="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MCIsImlhdCI6MTcxNzA5NDk5MywiZXhwIjoxNzE3MDk1NTkzfQ.VsEQvnbCMoakD06_Sm9_MRabUT4CHHYA6eSWydlWZuozNR6oOhpgzF6gonn-3ZB--AfsMfHwGJi6yr1pI1bw ";
            EntrenadorDTO ed= new EntrenadorDTO(1L,1L,"123456789","Calle Falsa 123","12345678A",null,null,null,"Especialidad","Titulacion","Experiencia","Observaciones");
            mockServer.expect(ExpectedCount.once(),
                            requestTo(new URI("http://localhost:8080/entrenador/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON).body(String.valueOf(ed)));



        }

        @Test
        @DisplayName("devuelve la lista de ejercicios vacía")
        public void devuelveEjercicios() throws URISyntaxException {

            var peticion = get("http", "localhost", port, "/ejercicio");

            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<EjercicioDTO>>() {
                    });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody()).isEmpty();

        }

        @Test
        @DisplayName("error al intentar obtener ejercicio especifico cuando no hay ejercicios")
        public void obtenerEjercicioNoExiste() {
            Long idEjercicio = 1L;

            var peticion = get("http", "localhost", port, "/ejercicio/" + idEjercicio);
            var respuesta = testRestTemplate.exchange(peticion, Ejercicio.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("Error al actualizar un ejercicio especifico no existente")
        public void actualizarEjercicioNoExiste() {
            Long idEjercicio = 1L;
            EjercicioDTO ejercicioActualizado = new EjercicioDTO();
            ejercicioActualizado.setNombre("Nuevo Ejercicio 1");

            var peticion = put("http", "localhost", port, ejercicioActualizado, "/ejercicio/" + idEjercicio);

            var respuesta = testRestTemplate.exchange(peticion, Ejercicio.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("error al eliminar ejercicio especifico no existente")
        public void eliminarEjercicioNoExiste() {
            Long idEjercicio = 1L;

            var peticion = delete("http", "localhost", port, "/ejercicio/" + idEjercicio);
            var respuesta = testRestTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

    }
    
    @Nested
    @DisplayName("cuando se crea un ejercicio")
    public class EjercicioEntrenador {
        public long idEj;
        @BeforeEach
        public void setup() throws URISyntaxException {
            ejercicioRepository.deleteAll();
            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setNombre("Ejercicio 1");
            ejercicio.setIdEntrenador(1L);
            ejercicioRepository.save(ejercicio);
           List<Ejercicio> listaEj = ejercicioRepository.findAll();
            idEj = listaEj.get(0).getId();


            String idEntrenadorValido="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MCIsImlhdCI6MTcxNzA5NDk5MywiZXhwIjoxNzE3MDk1NTkzfQ.VsEQvnbCMoakD06_Sm9_MRabUT4CHHYA6eSWydlWZuozNR6oOhpgzF6gonn-3ZB--AfsMfHwGJi6yr1pI1bw ";
            EntrenadorDTO ed= new EntrenadorDTO(1L,1L,"123456789","Calle Falsa 123","12345678A",null,null,null,"Especialidad","Titulacion","Experiencia","Observaciones");
            mockServer.expect(ExpectedCount.once(),
                            requestTo(new URI("http://localhost:8080/entrenador/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON).body(String.valueOf(ed)));


        }




        @Test
        @DisplayName("Crea un ejercicio nuevo a un entrenador")
        @WithMockUser(roles = "ENTRENADOR")
        public void crearEjercicio() {
            EjercicioNuevoDTO nuevoEjercicio = new EjercicioNuevoDTO();
            nuevoEjercicio.setNombre("Ejercicio 2");

            var peticion = post("http", "localhost", port, nuevoEjercicio, "/ejercicio");
            var respuesta = testRestTemplate.exchange(peticion, Ejercicio.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
            assertThat(respuesta.getBody().getNombre()).isEqualTo(nuevoEjercicio.getNombre());
        }

        @Test
        @DisplayName("Obtiene todos los ejercicios a un entrenador")
        public void obtenerEjercicios() {
            String idEntrenadorValido="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MCIsImlhdCI6MTcxNzA5NDk5MywiZXhwIjoxNzE3MDk1NTkzfQ.VsEQvnbCMoakD06_Sm9_MRabUT4CHHYA6eSWydlWZuozNR6oOhpgzF6gonn-3ZB--AfsMfHwGJi6yr1pI1bw ";
            var peticion = get("http", "localhost", port, "/ejercicio");
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Ejercicio>>() {
                    });
            
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }

    @Nested
    @DisplayName("cuando son ejercicios específicos")
    public class EjerciciosEspecificos {
        public long idEj;
        @BeforeEach
        public void setup() throws URISyntaxException {
            ejercicioRepository.deleteAll();
            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setNombre("Ejercicio 1");
            ejercicio.setIdEntrenador(1L);
            ejercicioRepository.save(ejercicio);
            List<Ejercicio> listaEj = ejercicioRepository.findAll();
            idEj = listaEj.get(0).getId();
            String idEntrenadorValido="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MCIsImlhdCI6MTcxNzA5NDk5MywiZXhwIjoxNzE3MDk1NTkzfQ.VsEQvnbCMoakD06_Sm9_MRabUT4CHHYA6eSWydlWZuozNR6oOhpgzF6gonn-3ZB--AfsMfHwGJi6yr1pI1bw ";
            EntrenadorDTO ed= new EntrenadorDTO(1L,1L,"123456789","Calle Falsa 123","12345678A",null,null,null,"Especialidad","Titulacion","Experiencia","Observaciones");
            mockServer.expect(ExpectedCount.once(),
                            requestTo(new URI("http://localhost:8080/entrenador/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON).body(String.valueOf(ed)));

        }

        @Test
        @DisplayName("Obtiene un ejercicio por id de ejercicio")
        public void obtenerEjercicio() {
            Long idEjercicio = idEj;

            var peticion = get("http", "localhost", port, "/ejercicio/" + idEjercicio);
            var respuesta = testRestTemplate.exchange(peticion, Ejercicio.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getId()).isEqualTo(idEjercicio);
        }

        @Test
        @DisplayName("Actualiza el ejercicio correctamente por id de ejercicio")
        public void actualizarEjercicio() {
            Long idEjercicio = idEj;
            EjercicioDTO ejercicioActualizado = new EjercicioDTO();
            ejercicioActualizado.setNombre("Nuevo Ejercicio 1");

            var peticion = put("http", "localhost", port, ejercicioActualizado, "/ejercicio/" + idEjercicio);

            var respuesta = testRestTemplate.exchange(peticion, Ejercicio.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getNombre()).isEqualTo(ejercicioActualizado.getNombre());
        }

        @Test
        @DisplayName("el ejercicio se elimina correctamente")
        public void eliminarEjercicio() {
            Long idEjercicio = idEj;

            var peticion = delete("http", "localhost", port, "/ejercicio/" + idEjercicio);
            var respuesta = testRestTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }


    @Nested
    @DisplayName("cuando no hay rutinas")
    public class RutinasVacias {

        @Test
        @DisplayName("devuelve la lista de rutinas vacia")
        public void devuelveRutinas() {
            var peticion = get("http", "localhost", port, "/rutina");

            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<RutinaDTO>>() {
                    });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody()).isEmpty();
        }

        @Test
        @DisplayName("error al intentar eliminar rutina  no existente")
        public void eliminarRutinaVacia() {
            Long idRutina = 1L;

            var peticion = delete("http", "localhost", port, "/rutina/" + idRutina);
            var respuesta = testRestTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("error al intentar obtener una rutina especifica vacia")
        public void obtenerRutinaVacia() {
            Long idRutina = 1L;

            var peticion = get("http", "localhost", port, "/rutina/" + idRutina);
            var respuesta = testRestTemplate.exchange(peticion, Rutina.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);

        }

        @Test
        @DisplayName("error al intentar actualizar una rutina existente vacia")
        public void actualizarRutinaVacia() {
            Long idRutina = 1L;
            RutinaDTO rutinaActualizada = new RutinaDTO();
            rutinaActualizada.setNombre("Nueva Rutina 1");

            var peticion = put("http", "localhost", port, rutinaActualizada, "/rutina/" + idRutina);

            var respuesta = testRestTemplate.exchange(peticion, Rutina.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }


    }


    @Nested
    @DisplayName("Rutinas de entrenador")
    public class RutinasEntrenador {
        public long idRut;
        @BeforeEach
        public void setup() throws URISyntaxException {
            rutinaRepository.deleteAll();
            Rutina rutina = new Rutina();
            rutina.setNombre("Rutina 1");
            rutina.setIdEntrenador(1L);
            rutinaRepository.save(rutina);
            List<Rutina> listaRut = rutinaRepository.findAll();
            idRut = listaRut.get(0).getId();

            String idEntrenadorValido="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MCIsImlhdCI6MTcxNzA5NDk5MywiZXhwIjoxNzE3MDk1NTkzfQ.VsEQvnbCMoakD06_Sm9_MRabUT4CHHYA6eSWydlWZuozNR6oOhpgzF6gonn-3ZB--AfsMfHwGJi6yr1pI1bw ";
            EntrenadorDTO ed= new EntrenadorDTO(1L,1L,"123456789","Calle Falsa 123","12345678A",null,null,null,"Especialidad","Titulacion","Experiencia","Observaciones");
            mockServer.expect(ExpectedCount.once(),
                            requestTo(new URI("http://localhost:8080/entrenador/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON).body(String.valueOf(ed)));

        }

        @Test
        @DisplayName("Crea una rutina para un entrenador")
        public void crearRutina() {
            RutinaNuevaDTO rutina = new RutinaNuevaDTO();
            rutina.setNombre("Rutina 2");

            var peticion = post("http", "localhost", port, rutina, "/rutina");
            var respuesta = testRestTemplate.exchange(peticion, Rutina.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
            assertThat(respuesta.getBody().getNombre()).isEqualTo(rutina.getNombre());
        }

        @Test
        @DisplayName("devuelve la lista de rutinas de un entrenador")
        public void obtenerRutinas() {
            var peticion = get("http", "localhost", port, "/rutina");
            var respuesta = testRestTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Rutina>>() {
                    });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }

    @Nested
    @DisplayName("Rutinas específicas")
    public class RutinasEspecificas {
        public long idRut;
        @BeforeEach
        public void setup() throws URISyntaxException {
            rutinaRepository.deleteAll();
            Rutina rutina = new Rutina();
            rutina.setNombre("Rutina 1");
            rutina.setIdEntrenador(1L);
            rutinaRepository.save(rutina);
            List<Rutina> listaRut = rutinaRepository.findAll();
            idRut = listaRut.get(0).getId();

            String idEntrenadorValido="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MCIsImlhdCI6MTcxNzA5NDk5MywiZXhwIjoxNzE3MDk1NTkzfQ.VsEQvnbCMoakD06_Sm9_MRabUT4CHHYA6eSWydlWZuozNR6oOhpgzF6gonn-3ZB--AfsMfHwGJi6yr1pI1bw ";
            EntrenadorDTO ed= new EntrenadorDTO(1L,1L,"123456789","Calle Falsa 123","12345678A",null,null,null,"Especialidad","Titulacion","Experiencia","Observaciones");
            mockServer.expect(ExpectedCount.once(),
                            requestTo(new URI("http://localhost:8080/entrenador/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON).body(String.valueOf(ed)));


        }

        @Test
        @DisplayName("Obtiene una rutina de un entrenador por id de rutina")
        public void obtenerRutina() {
            Long idRutina = idRut;

            var peticion = get("http", "localhost", port, "/rutina/" + idRutina);
            var respuesta = testRestTemplate.exchange(peticion, Rutina.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getId()).isEqualTo(idRutina);
        }



        @Test
        @DisplayName("Actualiza la rutina correctamente por id de rutina")
        public void actualizarRutina() {
            Long idRutina = idRut;
            RutinaDTO rutinaActualizada = new RutinaDTO();
            rutinaActualizada.setNombre("Nueva Rutina 1");

            var peticion = put("http", "localhost", port, rutinaActualizada, "/rutina/" + idRutina);

            var respuesta = testRestTemplate.exchange(peticion, Rutina.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getNombre()).isEqualTo(rutinaActualizada.getNombre());
        } 

        @Test
        @DisplayName("la rutina se elimina correctamente por id de rutina")
        public void eliminarRutina() {
            Long idRutina = idRut;

            var peticion = delete("http", "localhost", port, "/rutina/" + idRutina);
            var respuesta = testRestTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }
}

