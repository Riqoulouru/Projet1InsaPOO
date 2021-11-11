# Projet1InsaPOO
Git pour le projet de POO


Pour ce projet nous avons décidé d'utiliser Gradle à la place de Maven puisque nous avions déjà certaines bases avec ce dernier.

La classe permettant le lancement de l'application est src/main/java/com/exempleProjet1InsaPOO/Projet1InsaPooApplication .

Nous avons utilisé Spring pour la réalisation d'une interface web puisque que nous avons déjà travaillé avec ce framework. Ainsi notre projet est une application web.

Evidement pour garder un maximum de cohérence avec le projet de base tout est fait en langage Java comme demandé, à l'exception de :
  - Envoie et réception des données (requête AJAX pour pouvoir ensuite traiter dans le back)
  - Affichage des données (traitement pour pouvoir afficher correctement les données)
  
Toute cette partie front a été réalisée avec JQuery.


# Informations complémentaires

  - La classe Main n'est pas utilisée, elle correspondait au mode console avant l'interface graphique
  - Il y a toujours un affichage plus précis de la cuisine dans la console


# Attention : Si dans le dossier "Save" il n'y a pas les fichiers suivant : 
  ![image](https://user-images.githubusercontent.com/17967215/141336990-be169ee9-7954-480f-ac72-198051c4b755.png)
  
  alors il faudra executer la fonction initiliazeAllElements() contenue dans la classe Projet1InsaPooApplication
  Cette fonction est déjà en commentaire dans le code, il suffit donc de la décommenté avant de lancer le programme.
  
  Si même les dossiers ne se sont pas créers, alors il faudra créer les dossiers à la main.
  
