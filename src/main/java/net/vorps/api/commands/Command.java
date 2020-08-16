package net.vorps.api.commands;

import lombok.AllArgsConstructor;
import net.vorps.api.cooldowns.CoolDowns;
import lombok.Getter;
import net.vorps.api.lang.Lang;
import org.javatuples.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project Bungee Created by Vorps on 02/04/2017 at 22:12.
 */
public class Command {

    @AllArgsConstructor
    private static class CommandMethod{
        private final Method method;
        private final CommandPermission[] commandPermission;
        private final ArrayList<String> parameter;
    }

    private @Getter final String name;
    private final int time;
    private final String color = "§4";
    public HashMap<String, CommandMethod> commandMethods;
    public Set<String> methodsName = new HashSet<>();
    private final int NB_HELP_PER_PAGE = 4;

    /**
     * Construire une commande
     * @param nameCommand String - Name
     * @param time int - Temps de cooldown de la commande
     */
    public Command(String nameCommand, int time, Class<?> command) {
        this.name = nameCommand;
        this.time = time;
        this.commandMethods = new HashMap<>();
        for (Method method : Arrays.stream(command.getDeclaredMethods()).filter((m) -> Modifier.isStatic(m.getModifiers())).collect(Collectors.toList())) {
            ArrayList<String> nameParameter = new ArrayList<>();
            boolean tab = false;
            for(Parameter parameter : method.getParameters()){
                if(parameter.getAnnotationsByType(CommandParameter.class).length == 1){
                    nameParameter.add(parameter.getAnnotationsByType(CommandParameter.class)[0].value());
                }
                tab = parameter.getType().equals(String[].class);
            }
            this.commandMethods.put(method.getName()+(tab ? "" : ":"+method.getParameterCount()), new CommandMethod(method, method.getAnnotationsByType(CommandPermission.class), nameParameter));
            this.methodsName.add(method.getName());
        }
    }

    private void invokeMethod(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        int i = 0;
        Object[] argument = new Object[method.getParameters().length];
        for(Parameter parameter : method.getParameters()){
            if (String[].class.equals(parameter.getType())) {
                argument[i] = Arrays.stream(args).map(Object::toString).skip(i).collect(Collectors.toList()).toArray(new String[args.length-i]);
            } else {
                argument[i] = args[i];
                i++;
            }
        }
        method.invoke(null, argument);
    }
    public boolean execute(CommandSender commandSender, String[] args)  {
        if(args.length == 0) {
            this.helpFunction(commandSender, 0);
        } else {
            if (args[0].equalsIgnoreCase("help")) {
                int page = 0;
                if(args.length == 2) {
                    try {
                        page = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ignored){}
                }
                this.helpFunction(commandSender, page);
            } else {
                CommandMethod commandMethod = null;
                int i = 1;
                if(this.commandMethods.containsKey(args[0]+":"+args.length)) commandMethod = this.commandMethods.get(args[0]+":"+args.length);
                else if(this.commandMethods.containsKey(args[0])) commandMethod = this.commandMethods.get(args[0]);
                else if(this.commandMethods.containsKey(this.name+":"+(args.length+1))) {
                    commandMethod = this.commandMethods.get(this.name + ":" + (args.length + 1));
                    i = 0;
                } else if(this.commandMethods.containsKey(this.name)){
                    commandMethod = this.commandMethods.get(this.name);
                    i = 0;
                }
                if(commandMethod != null){
                    if(this.hasPermission(commandSender, commandMethod)){
                        if(this.isEnable(commandSender.getName())) {
                            try {
                                ArrayList<Object> argument = new ArrayList<>();
                                argument.add(commandSender);
                                argument.addAll(Arrays.asList(args).subList(i, args.length));
                                this.invokeMethod(commandMethod.method, argument.toArray());
                            } catch (Exception ignored) {
                                ignored.printStackTrace();
                                helpFunction(commandSender, Collections.singleton(commandMethod.method.getName()));
                            }
                        }
                    }
                } else {
                    if(this.methodsName.contains(args[0])) helpFunction(commandSender,  Collections.singleton(args[0]));
                    else if(this.methodsName.contains(this.name)) helpFunction(commandSender,  Collections.singleton(this.name));
                    else  helpFunction(commandSender, 1);

                }
            }
        }
        return true;
    }

    private Pair<List<String>, Boolean> getCompletion(String value){
        for(TabCompletionType tabCompletionType : TabCompletionType.values())
            if(tabCompletionType.name().equals(value.toUpperCase())){
                return new Pair<>(tabCompletionType.getList().getList(), true);
            }
        return new Pair<>(new ArrayList<>(Collections.singleton(value)), false);
    }

    public List<String> onTabComplete(CommandSender commandSender, String[] args){
        Set<String> matches = new HashSet<>();
        if (commandSender.hasPermissionStartWith(this.name)){
            switch (args.length) {
                case 1:
                    matches.add("help");
                    for(CommandMethod commandMethod : this.commandMethods.values()){
                        if(this.hasPermission(commandSender, commandMethod)){
                            if(!commandMethod.method.getName().equals(this.name)){
                                    matches.add(commandMethod.method.getName());
                            } else {
                                if(commandMethod.parameter.size() > 0){
                                    matches.addAll(this.getCompletion(commandMethod.parameter.get(0)).getValue0());
                                }
                            }
                        }

                    }
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("help"))
                        for (int i = 1; i <= (int) Math.ceil(this.commandMethods.values().size()/((double)NB_HELP_PER_PAGE)); i++) matches.add(Integer.toString(i));
                    break;
                default:
                    break;
            }
        }
        if(args.length > 1){
            matches.addAll(this.getCommandMethod(commandSender, args));
        }
        matches = this.getList(matches, args[args.length - 1]);
        return new ArrayList<>(matches);
    }

    private  Set<String> getCommandMethod(CommandSender commandSender, String[] args){
        Set<String> result = new HashSet<>();
        for(CommandMethod commandMethod : this.commandMethods.values()) {
            if(this.hasPermission(commandSender, commandMethod)){
                int startArgs = 0;
                if(commandMethod.method.getName().equals(args[0])) startArgs = 1;
                else if(!commandMethod.method.getName().equals(this.name)) continue;
                boolean ok = commandMethod.parameter.size() >= args.length-startArgs;
                Pair<List<String>, Boolean> completion = null;
                for(int i = startArgs; i < args.length-1 && ok; i++){
                    completion = this.getCompletion(commandMethod.parameter.get(i-startArgs));
                    if(completion.getValue1()){
                        if(!completion.getValue0().contains(args[i])){
                            ok = false;
                        }
                    }

                }
                if(ok) result.addAll(this.getCompletion(commandMethod.parameter.get(args.length-startArgs-1)).getValue0());
            }
        }
        return result;
    }

    private void helpFunction(CommandSender sender, Set<String> nameMethod) {
        if(sender.hasPermissionStartWith(this.name)){
            sender.sendMessage(this.color + "┌─────────────────────────────────┐");
            sender.sendMessage(this.color + "│");
            sender.sendMessage(this.color + "├ §e► §9§lHelp " + name.toUpperCase() +" " +Lang.getMessage("CMD." + this.name.toUpperCase() + ".HELP", sender.getLang() ) + " §e◄");
            sender.sendMessage(this.color + "│");
            String lang = sender.getLang();
            for(String help :  this.helpFunction(sender, this.commandMethods.values().stream().filter(e -> nameMethod.contains(e.method.getName())).collect(Collectors.toCollection(ArrayList::new)), lang)) sender.sendMessage(help);
            sender.sendMessage(this.color + "│");
            sender.sendMessage(this.color + "└─────────────────────────────────┘");
        }
    }

    private boolean hasPermission(CommandSender commandSender, CommandMethod commandMethod){
        return commandSender.hasPermission(Arrays.stream(commandMethod.commandPermission).map(e -> this.name + "." + (this.name.equals(commandMethod.method.getName()) ? "" : commandMethod.method.getName()+ ".") + e.value()).collect(Collectors.toCollection(ArrayList::new))) && (Arrays.stream(commandMethod.commandPermission).map(CommandPermission::console).reduce(true, (last, next) -> last & next) || commandSender.isPlayer());
    }
    private ArrayList<String> helpFunction(CommandSender commandSender, Collection<CommandMethod> commandMethods, String lang){
        ArrayList<String> help = new ArrayList<>();
        for(CommandMethod commandMethod : commandMethods){
            if(this.hasPermission(commandSender, commandMethod)){
                StringBuilder stringBuilder = new StringBuilder(this.color + "│ "+this.name.toLowerCase()+(this.name.toLowerCase().equals(commandMethod.method.getName().toLowerCase()) ? "": " "+commandMethod.method.getName().toLowerCase())+" §6");
                for(String nameParameter : commandMethod.parameter){
                    stringBuilder.append("<").append(nameParameter).append(">").append(" ");
                }
                stringBuilder.append(Lang.getMessage("CMD." + this.name.toUpperCase()+(this.name.toLowerCase().equals(commandMethod.method.getName().toLowerCase()) ? "" : "."+commandMethod.method.getName().toUpperCase())+"."+ Arrays.stream(commandMethod.commandPermission).map(CommandPermission::value).reduce("", (last, next) -> last+next).toUpperCase()+".HELP", lang));
                help.add(stringBuilder.toString());
            }
        }
        return help;
    }

    private void helpFunction(CommandSender sender, int page) {
        if(sender.hasPermissionStartWith(this.name)){
            if(this.commandMethods.values().size() < page * NB_HELP_PER_PAGE) page = (int) Math.ceil(this.commandMethods.values().size()/((double)NB_HELP_PER_PAGE));
            if(page < 1) page = 1;
            sender.sendMessage(this.color + "┌─────────────────────────────────┐");
            sender.sendMessage(this.color + "│");
            sender.sendMessage(this.color + "├ §e► §9§lHelp " + name.toUpperCase() + "§a(§6" + page + "§a/§6" + ((int) Math.ceil(this.commandMethods.size()/((double)NB_HELP_PER_PAGE))) + "§a)   " + Lang.getMessage("CMD." + this.name.toUpperCase() + ".HELP", sender.getLang() ) + " §e◄");
            sender.sendMessage(this.color + "│");
            ArrayList<CommandMethod> commandMethods = new ArrayList<>(this.commandMethods.values());
            for(String help : this.helpFunction(sender, commandMethods.subList((page-1)*NB_HELP_PER_PAGE, Math.min(commandMethods.size(), page * NB_HELP_PER_PAGE)), sender.getLang())) sender.sendMessage(help);
            sender.sendMessage(this.color + "│");
            sender.sendMessage(this.color + "└─────────────────────────────────┘");
        }
    }


    private boolean isEnable(String name) {
        boolean state = false;
        if(this.time > 0) return true;
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

    private Set<String> getList(Set<String> list, String args) {
        Set<String> listResult = new HashSet<>();
        for (String s : list) if (s.toLowerCase().startsWith(args.toLowerCase())) listResult.add(s);
        return listResult;
    }

}

