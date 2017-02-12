<hr/>
<h1>Introduction</h1>
<p>Pour crée des feux d'artifices une classe est disponible
	<ul>
		<li><strong>Firework.java</strong></li>
	</ul>
	</br>
	Cette classe permet de crée des sessions de feux d'artifices simplement et personalisable
</p>
<h1>Comment l'utiliser</h1>
<h3>Chargement des paramètres des feux d'artifices</h3>
```java
private static void getFireWork(){
	Firework.clear();
	try {
		ResultSet results = Database.RUSH_VOLCANO.getDatabase().getData("firework");
		while (results.next()) {
			new Firework(results);
		}
	} catch (SQLException e){
		//
	} catch (SqlException e){
		e.printStackTrace();
	}
}
```
<h3>Lancée le spectacle</h3>
```java
Firework firework = Firework.getFirework("nameFirework");
firework.start(plugin); //Démarrer
firework.stop(); //Stoper 
```

<h1>Base de donnée</h1>
<h3>Création de la table firework</h3>
<p>La table location doit être crée avant de créer cette table : cf document <strong>Location.md</strong></p>
```sql
CREATE TABLE IF NOT EXISTS `firework` (
  `f_name` varchar(20) NOT NULL, 
  `f_type` text NOT NULL, 
  `f_main_color` text NOT NULL,
  `f_fade_color` text NOT NULL,
  `f_speed` int(11) unsigned NOT NULL,
  `f_time` int(10) unsigned NOT NULL,
  `f_random` tinyint(1) NOT NULL,
  `f_location` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `firework`
 ADD PRIMARY KEY (`f_name`), ADD KEY `f_location` (`f_location`);

ALTER TABLE `firework`
ADD CONSTRAINT `firework_ibfk_1` FOREIGN KEY (`f_location`) REFERENCES `location` (`loc_name`);
```

<h3>Insertion des données</h3>
<p>Pour la location du feux d'artifice, une clée de location est requise</p>
```sql
INSERT INTO `firework` (`f_name`, `f_type`, `f_main_color`, `f_fade_color`, `f_speed`, `f_time`, `f_random`, `f_location`) VALUES
('firework', 'ball;star;creeper;', 'blue;green;yellow;', 'red;orange;', 1, 10, 0, 'key_location');
```