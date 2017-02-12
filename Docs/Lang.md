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
        results = Database.<NAME>.getDatabase().getData("lang");
        while(results.next()) new Lang(results);
    } catch (SQLException e){
        //
    } catch (SqlException e) {
            e.printStackTrace();
    }
}
```
<h1>Type de paramètre</h1>
<p>
    Les parametres servent à inséré des messages dans les messages de la base de donnée 
</p>
<ul>
	<li><strong>"state"</strong></li>
    <li><strong>"time"</strong></li>
    <li><strong>"team"</strong></li>
    <li><strong>"point"</strong></li>
    <li><strong>"nbr_player"</strong></li>
    <li><strong>"golds"</strong></li>
    <li><strong>"kill"</strong></li>
    <li><strong>"dead"</strong></li>
    <li><strong>"wool"</strong></li>
    <li><strong>"var"</strong></li>
    <li><strong>"player"</strong></li>
    <li><strong>"mode"</strong></li>
    <li><strong>"server"</strong></li>
    <li><strong>"message"</strong></li>
    <li><strong>"killer"</strong></li>
    <li><strong>"nbr_max_player"</strong></li>
    <li><strong>"color"</strong></li>
    <li><strong>"winner"</strong></li>
    <li><strong>"price"</strong></li>
    <li><strong>"device"</strong></li>
    <li><strong>"looser"</strong></li>
    <li><strong>"kit"</strong></li>
    <li><strong>"page"</strong></li>
    <li><strong>"life"</strong></li>
    <li><strong>"bonus"</strong></li>
    <li><strong>"killed"</strong></li>
    <li><strong>"spectator"</strong></li>
    <li><strong>"speed"</strong></li>
</ul>
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
 ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
 ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `lang`
 ADD PRIMARY KEY (`l_id`);
```
<h3>Insertion des messages</h3>
```sql
INSERT INTO `NAME_DATABASE`.`lang` (`l_id`, `l_en`) VALUES ('key', 'message <TYPE>'); /*<TYPE> est un argument du message*/
```