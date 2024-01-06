package at.jku.se.prse.views;

import at.jku.se.prse.enums.Status;
import at.jku.se.prse.model.Fahrt;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AdministrationViewTest {

    // Start Durchschnittsgeschwindigkeitn bzw. Aktive Fahrzeit
    @Test
    public void testAktiveFahrzeit(){           //ohne Stehzeit
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now());
        fahrt.setDepTime(LocalTime.now().minusHours(2));
        fahrt.setArrTime(LocalTime.now());
        fahrt.setRiddenKM(5);
        assertEquals(2, AdministrationView.aktiveFahrzeit(fahrt));
    }

    @Test
    public void testAktiveFahrzeitStehzeit(){   //mit Stehzeit
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now());
        fahrt.setDepTime(LocalTime.now().minusHours(2));
        fahrt.setArrTime(LocalTime.now());
        fahrt.setRiddenKM(5);
        fahrt.setTimeStood(LocalTime.of(1,0));
        assertEquals(1, AdministrationView.aktiveFahrzeit(fahrt));
    }

    @Test
    public void testDurchschnittsgeschwindigkeit(){ //alle Daten sind angegeben
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now());
        fahrt.setDepTime(LocalTime.now().minusHours(2));
        fahrt.setArrTime(LocalTime.now());
        fahrt.setRiddenKM(60);
        fahrt.setTimeStood(LocalTime.of(1,0));
        assertEquals(60, AdministrationView.averageSpeed(fahrt));
    }

    @Test
    public void testDurchschnittsgeschwindigkeitZero(){ //Ankunftszeit ist nicht angegeben
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now());
        fahrt.setDepTime(LocalTime.now().minusHours(2));
        fahrt.setRiddenKM(60);
        fahrt.setTimeStood(LocalTime.of(1,0));
        assertEquals(0, AdministrationView.averageSpeed(fahrt));
    }

    @Test
    public void testDurchschnittsgeschwindigkeitStehzeit(){ //Stehzeit ist nicht angegeben
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now());
        fahrt.setDepTime(LocalTime.now().minusHours(2));
        fahrt.setArrTime(LocalTime.now());
        fahrt.setRiddenKM(60);
        assertEquals(30, AdministrationView.averageSpeed(fahrt));
    }

    // Start Status Tests
    @Test
    public void testGetStatusAbsolviert1(){
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now().minusWeeks(1));
        fahrt.setDepTime(LocalTime.now().minusHours(5));
        fahrt.setArrTime(LocalTime.now());
        assertEquals(Status.ABSOLVIERT, fahrt.getStatus());
    }

    @Test
    public void testGetStatusAbsolviert2(){
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now());
        fahrt.setDepTime(LocalTime.now().minusHours(5));
        fahrt.setArrTime(LocalTime.now().minusHours(2));
        assertEquals(Status.ABSOLVIERT, fahrt.getStatus());
    }

    @Test
    public void testGetStatusAufFahrt(){
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now());
        fahrt.setDepTime(LocalTime.now().minusHours(3));
        fahrt.setArrTime(LocalTime.now().plusMinutes(3));
        assertEquals(Status.AUF_FAHRT, fahrt.getStatus());
    }

    @Test
    public void testGetStatusZukuenftig1(){
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now().plusWeeks(1));
        fahrt.setDepTime(LocalTime.now());
        fahrt.setArrTime(LocalTime.now().plusMinutes(2));
        assertEquals(Status.ZUKUENFTIG, fahrt.getStatus());
    }

    @Test
    public void testGetStatusZukuenftig2(){
        Fahrt fahrt = new Fahrt();
        fahrt.setDate(LocalDate.now());
        fahrt.setDepTime(LocalTime.now().plusMinutes(2));
        fahrt.setArrTime(LocalTime.now().plusMinutes(4));
        assertEquals(Status.ZUKUENFTIG, fahrt.getStatus());
    }
    // End Status Tests
}