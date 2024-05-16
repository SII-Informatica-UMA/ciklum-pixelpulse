package EntidadesApplication.repositories;

import java.util.List;


import EntidadesApplication.entities.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository

public interface EjerciciosRepo extends JpaRepository<Ejercicio, Long> {
    List<Ejercicio> findByIdEntrenador(Long idEntrenador);




}