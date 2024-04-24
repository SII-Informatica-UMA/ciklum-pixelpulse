package repositorio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pixelpulse.entidades.RutinaDTO;

@Repository

public interface RutinaRepo extends JpaRepository<RutinaDTO, Long> {
    List<RutinaDTO> findByIdEntrenador(Long idEntrenador);
    List<RutinaDTO> findByIDAndNombreOrderByNombreAsc(String idEntrenador, String nombre);
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Rutina r WHERE EXISTS (SELECT 1 FROM r.ejercicios f WHERE f.ejercicio.id = :idEjercicio)")
    boolean existsRutinaWithEjercicio(@Param("idEjercicio") Long idEjercicio);
}
