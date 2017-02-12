package fr.herezia.api.utils;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:41.
 */
public class Permission {
    private Map<String, PermissionAttachment> permissions = new HashMap<>();
    private Plugin registeringPlugin;
    private static Permission instance;

    public static Permission getInstance(Plugin plugin){
        if(instance == null) instance = new Permission(plugin);
        return instance;
    }

    private Permission(Plugin plugin) {
        registeringPlugin = plugin;
        permissions = new HashMap<>();
    }

    public void addPermission(Player p, String permission) {
        if (!permissions.containsKey(p.getName())) {
            permissions.put(p.getName(), p.addAttachment(registeringPlugin));
        }
        permissions.get(p.getName()).setPermission(permission, true);
    }

    public void denyPermission(Player p, String permission) {
        if (!permissions.containsKey(p.getName())) {
            permissions.put(p.getName(), p.addAttachment(registeringPlugin));
        }
        permissions.get(p.getName()).setPermission(permission, false);
    }

    public void unsetAllPermissions(String pName) {
        try {
            permissions.remove(pName).remove();
        } catch (NullPointerException e) {
            //
        }
    }

    public void removeAllPermissions() {
        String[] names  = permissions.keySet().toArray(new String[permissions.size()]);
        for (String name : names) {
            this.unsetAllPermissions(name);
        }
    }
}

