<hr/>
<h1>Introduction</h1>
<p>Pour définir des effets de particule une classe est disponible
	<ul>
		<li><strong>Particle.java</strong></li>
	</ul>
</p>
<h1>Comment l'utiliser</h1>
<h3>Chargement des particules</h3>
```java
private static void getParticle(){
	Particle.clear();
	try {
		ResultSet results = Database.RUSH_VOLCANO.getDatabase().getData("particle");
		while (results.next()) new Particle(results);
	} catch (SQLException e){
		//
	} catch (SqlException e){
		e.printStackTrace();
	}
}
```
<h3>Utiliser les particules</h3>
```java
Particle particule = Particle.getParticle("name_particule");
particule.startParticle(players.getLocation(), players); //Démmare l'animation
particule.stopParticle(); //Stop l'animation
```

<h1>Base de donnée</h1>
<h3>Création des tables</h3>
<p>Création des tables <strong>particle</strong>, <strong>particle_model</strong>, <strong>enum_particle</strong></p>
```sql
CREATE TABLE IF NOT EXISTS `enum_particle` (
  `ep_name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `enum_particle`
 ADD PRIMARY KEY (`ep_name`);

CREATE TABLE IF NOT EXISTS `particle_model` (
  `pcm_name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `particle_model`
 ADD PRIMARY KEY (`pcm_name`);

CREATE TABLE IF NOT EXISTS `particle` (
  `pc_name` varchar(20) NOT NULL,
  `pc_particle_name` varchar(20) NOT NULL,
  `pc_model` varchar(20) NOT NULL,
  `pc_time` int(10) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `particle`
 ADD PRIMARY KEY (`pc_name`), ADD KEY `pc_model` (`pc_model`), ADD KEY `pc_particle_name` (`pc_particle_name`);

ALTER TABLE `particle`
ADD CONSTRAINT `particle_ibfk_1` FOREIGN KEY (`pc_model`) REFERENCES `NAME_DATABASE`.`particle_model` (`pcm_name`),
ADD CONSTRAINT `particle_ibfk_2` FOREIGN KEY (`pc_particle_name`) REFERENCES `NAME_DATABASE`.`enum_particle` (`ep_name`);
```

<h3>Insertion des données</h3>
```sql
INSERT INTO `enum_particle` (`ep_name`) VALUES
('BARRIER'),
('BLOCK_CRACK'),
('BLOCK_DUST'),
('CLOUD'),
('CRIT'),
('CRIT_MAGIC'),
('DAMAGE_INDICATOR'),
('DRAGON_BREATH'),
('DRIP_LAVA'),
('DRIP_WATER'),
('ENCHANTMENT_TABLE'),
('END_ROD'),
('EXPLOSION_HUGE'),
('EXPLOSION_LARGE'),
('EXPLOSION_NORMAL'),
('FIREWORKS_SPARK'),
('FLAME'),
('FOOTSTEP'),
('HEART'),
('ITEM_CRACK'),
('ITEM_TAKE'),
('LAVA'),
('MOB_APPEARANCE'),
('NOTE'),
('PORTAL'),
('REDSTONE'),
('SLIME'),
('SMOKE_LARGE'),
('SMOKE_NORMAL'),
('SNOWBALL'),
('SNOW_SHOVEL'),
('SPELL'),
('SPELL_INSTANT'),
('SPELL_MOB'),
('SPELL_MOB_AMBIENT'),
('SPELL_WITCH'),
('SUSPENDED'),
('SUSPENDED_DEPTH'),
('SWEEP_ATTACK'),
('TOWN_AURA'),
('VILLAGER_ANGRY'),
('VILLAGER_HAPPY'),
('WATER_BUBBLE'),
('WATER_DROP'),
('WATER_SPLASH'),
('WATER_WAKE');

INSERT INTO `particle_model` (`pcm_name`) VALUES
('ModelHelix');

INSERT INTO `particle` (`pc_name`, `pc_particle_name`, `pc_model`, `pc_time`) VALUES
('name_particule', 'CRIT', 'ModelHelix', 1);
```