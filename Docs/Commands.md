<hr/>
<h1>Introduction</h1>
<p>Pour créer une commande bukkit la classe <strong>commands.java</strong> est disponible. 
<br>Elle permet de gérer simplement les commandes avec ces permissions son autocompletion et son aide.
<br>Insérer les classe unique <strong>CommandManager.java</strong>, <strong>CommandsAutoCompletion.java</strong> et l'enum <strong>Commands.java</strong>
<br> dans votre projet et ajouter des classe pour les différentes commandes qui étend <strong>Commands.java</strong> de l'API</p>
<h1>Créer une commande</h1>
<p>Création d'une commande exemple :</p>
```java
Condition condition = new Condition(() -> return true); //Condition suplémentaire
CommandSystem commandSystem_action1 = new CommandSystem(condition, new CommandExecute((String[] args) -> System.out.println("Action_1"), , new CommandArg("action_1", TabCompletionType.PLAYER_ONLINE.getList())));
CommandSystem commandSystem_action2 = new CommandSystem(condition, new CommandExecute((String[] args) -> System.out.println("Action_2"), , new CommandArg("action_2", TabCompletionType.PLAYER_OFFLINE.getList())));
new Command("exemple", "server.exemple", new String[] {"action_1", "action_2"}, "§4", 2, 0, commandSystem_action1, commandSystem_action2);
```