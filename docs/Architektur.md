# Überblick über das System

## Entwurf

### UML-Diagramm

![test drawio](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/fb91ff2e-fc2a-4328-8dfb-8754975cd8fe "UML-Klassendiagramm")

Das UML-Diagramm beschreibt die drei Entitäten (Fahrt, Fahrzeug und Kategorie) und Enum Klassen, wobei die Beziehungen wichtig sind. Dadurch wird gezeigt, dass ein Fahrzeug mehrere Fahrten machen kann, aber eine Fahrt nur von einem Fahrzeug durchgeführt werden. Dabei können mehrere Kategorien einer Fahrt zugewiesen und die Kategorien für mehrere Fahrten verwendet werden.

### Design

#### Entscheidung 1:
- Entscheidung: Startseite entspricht der Hauptseite, worauf die Einträge/Fahrten angelegt und bearbeitet werden können
- Begründung: einfache und schnelle Handhabung der wichtigsten Funktionen
- Alternativen, die in Betracht gezogen wurden: Übersichtsseite, welche auf jeweilige Unterseiten verlinkt
- Annahmen: -
- Konsequenzen: Startseite und somit die Landingpage beinhaltet für die meisten Nutzer alle Funktionen

#### Entscheidung 2:
- Entscheidung: Wiederholende Fahrten werden durch eine Auswahl von wöchentlich, monatlich oder jährlich und mit der Angaben der Anzahl der Wiederholungen angelegt
- Begründung: Projektteam empfand diese Lösung am intuitivsten
- Alternativen, die in Betracht gezogen wurden: Zeitraum ist auszuwählen in dem entweder wöchentliche, monatliche oder jährliche Wiederholungen stattfinden sollen
- Annahmen: Es werden nur wöchentliche, monatliche oder jährliche Wiederholungen benötigt
- Konsequenzen: Benutzer muss wissen wie oft eine Fahrt angelegt werden soll

#### Entscheidung 3:
- Entscheidung: Grafische Darstellung soll als Liniendiagramm dargestellt werden
- Begründung: "Ausbrüche" der gefahrenen Kilometer können schnell und einfach eingesehen werden
- Alternativen, die in Betracht gezogen wurden: Balkendiagramm
- Annahmen: Grafische Darstellung war dem Projektteam überlassen, wie diese aussehen soll
- Konsequenzen: Alle Fahrzeuge werden auf eine Linie zusammengefasst

#### Entscheidung 4:
- Entscheidung: Export so generisch wie möglich gestalten
- Begründung: Falls Erweiterungen an der Applikation durchgenommen werden, können diese leichter im Export berücksichtigt werden
- Konsequenzen: Verwendung von Java Reflect KLassen

## Implementierung

### Aspekte der Implementierung
Das Projekt soll eine einfache Bedienung der Webapp ermöglichen und für Benutzer in wenigen Arbeitsschritten die erforderte Leistung erbringen. Für jeweilige Entitäten sollen eigene Klassen erstellt werden. Somit entstanden drei Model Klassen für die Fahrt, Fahrzeuge und Kategorien.
Da am Ende zwei wirkliche Seiten (einmal die Startseite und einmal die Geschäftsführung), werden diese in zwei view Klassen aufgeteilt.
Statische Daten, welche im Zusammenhand stehen, sollen in Enum Klassen hinterlegt werden.

Bei der Speicherung von neuen Fahrten ist die Berücksichtigung der gewünschten Forderungen wichtig und müssen verschiedene Ergebnisse berücksichtigt werden. Zu den Berücksichtigungen gehören Punkte gehören zum Beispiel, die Durchschnittsgeschwindigkeit, der Tachostand, oder Wiederholungen von Fahrten (wöchentlich, monatlich, oder jährlich).

>      public void saveNewFahrt() {
>        //Issue #23
>        if (newFahrt.getArrTime() != null && newFahrt.getDepTime() != null && newFahrt.getTimeStood() != null && newFahrt.getRiddenKM() != null) {
>            newFahrt.setAverageSpeed(newFahrt.getRiddenKM() / aktiveFahrzeit(newFahrt));
>        }
>        else newFahrt.setAverageSpeed(0.0);
>        if((newFahrt.getMileage() == null || newFahrt.getMileage() == 0) && newFahrt.getRiddenKM() != null && newFahrt.getRiddenKM() != 0) {
>            int mileageAfterRide = newFahrt.getFahrzeug().getMileage() + newFahrt.getRiddenKM();
>            newFahrt.setMileage(mileageAfterRide);
>            newFahrt.getFahrzeug().setMileage(mileageAfterRide);
>        }
>        else if(newFahrt.getMileage() != null && newFahrt.getMileage() != 0 && (newFahrt.getRiddenKM() == null || newFahrt.getRiddenKM() == 0)) {
>            int riddenKM = newFahrt.getMileage() - newFahrt.getFahrzeug().getMileage();
>            newFahrt.setRiddenKM(riddenKM);
>            newFahrt.getFahrzeug().setMileage(newFahrt.getMileage());
>        }
>        //End Issue #23
>        fahrtService.save(newFahrt);
>        //Issue #6
>        if(newFahrt.getNumberOfRepetitions() > 1 && newFahrt.getRepetition() != Wiederholung.NICHT_DEFINIERT) {
>            Fahrt fahrt = new Fahrt();
>            fahrt = setAdditionalFahrt(fahrt);
>            if(fahrt.getRepetition() == Wiederholung.WOECHENTLICH) repetitionWeekly(fahrt);
>            else if(fahrt.getRepetition() == Wiederholung.MONATLICH) repetitionMonthly(fahrt);
>            else if(fahrt.getRepetition() == Wiederholung.JAEHRLICH) repetitionYearly(fahrt);
>        }
>        //End Issue #6
>        initFahrten();
>      }

Falls eine Zeile bearbeitet wird, sollen natürlich auch die automatisch hinterlegten Daten angepasst werden.

>      public void rowEditFahrt(RowEditEvent<Fahrt> event) {
>       Fahrt f = event.getObject();
>       if (f.getArrTime() != null && f.getDepTime() != null && f.getTimeStood() != null && f.getRiddenKM() != null) {
>           f.setAverageSpeed(f.getRiddenKM() / aktiveFahrzeit(f));
>       }
>       else f.setAverageSpeed(0.0);
>
>        if(f.getMileage() != f.getFahrzeug().getMileage()) {
>            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tachostand unterschiedlich", "Tachostand unterscheidet sich zwischen Fahrzeug und Fahrt. Bitte überprüfen!"));
>        }
>        fahrtService.save(f);
>        FacesMessage msg = new FacesMessage("Edited", "Fahrt " + event.getObject().getId());
>        FacesContext.getCurrentInstance().addMessage(null, msg);
>        initFahrten();
>      }

### Projektstruktur
Um den Code so leserlich wie möglich zu gestalten wird der Code in entsprechende Unterordner aufgeteilt, um gleich bei der Struktur einen Überblick zu bekommen, ohne dass man den Code analysieren muss. Die Namensgebung der Klassen soll so klar wie möglich gehalten werden. Das Programm wurde in folgende Projektstruktur aufgeteilt:
- java
  - at.jku.se.prse
    - enums
      - Status.java  (Enum)
      - Wiederholung.java  (Enum)
    - model
      - Fahrt.java  (Klasse)
      - Fahrzeug.java (Klasse)
      - Kategorie.java  (Klasse)
    - repositories
      - FahrtRepository.java  (Interface)
      - KategorieRepository.java  (Interface)
    - services
      - FahrtServiceImpl.java  (Klasse)
      - FahrtService.java  (Interface)
      - KategorieServiceImpl.java (Klasse)
      - KategorieService.java (Interface)
      - FahrzeugService.java (Interface)
      - FahrzeugServiceImpl.java (Klasse)
      - ImportExportService.java (Interface)
      - ImportExportServiceImpl.java
    - views
      - AdministrationView.java (Klasse)
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
- Fahrt benutzt Daten von Kategorie, Fahrzeug und beiden Enum Klassen

### Alle Imports
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

- Erfolgreiches Anlegen einer Fahrt
  - gespeicherte Daten in einer Fahrt werden gespeichert
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

file:developer.properties können nicht gefunden werden.
In der Datei developer.properties wird der OAUTH2-Token vom verlinkten Dropbox-Account gespeichert. Damit dieser nicht im Reository landet, wird die Datei durch einen Eintrag im Git-ignore-File nicht auf Git gespeichert. Dadurch wird sie jedoh auch nicht beim Klonen des Repositories heruntergeladen, was beim Starten der Applikation zu einem Fehler führt.
Dieser würde jedoch jedenfalls auftreten, wenn kein OAUTH2-Token angegeben wird, da beim Starten der Applikation ein SPring-Bean mit der Dropbox-Connection erzeugt wird, der eine Exception wirft, wenn er sich nicht erfolgreich mit Dropbox verbinden kann.

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

