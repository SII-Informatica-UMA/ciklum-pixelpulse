package EntidadesApplication.repositories;

import java.util.List;

import EntidadesApplication.entities.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository

public interface RutinaRepo extends JpaRepository<Rutina, Long> {
    List<Rutina> findByIdEntrenador(Long idEntrenador);



}
