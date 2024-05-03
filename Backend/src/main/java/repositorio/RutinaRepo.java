package repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pixelpulse.entidades.Rutina;

@Repository

public interface RutinaRepo extends JpaRepository<Rutina, Long> {
    List<Rutina> findByIdEntrenador(Long idEntrenador);

    List<Rutina> findByIDAndNombreOrderByNombreAsc(String idEntrenador, String nombre);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Rutina r WHERE EXISTS (SELECT 1 FROM r.ejercicios f WHERE f.ejercicio.id = :idEjercicio)")
    boolean existsRutinaWithEjercicio(@Param("idEjercicio") Long idEjercicio);

    @Query("SELECT r FROM Rutina r WHERE r.descripcion = :descripcion AND r.idEntrenador = :idEntrenador")
    List<Rutina> BuscarPorDescripcionYIdEntrenador(@Param("descripcion") String descripcion, @Param("idEntrenador") Long idEntrenador);

}
