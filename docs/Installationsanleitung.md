# Installation

1. Repository mit gewünschter Integrierter Entwicklungsumgebung (IDE) verbinden
2. "Main" Klasse 'FahrtenbuchApp' ausführen
3. Im lokalen Browser 'localhost:8080/index.xhtml' ausführen

# Installation / Einrichtung via Docker

Um das laufen der Applikation in Docker zu ermöglichen, muss zuerst ein Container erzeugt werden.
Dies kann z.B. erreicht werden, indem man mittels PowerShell in den lokalen Projektordner hineinnavigiert und dann den folgenden Befehl ausführt:

`docker build --tag={NAME}:{VERSION} .`

Wobei {NAME} ein zufälliger Name sein kann und {VERSION} ebenso eine zufällige Version sein kann.
Für diesen Test schlagen wir jedoch als Name <b>Fahrtenbuch</b> und Version <b>1.0.0</b> vor.

Nachdem nun Docker das Dockerfile des Projektes lädt und die ganzen notwendigen Daten Kopiert bzw. die Konfiguration des Containers, Images und Builds erledigt muss der Container nurnoch gestartet werden.
Dies funktioniert mit folgendem Befehl:

`docker run -p8080:8080 {NAME}:{VERSION}`

Hierbei wird der Container gestartet und der Port 8080 des Containers (auf welchem die Webapplikation erreichbar ist) an den Host-Port 8080 gebunden. Das heißt, Anfragen (TCP)  werden vom Host an den Container "weitergeleitet".

Danach ist die Webapplikation jederzeit unter "localhost:8080/index.xhtml" erreichbar. Dies kann natürlich Netzwerktechnisch erweitert werden, indem man dem Container eine IP-Adresse zuweist und den Port auch im Netzwerk exposed. Dann würde der Container von jedem Gerät im Netzwerk erreicht werden können. In weiterer Folge kann dann natürlich auch die IP-Adresse via internem DNS-Service oder Mapping durch eine URL ersetzt werden.
