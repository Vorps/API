package net.vorps.api.menu;

import net.vorps.api.API;
import net.vorps.api.listeners.AdvancedEventHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
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
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:41.
 */
@SuppressWarnings("deprecation")
public class ItemBuilder implements Serializable{

    private @Getter String uuid;
    private @Getter String name;
    private @Getter Material material;
    private @Getter int amount;
    private @Getter boolean hideEnchant;
    private @Getter List<String> lore = new ArrayList<>();
    private @Getter Map<String, Integer> enchantments = new HashMap<>();
    private @Getter String skullOwnerName;
    private @Getter Color color;
    private @Getter PotionType potionType;
    private @Getter short durability;

    {
        this.amount = 1;
        this.durability = -1;
    }


    public ItemBuilder withAction(Consumer<InventoryClickEvent> action){
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler public void onEvent(InventoryClickEvent e){
                if(get().isSimilar(e.getCurrentItem())){
                    action.accept(e);
                }
            }
        }, API.getPlugin());
        return this;
    }

    /**
     * @param material Material
     */
    public ItemBuilder(Material material) {
        this.material = material;
    }

    /**
     *
     * @param potionType PotionType
     */
    public ItemBuilder(PotionType potionType){
        this.material = Material.POTION;
        this.potionType = potionType;
    }

    public ItemBuilder(String namePlayer) {
        this.material = Material.PLAYER_HEAD;
        this.skullOwnerName = namePlayer;
    }

    public ItemBuilder(ItemBuilder itemBuilder){
        this.name = itemBuilder.name;
        this.material = itemBuilder.material;
        this.amount = itemBuilder.amount;
        this.hideEnchant = itemBuilder.hideEnchant;
        this.lore = itemBuilder.lore;
        this.enchantments = itemBuilder.enchantments;
        this.skullOwnerName = itemBuilder.skullOwnerName;
        this.color = itemBuilder.color;
        this.potionType = itemBuilder.potionType;
        this.durability = itemBuilder.durability;
    }

    public ItemBuilder(ItemStack itemStack) {
        this.name = itemStack.getItemMeta().getDisplayName();
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.lore = itemStack.getItemMeta().getLore();
        this.hideEnchant = itemStack.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS);
        for(Map.Entry<Enchantment, Integer> enchant : itemStack.getEnchantments().entrySet()) this.enchantments.put(enchant.getKey().getName(), enchant.getValue());
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
     * @param uuid String
     * @return ItemBuilder
     */
    public ItemBuilder withUUID(String uuid) {
        this.uuid = uuid;
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
    public ItemBuilder withEnchant(Enchantment enchant, int level){
        this.enchantments.put(enchant.getName(), level);
        return this;
    }

    /**
     * Return the item.
     * @return ItemStack
     */
    public ItemStack get() {
        ItemStack item = new ItemStack(this.material);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            if(this.name != null) meta.setDisplayName(this.name);
            if(this.potionType != null){
                ((PotionMeta)meta).setBasePotionData(new PotionData(this.potionType));
            }
            if(this.enchantments.size() > 0)
                for(Map.Entry<String, Integer> enchant : this.enchantments.entrySet())
                    meta.addEnchant(Enchantment.getByName(enchant.getKey()), enchant.getValue(), true);
            if(this.hideEnchant)
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if(this.color != null)
                ((LeatherArmorMeta)meta).setColor(this.color);
            else if(this.skullOwnerName != null) ((SkullMeta) meta).setOwner(this.skullOwnerName);

            meta.setLore(this.lore);
            item.setItemMeta(meta);
        }
        item.setAmount(this.amount);
        if(durability != -1) item.setDurability((short)  (item.getType().getMaxDurability()-durability));
        return item;
    }

    @Override
    public boolean equals(Object item) {
        return  (item instanceof ItemBuilder) &&
                (Objects.equals(this.name, ((ItemBuilder) item).name)) &&
                (Objects.equals(this.material, ((ItemBuilder) item).material)) &&
                this.amount == ((ItemBuilder)item).amount &&
                this.hideEnchant == ((ItemBuilder)item).hideEnchant &&
                this.lore.equals(((ItemBuilder)item).lore) &&
                this.enchantments.equals(((ItemBuilder)item).enchantments) &&
                (Objects.equals(this.skullOwnerName, ((ItemBuilder) item).skullOwnerName)) &&
                (Objects.equals(this.color, ((ItemBuilder) item).color)) &&
                (Objects.equals(this.potionType, ((ItemBuilder) item).potionType));
    }

}