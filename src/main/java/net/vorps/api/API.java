package net.vorps.api;

import lombok.Getter;
import lombok.Setter;
import net.vorps.api.data.Data;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class API {

    private static  @Getter @Setter JavaPlugin plugin;

    private static @Getter String name;

    static {
        try {
            name = new File(".").getCanonicalFile().getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
