package fr.herezia.api.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import fr.herezia.api.lang.Lang;

import java.util.ArrayList;

/**
 * Project FortyCubeAPIBukkit Created by Vorps on 28/04/2016 at 17:21.
 */
public abstract class MenuRecursive extends Menu {
    protected int page;
    private String lang;
    protected ArrayList<ItemBuilder> list;
    private int lineSize;
    private int start;
    private int[] exclude;
    private Type type;

    /**
     * Initialize menu
     * @param player Player
     * @param page int
     */
    public abstract void initMenu(Player player, int page);

    /**
     * Contruct menu Recursive :: lineSize : lineSize == 9 || 7  -- start : start%9 == 0
     * @param ids byte
     * @param menu Inventory
     * @param model int[][]
     * @param list  ArrayList<ItemBuilder>
     * @param lang String
     * @param lineSize int
     * @param start int
     * @param exclude int[]
     * @param type Type
     * @param plugin Plugin
     */
    public MenuRecursive(byte[] ids, Inventory menu, int[][] model, ArrayList<ItemBuilder> list, String lang, int lineSize, int start , int[] exclude, Type type, Plugin plugin){
        this(ids, menu, model, list, lang, lineSize, start, type, plugin);
        this.exclude = exclude;
    }

    /**
     * Contruct menu Recursive :: lineSize : lineSize == 9 || 7  -- start : start%9 == 0
     * @param ids byte
     * @param menu Inventory
     * @param model int[][]
     * @param list  ArrayList<ItemBuilder>
     * @param lang String
     * @param lineSize int
     * @param start int
     * @param type Type
     * @param plugin Plugin
     */
    public MenuRecursive(byte[] ids, Inventory menu, int[][] model, ArrayList<ItemBuilder> list, String lang, int lineSize, int start, Type type, Plugin plugin){
        super(ids, menu, model, plugin);
        this.exclude = new int[0];
        this.list = list;
        this.lang = lang;
        this.lineSize = lineSize;
        this.start = start;
        this.page = 1;
        this.type = type;
    }

    /**
     * Select page :: (page-1)*((lineSize*(((size-9)/9)-(start/9)))-(exclude.lenght == 0 ? 0 : exclude.length)) <= list.size()
     * @param page int
     */
    protected void getPage(int page){
        if(this.ids != null && this.model != null) constructModel();
        int index = (page-1)*((this.lineSize*(((super.menu.getSize()-9)/9)-(this.start/9)))-(this.exclude.length == 0 ? 0 : this.exclude.length));
        if(this.type == Type.DYNAMIQUE) super.init(start, super.menu.getSize(), this.list.size(), index, this.lineSize, this.exclude);
        super.menu.setItem(super.menu.getSize()-9, new ItemBuilder(Material.ARROW).withName(Lang.getMessage("RECURSIVE.INVENTORY.QUIT", this.lang)).get());
        this.page = page;
        if(page > 1) super.menu.setItem(super.menu.getSize()-2, new ItemBuilder(Material.EMPTY_MAP).withName(Lang.getMessage("RECURSIVE.INVENTORY.BACK", this.lang, new Lang.Args(Lang.Parameter.PAGE, ""+(page-1)))).get());
        if(list(0, index, this.list, this.lineSize, this.exclude, index, 0)) super.menu.setItem(super.menu.getSize()-1, new ItemBuilder(Material.PAPER).withName(Lang.getMessage("RECURSIVE.INVENTORY.NEXT", this.lang, new Lang.Args(Lang.Parameter.PAGE, ""+(page+1)))).get());
    }
}
