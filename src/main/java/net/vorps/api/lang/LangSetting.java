package net.vorps.api.lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.data.Data;
import net.vorps.api.data.DataCore;
import net.vorps.api.databases.Database;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class LangSetting {

    private static HashMap<String, LangSetting> listLangSetting;

    private String name;
    private String columnId;
    private String nameDisplay;

    public LangSetting(ResultSet result) throws SQLException {
        this.name = result.getString(1);
        this.columnId = result.getString(2);
        this.nameDisplay = result.getString(3);
        LangSetting.listLangSetting.put(this.name, this);
    }

    static {
        LangSetting.listLangSetting = new HashMap<>();
        DataCore.getInstance().loadLangSetting();
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
