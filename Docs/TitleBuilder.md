<hr/>
<h1>Introduction</h1>
<p>Pour crée des messages title une classe est disponible
	<ul>
		<li><strong>TitleBuilder.java</strong></li>
	</ul>
	</br>
	Cette classe permet de créer deux ligne de message
</p>
<h1>Personnaliser la tabList</h1>
```java
TitleBuilder titleBuilder = new TitleBuilder("title1", "subTitle1");
titleBuilder.withTitle("title2"); //Redéfinition du title
titleBuilder.withSubTitle("subTitle2"); //Redéfinition du subTitle
titleBuilder.withFadeIn(1); //Temps de transition au début (fondu)
titleBuilder.withFadeOut(1); //Temps de transition a la fin (fondu)
titleBuilder.withStay(1); //temps d'affichage du message
titleBuilder.sendTo(playerInterract); //Affichage du title au playerInterract
```