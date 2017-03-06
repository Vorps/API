package fr.herezia.api.objects;

import fr.herezia.api.data.Data;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Project RushVolcano Created by Vorps on 23/06/2016 at 05:14.
 */
public class Firework {

    private String name;
    private String[] type;
    private String[] mainColor;
    private String[] fadeColor;
    private double speed;
    private double time;
    private boolean random;
    private Location location;
    private int task;
    private int i;
    private int y;
    private int z;

    public Firework(ResultSet resultSet) throws SqlException {
        this.name = Database.RUSH_VOLCANO.getDatabase().getString(resultSet, 1);
        this.type = this.data(Database.RUSH_VOLCANO.getDatabase().getString(resultSet, 2));
        this.mainColor = this.data(Database.RUSH_VOLCANO.getDatabase().getString(resultSet, 3));
        this.fadeColor = this.data(Database.RUSH_VOLCANO.getDatabase().getString(resultSet, 4));
        this.speed = Database.RUSH_VOLCANO.getDatabase().getDouble(resultSet, 5);
        this.time = Database.RUSH_VOLCANO.getDatabase().getDouble(resultSet, 6);
        this.random = Database.RUSH_VOLCANO.getDatabase().getBoolean(resultSet, 7);
        this.location = fr.herezia.api.objects.Location.getLocation(Database.RUSH_VOLCANO.getDatabase().getString(resultSet, 8));
        Firework.listFirework.put(this.name, this);
    }

    private String[] data(final String data){
        ArrayList<String> dataTab = new ArrayList<>();
        int y = 0;
        if(data != null){
            for(int i = 0; i < data.length(); i++)
                if(data.charAt(i) == ';'){
                    dataTab.add(data.substring(y, i));
                    y = i+1;
                }
            return dataTab.toArray(new String[dataTab.size()]);
        }
        return new String[0];
    }

    public void start(Plugin plugin){
        this.i = 0;
        this.y = 0;
        this.z = 0;
        Random random1 = new Random();
        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
			public void run() {
                org.bukkit.entity.Firework f = Bukkit.getWorlds().get(0).spawn(location, org.bukkit.entity.Firework.class);
                FireworkMeta fm = f.getFireworkMeta();
                fm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(FireworkEffect.Type.valueOf((random ? type[random1.nextInt(type.length)] : type[i]).toUpperCase())).withColor(fr.herezia.api.utils.Color.valueOf((random ? mainColor[random1.nextInt(mainColor.length)] : mainColor[y]).toUpperCase()).getColor()).withFade(fr.herezia.api.utils.Color.valueOf((random ? fadeColor[random1.nextInt(fadeColor.length)] : fadeColor[z]).toUpperCase()).getColor()).build());
                fm.setPower(3);
                f.setFireworkMeta(fm);
                if(time <= 0) stop();
                time -= speed;
                if(!random) {
                    if (++i == type.length) i = 0;
                    if (++y == mainColor.length) y = 0;
                    if (++z == fadeColor.length) z = 0;
                }
            }
        },0L, (int) (speed*20));
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(this.task);
    }

    private static HashMap<String, Firework> listFirework;

    static {
        Firework.listFirework = new HashMap<>();
        Data.getInstance().loadFireWork();
    }

    public static void clear(){
        Firework.listFirework.clear();
    }

    public static Firework getFirework(String name){
        return Firework.listFirework.get(name);
    }
}
