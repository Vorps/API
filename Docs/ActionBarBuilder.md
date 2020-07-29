<hr/>
<h1>Introduction</h1>
<p>Pour afficher des messages dans la bare d'action d'un joueur une classe existe
	<ul>
		<li><strong>ActionBarBuilder.java</strong></li>
	</ul>
</p>
<h1>Créer un message d'action</h1>
```java
ActionBarBuilder actionBarBuilder = new ActionBarBuilder("exemple1"); //Création du message
actionBarBuilder.withMessage("exemple2"); //Redéfinition du message
actionBarBuilder.withStay(10); //Temps d'affichage du message
actionBarBuilder.sendTo(playerInterract); //Affichage du message dans la barre d'action du joueur
```