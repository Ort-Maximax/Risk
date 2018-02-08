# Risk

Java SDK 9

Enoncé v2 : https://pastebin.com/NrahnNDv

Trello : https://trello.com/b/SkHNPCbR/java-risk

-------------------------------------------------------------

## Resumé 

L'application comporte plusieurs mode d'execution :
 - -c : mode console -> les affichages et les choix des joueurs se feront via la console
 - -g : mode graphique -> les affichages et les choix des joueurs se feront via une interface graphique
 - -r : mode random -> La plupart des choix sont aléatoire jusqu'a la fin de la partie
        Certains choix ne sont pas aléatoire dans le but d'écourté la partie. Neanmoins, aucune partie n'a jamais été fini...
 
Les deux modes principaux (console et GUI) ont un launcher propre, qui s'execute quand leur parametre est passé au lancement de l'application.

Chaque mode gère les itérations de son coté, mais utilisent des classes et fonctions communes, afin que le fonctionnement du jeu reste le même quel que soit le mode.

### Livrable
Code source de l'application à compiler par vos soin.
Un fichier de configuration à paramétrer (config.properties) :
 * Le répertoire de sauvegarde des cartes --> savedMapPath
 * Le répertoire de la carte qui sera utilisée pour les parties --> currentMapPath

-------------------------------------------------------------

## Avancée

### Mode console : Clément Simonin

Version du 08/02/2018 : 
Toutes les fonctionnalités demandées par le cahier des charges sont implémentées, à l'exception de l'action 'Bombardment'.
Nous avons envisagé de faire une IA, permettant a un joueur d'etre controller par l'ordinateur, mais cela n'a pas été fait.
Le mode random à été longuement testé, et à prouvé être parfois instable. 

### Mode graphique : Thibaut Moiroud

Version du 08/02/2018 : 
Fonctionnalitées présentes :
 - Importation de la map (avec sauvegarde)
 - Choix du mode de jeu + inscription des joueurs
 - Affichage de la map avec toutes ces régions
 - Attribution des régions

Je rencontre des difficultés dans le workflow du jeu avec l'interface graphique et la gestion des composants dans une seule frame.


-------------------------------------------------------------


## Dependances

 - JavaFX 3.0.0
 - Gestionnaire de dépendances Maven :
   - org.apache Commons text 1.2
