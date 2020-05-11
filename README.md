# restPA

La partea de Compulsory nu a fost facuta testarea Postman, deoarece din nu stiu
ce motiv nu pot accesa serverul localhost:8080. In rest celelalte 3 puncte au
fost abordate.

La Optional am facut primele 3 buline.

Sunt mai multe clase si pachete decat ar fi trebuit cele mai importante fiind pachetele
-jav.rest.services
-jav.server.callservices
-jav.rest.control
si clasele lor, restul ar fi sa zicem clase ajutatoare.

URL-urile http://localhost:8080/games
          http://localhost:8080/players
sunt adresele pentru vizualizarea/inserarea/stergerea datelor. 

Baza de date contine 3 tabele:
-players care contine are id si name.
-games care are id, content,result si date.
-players_games care are id game_id si player_id.
Cheia primara a fiecaruia dintre tabele este id.

Clasele din pachetele: src/main/java/jav/rest/baza
                       src/main/java/jav/rest/control
                       src/main/java/jav/rest/repositories
                       src/main/java/jav/rest/services
                       src/main/java/jav/rest/trans
au rolul de a efectua operatii CRUD pe jucatori(players) si de a crea servicii
REST de inserare si citire a jocurilor.

In src/main/java/jav/server/callservices/GameCallService.java
si src/main/java/jav/server/callservices/PlayerCallService.java 
am integrat serviciile de la laboratorul 10.

In src/main/java/jav/rest/exceptions sunt gestionate exceptiile.

In pom.xml au fost introduse dependentele necesare.
