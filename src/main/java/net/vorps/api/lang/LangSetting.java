package net.vorps.api.lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import net.vorps.api.data.DataCore;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class LangSetting {

    private static HashMap<String, LangSetting> listLangSetting;

    private final @Getter String name;
    private final @Getter String columnId;
    private final @Getter String nameDisplay;

    public LangSetting(ResultSet result) throws SQLException {
        this.name = result.getString(1);
        this.columnId = result.getString(2);
        this.nameDisplay = result.getString(3);
        LangSetting.listLangSetting.put(this.name, this);
    }

    static {
        LangSetting.listLangSetting = new HashMap<>();
        DataCore.loadLangSetting();
    }

    public static LangSetting getLangSetting(String nameLang){
        return LangSetting.listLangSetting.get(nameLang);
    }

    public static List<String> getListLangSetting(){
        return new ArrayList<String>(LangSetting.listLangSetting.keySet());
    }
    public static void clearLangSetting(){
        LangSetting.listLangSetting.clear();
    }
}
