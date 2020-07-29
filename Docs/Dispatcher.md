<hr/>
<h1>Introduction</h1>
<p>Pour la gestion des serveur une classe est disponible :
	<ul>
		<li><strong>Dispacher.java</strong></li>
	</ul>
	<br/>
	Cette classe permet de démarrer des serveurs en fonction de la demande et de connecter le joueur au serveur qu'il souhaite 
	<br/>
</p>
<h1>Comment l'utiliser</h1>
<h3>Exemple d'utilisation</h3>

```java
Dispatcher.connectServer(Type.TYPE, playerInterract, play, lang, plugin); 
//Permet d'envoyer le joueur sur le server du type indiquer
Dispatcher.connectServer(nameServer, playerInterract, lang, plugin);
//Permet d'envoyer le joueur sur le server avec le nom indiquer
Dispatcher.connectServer(Type.TYPE, map, playerInterract, play, lang, plugin);
//Permet d'envoyer le joueur sur le server du type indiquer avec la map indiquer
```