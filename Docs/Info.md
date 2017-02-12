<hr/>
<h1>Introduction</h1>
<p>Pour gérer les états des serveurs une classe est disponible :
	<ul>
		<li><strong>Info.java</strong></li>
	</ul>
	<br/>
	Cette classe permet de sérialiser un fichier qui contient l'état du serveur
	<br/>
	Le fichier ce trouve dans le repertoire du server /game_parameter/parameter.ser
</p>
<h1>Comment l'utiliser</h1>
<h3>Exemple d'utilisation</h3>
```java
@Override
public void onEnable() {
	* @param canPlay boolean Indique si le server peut être rejoint en tant que joueur
	* @param canSpectator boolean Indique si le server peut être rejoint en tant que spectateur
	* @param map String Indique la map du serveur
	* @param online boolean Indique si le server est allumé
	Info.setInfo(true, false, map, true);
}
```