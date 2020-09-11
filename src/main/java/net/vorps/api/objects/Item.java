package net.vorps.api.objects;

import net.vorps.api.data.DataCore;
import net.vorps.api.menu.ItemBuilder;
import org.bukkit.potion.PotionType;

import net.vorps.api.lang.Lang;
import net.vorps.api.lang.LangSetting;
import net.vorps.api.utils.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project Hub Created by Vorps on 17/05/2016 at 20:51.
 */
public class Item {

    private final HashMap<String, String> label;
    private HashMap<String, String[]> lore;
    private final ItemBuilder item;

    /**
     * Load item bdd
     * @param result ResultSet
     * @throws SQLException
     */
    public Item(final ResultSet result) throws SQLException {
        this.label = new HashMap<>();
        ItemBuilder item;
        if(result.getString(5) != null) item = new ItemBuilder(result.getString(5));
        else if(result.getString(6) != null) item = new ItemBuilder(PotionType.valueOf(result.getString(6)));
        else {
            item = new ItemBuilder(result.getString(3));
        }
        String labelTmp = result.getString(2);
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.label.put(langSetting.getName(), Lang.getMessage(labelTmp, langSetting.getName()));
        String loreTmp = result.getString(8);
        if(loreTmp != null){
            this.lore = new HashMap<>();
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.lore.put(langSetting.getName(), lore(Lang.getMessage(loreTmp,  langSetting.getName())));
        }
        item.withAmount(result.getInt(7));
        enchant(result.getString(9), item);
        int durability = result.getInt(10);
        if(durability != 0) item.withDurability(durability);
        String color =result.getString(11);
        if(color != null) item.withColor(Color.valueOf(color).getColor());
        item.hideEnchant(result.getBoolean(12));
        this.item = item;
        Item.listItem.put(result.getString(1), this);
    }

    /**
     * Return item
     * @param lang String
     * @return me.vorps.snowar.menu.ItemBuilder
     */
    private ItemBuilder getItem(final String lang){
        ItemBuilder item = new ItemBuilder(this.item);
        item.withName(this.label.get(lang));
        if(this.lore != null) item.withLore(this.lore.get(lang));
        return item;
    }

    private static HashMap<String, Item> listItem;

    static {
        Item.listItem = new HashMap<>();
        DataCore.loadItem();
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
	private static void enchant(final String enchentment, final ItemBuilder item){
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
                /*if(var[0] != -1 && var[1] != -1){
                    item.withEnchant(Enchantment.getById(var[0]), var[1]);
                    var[0] = -1;
                    var[1] = -1;
                }*/
            }
        }
    }


    public static ItemBuilder getItem(String name, String lang){
        return Item.listItem.get(name).getItem(lang);
    }


    /**
     * Clear all Item
     */
    public static void clear(){
        Item.listItem.clear();
    }
}
