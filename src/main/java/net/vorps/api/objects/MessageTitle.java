package net.vorps.api.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.data.Data;
import net.vorps.api.data.DataCore;
import net.vorps.api.databases.Database;
import net.vorps.api.databases.DatabaseManager;
import net.vorps.api.lang.Lang;

/**
 * Project API Created by Vorps on 25/01/2017 at 14:42.
 */
public class MessageTitle {

    private final String title;
    private final String subTitle;

    public MessageTitle(ResultSet result) throws SQLException {
        this.title = result.getString(2);
        this.subTitle = result.getString(3);
        MessageTitle.messageTitleList.put(result.getString(1), this);
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
        DataCore.getInstance().loadMessageTitle();
    }


    public static void clear(){
        MessageTitle.messageTitleList.clear();
    }

    public static MessageTitle getMessageTitle(String name){
        return MessageTitle.messageTitleList.get(name);
    }
}
