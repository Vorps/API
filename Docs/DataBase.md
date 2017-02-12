<hr/>
<h1>Introduction</h1>
<p>Pour interagir avec les bases de données un ensemble de classes est disponible :
	<ul>
		<li><strong>Database.java</strong></li>
		<li><strong>DatabaseManager.java</strong></li>
		<li><strong>Encryptor.java</strong></li>
		<li><strong>File.java</strong></li>
	</ul>
	<br/>
	Ces classes permetent de créer des connections et de controler de manière simple et rapide les bases de données de manière sécurisé
</p>
<h1>Comment les utiliser</h1>

<h3>Acces à la base de donnée</h3>
```java
Database.NAME.getDatabase() //Retourne un objet DatabaseManager
```

<h3>Exemple d'utilisation</h3>
```java
Database.NAME.getDatabase().insertTable(nameTable, list des valeur dans l'ordre) //Insertion dans la table les objects
Database.NAME.getDatabase().delete(nameTable)                                    //Suprimme tous les n-uplet de la table
Database.NAME.getDatabase().getInt(result>, index)                               //retourne la donnée du result à la position index
```
<h3>Garder la connection</h3>
<p>Il arrive parfois que la connection drop due a un timeout.
<br/>Pour éviter ce problème il faut tester si la connection est activé ou pas si non l'activer.
<br/>Il faut faire ce test à chaque fois qu'un joueur ce connecte</p>

```java
@EventHandler
public void onPlayerJoin(PlayerJoinEvent e){
    Database.NAME.tryConnectionDatabase();
}
```