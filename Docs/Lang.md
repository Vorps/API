<hr/>
<h1>Introduction</h1>
<p>Pour gérer les langues deux classe sont disponible :
	<ul>
		<li><strong>Lang.java</strong></li>
		<li><strong>LangSetting.java</strong></li>
	</ul>
	<br/>
	Ces classes permetent de charger et d'afficher les bon messages en fonction de la langue du joueur
</p>
<h1>Comment les utiliser</h1>
<h3>Charger les messages du plugin</h3>
```java
public static void getLang(){ 
    Lang.clearLang();
    LangSetting.clearLangSetting();
    ResultSet results;
    try {
        results = Database.SERVER.getDatabase().getDataTmp("lang_setting");
        while(results.next()) new LangSetting(results);
        results = Database.RUSH_VOLCANO.getDatabase().getDataTmp("lang");
        while(results.next()) new Lang(results);
    } catch (SQLException e){
        //
    } catch (SqlException e) {
            e.printStackTrace();
    }
}
```

<h3>Exemple d'utilisation</h3>
```java
Lang.getMessage(Key, langPlayer, new Lang.Args(Lang.Parameter.TYPE, value));
```

<h1>Base de donnée</h1>
<h3>Création de la table lang_setting</h3>
<p>Cette table stoque les langues suporter</p>
```sql
CREATE TABLE IF NOT EXISTS `lang_setting` (
  `ls_name` varchar(20) NOT NULL,
  `ls_column_id` varchar(4) NOT NULL,
  `ls_name_display` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lang_setting`
 ADD PRIMARY KEY (`ls_name`);
```
<h3>Ajout d'une langue</h3>
<p>Exemple d'ajout de la langue anglaise</p>
```sql
INSERT INTO `NAME_DATABASE`.`lang_setting` (`ls_name`, `ls_column_id`, `ls_name_display`) VALUES ('en', 'l_en', 'english');
```
<h3>Crée la table pour les langues</h3>
<p>Cette table contient les messages du plugin en fonction des langues</p>
```sql
CREATE TABLE IF NOT EXISTS `lang` (
  `l_id` varchar(50) NOT NULL,
  `l_en` text NOT NULL /*correspondance avec ls_column_id de lang_setting*/
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lang`
 ADD PRIMARY KEY (`l_id`);
```
<h3>Insertion des messages</h3>
```sql
INSERT INTO `NAME_DATABASE`.`lang` (`l_id`, `l_en`) VALUES ('key', 'message <TYPE>'); /*<TYPE> est un argument du message*/
```