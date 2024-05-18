package pixelpulse.entidades;

import EntidadesApplication.Dtos.RutinaDTO;
import EntidadesApplication.Dtos.RutinaNuevaDTO;
import EntidadesApplication.EntidadesApplication;
import EntidadesApplication.entities.Rutina;
import EntidadesApplication.repositories.EjerciciosRepo;
import EntidadesApplication.repositories.RutinaRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import EntidadesApplication.Dtos.EjercicioDTO;
import EntidadesApplication.Dtos.EjercicioNuevoDTO;
import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.services.EjercicioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = EntidadesApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de Gestion y Ejercicios")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EntidadesApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private EjerciciosRepo ejercicioRepository;

    @Autowired
    private RutinaRepo rutinaRepository;

    @BeforeEach
    public void initializeDatabase() {
        ejercicioRepository.deleteAll();
        rutinaRepository.deleteAll();
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
        URI uri = uri(scheme, host, port, null, path);
        var peticion = RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, T body, String path) {
        URI uri = uri(scheme, host, port, null, path);
        return RequestEntity.post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, T body, String path) {
        URI uri = uri(scheme, host, port, null, path);
        return RequestEntity.put(uri)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, null, path);
        return RequestEntity.delete(uri).build();
    }

    @Nested
    @DisplayName("cuando no hay ejercicios")
    public class EjerciciosVacios {

        @Test
        @DisplayName("devuelve la lista de ejercicios vacía")
        public void devuelveEjercicios() {
            var peticion = get("http", "localhost", port, "/ejercicios");

            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<EjercicioDTO>>() {
                    });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody()).isEmpty();
        }
    }

    @Nested
    @DisplayName("cuando se crea un ejercicio")
    public class EjercicioEntrenador {

        @BeforeEach
        public void setup() {
            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setNombre("Ejercicio 1");
            ejercicioRepository.save(ejercicio);
        }

        @Test
        @DisplayName("Crea un ejercicio nuevo a un entrenador")
        @WithMockUser(roles = "ENTRENADOR")
        public void crearEjercicio() {
            EjercicioNuevoDTO nuevoEjercicio = new EjercicioNuevoDTO();
            nuevoEjercicio.setNombre("Ejercicio 2");

            var peticion = post("http", "localhost", port, nuevoEjercicio, "/ejercicios");
            var respuesta = restTemplate.exchange(peticion, Ejercicio.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
            assertThat(respuesta.getBody().getNombre()).isEqualTo(nuevoEjercicio.getNombre());
        }

        @Test
        @DisplayName("Obtiene todos los ejercicios a un entrenador")
        public void obtenerEjercicios() {
            var peticion = get("http", "localhost", port, "/ejercicios");
            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Ejercicio>>() {
                    });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }

    @Nested
    @DisplayName("cuando son ejercicios específicos")
    public class EjerciciosEspecificos {

        @BeforeEach
        public void setup() {
            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setNombre("Ejercicio 1");
            ejercicioRepository.save(ejercicio);
        }

        @Test
        @DisplayName("Obtiene un ejercicio por id de ejercicio")
        public void obtenerEjercicio() {
            Long idEjercicio = 1L;

            var peticion = get("http", "localhost", port, "/ejercicios/" + idEjercicio);
            var respuesta = restTemplate.exchange(peticion, Ejercicio.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getId()).isEqualTo(idEjercicio);
        }

        @Test
        @DisplayName("Actualiza el ejercicio correctamente por id de ejercicio")
        public void actualizarEjercicio() {
            Long idEjercicio = 1L;
            EjercicioDTO ejercicioActualizado = new EjercicioDTO();
            ejercicioActualizado.setNombre("Nuevo Ejercicio 1");

            var peticion = put("http", "localhost", port, ejercicioActualizado, "/ejercicios/" + idEjercicio);

            var respuesta = restTemplate.exchange(peticion, Ejercicio.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getNombre()).isEqualTo(ejercicioActualizado.getNombre());
        }

        @Test
        @DisplayName("el ejercicio se elimina correctamente")
        public void eliminarEjercicio() {
            Long idEjercicio = 1L;

            var peticion = delete("http", "localhost", port, "/ejercicios/" + idEjercicio);
            var respuesta = restTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }

    @Nested
    @DisplayName("Rutinas de entrenador")
    public class RutinasEntrenador {
        @BeforeEach
        public void setup() {
            Rutina rutina = new Rutina();
            rutina.setNombre("Rutina 1");
            rutinaRepository.save(rutina);
        }

        @Test
        @DisplayName("Crea una rutina para un entrenador")
        public void crearRutina() {
            RutinaNuevaDTO rutina = new RutinaNuevaDTO();
            rutina.setNombre("Rutina 2");

            var peticion = post("http", "localhost", port, rutina, "/rutinas");
            var respuesta = restTemplate.exchange(peticion, Rutina.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
            assertThat(respuesta.getBody().getNombre()).isEqualTo(rutina.getNombre());
        }

        @Test
        @DisplayName("devuelve la lista de rutinas de un entrenador")
        public void obtenerRutinas() {
            var peticion = get("http", "localhost", port, "/rutinas");
            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Rutina>>() {
                    });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }

    @Nested
    @DisplayName("Rutinas específicas")
    public class RutinasEspecificas {
        @BeforeEach
        public void setup() {
            Rutina rutina = new Rutina();
            rutina.setNombre("Rutina 1");
            rutinaRepository.save(rutina);
        }

        @Test
        @DisplayName("Obtiene una rutina de un entrenador por id de rutina")
        public void obtenerRutina() {
            Long idRutina = 1L;

            var peticion = get("http", "localhost", port, "/rutinas/" + idRutina);
            var respuesta = restTemplate.exchange(peticion, Rutina.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getId()).isEqualTo(idRutina);
        }

        @Test
        @DisplayName("Actjaliza la rutina correctamente por id de rutina")
        public void actualizarRutina() {
            Long idRutina = 1L;
            RutinaDTO rutinaActualizada = new RutinaDTO();
            rutinaActualizada.setNombre("Nueva Rutina 1");

            var peticion = put("http", "localhost", port, rutinaActualizada, "/rutinas/" + idRutina);

            var respuesta = restTemplate.exchange(peticion, Rutina.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getNombre()).isEqualTo(rutinaActualizada.getNombre());
        }

        @Test
        @DisplayName("la rutina se elimina correctamente por id de rutina")
        public void eliminarRutina() {
            Long idRutina = 1L;

            var peticion = delete("http", "localhost", port, "/rutinas/" + idRutina);
            var respuesta = restTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
    }
}

