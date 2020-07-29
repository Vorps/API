package net.vorps.api.lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.data.Data;
import net.vorps.api.data.DataCore;
import net.vorps.api.databases.Database;
import net.vorps.api.databases.DatabaseManager;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Lang {

    /**
     * Load all message
     * @param result ResultSet
     * @throws SQLException
     */
    public Lang(ResultSet result) throws SQLException{
        String key = result.getString(1);
        HashMap<String, String> langMessage =  new HashMap<>();
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
            langMessage.put(langSetting.getName(), result.getString(langSetting.getColumnId()));
        }
        Lang.lang.put(key, langMessage);
    }



    public static class Args{
        private final Parameter parameter;
        private final String value;

        public Args(Parameter parameter, String value){
            this.parameter = parameter;
            this.value = value;
        }
    }

    public enum Parameter {
        STATE,
        TIME,
        TEAM,
        POINT,
        NBR_PLAYER,
        GOLDS,
        KILL,
        DEAD,
        WOOL,
        VAR,
        PLAYER,
        MODE,
        SERVER,
        MESSAGE,
        KILLER,
        NBR_MAX_PLAYER,
        COLOR,
        WINNER,
        PRICE,
        DEVICE,
        LOOSER,
        KIT,
        PAGE,
        LIFE,
        BONUS,
        KILLED,
        SPECTATOR,
        SPEED,
        REASON,
        CHANNEL;
        private final String label;

        Parameter(){
            this.label = "<"+this.name().toLowerCase()+">";
        }
    }

    private static HashMap<String, HashMap<String, String>> lang;

    static {
        Lang.lang = new HashMap<>();
        DataCore.getInstance().loadLang();
    }

    public static String getMessage(String key, String langPlayer, Args... args){
        String message;
        switch (args.length){
            case 0:
                HashMap<String, String> messageHashMap = lang.get(key);
                message = messageHashMap.get(langPlayer);
                break;
            case 1:
                message = getMessage(key, langPlayer).replaceAll(args[0].parameter.label , args[0].value);
                break;
            default:
                message = getMessage(key, langPlayer);
                for(Args argsList : args){
                    message = message.replaceAll(argsList.parameter.label , argsList.value);
                }
                break;
        }
        return message;
    }

    public static void clearLang(){
        Lang.lang.clear();
    }
}
