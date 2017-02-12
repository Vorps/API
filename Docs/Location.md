<hr/>
<h1>Introduction</h1>
<p>Pour définir des locations une classe éxiste
	<ul>
		<li><strong>Location.java</strong></li>
	</ul>
</p>
<h1>Comment l'utiliser</h1>
<h3>Chargement des locations</h3>
```java
private static void getLocation(){
	Location.clear();
	ResultSet results;
	try {
		results = Database.RUSH_VOLCANO.getDatabase().getData("location");
		while (results.next()) new Location(results);
	} catch (SQLException e){
		//
	} catch (SqlException e) {
		e.printStackTrace();
	}
}
```
<h3>Utiliser la location</h3>
```java
Location Location = Location.getLocation("name_location"); //Retourne une location bukkit
player.teleport(location); //Téléporte le joueur à la position
```

<h1>Base de donnée</h1>
<h3>Création de la table limite</h3>
```sql
CREATE TABLE IF NOT EXISTS `location` (
  `loc_name` varchar(40) NOT NULL,
  `loc_x` double NOT NULL,
  `loc_y` double NOT NULL,
  `loc_z` double NOT NULL,
  `loc_yaw` float NOT NULL,
  `loc_pitch` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Location factory';

ALTER TABLE `location`
 ADD PRIMARY KEY (`loc_name`);
```

<h3>Insertion des données</h3>
```sql
INSERT INTO `location` (`loc_name`, `loc_x`, `loc_y`, `loc_z`, `loc_yaw`, `loc_pitch`) VALUES
('name_location', 25.5, 130, 96.5, 90, 0);
```