<hr/>
<h1>Introduction</h1>
<p>Pour définir des limites de déplacment des joueurs une classe est diponible
	<ul>
		<li><strong>Limite.java</strong></li>
	</ul>
</p>
<h1>Comment l'utiliser</h1>
<h3>Chargement des limites</h3>
```java
private static void getLimite(){
	Limite.clear();
	ResultSet results;
	try {
		results = Database.RUSH_VOLCANO.getDatabase().getData("limite");
		while (results.next()) new Limite(results);
	} catch (SQLException e){
		//
	} catch (SqlException e) {
		e.printStackTrace();
	}
}
```
<h3>Affecter ces limites</h3>
```java
@EventHandler
public void onPlayerMove(PlayerMoveEvent e){
	Double limite[] = Limite.getLimite("name_limite"); retroune un tableau de double
	Limite.limite(playerInterract.getLocation(), limite); //Est vrai quand le joueur est en dehort des limites
}
```

<h1>Base de donnée</h1>
<h3>Création de la table limite</h3>
```sql
CREATE TABLE IF NOT EXISTS `limite` (
  `lim_name` varchar(20) NOT NULL,
  `lim_x+` double NOT NULL,
  `lim_x-` double NOT NULL,
  `lim_y+` double NOT NULL,
  `lim_y-` double NOT NULL,
  `lim_z+` double NOT NULL,
  `lim_z-` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Limite factory';

ALTER TABLE `limite`
 ADD PRIMARY KEY (`lim_name`);
```

<h3>Insertion des données</h3>
```sql
INSERT INTO `limite` (`lim_name`, `lim_x+`, `lim_x-`, `lim_y+`, `lim_y-`, `lim_z+`, `lim_z-`) VALUES
('name_limite', 50, -250, 250, -500, 200, -100);
```