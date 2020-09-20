package net.vorps.api.objects;

import net.vorps.api.data.DataCore;
import net.vorps.api.menu.ItemBuilder;
import org.bukkit.Material;
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

    private final String label;
    private String lore;
    private final ItemBuilder item;

    /**
     * Load item bdd
     * @param result ResultSet
     * @throws SQLException
     */
    public Item(final ResultSet result) throws SQLException {
        ItemBuilder item;
        if(result.getString("i_skull_owner") != null) item = new ItemBuilder(result.getString("i_skull_owner"));
        else if(result.getString("i_potion_type") != null) item = new ItemBuilder(PotionType.valueOf(result.getString("i_potion_type")));
        else item = new ItemBuilder(Material.getMaterial(result.getString("i_material").toUpperCase()));

        this.label = result.getString("i_label");
        this.lore = result.getString("i_lore");

        item.withAmount(result.getInt("i_amount"));
        enchant(result.getString("i_enchant"), item);
        int durability = result.getInt("i_durability");
        if(durability != 0) item.withDurability(durability);
        String color = result.getString("i_color");
        if(color != null) item.withColor(Color.valueOf(color).getColor());
        item.hideEnchant(result.getBoolean("i_hide_enchant"));
        this.item = item;
        Item.listItem.put(result.getString("i_name"), this);
    }

    /**
     * Return item
     * @param lang String
     * @return me.vorps.snowar.menu.ItemBuilder
     */
    private ItemBuilder getItem(final String lang){
        ItemBuilder item = new ItemBuilder(this.item);
        item.withName(Lang.getMessage(this.label, lang));
        if(this.lore != null) item.withLore(Lang.getMessage(this.lore, lang).split(";"));
        return item;
    }

    private static HashMap<String, Item> listItem;

    static {
        Item.listItem = new HashMap<>();
        DataCore.loadItem();
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
    public static boolean isItem(String name){
        return Item.listItem.containsKey(name);
    }

    /**
     * Clear all Item
     */
    public static void clear(){
        Item.listItem.clear();
    }
}
