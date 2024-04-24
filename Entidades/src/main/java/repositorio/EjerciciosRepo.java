package repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pixelpulse.entidades.EjercicioDTO;

@Repository

public interface EjerciciosRepo extends JpaRepository<EjercicioDTO, Long> {
    List<EjercicioDTO> findByIdEntrenador(Long idEntrenador);

    List<EjercicioDTO> findByIDAndNombreOrderByNombreAsc(String idEntrenador, String nombre);

    @Query("SELECT e FROM Ejercicio e WHERE e.tipo = :tipo AND e.dificultad = :dificultad")
    List<EjercicioDTO> buscarPorTipoYDificultad(String tipo, String dificultad);

    @Query("SELECT e FROM Ejercicio e WHERE e.musculosTrabajados = :musculo AND e.dificultad = :dificultad")
    List<EjercicioDTO> buscarPorMusculoYDificultad(String musculo, String dificultad);

    @Query("SELECT e FROM Ejercicio e WHERE e.nombre = :nombre AND e.dificultad = :dificultad")
    List<EjercicioDTO> buscarPorNombreYDificultad(String nombre,String dificultad);

}