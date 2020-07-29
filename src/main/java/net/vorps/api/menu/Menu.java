package net.vorps.api.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Project Hub Created by Vorps on 03/03/2016 at 06:09.
 */
public abstract class Menu implements Listener{

    protected int[][] model;
    private ItemStack[] items;
    protected byte[] ids;
    protected @Getter Inventory menu;
    private ModelList[] modelList;
    private ArrayList<Integer> place = new ArrayList<>();

    /**
     * @param ids byte[] ids color of glass
     * @param menu Inventory menu create menu bukkit
     * @param model int[][] mode [1][2] 1 : Position of menu 2 : Position of table ids
     */
    protected Menu(byte[] ids, Inventory menu, int[][] model, Plugin plugin){
        this.menu = menu;
        this.model = model;
        this.ids = ids;
        if(ids != null){
            init();
            if(menu != null && model != null) constructModel();
        }
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Init Model
     */
    private void init(){
        this.items = new ItemStack[this.ids.length];
        //for(int i = 0; i < this.ids.length; i++) this.items[i] = new ItemBuilder(Material.BLACK_STAINED_GLASS).withData(this.ids[i]).withName(" ").get();
    }

    /**
     * Set item model
     */
    protected void constructModel(){
        for(int i = 0; i < this.model.length; i++) this.menu.setItem(this.model[i][0], this.items[model[i][1]]);
    }

    /**
     * Test if pos is exclude
     * @param i int
     * @param exclude int[]
     * @return boolean
     */
    private boolean content(int i, int[] exclude){
        if(exclude.length > 0){
            for(int y : exclude) if(y == i) return true;
            if(this.place.contains(i)) return true;
        }
        return false;
    }

    /**
     * Place item pos
     * @param i int
     * @param exclude int[]
     * @param state boolean
     * @return int
     */
    private int placeExclude(int i, int[] exclude, boolean state){
        if(exclude.length > 0 && content(i, exclude)){
            int var = 0;
            if(state){
                while(content(i+var, exclude) && (i+var)%9 != 0) var--;
                if((i+var)%9 != 8){
                    this.place.add(i+var);
                    return i + var;
                }
            } else {
                while(content(i+var, exclude) && (i+var)%9 != 8) var++;
                if((i+var)%9 != 0){
                    this.place.add(i+var);
                    return i + var;
                }
            }
        }
        return i;
    }

    /**
     * Get Nummber slot exclude per line
     * @param exclude int[]
     * @param lineSize int
     * @param i int
     * @return int
     */
    private int getVar(int[] exclude, int lineSize, int i){
        int y = 0;
        if(exclude != null) for(int var : exclude) if(var <= lineSize + i && var >= i) y++;
        return y;
    }

    /**
     * @param start int
     * @param size int
     */
    protected void init(int start, final int size, int listSize, int index, int lineSize, int[] exclude){
        this.modelList = new ModelList[((size-9)/9)-start/9];
        if(this.modelList.length > 0){
            int middle = (this.modelList.length+1)/2;
            this.modelList[0] = new ModelList(middle);
            for(int i = 1; i < this.modelList.length; i++){
                if((i & 1) == 1)
                    if((this.modelList.length & 1) == 1) this.modelList[i] = new ModelList(middle-((i+2)/2));
                    else this.modelList[i] = new ModelList(middle+((i+2)/2));
                else
                    if((this.modelList.length & 1) == 1) this.modelList[i] = new ModelList(middle+((i+1)/2));
                    else this.modelList[i] = new ModelList(middle-((i+1)/2));
            }
        }
        for(ModelList modelList : this.modelList) modelList.pos--;
        int max = 0;
        for(ModelList modelList : this.modelList){
            modelList.nbrMax =  lineSize-getVar(exclude, lineSize,  modelList.pos*9);
            int rest = listSize - (index+max);
            max += modelList.nbrMax;
            if(modelList.nbrMax >= rest){
                modelList.nbrMax = rest;
                break;
            }
        }
        ModelList[] modelLists = this.modelList.clone();
        Arrays.sort(modelLists);
        for(int i = 0; i < modelLists.length; i++){
            if(i == 0) modelLists[i].index = index;
            else modelLists[i].index = modelLists[i-1].nbrMax+modelLists[i-1].index;
        }
    }

    /**
     * Gestion des menu dynamique :: lineSize : lineSize == 7 || 9 -- exclude : exclude.lenght/line < lineSize
     * @param pos int
     * @param index int
     * @param list ArrayList<ItemBuilder>
     * @param lineSize int
     * @param exclude int[]
     * @param indexInit int
     * @param var int
     * @return boolean
     */
    boolean list(int pos, int index, final ArrayList<ItemBuilder> list , final int lineSize, final int[] exclude, int indexInit, int var){
        this.place.clear();
        if ((this.modelList != null ? this.modelList.length > pos : pos < (this.menu.getSize()-9)/9) && list.size() != 0){
            int i = this.modelList != null ? this.modelList[pos].pos*9 : pos*9;
            index = this.modelList != null ? this.modelList[pos].index : index;
            if ((var+indexInit + lineSize) - getVar(exclude, lineSize, i) < list.size()){
                i += ((9-lineSize) / 2 + ((lineSize & 1) == 0 ? 1 : 0));
                do {
                    if(!content(i, exclude)) {
                        var++;
                        this.menu.setItem(i, list.get(index++).get());
                    }
                } while (i++%9 !=  9 + (((9 - lineSize) / 2 + (lineSize & 1) == 0 ? 1 : 0) + lineSize - (lineSize == 9 ? 10 : 9)));
            } else {
                int rest = list.size() - (indexInit+var);
                if(this.modelList != null){
                    for(int e = -rest / 2; e <= rest/2 && rest > 1; e++){
                        if (e == 0) {
                            if((rest & 1) == 1) this.menu.setItem(placeExclude(i+4, exclude, true), list.get(index++).get());
                        } else this.menu.setItem(placeExclude((i + 4) + e, exclude, false), list.get(index++).get());
                    }
                    if ((rest & 1) == 1 && rest == 1) this.menu.setItem(placeExclude(i + 4, exclude, true), list.get(index).get());
                } else {
                    i += ((9-lineSize) / 2 + ((lineSize & 1) == 0 ? 1 : 0));
                    while (index < list.size()){
                        if(!content(i, exclude)) this.menu.setItem(i, list.get(index++).get());
                        i++;
                    }
                }
                return false;
            }
            return list(++pos, index, list, lineSize, exclude, indexInit, var);
        } else return index < list.size() && list.size() != 0;
    }

    protected abstract void interractInventory(InventoryClickEvent e);

    @EventHandler
    public void onInterractInventory(InventoryClickEvent e){
        if(e.getInventory().equals(this.menu) && e.getCurrentItem() != null) interractInventory(e);
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e){
        if(e.getInventory().equals(this.menu)) HandlerList.unregisterAll(this);
    }

    public enum Type{
        DYNAMIQUE,
        STATIC;
    }

    private static class ModelList implements Comparable<ModelList>{
        private int pos;
        private int index;
        private int nbrMax;

        @Override
		public int compareTo(ModelList modelList){
            return this.pos >= modelList.pos ? 1 : -1;
        }

        private ModelList(int pos){
            this.pos = pos;
        }
    }
}
