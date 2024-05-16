package EntidadesApplication.services;


import EntidadesApplication.CustomExceptions.RutinaNotFoundException;
import EntidadesApplication.entities.Rutina;
import EntidadesApplication.repositories.RutinaRepo;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class RutinaService {
    private RutinaRepo rutinaRepo;

    public  RutinaService(RutinaRepo rutinaRepo){
        this.rutinaRepo=rutinaRepo;
    }

    //Debemos hacer un CRUD, añadir, modificar y eliminar ademas de buscar

    public List<Rutina> GetRutinas(Long idEntrenador) {
        return this.rutinaRepo.findByIdEntrenador(idEntrenador);
    }

    public Optional<Rutina> GetRutina(Long idRutina){
        Optional<Rutina> rutina = rutinaRepo.findById(idRutina);
        if(rutina.isEmpty()) throw new RutinaNotFoundException();
        return  rutina;
    }

    public void  DeleteRutina(Long idRutina){
        Optional<Rutina> rutina = rutinaRepo.findById(idRutina);
        if(rutina.isEmpty()) throw new RutinaNotFoundException();
        rutinaRepo.deleteById(idRutina);
    }

    public Rutina PutOrPostRutina(Rutina rutina) {   //Creo que es muy redundante porque JPA hace el post o put solo, pero es la solucion que he visto en StackOverflow
        if (rutina.getId() != null) {
            Optional<Rutina> existingRutina = rutinaRepo.findById(rutina.getId());
            if (existingRutina.isPresent()) {
                return rutinaRepo.save(rutina);
            } else {
                throw new RutinaNotFoundException();
            }
        } else {
            return rutinaRepo.save(rutina);
        }
    }



}
