package Services;

import org.springframework.stereotype.Service;
import repositorio.RutinaRepo;

@Service
public class RutinaService {
    private RutinaRepo rutinaRepo;

    public  RutinaService(RutinaRepo rutinaRepo){
        this.rutinaRepo=rutinaRepo;
    }

    //Debemos hacer un CRUD, añadir, modificar y eliminar



}
