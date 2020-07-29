<hr/>
<h1>Introduction</h1>
<p>Il est utile de communiquer des données entre bungee et spigot des classes sont disponible
	<ul>
		<li><strong>Action.java</strong></li>
		<li><strong>ChanelManager.java</strong></li>
	</ul>
</p>
<h1>Utilisation</h1>
<p>Pour communiquer des données le spigot et le bungee doivent utiliser le même channel et subchannel</p>
```java
Action action = new Action(){
    @Override
    public void receive(Player playerInterract, DataInputStream message){
        String argument_1 = in.readUTF();
        String argument_2 = in.readUTF();
        //Action de reception
    }
    
    @Override
    public String getName() {
        return "SubChannel";
    }
}
ChanelManager.Channel.BUNGEE.addAction(action); //Ajout de l'action au channel
ChanelManager.send(ChanelManager.Channel.BUNGEE, action, "argument_1", "argument_2"); //Send des données en direction du bungee
ChanelManager.Channel.BUNGEE.removeAction(action); //Retire l'action du channel
```