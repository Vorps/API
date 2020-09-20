package net.vorps.api.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import net.vorps.api.lang.Lang;

import java.util.ArrayList;
import java.util.UUID;

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
     * @param uuid UUID
     * @param page int
     */
    public abstract void initMenu(UUID uuid, int page);

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
    public MenuRecursive(UUID uuid, byte[] ids, Inventory menu, int[][] model, ArrayList<ItemBuilder> list, String lang, int lineSize, int start , int[] exclude, Type type, Plugin plugin){
        this(uuid, ids, menu, model, list, lang, lineSize, start, type, plugin);
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
    public MenuRecursive(UUID uuid, byte[] ids, Inventory menu, int[][] model, ArrayList<ItemBuilder> list, String lang, int lineSize, int start, Type type, Plugin plugin){
        super(uuid, ids, menu, model, plugin);
        this.exclude = new int[0];
        this.list = list;
        this.lang = lang;
        this.lineSize = lineSize;
        this.start = start;
        this.page = 1;
        this.type = type;
        menu.clear();
        this.initMenu(uuid, 1);
        this.getPage(this.page);
    }

    /**
     * Select page :: (page-1)*((lineSize*(((size-9)/9)-(start/9)))-(exclude.lenght == 0 ? 0 : exclude.length)) <= list.size()
     * @param page int
     */
    protected void getPage(int page){
        if(this.ids != null && this.model != null) constructModel();
        int index = (page-1)*((this.lineSize*(((super.menu.getSize()-9)/9)-(this.start/9)))-(this.exclude.length));
        if(this.type == Type.DYNAMIQUE) super.init(start, super.menu.getSize(), this.list.size(), index, this.lineSize, this.exclude);
        super.menu.setItem(super.menu.getSize()-9, new ItemBuilder(Material.ARROW).withName(Lang.getMessage("RECURSIVE.INVENTORY.QUIT", this.lang)).get());
        this.page = page;
        if(page > 1) super.menu.setItem(super.menu.getSize()-2, new ItemBuilder(Material.MAP).withName(Lang.getMessage("RECURSIVE.INVENTORY.BACK", this.lang, new Lang.Args(Lang.Parameter.PAGE, ""+(page-1)))).get());
        if(list(0, index, this.list, this.lineSize, this.exclude, index, 0)) super.menu.setItem(super.menu.getSize()-1, new ItemBuilder(Material.PAPER).withName(Lang.getMessage("RECURSIVE.INVENTORY.NEXT", this.lang, new Lang.Args(Lang.Parameter.PAGE, ""+(page+1)))).get());
    }

    @EventHandler
    public void onInteractInventory(InventoryClickEvent e){
        if(e.getInventory().equals(this.menu) && e.getCurrentItem() != null) {
            switch (e.getCurrentItem().getType()) {
                case ARROW:
                    this.back(e.getWhoClicked().getUniqueId());
                    break;
                case PAPER:
                    menu.clear();
                    initMenu(e.getWhoClicked().getUniqueId(), ++page);
                    getPage(this.page);
                    ((Player)e.getWhoClicked()).updateInventory();
                    break;
                case MAP:
                    menu.clear();
                    initMenu(e.getWhoClicked().getUniqueId(), --page);
                    getPage(this.page);
                    ((Player)e.getWhoClicked()).updateInventory();
                    break;
                default:
                    interactInventory(e.getWhoClicked().getUniqueId(), e.getCurrentItem().getType(), e);
                    break;
            }
        }
    }
}
