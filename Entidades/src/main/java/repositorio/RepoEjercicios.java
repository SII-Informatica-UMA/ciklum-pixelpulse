package repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pixelpulse.entidades.Ejercicio;

@Repository


public interface RepoEjercicios extends JpaRepository<Ejercicio, Long> {
    List<Ejercicio> findByIdEntrenador(Long idEntrenador);
    @Query("SELECT e FROM Ejercicio e WHERE e.tipo = :tipo AND e.dificultad = :dificultad")
    List<Ejercicio> buscarPorTipoYDificultad(String tipo, String dificultad);
}