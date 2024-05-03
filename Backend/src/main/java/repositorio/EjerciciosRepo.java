package repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pixelpulse.entidades.Ejercicio;

@Repository

public interface EjerciciosRepo extends JpaRepository<Ejercicio, Long> {
    List<Ejercicio> findByIdEntrenador(Long idEntrenador);

    List<Ejercicio> findByIDAndNombreOrderByNombreAsc(String idEntrenador, String nombre);

    @Query("SELECT e FROM Ejercicio e WHERE e.tipo = :tipo AND e.dificultad = :dificultad")
    List<Ejercicio> buscarPorTipoYDificultad(String tipo, String dificultad);

    @Query("SELECT e FROM Ejercicio e WHERE e.musculosTrabajados = :musculo AND e.dificultad = :dificultad")
    List<Ejercicio> buscarPorMusculoYDificultad(String musculo, String dificultad);

    @Query("SELECT e FROM Ejercicio e WHERE e.nombre = :nombre AND e.dificultad = :dificultad")
    List<Ejercicio> buscarPorNombreYDificultad(String nombre,String dificultad);

}