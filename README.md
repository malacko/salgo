# salgo

Klónozd a két repositoryt ugyanabba könyvtárba:
https://github.com/malacko/salgo.git
https://github.com/malacko/persongen.git

az application.properties fileban az adatbázist állítsd be a saját környezetedre.

Ha van CLI maven installálva, akkor
nyiss két command ablakot a két projektnek (ahol a pom.xml van)
majd indíts el egy-egy 
mvn install
parancsot
a salgo projekt resource könyvtárában van egy postman collection és environment, ezt importáld be a postman alkalmazásba. És válaszd ki az importált environmentet.
a salgo projekt gyökér könyvtárából add ki az
mvn spring-boot:run
parancsot
Az application.properties file úgy van beállítva, hogy az alkalmazás minden egyes indításkor újra generálja az adatbázist.

ha manuális teszteket akarsz végrehajtani, akkor a postman-ből a
GET resourcepath 
requestet ki kell adni, mert ez bekéri a resource könyvtár elérhetőségét egy postman változóba.

a következő parancsokat adhatod ki 

POST when createPersonFemale
POST when createPersonMale
ez a két utasítás létrehoz egy-egy személyt a csatolt bináris fájljaival

POST when createPersonBad
Ez rosszul formázott POST bodyt tartalmaz így hibára fut. TODO: az alkalmazásban nem kezeltem le ezeket a validálási hibákat.

a
PUT when updatePerson
DELETE person

requesteknél
meglévő adatbázis id ra kell hivatkozni.

a
GET people with page az első 10 person objektumot lekéri. A request parameterek módosíthatók. Ezt a persongen alkalmazás futtatása után érdemes elindítani.
GET cleanup  
lefuttatja a cronjobot manuálisan

A persongen alkalmazást is
mvn spring-boot:run
paranccsal kell indítani. Ehhez a salgo alkalmazásnak futnia kell.
Ez feltölt 100 person objektumat az adatbázisba.

login page tesztelése.

Indítsd újra a salgo alkalmazást. Nyiss egy új böngészőt, mert a sessionben eltárolhatja a user password párost.
a böngészőbe (Firefox nálam szépen formázta) írd be a következő urlt:
http://localhost:8088/api/people

Ez login page-t fog előhívni.

username: user
password: titkos

TODOs:
jelszókezelés adatbázisba kell, hogy átkerüljön. Regiszráció nem megoldott.
JSON validálási hibák, hibaüzeneteinek visszaküldése.

