<hr/>
<h1>Introduction</h1>
<p>Pour crée des livres d'aide une classe est disponible
	<ul>
		<li><strong>BookHelp.java</strong></li>
	</ul>
	</br>
	Cette classe permet de crée des livres d'aide en fonction de la lange du joueur
</p>
<h1>Comment l'utiliser</h1>
<h3>Chargement des livres d'aide</h3>
```java
private static void getBookHelp(){
	BookHelp.clear();
	try{
		ResultSet results = Database.RUSH_VOLCANO.getDatabase().getData("book");
		while(results.next()) new BookHelp(results, false);
	} catch(SQLException e){
		//
	} catch (SqlException e) {
		e.printStackTrace();
	}
}
```
<h3>Donnée le livre d'aide</h3>
```java
BookHelp bookHelp = BookHelp.getBookList().get(nameBook); //Récupération du livre d'aide avec le titre du livre
ItemStack book = bookHelp.getBook(lang);
player.getInventory().setItem(index, book);
```

<h1>Base de donnée</h1>
<h3>Création des tables</h3>
<p>La table lang doit être créer avant de créer ces tables : cf document <strong>Lang.md</strong></p>
```sql
CREATE TABLE IF NOT EXISTS `book` (
  `book_name` varchar(10) CHARACTER SET utf8 NOT NULL,
  `book_author` varchar(16) CHARACTER SET utf8 NOT NULL,
  `book_label` varchar(50) CHARACTER SET utf8 NOT NULL, /*Titre du livre*/
  `book_level` tinyint(4) unsigned NOT NULL /*Trie des livre*/
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `book`
 ADD PRIMARY KEY (`book_name`), ADD KEY `book_label` (`book_label`);
 
 ALTER TABLE `book`
ADD CONSTRAINT `book_ibfk_1` FOREIGN KEY (`book_label`) REFERENCES `lang` (`l_id`);

 CREATE TABLE IF NOT EXISTS `book_setting` (
  `bs_book` varchar(10) NOT NULL,
  `bs_page` tinyint(3) unsigned NOT NULL,
  `bs_value` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `book_setting`
 ADD PRIMARY KEY (`bs_book`,`bs_page`), ADD KEY `bs_value` (`bs_value`);
 
ALTER TABLE `book_setting`
 ADD CONSTRAINT `book_setting_ibfk_1` FOREIGN KEY (`bs_book`) REFERENCES `book` (`book_name`),
 ADD CONSTRAINT `book_setting_ibfk_2` FOREIGN KEY (`bs_value`) REFERENCES `lang` (`l_id`);
```

<h3>Insertion des données</h3>
<p>Des clées de langue sont nécessaire à l'insertion des données</p>
```sql
INSERT INTO `NAME_DATABASE`.`book` (`book_name`, `book_author`, `book_label`, `book_level`) VALUES ('help', 'Vorps', 'key_lang', '1');
INSERT INTO `NAME_DATABASE`.`book_setting` (`bs_book`, `bs_page`, `bs_value`) VALUES ('help', '1', 'key_lang');
```