package fr.herezia.api.dispatcher;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.herezia.api.lang.Lang;
import fr.herezia.api.players.PlayerData;
import fr.herezia.api.threads.ThreadFile;
import fr.herezia.api.threads.ThreadServer;
import fr.herezia.api.type.GameState;
import fr.herezia.api.utils.Serialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project Hub Created by Vorps on 01/04/2016 at 00:41.
 */
public class Dispatcher {

    private static @Getter HashMap<String, ArrayList<String>> listServer = new HashMap<>();

    public static void updateListServer(Player player, Plugin plugin){
        if(listServer.isEmpty()){
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("GetServers");
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        }
    }

    public static void listServer(String[] server){
        for(Type type : Type.values()){
            ArrayList<String> nameServer = new ArrayList<>();
            for(String name : server) if(type.parameter.type.equals(name.substring(0, name.length()-3).toLowerCase())) nameServer.add(name);
            listServer.put(type.parameter.type, nameServer);
        }
    }

    @AllArgsConstructor
    public enum Type{
        RV(new Parameter("512M", "1024M", "rv", "Rush Volcano", 10000, 1));

        private @Getter Parameter parameter;

        private static Type getType(String type){
            Type resultType = null;
            for (Type typeList : Type.values()){
                if(typeList.parameter.type.equals(type)){
                    resultType = typeList;
                    break;
                }
            }
            return resultType;
        }
    }

    @AllArgsConstructor
    public static class Parameter {
        private String xms;
        private String xmx;
        private @Getter String type;
        private @Getter String typeLabel;
        private long time;
        private int nbrServer;
    }

    private @Getter static String path = Paths.get( System.getProperty("user.dir")).getParent().toString().substring(0, Paths.get( System.getProperty("user.dir")).getParent().toString().length()-3);
    public static final char DS = '/';

    private static void startServer(Type type, String nameServer){
        if(!ThreadServer.getServerStart().contains(nameServer)){
            try {
                Runtime.getRuntime().exec("screen -dmS "+nameServer+" java -Xms"+type.parameter.xms+" -Xmx"+type.parameter.xmx+" -jar spigot.jar", null,  new File(path+type.parameter.type+DS+nameServer+DS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Connect player to server of type selected on player or spectator
     * @param type String
     * @param player Player
     * @param play boolean
     * @param lang String
     * @param plugin Plugin
     */
    public static void connectServer(String type, Player player, boolean play, String lang, Plugin plugin){
        int state = 0;
        String nameServer = null;
        try {
            for(String name : listServer.get(type)){
                fr.herezia.api.type.Parameter parameter = deSerialisation(path+type+DS+name);
                if((parameter.isCanPlay() && play) || (parameter.isCanSpect() && !play)){
                    state = 1;
                    nameServer = name;
                    break;
                }
            }
            if(state == 0 && play){
                Type type1 = Type.getType(type);
                for(int id = 1; id <= type1.parameter.nbrServer; id++){
                    String name;
                    if(id < 10) name = type+"_0"+id;
                    else name = type+"_"+id;
                    name = name.toUpperCase();
                    fr.herezia.api.type.Parameter parameter = deSerialisation(path+type+DS+name);
                    if(!parameter.isCanPlay()){
                        state = 2;
                        nameServer = name;
                        break;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        file(player, state, type, nameServer, play, lang, plugin);
    }

    /**
     * Connect player to server of nameServer selected on player or spectator
     * @param nameServer String
     * @param player Player
     * @param lang String
     * @param plugin Plugin
     */
    public static void connectServer(String nameServer, Player player, String lang, Plugin plugin){
        int state = 0;
        try {
            fr.herezia.api.type.Parameter parameter = deSerialisation(path+nameServer.substring(0, nameServer.length()-3).toLowerCase()+DS+nameServer);
            if(parameter.getStatus() != GameState.STOP){
                if(parameter.isCanPlay() || parameter.isCanSpect()) state = 1;
            } else
                state = 2;
        } catch (Exception e){
            e.printStackTrace();
        }
        file(player, state, nameServer.substring(0, nameServer.length()-3).toLowerCase(), nameServer, true, lang, plugin);
    }

    /**
     * Connect player to server of type and map selected on player or spectator
     * @param type String
     * @param map String
     * @param player Player
     * @param play boolean
     * @param lang String
     * @param plugin Plugin
     */
    public static void connectServer(String type, String map, Player player, boolean play, String lang,Plugin plugin) {
        int state = 0;
        String nameServer = null;
        try {
            for(String name : listServer.get(type)){
                fr.herezia.api.type.Parameter parameter = deSerialisation(path+type+DS+name.toUpperCase());
                if(parameter.getMap().equals(map) && ((parameter.isCanPlay() && play) || (parameter.isCanSpect() && !play))){
                    state = 1;
                    nameServer = name;
                    break;
                }
            }
            if(state == 0 && play){
                Type type1 = Type.getType(type);
                for(int id = 1; id <= type1.parameter.nbrServer; id++){
                    String name;
                    if(id < 10) name = type+"_0"+id;
                    else name = type+"_"+id;
                    name = name.toUpperCase();
                    fr.herezia.api.type.Parameter parameter = deSerialisation(path+type+DS+name);
                    if(!parameter.isCanPlay() && parameter.getMap().equals(map)){
                        state = 2;
                        nameServer =  name;
                        break;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        file(player, state, type, nameServer, play, lang, plugin);
    }

    private static fr.herezia.api.type.Parameter deSerialisation(String path) throws Exception{
        return (fr.herezia.api.type.Parameter) Serialize.deSerialisation(path+"/game_parameter/parameter.ser");
    }

    private static void file(Player player, int state, String type, String nameServer, boolean play, String lang, Plugin plugin){
        if(PlayerData.getPlayerData(player.getUniqueId()).getFile() == null || !PlayerData.getPlayerData(player.getUniqueId()).getFile().getNameServer().equals(nameServer)){
            player.sendMessage(Lang.getMessage("FILE.LABEL", lang)+Lang.getMessage("FILE.JOIN", lang, new Lang.Args(Lang.Parameter.SERVER, Type.getType(type).getParameter().typeLabel)));
            switch (state){
                case 0:
                    if(play) player.sendMessage(Lang.getMessage("FILE.LABEL", lang)+Lang.getMessage("FILE.SERVER.FULL", lang));
                    else player.sendMessage(Lang.getMessage("FILE.LABEL", lang)+Lang.getMessage("FILE.SERVER.SPEC.FULL", lang));
                    break;
                case 1:
                    player.sendMessage(Lang.getMessage("FILE.LABEL", lang)+Lang.getMessage("FILE.START.PENDING", lang)+(PlayerData.getPlayerData(player.getUniqueId()).getRank().getPendingGame() == 0 ? "" : Lang.getMessage("FILE.START.MESSAGE", lang, new Lang.Args(Lang.Parameter.TIME, ""+PlayerData.getPlayerData(player.getUniqueId()).getRank().getPendingGame()))));
                    PlayerData.getPlayerData(player.getUniqueId()).setFile(new ThreadFile(nameServer,PlayerData.getPlayerData(player.getUniqueId()).getRank().getPendingGame(), player));
                    PlayerData.getPlayerData(player.getUniqueId()).getFile().start();
                    break;
                case 2:
                    startServer(Type.getType(type), nameServer);
                    PlayerData.getPlayerData(player.getUniqueId()).setFile(new ThreadServer(Type.getType(type).parameter.time, player, nameServer));
                    PlayerData.getPlayerData(player.getUniqueId()).getFile().start();
                    player.sendMessage(Lang.getMessage("FILE.CONNECT", lang, new Lang.Args(Lang.Parameter.TIME, ""+(Type.getType(type).getParameter().time/1000))));
                    break;
                default:
                    break;
            }
        }
    }
}