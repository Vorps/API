<hr/>
<h1>Introduction</h1>
<p>Pour créer une commande bukkit la classe <strong>commands.java</strong> est disponible. 
<br>Elle permet de gérer simplement les commandes avec ces permissions.
<br>Inserer les classe unique <strong>CommandManager.java</strong>, <strong>CommandsAutoCompletion.java</strong> et l'enum <strong>Commands.java</strong>
<br> dans votre projet et ajouter des classe pour les différentes commandes qui étend <strong>Commands.java</strong> de l'API</p>
<h1>Créer une commande</h1>
<p>Création d'une commande exemple :</p>
```java
public class CommandExemple extends Commands {
    
	/**
	 * Constructeur
	 */
	public CommandExemple(CommandSender sender, String[] args){
		super(sender, Commands.EXEMPLE.getPermissions());
		if(args[0].equalsIgnoreCase("test")){
			super.setStateExec(true); //Condition requise pour que la commande aboutisse
			//Code de la commande	
		}
		super.onDisable(); //Fin de la commande
	}

	/**
	 * Affiche l'aide quand la commande n'a pas aboutie et que le joueur à la permission	
	 */
	@Override
	protected void help(){
		super.getSender().sendMessage("HELP");
		//Code de l'aide
	}
}
```

<h1>Commands.java</h1>
```java
public enum Commands{
	
	EXEMPLE("exemple", "server.exemple");
    
	private @Getter String command;
	private @Getter String permissions;
    
	Commands(String command, String permissions){
		this.command = command;
		this.permissions = permissions;
	}
}
```
<h1>CommandManager.java</h1>
```java
public class CommandManager {

    public CommandManager(){
        for(Commands commands : Commands.values()) (Instance plugin).getCommand(commands.getCommand()).setTabCompleter(new CommandsAutoCompletion(commands));
    }

    @EventHandler
    public static boolean onCommand(CommandSender sender, String label, String args[]){
        boolean state = false;
        if(Commands.valueOf(label.toUpperCase()) != null) {
            try {
                java.lang.reflect.Constructor constructor = Class.forName("(package).Command"+label.toUpperCase().substring(0, 1)+label.toLowerCase().substring(1)).getConstructor (CommandSender.class, String[].class);
                state = ((Commands) constructor.newInstance(sender, args)).isStateExec();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return state;
    }

}
```
<h1>CommandsAutoCompletion.java</h1>
```java
public class CommandsAutoCompletion implements TabCompleter {

    private Commands command;

    public CommandsAutoCompletion(Commands command){
        this.command = command;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args){
        List<String> matches = new ArrayList<>();
        switch (command){
            default:
                break;
        }
        return matches;
    }

}
```

