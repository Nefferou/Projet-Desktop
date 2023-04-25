# Email Manager
Ceci est un petit projet de gestionnaire d'email développé en Java. Il permet de récupérer les emails d'un serveur de messagerie en utilisant le protocole IMAP, de les afficher dans une interface graphique et de les stocker dans une base de données MySQL.

## Configuration requise
Java 8 ou supérieur
MySQL 5.7 ou supérieur

## Installation
Clonez le projet depuis GitHub :
bash
Copy code
git clone https://github.com/votre-nom/email-manager.git
Importez le projet dans votre IDE préféré (Eclipse, IntelliJ, NetBeans, etc.)

Configurez les informations de connexion à votre serveur de messagerie IMAP et à votre base de données MySQL dans la classe EmailManager.

Créez la table email dans votre base de données MySQL en exécutant le script SQL fourni dans le dossier sql.

Exécutez l'application.

## Utilisation
Récupérez les emails en cliquant sur le bouton "Rafraîchir".

Visualisez les emails dans la liste affichée à gauche de l'interface graphique. Les détails de chaque email s'affichent dans la zone de détails à droite lorsque vous cliquez sur un email dans la liste.

Les emails récupérés sont automatiquement stockés dans la base de données MySQL. Vous pouvez récupérer tous les emails stockés dans la base de données en cliquant sur le bouton "Afficher tous les emails".

## Auteur
Ce projet a été développé par [FERTILATI Julien.

Licence
Ce projet est sous licence MIT.
