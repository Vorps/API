<hr/>
<h1>Introduction</h1>
<p>Pour créer facilement des messages title avec la base de donnée une classe est disponible
	<ul>
		<li><strong>MessageTitle.java</strong></li>
	</ul>
</p>
<h1>Comment l'utiliser</h1>
<h3>Chargement des messages title</h3>
```java
private static void getMessageTitle(){
	MessageTitle.clear();
	try {
		ResultSet results = Database.RUSH_VOLCANO.getDatabase().getData("message_title");
		while (results.next()) new MessageTitle(results);
	} catch (SQLException e){
		//
	} catch (SqlException e) {
		e.printStackTrace();
	}
}
```
<h3>Utiliser les messages title</h3>
```java
MessageTitle messageTitle = MessageTitle.getMessageTitle("name_message_title")
TitleBuilder titleBuilder = new TitleBuilder(messageTitle.getTitle(lang), messageTitle.getSubTitle(lang));
titleBuilder.senTo(player);
```

<h1>Base de donnée</h1>
<h3>Création des tables</h3>
<p>Création de la table lang est nécéssaire : cf Lang.md</p>
```sql
CREATE TABLE IF NOT EXISTS `message_title` (
  `mt_name` varchar(30) NOT NULL,
  `mt_title` varchar(50) NOT NULL,
  `mt_sub_title` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `message_title`
 ADD PRIMARY KEY (`mt_name`), ADD KEY `mt_title` (`mt_title`), ADD KEY `mt_sub_title` (`mt_sub_title`);

ALTER TABLE `message_title`
ADD CONSTRAINT `message_title_ibfk_1` FOREIGN KEY (`mt_title`) REFERENCES `lang` (`l_id`),
ADD CONSTRAINT `message_title_ibfk_2` FOREIGN KEY (`mt_sub_title`) REFERENCES `lang` (`l_id`);
```

<h3>Insertion des données</h3>
<p>Pour le title et le subtitle une clée de langue est nécessaire</p>
```sql
INSERT INTO `message_title` (`mt_name`, `mt_title`, `mt_sub_title`) VALUES
('name_message_title', 'key_lang', 'key_lang');
```