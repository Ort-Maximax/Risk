# Risk

Enoncé v2 : https://pastebin.com/NrahnNDv

Trello : https://trello.com/b/SkHNPCbR/java-risk

-------------------------------------------------------------

## Versions
```xml
<!-- Parent -->
<commons-text.version>1.2</commons-text.version>
<log4j2.version>2.11.0</log4j2.version>

<!-- risk-ws -->
<jdk.version>1.8</jdk.version>
<spring.version>5.0.0.RELEASE</spring.version>
<servlet.api.version>3.1.0</servlet.api.version>
<jackson.version>2.9.5</jackson.version>
<mongodb.version>2.0.8.RELEASE</mongodb.version>
<commons-io.version>2.6</commons-io.version>
```
## Resumé 

### Modes d'exécution
#### UI
L'application comporte plusieurs mode d'execution :
 - -c : mode console -> les affichages et les choix des joueurs se feront via la console
 - -g : mode graphique -> les affichages et les choix des joueurs se feront via une interface graphique (client lourd)
 - -r : mode random -> La plupart des choix sont aléatoire jusqu'a la fin de la partie
        Certains choix ne sont pas aléatoire dans le but d'écourté la partie. Neanmoins, aucune partie n'a jamais été fini...
 
Les deux modes principaux (console et GUI) ont un launcher propre, qui s'execute quand leur parametre est passé au lancement de l'application.

Chaque mode gère les itérations de son coté, mais utilisent des classes et fonctions communes, afin que le fonctionnement du jeu reste le même quel que soit le mode.

#### WebApp
L'application peut aussi être déployé dans un environnement web (Tomcat 9 etait le serveur http utilisé).
Route principale risk-ws/api/*.

### Livrable
Code source de l'application.
Un fichier de configuration à paramétrer (répertoire de ressources --> config.properties) :
 * Le répertoire de sauvegarde des cartes --> savedMapPath
 * Le répertoire de la carte qui sera utilisée pour les parties --> currentMapPath

-------------------------------------------------------------

## Avancée

### Mode console

Version du 08/02/2018 : 
Toutes les fonctionnalités demandées par le cahier des charges sont implémentées, à l'exception de l'action 'Bombardment'.
Nous avons envisagé de faire une IA, permettant a un joueur d'etre controller par l'ordinateur, mais cela n'a pas été fait.
Le mode random à été longuement testé, et à prouvé être parfois instable. 

### Mode graphique

Version du 08/02/2018 : 
Fonctionnalitées présentes :
 - Importation de la map (avec sauvegarde)
 - Choix du mode de jeu + inscription des joueurs
 - Affichage de la map avec toutes ces régions
 - Attribution des régions

Je rencontre des difficultés dans le workflow du jeu avec l'interface graphique et la gestion des composants dans une seule frame.

### Web App

Version du 29/06/2018 :
 - Déployée dans un .war
 - Les données de la WebApp sont stockées dans une bdd NoSQL (MongoDB). Nous n'avons pas eu le temps d'en déployer une sur un serveur (localhost uniquement).
 - Fonctionnalitées présentes :
  - Paramétrage de la servlet
  - Repository des maps et players (Non fonctionnels)
  - Controlleurs (players & maps) (routes fonctionnelles)
  - Logger
  - Pas de tests unitaires

Le traitements des données n'est pas fonctionnel.
Le logger ne fonctionne qu'en console (les fichiers de logs ne sont pas créés). NB : ce problème ne survient qu'en environnement web (sur une application classique java, les fichiers de logs sont créés).
Pour accéder aux ressources je suis obligé d'indiquer un chemin absolu.
