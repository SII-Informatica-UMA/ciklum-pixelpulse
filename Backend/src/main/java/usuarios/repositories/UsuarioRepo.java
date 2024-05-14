package usuarios.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usuarios.entities.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}
