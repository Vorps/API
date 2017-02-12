package fr.herezia.api.objects;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionType;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import fr.herezia.api.lang.Lang;
import fr.herezia.api.lang.LangSetting;
import fr.herezia.api.utils.Color;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project Hub Created by Vorps on 17/05/2016 at 20:51.
 */
public class Item {

    private HashMap<String, String> label;
    private HashMap<String, String[]> lore;
    private fr.herezia.api.menu.ItemBuilder item;

    /**
     * Load item bdd
     * @param result ResultSet
     * @throws SqlException
     */
    public Item(final ResultSet result) throws SqlException {
        this.label = new HashMap<>();
        fr.herezia.api.menu.ItemBuilder item;
        if(Database.RUSH_VOLCANO.getDatabase().getString(result, 5) != null) item = new fr.herezia.api.menu.ItemBuilder(Database.RUSH_VOLCANO.getDatabase().getString(result, 5));
        else if(Database.RUSH_VOLCANO.getDatabase().getString(result, 6) != null) item = new fr.herezia.api.menu.ItemBuilder(PotionType.valueOf(Database.RUSH_VOLCANO.getDatabase().getString(result, 6)));
        else {
            item = new fr.herezia.api.menu.ItemBuilder(Database.RUSH_VOLCANO.getDatabase().getInt(result, 3));
            item.withData((byte) Database.RUSH_VOLCANO.getDatabase().getInt(result, 4));
        }
        String labelTmp = Database.RUSH_VOLCANO.getDatabase().getString(result, 2);
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.label.put(langSetting.getName(), Lang.getMessage(labelTmp, langSetting.getName()));
        String loreTmp = Database.RUSH_VOLCANO.getDatabase().getString(result, 8);
        if(loreTmp != null){
            this.lore = new HashMap<>();
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.lore.put(langSetting.getName(), lore(Lang.getMessage(loreTmp,  langSetting.getName())));
        }
        item.withAmount(Database.RUSH_VOLCANO.getDatabase().getInt(result, 7));
        enchant(Database.RUSH_VOLCANO.getDatabase().getString(result, 9), item);
        int durability = Database.RUSH_VOLCANO.getDatabase().getInt(result, 10);
        if(durability != 0) item.withDurability(durability);
        String color = Database.RUSH_VOLCANO.getDatabase().getString(result, 11);
        if(color != null) item.withColor(Color.valueOf(color).getColor());
        item.hideEnchant(Database.RUSH_VOLCANO.getDatabase().getBoolean(result, 12));
        this.item = item;
        Item.listItem.put(Database.RUSH_VOLCANO.getDatabase().getString(result, 1), this);
    }

    /**
     * Return item
     * @param lang String
     * @return me.vorps.snowar.menu.ItemBuilder
     */
    private fr.herezia.api.menu.ItemBuilder getItem(final String lang){
        fr.herezia.api.menu.ItemBuilder item = new fr.herezia.api.menu.ItemBuilder(this.item);
        item.withName(this.label.get(lang));
        if(this.lore != null) item.withLore(this.lore.get(lang));
        return item;
    }

    private static HashMap<String, Item> listItem;

    static {
        Item.listItem = new HashMap<>();
    }

    /**
     * return Lore
     * @param lore String
     * @return String[]
     */
    private static String[] lore(final String lore){
        ArrayList<String> loreTab = new ArrayList<>();
        int y = 0;
        if(lore != null){
            for(int i = 0; i < lore.length(); i++)
                if(lore.charAt(i) == ';'){
                    loreTab.add(lore.substring(y, i));
                    y = i+1;
                }
            return loreTab.toArray(new String[loreTab.size()]);
        }
        return new String[0];
    }

    /**
     * Load enchant item
     * @param enchentment String
     * @param item me.vorps.snowar.menu.ItemBuilder
     */
    @SuppressWarnings("deprecation")
	private static void enchant(final String enchentment, final fr.herezia.api.menu.ItemBuilder item){
        int y = 0;
        int[] var = new int[2];
        var[0] = -1;
        var[1] = -1;
        if(enchentment != null){
            for(int i = 0; i < enchentment.length(); i++){
                if(enchentment.charAt(i) == ':'){
                    if(y != 0){
                        y++;
                    }
                    var[0] = Integer.parseInt(enchentment.substring(y, i));
                    y = i;
                }
                if(enchentment.charAt(i) == ';'){
                    var[1] = Integer.parseInt(enchentment.substring(y+1, i));
                    y = i;
                }
                if(var[0] != -1 && var[1] != -1){
                    item.withEnchant(Enchantment.getById(var[0]), var[1]);
                    var[0] = -1;
                    var[1] = -1;
                }
            }
        }
    }


    public static fr.herezia.api.menu.ItemBuilder getItem(String name, String lang){
        return Item.listItem.get(name).getItem(lang);
    }


    /**
     * Clear all Item
     */
    public static void clear(){
        Item.listItem.clear();
    }
}
