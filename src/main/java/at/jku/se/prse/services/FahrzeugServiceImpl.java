package at.jku.se.prse.services;


import at.jku.se.prse.model.Fahrzeug;
import at.jku.se.prse.repositories.FahrzeugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FahrzeugServiceImpl implements FahrzeugService{

    @Autowired
    FahrzeugRepository repository;

    @Override
    public Fahrzeug save(Fahrzeug fz) {
        return repository.save(fz);
    }

    @Override
    public List<Fahrzeug> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Fahrzeug fz) {
        repository.delete(fz);
    }
}