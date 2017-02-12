package fr.herezia.api.utils;

import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EntityVillager;
import net.minecraft.server.v1_9_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_9_R2.World;

/**
 * Project EclipseApi Created by Vorps on 08/11/2016 at 20:33.
 */
public class CustomEntityVillager extends EntityVillager{

    public CustomEntityVillager(World world) {
        super(world, 1);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
    }

}
