<hr/>
<h1>Introduction</h1>
<p>Pour crée des item par base de donnée une classe existe
	<ul>
		<li><strong>Item.java</strong></li>
	</ul>
</p>
<h1>Comment l'utiliser</h1>
<h3>Chargement des items</h3>
```java
private static void getItem(){
	tem.clear();
	ResultSet results;
	try {
		results = Database.RUSH_VOLCANO.getDatabase().getData("item");
		while (results.next()) new Item(results);
	} catch (SQLException e){
		//
	} catch (SqlException e) {
		e.printStackTrace();
	}
}
```
<h3>Donner un item</h3>
<p>La classe ItemBuilder doit éxisté : cf Menu.md</p>
```java
ItemBuilder itemBuilder = Item.getItem("namme_item", lang); //Retourne un itemBuilder
ItemStack itemStack = itemBuilder.get();
playerInterract.getInventory().setItem(index, itemStack);
```

<h1>Base de donnée</h1>
<h3>Création de la table item</h3>
<p>La table langue doit être crée avant de créer cette table : cf document <strong>Lang.md</strong></p>
```sql
CREATE TABLE IF NOT EXISTS `item` (
  `i_name` varchar(40) NOT NULL,
  `i_label` varchar(50) NOT NULL,
  `i_id` smallint(5) unsigned DEFAULT NULL,
  `i_byte` tinyint(3) unsigned DEFAULT NULL,
  `i_skull_owner` varchar(50) DEFAULT NULL,
  `i_potion_type` varchar(20) DEFAULT NULL,
  `i_amount` int(11) unsigned NOT NULL DEFAULT '1',
  `i_lore` varchar(50) DEFAULT NULL,
  `i_enchant` text,
  `i_durability` smallint(5) unsigned DEFAULT NULL,
  `i_color` varchar(10) DEFAULT NULL,
  `i_hide_enchant` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Item factory';

ALTER TABLE `item`
 ADD PRIMARY KEY (`i_name`), ADD KEY `i_label` (`i_label`), ADD KEY `i_lore` (`i_lore`);

ALTER TABLE `item`
ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`i_label`) REFERENCES `lang` (`l_id`),
ADD CONSTRAINT `item_ibfk_2` FOREIGN KEY (`i_lore`) REFERENCES `lang` (`l_id`);
```

<h3>Insertion des données</h3>
<p>Pour le label et la description de l'item une clée de langue est requise
</br>
Pour la description des points virgule son néccéssaire pour faire un saut de ligne et un point virgule à la fin
</p>
```sql
INSERT INTO `item` (`i_name`, `i_label`, `i_id`, `i_byte`, `i_skull_owner`, `i_potion_type`, `i_amount`, `i_lore`, `i_enchant`, `i_durability`, `i_color`, `i_hide_enchant`) VALUES
('apple', 'Key lang', 322, NULL, NULL, NULL, 1, NULL, "key lang", NULL, NULL, 0),
```