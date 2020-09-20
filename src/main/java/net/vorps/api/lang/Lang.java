package net.vorps.api.lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.vorps.api.data.DataCore;

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
        for(String langSetting : LangSetting.getListLangSetting()){
            langMessage.put(langSetting, result.getString(LangSetting.getLangSetting(langSetting).getColumnId()));
        }
        Lang.lang.put(key, langMessage);
    }



    public static class Args{
        private final String parameter;
        private final String value;

        public Args(Parameter parameter, String value){
            this.parameter = parameter.label;
            this.value = value;
        }

        public Args(String parameter, String value){
            this.parameter = parameter;
            this.value = value;
        }
    }

    public enum Parameter {
        VAR,PAGE, STATE, PLAYER, MESSAGE, MONEY, RANK, TIME, CHANNEL;
        private final String label;

        Parameter(){
            this.label = "<"+this.name().toLowerCase()+">";
        }
    }

    private static HashMap<String, HashMap<String, String>> lang;

    static {
        Lang.lang = new HashMap<>();
        DataCore.loadLang();
    }

    public static String getMessage(String key, String langPlayer, Args... args){
        String message = key;
        if(lang.containsKey(key)){
            HashMap<String, String> messageHashMap = lang.get(key);
            message = messageHashMap.get(langPlayer);
            for (Args argsList : args) {
                message = message.replaceAll(argsList.parameter, argsList.value);
            }
        }
        return message;
    }

    public static boolean isMessageLang(String key){
        return Lang.lang.containsKey(key);
    }
    public static void clearLang(){
        Lang.lang.clear();
    }
}
