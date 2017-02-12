package fr.herezia.api.lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Lang {

    /**
     * Load all message
     * @param result ResultSet
     * @throws SqlException
     * @throws SQLException
     */
    public Lang(ResultSet result) throws SqlException, SQLException{
        String key = Database.SERVER.getDatabase().getString(result, 1);
        HashMap<String, String> langMessage =  new HashMap<>();
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
            langMessage.put(langSetting.getName(), result.getString(langSetting.getColumnId()));
        }
        Lang.lang.put(key, langMessage);
    }

    public static class Args{
        private Parameter parameter;
        private String value;

        public Args(Parameter parameter, String value){
            this.parameter = parameter;
            this.value = value;
        }
    }

    public enum Parameter {
        STATE("<state>"),
        TIME("<time>"),
        TEAM("<team>"),
        POINT("<point>"),
        NBR_PLAYER("<nbr_player>"),
        GOLDS("<golds>"),
        KILL("<kill>"),
        DEAD("<dead>"),
        WOOL("<wool>"),
        VAR("<var>"),
        PLAYER("<player>"),
        MODE("<mode>"),
        SERVER("<server>"),
        MESSAGE("<message>"),
        KILLER("<killer>"),
        NBR_MAX_PLAYER("<nbr_max_player>"),
        COLOR("<color>"),
        WINNER("<winner>"),
        PRICE("<price>"),
        DEVICE("<device>"),
        LOOSER("<looser>"),
        KIT("<kit>"),
        PAGE("<page>"),
        LIFE("<life>"),
        BONUS("<bonus>"),
        KILLED("<killed>"),
        SPECTATOR("<spectator>"),
        SPEED("<speed>");

        private String label;

        Parameter(String label){
            this.label = label;
        }
    }

    private static HashMap<String, HashMap<String, String>> lang = new HashMap<>();

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
