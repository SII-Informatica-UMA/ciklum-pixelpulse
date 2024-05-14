package usuarios.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import usuarios.entities.PasswordReset;
import usuarios.entities.Usuario;

import java.util.Optional;

@Repository
public interface PasswordResetRepo extends JpaRepository<PasswordReset, String>{

    @Query("SELECT p FROM PasswordReset p WHERE p.usuario = :usuario")
    Optional<PasswordReset> findByUsuario(Usuario usuario);

    @Query("SELECT p FROM PasswordReset p WHERE p.usuario.id = :id")
    Optional<PasswordReset> findByUsuario(Long id);

}
