# Überblick über das System

## Entwurf

### UML-Diagramm

![284981659-7b235fe3-97c6-48c2-8011-4e85c015596c](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/522afbe1-19ea-4bb1-901b-bed74a52d744 "UML-Diagramm")

### Design

## Implementierung

### Projektstruktur
Das Programm wurde in folgende Projektstruktur aufgeteilt:
- java
  - at.jku.se.prse
    - enums
      - Status.java  (Enum)
      - Wiederholung.java  (Enum)
    - model
      - Fahrt.java  (Klasse)
      - Kategorie.java  (Klasse)
    - repositories
      - FahrtRepository.java  (Interface)
      - KategorieRepository.java  (Interface)
    - services
      - FahrtServiceImpl.java  (Klasse)
      - FahrtService.java  (Interface)
      - KategorieServiceImpl.java (Klasse)
      - KategorieService.java (Interface)
    - views
      - AnalyticsView.java  (Klasse)
    - FahrtenpuchApp.java (Main Klasse)
- webapp
  - templates
    - footer.xhtml
    - menu.xhtml
    - template.xhtml
  - analytics.xhtml
  - index.xhtml
- test
  - java
    - at.jku.se.prse
      - views
        - AdministrationViewTest.java (Klasse JUnit Tests)
      - FahrtenbuchAppTests.java (Klasse JUnit Tests)

### Abhängigkeiten
- FahrtServiceImpl implementiert FahrtService
- KategorieServiceImpl implementiert KategorieService
- AdministrationsView benutzt Daten von Fahrt und Kategorie, sowie FahrtService und KategorieService
- AnalyticsView benutzt Daten von Fahrt und Kategorie, sowie FahrtService und KategorieService
- Fahrt benutzt Daten von Kategorie und beine Enum Klassen


### Alle Imports

- import at.jku.se.prse.services.KategorieService;
- import jakarta.annotation.PostConstruct;
- import jakarta.faces.annotation.View;
- import jakarta.faces.application.FacesMessage;
- import jakarta.faces.context.FacesContext;
- import lombok.Getter;
- import lombok.Setter;
- import org.primefaces.event.RowEditEvent;
- import org.springframework.beans.factory.annotation.Autowired;
- import org.springframework.stereotype.Component;
- import java.util.List;
- import jakarta.persistence.*;
- import lombok.NoArgsConstructor;
- import java.time.LocalDate;
- import java.time.LocalTime;
- import java.util.HashSet;
- import java.util.Objects;
- import java.util.Set;
- import java.util.stream.Collectors;
- import org.springframework.data.jpa.repository.JpaRepository;
- import org.springframework.stereotype.Service;
- import jakarta.faces.annotation.View;
- import org.primefaces.model.charts.ChartData;
- import org.primefaces.model.charts.line.LineChartDataSet;
- import org.primefaces.model.charts.line.LineChartModel;
- import org.primefaces.model.charts.line.LineChartOptions;
- import org.primefaces.model.charts.optionconfig.title.Title;
- import org.primefaces.model.dashboard.DashboardModel;
- import org.primefaces.model.dashboard.DefaultDashboardModel;
- import org.primefaces.model.dashboard.DefaultDashboardWidget;
- import java.time.LocalDate;
- import java.time.Month;
- import java.time.format.TextStyle;
- import org.junit.jupiter.api.Test;
- import org.springframework.boot.SpringApplication;
- import org.springframework.boot.autoconfigure.SpringBootApplication;

## Testen

### JUnit Tests

- Durchschnittsgeschwindigkeit
  - Alle Daten sind angegeben
  - Ankunftszeit ist nicht angegeben
  - Stehzeit ist nicht angegeben
- Aktive Fahrzeit
  - ohne Stehzeit
  - mit Stehzeit
- Status
  - Auf Fahrt
  - Zukünftig (2 verschiedene Tests)
  - Absolviert (2 verschiedene Tests)
    - Beispiel:
      
>     @Test
>     public void testGetStatusAbsolviert1(){
>        Fahrt fahrt = new Fahrt();
>        fahrt.setDate(LocalDate.now().minusWeeks(1));
>        fahrt.setDepTime(LocalTime.now().minusHours(5));
>        fahrt.setArrTime(LocalTime.now());
>        assertEquals(Status.ABSOLVIERT, fahrt.getStatus());
>     }
    
### Code Qualität

PMD zeigt auch Warnungen als Fehler/Error an.
Es wurde nichts anderes außer Warnungen erkannt in den Builds.

### Testfälle
#### Testfall ID: #5

Entworfen von: Martin Froschauer

Ausgeführt am: 01.11.2023

Durchgeführt von: Martin Froschauer

Getestetes Requirement: 3. Bearbeitung von Fahrtenbucheinträgen

Voraussetzung: Einträge können bearbeitet werden

Testschritte:
  1. Neuen Eintrag (Fahrt) erstellen
  2. Button für das Bearbeiten klicken
  3. Eintrag bearbeiten (Daten ändern)
  4. Änderungen speichern
  5. Änderungen wurden übernommen

Testdaten:
- Kennzeichen: KFZ123
- Datum: 01.11.2023
- Abfahrtszeit: 12:00
- Ankunftszeit: 13:00
- Kilometer: 10
Änderung der Kilometer auf 30

Erwartetes Ergebnis:
- Kennzeichen: KFZ123
- Datum: 01.11.2023
- Abfahrtszeit: 12:00
- Ankunftszeit: 13:00
- Kilometer: 30
Daten werden wie hier angegeben gespeichert

Nachbedingung: -

Status: erfolgreich

Kommentare: ohne Probleme getestet
  

#### Testfall ID: #33

Entworfen von: Jakob Sommerauer

Ausgeführt am: 11.11.2023

Durchgeführt von: Jakob Sommerauer

Getestetes Requirement: Anzeige des Fahrtstatus im Backoffice

Voraussetzung:
- Datum, Abfahrts- und Ankunftszeit vor jetzigem Datum + Zeit = Status wird als *absolviert* angezeigt
- Datum, Abfahrtszeit vor jetzigem Datum + Zeit und Ankunftszeit nach jetzigem Datum + Zeit = Status wird als *auf Fahrt* angezeigt
- Datum, Abfahrts- und Ankunftszeit nach jetzigem Datum + Zeit = Status wird als *zukünftig* angezeigt

Testschritte:
- Neue Fahrt anlegen
  - Datum, Abfahrts- und Ankunftszeit vor jetzigem Datum + Zeit
- Status zeigt absolviert an
- Neue Fahrt anlegen
  - Datum, Abfahrtszeit vor jetzigem Datum + Zeit und Ankunftszeit nach jetzigem Datum + Zeit
- Status zeigt auf Fahrt an
- Neue Fahrt anlegen
  - Datum, Abfahrts- und Ankunftszeit nach jetzigem Datum + Zeit
- Status zeigt zukünftig an

Testdaten:
- Kennzeichen: TestStatus
- Datum: 01.10.2023
- Abfahrtszeit: 18:00
- Ankunftszeit: 19:00
- Kilometer: 100

- Kennzeichen: TestStatus
- Datum: 11.11.2023
- Abfahrtszeit: 08:00
- Ankunftszeit: 19:00
- Kilometer: 100

- Kennzeichen: TestStatus
- Datum: 11.11.2023
- Abfahrtszeit: 08:00
- Ankunftszeit: 19:00
- Kilometer: 100

Erwartetes Ergebnis:
- Status: absolviert
- Status: auf Fahrt
- Status: zukünftig

Nachbedingung: -

Status: erfolgreich

Kommentare: Testfall #32 war nicht erfolgreich - Kriterien und Daten der Testfälle sind ident
  

#### Testfall ID #35

Entworfen von: Sebastian Hinterbauer

Ausgeführt am: 12.11.2023

Durchgeführt von: Sebastian Hinterbauer

Getestetes Requirement: Zuordnung von Kategorien zu Fahrten im Backoffice

Voraussetzung: Eine oder mehrere Kategorien können einem Eintrag (Fahrt) zugeordnet werden

Testschritte:
- Manuell Kategorien auf der Datenbank speichern
- Neue Fahrt anlegen
- Fahrt bearbeiten
  - Kategorie auswählen
  - speichern
- Fahrt bearbeiten
  - Kategorie auswählen
  - speichern 

Testdaten:
- Kennzeichen: 1234
- Datum: 12.11.2023
- Abfahrtszeit: 08:00
- Ankunftszeit: 20:00
- Kilometer: 5
- Kategorie: Installation
- Kategorie: Reparatur

Erwartetes Ergebnis: Bei der ersten Kategoriefestlegung wird die erste ausgewählte Kategorie (Reparatur) angezeigt und bei der zweiten Kategoriefestlegung werden beide ausgewählten Kategorien (Reparatur und Installation) angezeigt

Nachbedingung: Beide Kategorien werden angezeigt

Status: erfolgreich

Kommentare: #34 war vorheriger, nicht erfolgreicher Testfall

