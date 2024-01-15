package at.jku.se.prse.services;

import at.jku.se.prse.model.Fahrzeug;

import java.util.List;

public interface FahrzeugService {

    Fahrzeug save(Fahrzeug fz);

    List<Fahrzeug> findAll();

    void delete(Fahrzeug fz);
}