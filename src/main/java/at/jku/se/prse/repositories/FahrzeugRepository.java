package at.jku.se.prse.repositories;

import at.jku.se.prse.model.Fahrzeug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FahrzeugRepository extends JpaRepository<Fahrzeug, Integer> {
}