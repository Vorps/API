package net.vorps.api.commands;

import net.vorps.api.cooldowns.CoolDowns;
import net.vorps.api.lang.Lang;
import net.vorps.api.players.PlayerData;
import net.vorps.api.utils.Settings;
import net.vorps.api.utils.StringBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Project Bungee Created by Vorps on 02/04/2017 at 22:12.
 */
public class Command {

    private @Getter String name;
    private int time;
    private @Getter @Setter String lang;
    private @Getter @Setter boolean stateExec;
    private @Getter String[] permissions;
    private String color;
    private CommandSystem[] commandSystems;
    private int nHelp;
    private int nPage;

    /**
     * Construire une commande
     * @param nameCommand String - Name
     * @param permissions String[] - Liste des permission par argument
     * @param color String - Couleur de l'aide
     * @param nHelp int - Nombre de d'aide par page
     * @param time int - Temps de cooldown de la commande
     * @param commandSystems CommandSystem - Action de la commande
     */
    public Command(String nameCommand, String[] permissions, String color, int nHelp, int time, CommandSystem... commandSystems) {
        this.name = nameCommand;
        this.time = time;
        this.stateExec = false;
        this.permissions = permissions;
        this.color = color;
        this.nHelp = nHelp;
        this.setCommandSystems(commandSystems);
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (this.hasPermission(sender)){
            this.stateExec = false;
            this.lang = PlayerData.isPlayerDataCore(sender.getName()) ? PlayerData.getPlayerDataCore(sender.getName()).getLang() : Settings.getConsoleLang();
            if ((args.length == 1 || args.length == 2) && args[0].equalsIgnoreCase("help")) {
                this.helpFunction(sender, new String[0], args.length == 2 ? args[1] : null);
                this.stateExec = true;
            } else {
                if(isEnable(sender.getName())){
                    net.vorps.api.commands.Command.CoupleCommandSystem[] coupleCommandSystems = this.getCoupleCommand(sender, args, true);
                    for (net.vorps.api.commands.Command.CoupleCommandSystem coupleCommandSystem : coupleCommandSystems) {
                        if (coupleCommandSystem.commandArg.length <= args.length && isValid(sender, coupleCommandSystem, args)) {
                            this.stateExec = true;
                            coupleCommandSystem.commandSystem.getCommandExecute().execute(args);
                        }
                    }
                    this.helpFunction(sender, args);
                }
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, String[] args){
        List<String> matches = new ArrayList<>();
        switch (args.length) {
            case 1:
                if (this.hasPermission(sender)) if (args[0].toLowerCase().startsWith("help")) matches.add("help");
                break;
            case 2:
                if (this.hasPermission(sender) && args[0].equalsIgnoreCase("help"))
                    for (int i = 1; i <= this.nPage; i++) matches.add("" + i);
                break;
            default:
                break;
        }
        for (net.vorps.api.commands.Command.CoupleCommandSystem coupleCommandSystem : this.getCoupleCommand(sender, args, false)) { //Donne tout les arguments possible
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

    private boolean isEnable(String name) {
        boolean state = false;
        if (!CoolDowns.hasCoolDown(name, this.name)) {
            new CoolDowns(name, this.time, this.name);
            state = true;
        } else {
            CoolDowns coolDowns = CoolDowns.getCoolDown(name, this.name);
            if (coolDowns.getSecondsLeft() <= 0) {
                coolDowns.removeCoolDown();
                state = true;
            }
        }
        return state;
    }


    public void setCommandSystems(CommandSystem... commandSystems) {
        int i = 0;
        int y = 0;
        for (CommandSystem commandSystem : commandSystems) {
            commandSystem.setIndexHelp(y++);
            if (commandSystem.getCondition().getIndexPerm() != -1)
                commandSystem.getCondition().setIndexPerm(i++);
        }
        this.commandSystems = commandSystems;
    }

    private boolean isValid(CommandSender sender, CoupleCommandSystem coupleCommandSystem, String[] args) {
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

    private CoupleCommandSystem[] getCoupleCommand(CommandSender sender, String[] args, boolean state) {
        ArrayList<CoupleCommandSystem> coupleCommandSystems = new ArrayList<>();
        ArrayList<ArrayList<CommandArg>> commandArgsTmp = new ArrayList<>();
        ArrayList<CommandSystem> commandSystems = new ArrayList<>();
        for (CommandSystem commandSystem : this.commandSystems) {
            if (this.isStatePerm(sender, commandSystem.getCondition())) {
                ArrayList<CommandArg> commandArgsTmp1 = new ArrayList<>();
                for (CommandArg commandArg : commandSystem.getArgs()) {
                    if (this.isStatePerm(sender, commandArg.getCondition())) commandArgsTmp1.add(commandArg);
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

    private void helpFunction(CommandSender sender, String[] args, String... pageArg) {
        if (!this.stateExec && this.commandSystems != null) {
            this.nPage = getNbPage(sender);
            int page = pageArg.length != 0 && pageArg[0] != null ? this.getPage(pageArg[0]) : 1;
            CoupleCommandSystem[] coupleCommandSystems = this.getCoupleCommand(sender, args, true);
            sender.sendMessage(this.color + "┌─────────────────────────────────┐");
            sender.sendMessage(this.color + "│");
            sender.sendMessage(this.color + "├ §e► §9§lHelp " + name.toUpperCase() + " " + (coupleCommandSystems.length == 1 ? "§a(§6" + new StringBuilder(coupleCommandSystems[0].commandArg[0].getArg()).toUpperFirstLetter().getString() + "§a)" : "§a(§6" + page + "§a/§6" + this.nPage + "§a)   " + Lang.getMessage("BUNGEE.CMD." + name.toUpperCase() + ".HELP", this.lang)) + " §e◄");
            sender.sendMessage(this.color + "│");
            getHelp(sender, page, coupleCommandSystems.length > 0 ? coupleCommandSystems : this.getCoupleCommand(sender, new String[0], true));
            sender.sendMessage(this.color + "│");
            sender.sendMessage(this.color + "└─────────────────────────────────┘");
        }
    }


    /**
     * Indique si le sender a une permission
     * @return boolean
     */
    public boolean hasPermission(CommandSender sender) {
        boolean state = false;
        for (String perm : this.permissions) {
            if (sender.hasPermission(perm)) {
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
    public boolean hasPermission(CommandSender sender, int index) {
        return sender.hasPermission(this.permissions[index]);
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

    private int getNbPage(CommandSender sender) {
        int i = 0;
        for (CommandSystem help : this.commandSystems) if (this.isStatePerm(sender, help.getCondition())) i++;
        return i / this.nHelp + (i % this.nHelp == 0 ? 0 : 1);
    }

    private void getHelp(CommandSender sender, int page, CoupleCommandSystem[] coupleCommandSystems) {
        if (coupleCommandSystems.length == 1) this.getHelpPlayer(sender, coupleCommandSystems[0]);
        else for (int i = (page - 1) * this.nHelp; i < page * this.nHelp && i < coupleCommandSystems.length; i++)
            this.getHelpPlayer(sender, coupleCommandSystems[i]);
    }

    private void getHelpPlayer(CommandSender sender, CoupleCommandSystem coupleCommandSystem) {
        for (String messageTmp : this.getMessage(this.color + "├ §7■§e §b/§9" + name + this.getHelp(coupleCommandSystem.commandArg)[1] + " §b◊ " + this.getHelp(coupleCommandSystem.commandArg)[0] + " §b► §7" + Lang.getMessage("BUNGEE.CMD." + name.toUpperCase() + ".HELP_" + coupleCommandSystem.commandSystem.getIndexHelp(), this.lang)))
            sender.sendMessage(messageTmp);
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

    private boolean isStatePerm(CommandSender sender, Condition var) {
        return var.isStatePerm() == (var.getIndexPerm() < 0 || var.getIndexPerm() > permissions.length || hasPermission(sender, var.getIndexPerm())) && (var.getCondition() == null || var.getCondition().condition()) && (!var.isPlayer() || sender.isPlayer());
    }
}

