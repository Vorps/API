package fr.herezia.api.menu;

import fr.herezia.api.API;
import fr.herezia.api.listeners.AdvancedEventHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.*;

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:41.
 */
@SuppressWarnings("deprecation")
public class ItemBuilder {
    private @Getter String name;
    private @Getter Material material;
    private @Getter int amount;
    private @Getter boolean hideEnchant;
    private @Getter byte data;
    private @Getter int id;
    private @Getter List<String> lore = new ArrayList<>();
    private @Getter Map<Enchantment, Integer> enchantments = new HashMap<>();
    private @Getter String skullOwnerName;
    private @Getter Color color;
    private @Getter PotionType potionType;
    private @Getter short durability;
    private @Getter List<AdvancedEventHandler<?>> advancedEventHandlerList;

    {
        this.advancedEventHandlerList = new ArrayList<>();
        this.amount = 1;
        this.data = (byte) 0;
        this.durability = -1;
    }

    public ItemBuilder removeAdvancedEventHandlerList(AdvancedEventHandler<?>... advancedEventHandlerList){
        this.advancedEventHandlerList.removeAll(Arrays.asList(advancedEventHandlerList));
        return this;
    }

    public ItemBuilder withAdvancedEventHandlerList(AdvancedEventHandler<?>... advancedEventHandlerList){
        this.advancedEventHandlerList.addAll(Arrays.asList(advancedEventHandlerList));
        this.advancedEventHandlerList.forEach((AdvancedEventHandler<?> handler) -> {
            if(handler.getEventClass() == InventoryClickEvent.class)
                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler public void onEvent(InventoryClickEvent e){eventAction(e.getCurrentItem(), handler, e);}
                }, API.getInstance());
            if(handler.getEventClass() == PlayerDropItemEvent.class)
                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler public void onEvent(PlayerDropItemEvent e){eventAction(e.getItemDrop().getItemStack(), handler, e);}
                }, API.getInstance());
            if(handler.getEventClass() == PlayerInteractEvent.class)
                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler public void onEvent(PlayerInteractEvent e){eventAction(e.getItem(), handler, e);}
                }, API.getInstance());
            if(handler.getEventClass() == PlayerItemBreakEvent.class)
                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler public void onEvent(PlayerItemBreakEvent e){eventAction(e.getBrokenItem(), handler, e);}
                }, API.getInstance());
            if(handler.getEventClass() == PlayerItemConsumeEvent.class)
                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler public void onEvent(PlayerItemConsumeEvent e){eventAction(e.getItem(), handler, e);}
                }, API.getInstance());
            if(handler.getEventClass() == PlayerItemDamageEvent.class)
                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler public void onEvent(PlayerItemDamageEvent e){eventAction(e.getItem(), handler, e);}
                }, API.getInstance());
            if(handler.getEventClass() == PlayerPickupArrowEvent.class)
                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler public void onEvent(PlayerPickupArrowEvent e){eventAction(e.getItem().getItemStack(), handler, e);}
                }, API.getInstance());
            if(handler.getEventClass() == PlayerPickupItemEvent.class)
                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler public void onEvent(PlayerPickupItemEvent e){eventAction(e.getItem().getItemStack(), handler, e);}
                }, API.getInstance());
        });
        return this;
    }

    private <T extends AdvancedEventHandler> void eventAction(ItemStack itemStack, T handler, Event e){
        if(get().isSimilar(itemStack)) handler.onEvent(e);
    }

    /**
     * @param material Material
     */
    public ItemBuilder(Material material) {
        this.material = material;
        this.id = material.getId();
    }

    /**
     *
     * @param potionType PotionType
     */
    public ItemBuilder(PotionType potionType){
        this.potionType = potionType;
    }
    /**
     * @param id int
     */
    public ItemBuilder(int id) {
        this.id = id;
        this.material = Material.getMaterial(id);
    }

    public ItemBuilder(ItemBuilder itemBuilder){
        this.name = itemBuilder.name;
        this.material = itemBuilder.material;
        this.amount = itemBuilder.amount;
        this.hideEnchant = itemBuilder.hideEnchant;
        this.data = itemBuilder.data;
        this.id = itemBuilder.id;
        this.lore = itemBuilder.lore;
        this.enchantments = itemBuilder.enchantments;
        this.skullOwnerName = itemBuilder.skullOwnerName;
        this.color = itemBuilder.color;
        this.potionType = itemBuilder.potionType;
        this.durability = itemBuilder.durability;
    }

    /**
     * Skull Player
     * @param namePlayer String
     */
    public ItemBuilder(String namePlayer) {
        skullOwnerName = namePlayer;
    }


    public ItemBuilder(ItemStack itemStack) {
        this.name = itemStack.getItemMeta().getDisplayName();
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.data = itemStack.getData().getData();
        this.id =  itemStack.getType().getId();
        this.lore = itemStack.getItemMeta().getLore();
        this.durability = itemStack.getDurability();
        this.hideEnchant = itemStack.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS);
        this.enchantments = itemStack.getItemMeta().getEnchants();
        this.skullOwnerName = (itemStack.getItemMeta() instanceof SkullMeta) ? ((SkullMeta) itemStack.getItemMeta()).getOwner() : null;
        this.color = (itemStack.getItemMeta() instanceof LeatherArmorMeta) ? ((LeatherArmorMeta) itemStack.getItemMeta()).getColor() : null;
        this.potionType = (itemStack.getItemMeta() instanceof PotionMeta) ?  ((PotionMeta) itemStack.getItemMeta()).getBasePotionData().getType() : null;
    }

    /**
     * Color
     * @param color Color
     * @return ItemBuilder
     */
    public ItemBuilder withColor(Color color){
        this.color = color;
        return this;
    }
    /**
     * @param name String
     * @return ItemBuilder
     */
    public ItemBuilder withName(String name) {
        this.name = name;
        return this;
    }
    /**
     * @param amount int
     * @return ItemBuilder
     */
    public ItemBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * @param hideEnchantement boolean
     * @return ItemBuilder
     */
    public ItemBuilder hideEnchant(boolean hideEnchantement) {
        this.hideEnchant = hideEnchantement;
        return this;
    }

    /**
     *
     * @param durability
     * @return
     */
    public ItemBuilder withDurability(int durability){
        this.durability = (short) durability;
        return this;
    }
    /**
     * @param data byte
     * @return ItemBuilder
     */
    public ItemBuilder withData(byte data) {
        this.data = data;
        return this;
    }

    /**
     * @param lore String[]
     * @return ItemBuilder
     */
    public ItemBuilder withLore(String[] lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    /**
     * @param enchant Enchantment
     * @param level int
     * @return ItemBuilder
     */
    public ItemBuilder withEnchant(Enchantment enchant, int level) {
        this.enchantments.put(enchant, level);
        return this;
    }

    /**
     * Return the item.
     * @return ItemStack
     */
    public ItemStack get() {
        ItemStack item;
        if(color != null){
            item = new ItemStack(this.material, 1);
            LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
            lam.setColor(this.color);
            item.setItemMeta(lam);
            if(this.name != null) lam.setDisplayName(this.name);
            if(this.enchantments.size() > 0)
                for(Map.Entry<Enchantment, Integer> enchant : this.enchantments.entrySet())
                    lam.addEnchant(enchant.getKey(), enchant.getValue(), true);
            if(this.hideEnchant) lam.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            lam.setLore(this.lore);
            item.setItemMeta(lam);
        } else {
            if(skullOwnerName != null){
                item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta sm = (SkullMeta)item.getItemMeta();
                sm.setOwner(skullOwnerName);
                if(this.name != null) sm.setDisplayName(this.name);
                if(this.enchantments.size() > 0)
                    for(Map.Entry<Enchantment, Integer> enchant : this.enchantments.entrySet())
                        sm.addEnchant(enchant.getKey(), enchant.getValue(), true);
                if(this.hideEnchant)
                    sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                sm.setLore(this.lore);
                item.setItemMeta(sm);
            } else {
                if(id > 0) item = new ItemStack(this.id);
                else {
                    if(potionType != null) item = new Potion(potionType).toItemStack(1);
                    else item = new ItemStack(this.material);
                }
                item.setDurability(this.data);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(this.lore);
                if(this.name != null) meta.setDisplayName(this.name);
                if(this.enchantments.size() > 0)
                    for(Map.Entry<Enchantment, Integer> enchant : this.enchantments.entrySet())
                        meta.addEnchant(enchant.getKey(), enchant.getValue(), true);
                if(this.hideEnchant) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(meta);
            }
        }
        item.setAmount(this.amount);
        if(durability != -1) item.setDurability((short)  (item.getType().getMaxDurability()-durability));
        return item;
    }

    @Override
    public boolean equals(Object item) {
        return  (item instanceof ItemBuilder) &&
                (this.name != null ? this.name.equals(((ItemBuilder)item).name) : ((ItemBuilder)item).name == null) &&
                (this.material != null ? this.material.equals(((ItemBuilder)item).material) : ((ItemBuilder)item).material == null) &&
                this.amount == ((ItemBuilder)item).amount &&
                this.hideEnchant == ((ItemBuilder)item).hideEnchant &&
                this.data == ((ItemBuilder)item).data &&
                this.id == ((ItemBuilder)item).id &&
                this.lore.equals(((ItemBuilder)item).lore) &&
                this.enchantments.equals(((ItemBuilder)item).enchantments) &&
                (this.skullOwnerName != null ? this.skullOwnerName.equals(((ItemBuilder)item).skullOwnerName) : ((ItemBuilder)item).skullOwnerName == null) &&
                (this.color != null ? this.color.equals(((ItemBuilder)item).color) : ((ItemBuilder)item).color == null) &&
                (this.potionType != null ? this.potionType.equals(((ItemBuilder)item).potionType) : ((ItemBuilder)item).potionType == null);
    }

    @Override
    public int hashCode() {
        return id+data;
    }
}