package net.vorps.api.objects;

import net.vorps.api.data.DataCore;
import net.vorps.api.utils.Color;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Project RushVolcano Created by Vorps on 23/06/2016 at 05:14.
 */
public class Firework{

    private final String[] type;
    private final String[] mainColor;
    private final String[] fadeColor;
    private final double speed;
    private double time;
    private final boolean random;
    private final Location location;
    private int task;
    private int i;
    private int y;
    private int z;

    public Firework(ResultSet result) throws SQLException {
        this.type = this.data(result.getString(2));
        this.mainColor = this.data(result.getString(3));
        this.fadeColor = this.data(result.getString(4));
        this.speed =result.getDouble(5);
        this.time = result.getDouble(6);
        this.random = result.getBoolean(7);
        this.location = net.vorps.api.objects.Location.getLocation(result.getString(8));
        Firework.listFirework.put(result.getString(1), this);
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
                fm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(FireworkEffect.Type.valueOf((random ? type[random1.nextInt(type.length)] : type[i]).toUpperCase())).withColor(net.vorps.api.utils.Color.valueOf((random ? mainColor[random1.nextInt(mainColor.length)] : mainColor[y]).toUpperCase()).getColor()).withFade(Color.valueOf((random ? fadeColor[random1.nextInt(fadeColor.length)] : fadeColor[z]).toUpperCase()).getColor()).build());
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
        DataCore.loadFireWork();
    }

    public static void clear(){
        Firework.listFirework.clear();
    }

    public static Firework getFirework(String name){
        return Firework.listFirework.get(name);
    }
}
