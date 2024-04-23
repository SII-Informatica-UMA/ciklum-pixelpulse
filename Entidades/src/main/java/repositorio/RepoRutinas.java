package repositorio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pixelpulse.entidades.Rutina;

@Repository

public interface RepoRutinas extends JpaRepository<Rutina, Long> {
    List<Rutina> findByIdEntrenador(Long idEntrenador);
    List<Rutina> findByIDAndNombreOrderByNombreAsc(String idEntrenador, String nombre);
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Rutina r WHERE EXISTS (SELECT 1 FROM r.ejercicios f WHERE f.ejercicio.id = :idEjercicio)")
    boolean existsRutinaWithEjercicio(@Param("idEjercicio") Long idEjercicio);
}
