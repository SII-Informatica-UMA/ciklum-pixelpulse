package EntidadesApplication.services;

import java.util.List;
import java.util.Optional;


import EntidadesApplication.entities.Ejercicio;
import EntidadesApplication.repositories.EjerciciosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;



@Service
@Transactional
public class EjercicioService {
    private EjerciciosRepo ejerciciosRepo;
    
    @Autowired
    public EjercicioService(EjerciciosRepo er){
        this.ejerciciosRepo=er;
    }

    public Optional<Ejercicio> getEjercicio(Long id){
        return ejerciciosRepo.findById(id);
    }

    public List<Ejercicio> obtenerEjercicios(Long idEntrenador) {
        return this.ejerciciosRepo.findByIdEntrenador(idEntrenador);
    }

    public void EliminarEjercicio(Long id) throws Exception{
            Optional<Ejercicio>aux=ejerciciosRepo.findById(id);
            if(aux.isEmpty()){

                throw new Exception();
            }
            ejerciciosRepo.deleteById(id);
    }
    public Ejercicio PutorPostEjercicios(Ejercicio ej) throws Exception{
             if (ej.getId() != null) {
            Optional<Ejercicio> existeejercicio = ejerciciosRepo.findById(ej.getId().longValue());
            if(existeejercicio.isPresent()){
                return ejerciciosRepo.save(ej);
            }else{
                throw new Exception();
            }
    }else{
        return ejerciciosRepo.save(ej);
    }
}
}