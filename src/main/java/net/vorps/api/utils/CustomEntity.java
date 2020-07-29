package net.vorps.api.utils;

import org.bukkit.entity.EntityType;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Project EclipseApi Created by Vorps on 08/11/2016 at 20:29.
 */
public enum CustomEntity {
    /*ENTITY_VILLAGER(EntityType.VILLAGER, CustomEntityVillager.class);

    CustomEntity(EntityType basicType, Class<? extends EntityInsentient> customClass) {
        registerEntity(basicType, customClass);
    }

    /**
     * Enregistrer une entité
     */
    /*
    @SuppressWarnings("deprecation")
	public static void registerEntity(EntityType entity, Class<? extends EntityInsentient> customClass){
        try {
            int id = entity.getTypeId();
            String name = entity.getName();
            List<Map<?, ?>> dataMap = new ArrayList<>();
            for (Field f : EntityTypes.class.getDeclaredFields()){
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())){
                    f.setAccessible(true);
                    dataMap.add((Map<?, ?>) f.get(null));
                }
            }

            if (dataMap.get(2).containsKey(id)){
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initEntities() {
        for(CustomEntity e : CustomEntity.values())
            e.name();
    }*/
}