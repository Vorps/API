<hr/>
<h1>Introduction</h1>
<p>Pour personnaliser la tablist d'un joueur une classe existe
	<ul>
		<li><strong>TablistBuilder.java</strong></li>
	</ul>
	</br>
	Cette classe peut ajouter un header et un footer à la tablist d'un joueur
</p>
<h1>Personnaliser la tabList</h1>
```java
TablistBuilder tablistBuilder = new TablistBuilder("header1", "footer1");
tablistBuilder.withHeader("header2"); //Redéfinition du header
tablistBuilder.withFooter("footer2"); //Redéfinition du footer
tablistBuilder.sendTo(playerInterract) //Ajoute un header et un footer à la tablist du joueur
```