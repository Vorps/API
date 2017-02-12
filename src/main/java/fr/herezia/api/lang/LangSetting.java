package fr.herezia.api.lang;

import java.sql.ResultSet;
import java.util.HashMap;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class LangSetting {

    private static HashMap<String, LangSetting> listLangSetting = new HashMap<>();

    private String name;
    private String columnId;
    private String nameDisplay;

    public LangSetting(ResultSet result) throws SqlException {
        this.name = Database.SERVER.getDatabase().getString(result, 1);
        this.columnId = Database.SERVER.getDatabase().getString(result, 2);
        this.nameDisplay = Database.SERVER.getDatabase().getString(result, 3);
        LangSetting.listLangSetting.put(this.name, this);
    }

    public static HashMap<String, LangSetting> getListLangSetting(){
        return LangSetting.listLangSetting;
    }

    public String getName(){
        return this.name;
    }

    public String getColumnId(){
        return this.columnId;
    }

    public String getNameDisplay(){
        return this.nameDisplay;
    }

    public static void clearLangSetting(){
        LangSetting.listLangSetting.clear();
    }
}
