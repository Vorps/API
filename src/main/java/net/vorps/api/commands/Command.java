package net.vorps.api.commands;

import net.vorps.api.API;
import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.cooldowns.CoolDowns;
import net.vorps.api.databases.Database;
import net.vorps.api.lang.Lang;
import net.vorps.api.players.PlayerData;
import net.vorps.api.utils.Settings;
import net.vorps.api.utils.StringBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Bungee Created by Vorps on 02/04/2017 at 22:12.
 */
public class Command extends org.bukkit.command.Command implements TabExecutor {

    private String name;
    private int time;
    private @Getter CommandSender sender;
    private @Getter @Setter String lang;
    private @Getter @Setter boolean stateExec;
    private String permission;
    private @Getter String[] permissions;
    private String color;
    private CommandSystem[] commandSystems;
    private int nHelp;
    private int nPage;

    /**
     * Construire une commande
     * @param nameCommand String - Name
     * @param permission String - Permission global
     * @param permissions String[] - Liste des permission par argument
     * @param color String - Couleur de l'aide
     * @param nHelp int - Nombre de d'aide par page
     * @param time int - Temps de cooldown de la commande
     * @param commandSystems CommandSystem - Action de la commande
     */
    public Command(String nameCommand, String permission, String[] permissions, String color, int nHelp, int time, CommandSystem... commandSystems) {
        super(nameCommand);
        this.name = nameCommand;
        this.time = time;
        this.stateExec = false;
        this.permission = permission;
        this.permissions = permissions;
        this.color = color;
        this.nHelp = nHelp;
        this.setCommandSystems(commandSystems);
        API.getPlugin().getCommand(nameCommand).setExecutor(this);
        try {
            this.database();
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    private void database() throws SqlException{
        Database.CORE.getDatabase().insertTable("command", this.name);
        for(String perm : this.permissions) Database.CORE.getDatabase().insertTable("permission", this.name, perm);
        for(String perm : this.permissions) Database.CORE.getDatabase().insertTable("permission", this.name, perm);
    }

    static {
        try {
            if(!Database.CORE.getDatabase().isTable("command")) Database.CORE.getDatabase().sendRequest("CREATE TABLE `command`(`c_name` varchar(10) NOT NULL, PRIMARY KEY (`c_name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Liste des commandes du serveur';");
            if(!Database.CORE.getDatabase().isTable("permission")) Database.CORE.getDatabase().sendRequest("CREATE TABLE `permission` (`c_command` varchar(10) NOT NULL, `c_permission` varchar(100) NOT NULL, PRIMARY KEY (`c_command`,`c_permission`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Permissions des commandes';ALTER TABLE `permission` ADD CONSTRAINT `permission_ibfk_1` FOREIGN KEY (`c_command`) REFERENCES `command` (`c_name`);");
        } catch (SqlException e){
            e.printStackTrace();
        }
    }
    private boolean isEnable() {
        boolean state = false;
        if (!CoolDowns.hasCoolDown(sender.getName(), this.name)) {
            new CoolDowns(sender.getName(), this.time, this.name);
            state = true;
        } else {
            CoolDowns coolDowns = CoolDowns.getCoolDown(sender.getName(), this.name);
            if (coolDowns.getSecondsLeft() <= 0) {
                coolDowns.removeCoolDown();
                state = true;
            }
        }
        return state;
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args){
        return this.execute(sender, label, args);
    }

    private void setCommandSystems(CommandSystem[] commandSystems) {
        int i = 0;
        int y = 0;
        for (CommandSystem commandSystem : commandSystems) {
            commandSystem.setIndexHelp(y++);
            if (commandSystem.getCondition().getIndexPerm() != -1)
                commandSystem.getCondition().setIndexPerm(i++);
        }
        this.commandSystems = commandSystems;
    }

    @Override
    public boolean execute(CommandSender sender, String command ,String[] args) {
        this.sender = sender;
        if (this.hasPermission()){
            this.stateExec = false;
            this.lang = this.sender instanceof Player ? PlayerData.getPlayerDataCore(this.sender.getName()).getLang() : Settings.getConsoleLang();
            if ((args.length == 1 || args.length == 2) && args[0].equalsIgnoreCase("help")) {
                this.helpFunction(new String[0], args.length == 2 ? args[1] : null);
                this.stateExec = true;
            } else {
                if(this.isEnable()){
                    CoupleCommandSystem[] coupleCommandSystems = this.getCoupleCommand(args, true);
                    for (CoupleCommandSystem coupleCommandSystem : coupleCommandSystems) {
                        if (coupleCommandSystem.commandArg.length <= args.length && isValid(coupleCommandSystem, args)) {
                            this.stateExec = true;
                            coupleCommandSystem.commandSystem.getCommandExecute().execute(args);
                        }
                    }
                    this.helpFunction(args);
                }
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args){
        List<String> matches = new ArrayList<>();
        this.sender = sender;
        switch (args.length) {
            case 1:
                if (this.hasPermission()) if (args[0].toLowerCase().startsWith("help")) matches.add("help");
                break;
            case 2:
                if (this.hasPermission() && args[0].equalsIgnoreCase("help"))
                    for (int i = 1; i <= this.nPage; i++) matches.add("" + i);
                break;
            default:
                break;
        }
        for (CoupleCommandSystem coupleCommandSystem : this.getCoupleCommand(args, false)) { //Donne tout les arguments possible
            if (coupleCommandSystem.commandArg.length >= args.length) {
                if (!coupleCommandSystem.commandArg[args.length - 1].isState())
                    matches.add(coupleCommandSystem.commandArg[args.length - 1].getArg());
                else if (coupleCommandSystem.commandArg[args.length - 1].getList() != null)
                    matches.addAll(coupleCommandSystem.commandArg[args.length - 1].getList().getList(args, sender.getName(), new String[0]));
            }
        }
        matches = this.getList(matches, args[args.length - 1]);
        return matches;
    }

    private boolean isValid(CoupleCommandSystem coupleCommandSystem, String[] args) {
        boolean state = true;
        int i = 0;
        for (CommandArg commandArg : coupleCommandSystem.commandArg) {
            if (!commandArg.isState()) {
                if (!commandArg.getArg().equalsIgnoreCase(args[i])) {
                    state = false;
                    break;
                }
            } else {
                if ((commandArg.getList() != null && commandArg.getList().getList(args, sender.getName(), new String[0]).contains(args[i])) || commandArg.getList() != null) {
                    state = false;
                    break;
                }
            }
            i++;
        }
        return state;
    }

    private List<String> getList(List<String> list, String args) {
        List<String> listResult = new ArrayList<>();
        for (String s : list) if (s.toLowerCase().startsWith(args.toLowerCase())) listResult.add(s);
        return listResult;
    }


    @AllArgsConstructor
    private class CoupleCommandSystem {
        private CommandSystem commandSystem;
        private CommandArg[] commandArg;
    }

    private CoupleCommandSystem[] getCoupleCommand(String[] args, boolean state) {
        ArrayList<CoupleCommandSystem> coupleCommandSystems = new ArrayList<>();
        ArrayList<ArrayList<CommandArg>> commandArgsTmp = new ArrayList<>();
        ArrayList<CommandSystem> commandSystems = new ArrayList<>();
        for (CommandSystem commandSystem : this.commandSystems) {
            if (this.isStatePerm(commandSystem.getCondition())) {
                ArrayList<CommandArg> commandArgsTmp1 = new ArrayList<>();
                for (CommandArg commandArg : commandSystem.getArgs()) {
                    if (this.isStatePerm(commandArg.getCondition())) commandArgsTmp1.add(commandArg);
                }
                commandArgsTmp.add(commandArgsTmp1);
                commandSystems.add(commandSystem);
            }
        }

        ArrayList<CommandArg> commandArgs = new ArrayList<>();
        CommandSystem commandSystemsTmp = null;
        int maxFind = 0;
        int i = 0;
        int find = 0;
        for (ArrayList<CommandArg> commandArg : commandArgsTmp) {
            commandArgs = new ArrayList<>();
            if (args.length == (state ? 0 : 1)) {
                if (!commandArg.isEmpty()) commandArgs.addAll(commandArg);
                coupleCommandSystems.add(new CoupleCommandSystem(commandSystems.get(i), StringBuilder.convert(commandArgs, new CommandArg[commandArgs.size()])));
            } else {
                for (int y = 0, z = 0; y < commandArg.size() && y < args.length; y++) {
                    if (!commandArg.get(y).isState()) {
                        System.out.println(commandArg.get(y).getArg() + " : " + args[y]);
                        if (commandArg.get(y).getArg().equalsIgnoreCase(args[y]) && z++ == y && maxFind <= y) {
                            find = i;
                            commandSystemsTmp = commandSystems.get(i);
                            System.out.println(commandArg.get(y).getArg());
                            maxFind = y;
                        }
                    } else {
                        if ((commandArg.get(y).getList() != null && commandArg.get(y).getList().getList(args, sender.getName(), new String[0]).contains(args[y]) && z++ == y && maxFind <= y) || commandArg.get(y).getList() != null) {
                            find = i;
                            System.out.println(commandArg.get(y).getArg());
                            commandSystemsTmp = commandSystems.get(i);
                            maxFind = y;
                        }
                    }
                }
            }
            i++;
        }
        if (commandSystemsTmp != null) {
            commandArgs.addAll(commandArgsTmp.get(find));
            if (!commandArgs.isEmpty())
                coupleCommandSystems.add(new CoupleCommandSystem(commandSystemsTmp, StringBuilder.convert(commandArgs, new CommandArg[commandArgs.size()])));
        }
        return StringBuilder.convert(coupleCommandSystems, new CoupleCommandSystem[coupleCommandSystems.size()]);
    }

    private void helpFunction(String[] args, String... pageArg) {
        if (!this.stateExec && this.commandSystems != null) {
            this.nPage = getNbPage();
            int page = pageArg.length != 0 && pageArg[0] != null ? this.getPage(pageArg[0]) : 1;
            CoupleCommandSystem[] coupleCommandSystems = this.getCoupleCommand(args, true);
            this.sender.sendMessage(this.color + "┌─────────────────────────────────┐");
            this.sender.sendMessage(this.color + "│");
            this.sender.sendMessage(this.color + "├ §e► §9§lHelp " + super.getName().toUpperCase() + " " + (coupleCommandSystems.length == 1 ? "§a(§6" + new StringBuilder(coupleCommandSystems[0].commandArg[0].getArg()).toUpperFirstLetter().getString() + "§a)" : "§a(§6" + page + "§a/§6" + this.nPage + "§a)   " + Lang.getMessage("BUNGEE.CMD." + super.getName().toUpperCase() + ".HELP", this.lang)) + " §e◄");
            this.sender.sendMessage(this.color + "│");
            this.getHelp(page, coupleCommandSystems.length > 0 ? coupleCommandSystems : this.getCoupleCommand(new String[0], true));
            this.sender.sendMessage(this.color + "│");
            this.sender.sendMessage(this.color + "└─────────────────────────────────┘");
        }
    }


    /**
     * Indique si le sender a une permission
     * @return boolean
     */
    public boolean hasPermission() {
        boolean state = false;
        for (String perm : this.permissions) {
            if (this.sender.hasPermission(this.permission + "." + perm)) {
                state = true;
                break;
            }
        }
        return state;
    }

    /**
     * Indique si le sender a la permission
     * @param index int
     * @return boolean
     */
    public boolean hasPermission(int index) {
        return this.sender.hasPermission(this.permission + "." + this.permissions[index]);
    }

    private int getPage(String page) {
        int pageResult = 0;
        try {
            pageResult = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            //
        }
        return pageResult > this.nPage ? this.nPage : pageResult <= 0 ? 1 : pageResult;
    }

    private int getNbPage() {
        int i = 0;
        for (CommandSystem help : this.commandSystems) if (this.isStatePerm(help.getCondition())) i++;
        return i / this.nHelp + (i % this.nHelp == 0 ? 0 : 1);
    }

    private void getHelp(int page, CoupleCommandSystem[] coupleCommandSystems) {
        if (coupleCommandSystems.length == 1) this.getHelpPlayer(coupleCommandSystems[0]);
        else for (int i = (page - 1) * this.nHelp; i < page * this.nHelp && i < coupleCommandSystems.length; i++)
            this.getHelpPlayer(coupleCommandSystems[i]);
    }

    private void getHelpPlayer(CoupleCommandSystem coupleCommandSystem) {
        for (String messageTmp : this.getMessage(this.color + "├ §7■§e §b/§9" + super.getName() + this.getHelp(coupleCommandSystem.commandArg)[1] + " §b◊ " + this.getHelp(coupleCommandSystem.commandArg)[0] + " §b► §7" + Lang.getMessage("BUNGEE.CMD." + super.getName().toUpperCase() + ".HELP_" + coupleCommandSystem.commandSystem.getIndexHelp(), this.lang)))
            this.sender.sendMessage(messageTmp);
    }

    private String[] getMessage(String message) {
        ArrayList<String> messageResult = new ArrayList<>();
        StringBuilder messageReal = new StringBuilder("", "");
        ArrayList<Integer> test = new ArrayList<>();

        int y = 0;
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '§') {
                test.add(i - (test.size() * 2));
                messageReal.append(message.substring(y, i));
                y = i + 2;
            }
        }
        if (y < message.length()) messageReal.append(message.substring(y, message.length()));
        if (messageReal.getString().length() > 61) {
            for (int i = 61; i > 0; i--) {
                if (messageReal.getString().charAt(i) == ' ') {
                    int z;
                    for (z = 0; z < test.size() && test.get(z) < i; z++) ;
                    messageResult.add(message.substring(0, i + (z * 2)));
                    messageResult.add(this.color + "├ §7" + message.substring(i + (z * 2), message.length()));
                    break;
                }
            }
        } else messageResult.add(message);
        return StringBuilder.convert(messageResult, new String[messageResult.size()]);
    }

    private String[] getHelp(CommandArg[] commandArgs) {
        StringBuilder helpCurrent = new StringBuilder();
        StringBuilder helpMessage = new StringBuilder();
        for (CommandArg arg : commandArgs) {
            if (arg.isState()) helpCurrent.append("§b[§e" + arg.getArg() + "§b]");
            else helpMessage.append("§b" + arg.getArg());
        }
        return new String[]{helpCurrent.getString(), helpMessage.getString()};
    }

    private boolean isStatePerm(Condition var) {
        return var.isStatePerm() == (var.getIndexPerm() < 0 || var.getIndexPerm() > permissions.length || hasPermission(var.getIndexPerm())) && (var.getCondition() == null || var.getCondition().condition()) && (!var.isPlayer() || this.sender instanceof Player);
    }
}

