<hr/>
<h1>Introduction</h1>
<p>
    Pour gérer les ScoreBoards une classe est disponible :  
    <br/>
	<ul>
		<li><strong>ScoreBoard.java</strong></li>
	</ul>
	<br/>
	Cette classe permet de crée des scorebords simplement
</p>
<h1>Comment l'utiliser</h1>
<h3>Exemple d'utilisation</h3>
```java
public class ScoreBoardExemple extends ScoreBoard {
  public ScoreBoardExemple(String lang){
        super(DisplaySlot.SIDEBAR, "titre", lang));
		super.add("id", "text", 1); //Ajout d'une ligne text avec 1 point
		super.updateValue("id", "test2"); //Update ligne text
		super.createTeam("red", "rouge"); //Crée nouvelle team
		super.addPlayerTeam("red", playerInterract); //Ajoute à la team red le joueur
		super.removePlayerTeam("red", playerInterract); //Retire à la team red le joueur
		super.removeTeam("red"); //Suprime la team red
		super.remove("id") //Suprime la ligne "text2"
	}
}
```