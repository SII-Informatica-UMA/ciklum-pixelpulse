package usuarios.exceptions;


import usuarios.entities.Usuario;

public class UsuarioExistente extends RuntimeException {
    public UsuarioExistente(Usuario usuario) {
        super("El usuario con email " + usuario.getEmail() + " ya existe");
    }

    public UsuarioExistente() {
        super();
    };
}
