package fr.herezia.api.utils;

import java.sql.ResultSet;
import java.util.HashMap;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import fr.herezia.api.lang.Lang;

/**
 * Project API Created by Vorps on 25/01/2017 at 14:42.
 */
public class MessageTitle {

    private String title;
    private String subTitle;

    public MessageTitle(ResultSet result) throws SqlException{
        this.title = Database.SERVER.getDatabase().getString(result, 2);
        this.subTitle = Database.SERVER.getDatabase().getString(result, 3);
        MessageTitle.messageTitleList.put(Database.SERVER.getDatabase().getString(result, 1), this);
    }

    public String getTitle(String lang, Lang.Args... args){
        return Lang.getMessage(this.title, lang, args);
    }

    public String getSubTitle(String lang, Lang.Args... args){
        return Lang.getMessage(this.subTitle, lang, args);
    }

    private static HashMap<String, MessageTitle> messageTitleList;

    static  {
        MessageTitle.messageTitleList = new HashMap<>();
    }

    public static void clear(){
        MessageTitle.messageTitleList.clear();
    }

    public static MessageTitle getMessageTitle(String name){
        return MessageTitle.messageTitleList.get(name);
    }
}
