# Funktionalität

## Inhalt
- Projektbeschreibung
- Umgesetzte Anforderungen
  1. Fahrt ins Fahrtenbuch eintragen
  2. Übersicht aller Einträge für das Backoffice
  3. Bearbeitung von Fahrtenbucheinträgen
  4. Löschen von Fahrtenbucheinträgen im Backoffice
  5. Anlegen von wiederkehrenden/zukünftigen Fahrten im Backoffice
  6. Anzeige des Fahrtstatus im Backoffice
  7. Zuordnung von Kategorien zu Fahrten im Backoffice
  8. Filtern nach Kategorie
  9. Filtern nach Datum
  10. Sortierung generell
  11. Sortieren & Filtern – Durchschnittsgeschwindigkeit
  12. Analyse grafisch
  13. Daten Export
  14. Analyse tabellarisch
  15. Import von Daten
  16. Datenexport Cloud
- Überblick über das System aus Benutzersicht
  1. Navigation
  2. Startseite
  3. Fahrzeuge
  4. Neue Fahrt anlegen
  5. Kategorien
  6. Export
  7. Geschäftsführung
  8. Beispiel

# Projektbeschreibung

In diesem Projekt wird ein Fahrtenbuch umgesetzt. Neue Fahrten können angelegt, bearbeitet und gelöscht werden, wobei ein jeweiliger Status beschreibt, ob Fahrten vergangen, zukünftig oder gerade durchgeführt werden. Auch eine zukünftige Planung mehrerer sich wiederholende Fahrten ist möglich.
Weiter Funktionalitäten gibt es für die Analyse und das Anlegen und Bearbeiten von Kategorien.

Zu den Stakeholdern gehören Mitarbeiter*innen eines Handwerkbetriebes. Dabei werden drei unterschiedliche Stakeholder unterschieden.
1.	Mitarbeiter*innen welche ein Fahrtenbuch führen müssen
2.	Administrator*innen welche administrativen Aufgaben nachkommen müssen
3.	Management, welches analytische Inhalte abrufen möchte


# Umgesetzte Anforderungen

Alle folgenden Anforderungen wurden als Webapp mit Java Spring Boot umgesetzt.

## 1. Fahrt ins Fahrtenbuch eintragen

Als Handwerker*in wünsche ich mir die Möglichkeit, Fahrten in einem digitalen Fahrtenbuch zu erfassen. Dies soll zur Erhöhung der Transparenz meiner täglichen Arbeitsabläufe beitragen. Dabei sollen folgende Daten für jede Fahrt erfasst werden.

**Umsetzung:** Martin Froschauer

## 2. Übersicht aller Einträge für das Backoffice

Als Mitarbeiter*in des Backoffice wünsche ich mir eine zentrale Übersicht aller erfassten Fahrteneinträge, um sicherzustellen, dass sämtliche Fahrten ordnungsgemäß protokolliert wurden.

**Umsetzung:** Martin Froschauer

## 3. Bearbeitung von Fahrtenbucheinträgen

Als Handwerker*in benötige ich die Möglichkeit, bestehende Einträge im Fahrtenbuch zu bearbeiten. Dies ermöglicht es mir, Einträge vor der Abfahrt anzulegen und nach getaner Arbeit die Ankunftszeit, die gefahrenen Kilometer oder den aktuellen Tachostand, sowie die aktive Fahrzeit oder Stehzeit anzupassen.

**Umsetzung:** Martin Froschauer

## 4. Löschen von Fahrtenbucheinträgen im Backoffice

Als Mitarbeiter*in des Backoffice benötige ich die Möglichkeit, bestehende Einträge im Fahrtenbuch zu löschen. Dies ermöglicht es mir, versehentlich doppelt erstellte Einträge oder nicht mehr relevante Einträge zu entfernen.

**Umsetzung:** Jakob Sommerauer

## 5. Anlegen von wiederkehrenden/zukünftigen Fahrten im Backoffice

Als Mitarbeiter*in des Backoffice möchte ich die Möglichkeit, wiederkehrende oder zukünftige Fahrten im Voraus anzulegen, ohne spezifische Abfahrts- und Ankunftszeiten, gefahrene Kilometer und aktive Fahrzeit einzugeben. Diese Funktion ermöglicht es mir, Fahrten für monatliche oder jährliche Kontrolltermine, sowie für andere wiederkehrende Fahrten zu planen. Dabei möchte ich ein Von-Datum und ein Bis-Datum oder eine Anzahl an Wiederholungen angeben, um die Zuteilung der Handwerker*innen optimal zu planen und einen Überblick über die voraussichtliche Auslastung meines Fuhrparks zu behalten.

**Umsetzung:** Jakob Sommerauer

## 6. Anzeige des Fahrtstatus im Backoffice

Als Mitarbeiter*in des Backoffice möchte ich den Status einer Fahrt sehen können. Dies ermöglicht es mir, einen klaren Überblick über absolvierte, aktive oder zukünftige Fahrten zu haben. Die Statuskennzeichnung soll "absolviert", "auf Fahrt" oder "zukünftig" besitzen.

**Umsetzung:** Jakob Sommerauer

## 7. Zuordnung von Kategorien zu Fahrten im Backoffice

Als Mitarbeiter*in des Backoffice möchte ich jeder Fahrt optional eine oder mehrere Kategorien zuordnen können. Dies ermöglicht mir, dem Management aussagekräftige Auswertungen bereitzustellen. Die Kategorien könnten beispielsweise die Ursache einer Fahrt (Installation, Reparatur, Kontrolle, usw.) darstellen.

**Umsetzung:** Jakob Sommerauer

## 8. Filtern nach Kategorie

Als Mitarbeiter*in des Backoffice möchte ich Fahrten nach ein oder mehreren Kategorien filtern können, um bei der Erstellung von Auswertungen für das Management Zeit sparen zu können.

**Umsetzung:** Jakob Sommerauer

## 9. Filtern nach Datum

Als Mitarbeiter*in des Backoffice möchte ich Fahrten nach Tag/Monat/Jahr filtern können, um gezielt nach einzelnen Fahrten suchen zu können.

**Umsetzung:** Jakob Sommerauer

## 10. Sortierung generell

Als Mitarbeiter*in des Backoffice möchte ich Fahrten nach Datum/Zeit, Status, Kategorien, Fahrtzeit, Kilometer sortieren können, um gezielt nach einzelnen Fahrten zu suchen.

**Umsetzung:** Jakob Sommerauer

## 11. Sortieren & Filtern – Durchschnittsgeschwindigkeit

Als Mitarbeiter*in des Backoffice möchte ich Fahrten nach der durchschnittlichen Geschwindigkeit*) (km/h) sortieren und filtern können, um etwaige Fehleinträge leicht zu identifizieren und spezielle Verkehrssituationen (z.B. Staus, Umleitungen) besser beurteilen zu können.
*durchschnittlichen Geschwindigkeit = gefahrene Kilometer/aktive Fahrzeit

**Umsetzung:** Jakob Sommerauer

## 12. Analyse grafisch

Als Manager*in möchte ich die gefahrenen Kilometer je Monat und Jahr (gesamt oder eingeschränkt auf eine Kategorie) grafisch sehen können, um auf Bedarfsänderungen im Fuhrpark reagieren zu können.

**Umsetzung:** Martin Froschauer

## 13. Daten Export

Als System Administrator:in möchte ich die im System gespeicherten Daten exportieren können (z.B. als CSV), um auf potentielle Gefahren (Hardware-Ausfall, etc.) vorbereitet zu sein.

**Umsetzung:** Martin Froschauer

## 14. Analyse tabellarisch

Als Manager:in möchte ich die gefahrenen Kilometer je Monat und Jahr (gesamt oder eingeschränkt auf eine Kategorie) tabellarisch sehen können, um auf Bedarfsänderungen im Fuhrpark reagieren zu können.

**Umsetzung:** Martin Froschauer

## 15. Import von Daten

Als System Administrator:in möchte ich exportierte Daten auch wieder importieren können, um diese nicht manuell zu rekonstruieren und eingeben zu müssen.

**Umsetzung:** Martin Froschauer

## 16. Datenexport Cloud
Als System Administrator:in möchte ich die im System gespeicherten Daten in ein Cloud-System (z.B. Dropbox) exportieren können, damit dem Unternehmen ein Backup der Daten losgelöst von der lokalen IT-Infrastruktur zur Verfügung steht.

**Umsetzung:** Martin Froschauer

# Überblick über das System aus Benutzersicht

## Navigation

Links oben befinden sich zwei Menüpunkte. Einmal "Administration" welcher auf die Startseite verlinkt und "Geschäftsführung" welcher auf die Seite für die grafische und tabellarische Übersicht der Fahrten verlinkt.
Auf der Startseite gibt es unten links drei Button, welche zu der Anlegeübersicht für eine neue Fahrt, den Kategorien und der Fahrzeuge verlinken. Rechts oben befinden sich zwei Button für den Export im xlsx Format und den Export in eine Dropbox.

*Hinweis: Wir empfehlen den Webbrowser Google Chrome zu verwenden und fall einmal etwas nicht gleich funktioniert, bitte laden Sie die Seite einfach neu.*

## Startseite

![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/ee14b975-eff6-4fdc-a10a-1030748494b5 "Startseite")

Grundsätzlich finden sich fast alle Funktionen auf der Startseite wieder. Wie im Screenshot erkennbar sieht man eine Tabelle mit den Spalten Id, Status, Kennzeichen, Datum, Abfahrtszeit, Ankunftszeit, Kilometer, Tachostand, Stehzeit, Durchschnittsgeschw. *(Durchschnittsgeschwindigkeit)* und Kategorien. Links unten befindet sich ein Button, wodurch man einen Eintrag (Fahrt) hinzufügen kann. Nach der Befüllung der gewünschten Felder und nach dem Speichern *(siehe Punkt "neue Fahrt anlegen")* wird ein neuer Eintrag in der Tabelle angezeigt. Der Status der Fahrt wird dabei im Hintergrund festgelegt.

![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/3128a619-f257-4c70-89f2-b61c2ae465ae "Startseite mit Eintrag")


![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/4b70a5f4-8e77-454b-8bc9-6f803dbdce3b "Startseite - Eintrag bearbeiten")

Der Eintrag kann bearbeitet werden indem man auf das Stift-Symbol direkt am rechten Rand des Eintrages klickt. Änderungen können durch einen Klick auf den erschienenen Haken gespeichert und mit einem Klick auf das erschienene Kreuz verworfern werden. Die Durchschnittsgeschwindigkeit kann nicht bearbeitet werden, da diese im Hintergrund berechnet wird.
Rechts neben der Änderungsfunktion befindet sich ein Symbol einer Mülltonne und mit einem Klick darauf kann der Eintrag wieder gelöscht werden.
Filtern und sortieren kann man die Einträge gleich in der Zeile der Spaltenbezeichnungen. Durch einen Klick auf die Pfeil-Symbole kann entwender auf- oder absteigend sortiert werden (die Sortierung findet in jener Spalte statt, in der die Pfeile angeklickt werden). Direkt unter den Spaltenüberschriften befindet sich ein Eingabefeld, wodurch man nach bestimmten Einträgen filtern kann. Gibt man zum Beispiel in der Spalte *Kennzeichen* ein Kennzeichen in das Filter-Eingabefeld ein, dann werden nur Einträge mit diesem Kennzeichen angezeigt.

*Szenario:* Als Mitarbeiter*in der Administration kann ich alle vergangenen, laufenden und zuküntige Fahrten sehen und bearbeiten.

## Fahrzeuge

![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/95a047e4-b7c6-48d3-b27c-56efc60d0d2c "Fahrzeuge 1")


![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/9fc67cbd-ac42-4a82-80a7-46d8ef8c6090 "Fahrzeuge 2")

Bevor überhaupt eine Fahrt angelegt werden kann, muss mindestens ein Fahrzeug angelegt worden sein. Zu der Übersicht über alle Fahrzeuge gelangt man, indem man auf den sich links unten befindended Button "Fahrzeuge" klickt. Die Fahrzeuge werden durch die Felder ID, Kennzeichen und Tachostand angezeigt.

Genau wie bei der Startseite können die Fahrzeuge bearbeitet und gelöscht werden. Durch einen Klick auf den sich links oben befindenden Button "+ Add" wird ein leeres Fahrzeug hinzugefügt, welches durch das Bearbeiten befüllt werden kann.
Durch einen Klick auf das "X" rechts oben kann man das Fenster schließen.

*Szenario:* Als Mitarbeiter*in der Administration möchte ich unsere Flotte anlegen und öffne die Fahrzeugübersicht. Wir besitzen vier Fahrzeuge und deshalb klicke ich viermal auf den "+ Add" Button. Danach bearbeite ich jeden Eintrag einzeln und gebe das jeweilige Kennzeichen und den dazugehörigen Tachostand ein. Danach schließe ich das Fenster wieder.

## Neue Fahrt anlegen

![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/4a334d58-7c6a-4a0e-8a3c-003484f9f596 "Neue Fahrt 1")


![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/4e65edf5-2b87-4c6a-8662-238a8474d78a "Neue Fahrt 2")

Will man eine neue Fahrt anlegen muss man auf der Startseite den Button "+ Neue Fahrt" klicken und ein kleines Fenster popt auf. Hier können in den Feldern der jeweiligen Beschriftungen die Daten entsprechend eingegeben werden. Folgende Daten können eingegeben werden: KFZ-Kennzeichen, Datum, Abfahrtszeit, Ankunftszeit, Gefahrene Kilometer, Tachostand, Stehzeit, Wiederholung (Auswahl zwischen: Keine Wiederholung, wöchentlich, monatlich und jährlich) und Anzahl der Wiederholungen. Die letzten beiden Felder dienen dazu, wiederkehrende Fahrten anzulegen. Wählt man zum Beispiel eine wöchentliche Wiederholung mit einer Anzahl von 4. Dann werden 4 neue Einträge erstellt, wobei das Datum immer um eine Woche weiter geht.

**Pflichtfeld: Anzahl der Wiederholungen** - dieses Feld muss ausgefüllt werden, wenn keine Wiederholungen gewünscht sind, dann soll das Feld mit einer 0 befüllt werden.

Wenn alle gewünschten Felder befüllt wurden, dann kann man durch einen Klick auf den Button "Speichern" einen neuen (oder mehrere) Eintrag erstellen. Klickt man vor dem Speichern auf das "X" in der rechten oberen Ecke, dann wird der neue Eintrag verworfen.

*Szenario:* Als Mitarbeiter*in kann ich die Daten meiner derzeitigen Fahrt eingeben und speichern. Bei der Wiederholung werden keine Daten eingegeben, da es sich um eine Einzelfahrt handelt.

## Kategorien

![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/6bddc374-0c1f-48df-b435-f27c57f63a2b "Kategorie 1")


![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/c88a8e1f-2a8c-44e7-aca5-0485dd3e7464 "Kategorie 2")

Alle derzeitigen Kategorien können durch einen Klick auf den sich links unten befindenden Button "Kategorien" angezeigt werden. Dafür popt ein kleines Fenster auf und zeigt alle Kategorien an. Genau wie bei der Startseite können die Kategorien bearbeitet und gelöscht werden. Durch einen Klick auf den sich links oben befindenden Button "+ Add" wird eine leere Kategorie hinzugefügt, welche durch das Bearbeiten befüllt werden kann.
Durch einen Klick auf das "X" rechts oben kann man das Fenster schließen.

*Szenario:* Die Administration soll eine die Kategorie Reparatur anlegen. Dazu öffnen sie das Fenster für die Kategorien und legen eine neue leere Kategorie an, welche durch das Bearbeitungstool zur Kategorie Reparatur gemacht wird.

*Hinweis: Ist eine Kategorie in Verwendung, so kann sie nicht gelöscht werden.*

## Export

![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/4ae8dd2d-c84c-4571-8e63-0f1de72a91c3 "Download")

Klickt man auf den Button "Export XLSX" links oben auf der Startseite, so wird eine xlsx Datei heruntergeladen mit der Bezeichnung output.xlsx. In dieser Datei befinden sich alle Daten unterteilt auf Fahrten, Kategorien und Fahrzeuge.

Klickt man auf den Button "Export Cloud" links oben auf der Startseite, so wird eine xlsx Datei mit der Bezeichnung output.xlsx auf Dropbox geladen.

*Szenario:* Als Mitarbeiter*in der Administration möchte ich alle Daten herunterladen, um die Sicherung der Daten zu garantieren.

## Geschäftsführung

![grafik](https://github.com/jku-win-se/teaching-2023.ws.prse.braeuer.team4/assets/147154110/8077c87c-fecc-495a-8b25-2b2a9d1ea4e8 "Geschäftsführung 1")

Auf dieser Seite werden die grafische und tabellarische Übersicht der Fahrten angezeigt. Durch die Auswahl des Zeitraumes (Felder des Datums von und bis), der Auswahl ob die Ergebnisse nach Monate oder Jahre angezeigt werden sollen und die Auswahl der Kategorien, können die Ergebnisse angezeigt werden. Somit kann eine Übersicht erlangt werden wieviele Kilometer je Kategorie in einem bestimmten Zeitraum zurückgelegt wurden.
Szenario: Die Geschäftsführung möchte wissen wieviele Kilometer Für Reparaturen im letzten Jahr gefahren wurden und wählen die Monatsansicht, um eine Gliederung pro Monat einsehen zu können.

## Beispiel

Ein/e Mitarbeiter*in legt auf der Startseite eine neue Fahrt (der Grund/Kategorie war eine Reparatur) an und füllt alle Felder aus (es handelt sich um eine Einzelfahrt). Dabei wird eine falsche Ankunftszeit angegeben.

Mitarbeiter*innen der Administration werden aufmerksam gemacht, dass bei einer Eintragung ein Fehler unterlaufen ist und berichtigen den Fehler. Außerdem wissen sie, dass diese Fahrt regelmäßig auftreten wird und legen eine neue Fahrt an, wobei eine wöchentliche Wiederholung mit 5 Wiederholungen ausgewählt wird. 5 zukünftige Einträge werden erstellt.

Die Geschäftsführung möchte sich nun einen Überblick verschaffen wie viele Kilometer für Reparaturen gefahren wurden im letzten Jahr. Sie wählen den Zeitraum aus und können die Ergebnisse ablesen. Danach wollen sie die Daten mit schon geplanten Fahrten vergleichen und wählen einen zukünftigen Zeitraum aus, der dann die Kilometer der 5 zukünftigen Reparaturen anzeigt (es wurden keine anderen zukünftigen Fahrten geplant).
