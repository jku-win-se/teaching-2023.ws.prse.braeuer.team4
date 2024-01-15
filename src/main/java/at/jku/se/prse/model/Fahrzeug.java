package at.jku.se.prse.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Fahrzeug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(length = 15, unique = true)
    @Getter
    @Setter
    private String carPlate;

    @Getter
    @Setter
    private Integer mileage;

    @Override
    public String toString() {
        return "Fahrzeug " + carPlate;
    }
}