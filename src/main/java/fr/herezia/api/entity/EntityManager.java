package fr.herezia.api.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import fr.herezia.api.utils.CustomEntity;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class EntityManager {
	
	private static void disableAI(Entity entity) {
        net.minecraft.server.v1_9_R2.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEntity.e(new NBTTagCompound());
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.e(1.0F);
        nmsEntity.f(tag);
    }
	
	public static void entityManager(EntityType ent, Location locEnt, String name, Villager.Profession profession){
        CustomEntity.initEntities();
		Villager entity = (Villager)Bukkit.getWorlds().get(0).spawnEntity(locEnt, ent);
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        entity.setProfession(profession);
        entity.setCollidable(false);
        EntityManager.disableAI(entity);
	}

    public static void removeEntity(){
        Bukkit.getWorlds().get(0).getEntities().forEach((Entity entity) -> {
            if(entity.getType() == EntityType.VILLAGER){
                entity.remove();
            }
        });
    }
}
