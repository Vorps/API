<hr/>
<h1>Introduction</h1>
<p>Pour la gestion des informations des joueurs une classe est disponible :
	<ul>
		<li><strong>PlayerData.java</strong></li>
	</ul>
	<br/>
	Cette classe permet de récuperer ou update les informations relatif aux joueurs
	<br/>Les methodes getter de cette classe interoge la base de donnée, il faut stoquer le résultat dans le plugin afin de les appeler une seule fois. 
	<br/>
</p>
<h1>Comment l'utiliser</h1>
<p>
	Methode disponible
	<ul>
		<li><strong>getLang(UUID)</strong></li>
		<li><strong>setLang(UUID, String lang)</strong></li>
		<li><strong>getMoney(UUID, String money)</strong></li>
		<li><strong>addMoney(UUID, String money, double value)</strong></li>
		<li><strong>setMoney(UUID, String money, double value)</strong></li>
		<li><strong>removeMoney(UUID, String money, double value)</strong></li>
		<li><strong>getRank(UUID)</strong></li>
		<li><strong>setRank(UUID, String rank)</strong></li>
		<li><strong>setHub(UUID, String hub)</strong></li>
		<li><strong>lobby(UUID)</strong></li>
	</ul>

</p>
<h3>Exemple d'utilisation</h3>
```java
PlayerData.getLang(uuid); //Permet de récupérer la langue du joueur
PlayerData.setLang(uuid, lang); //Permet d'update la langue du joueur
```