package net.vorps.api.nms;

import org.bukkit.entity.Player;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class TablistBuilder {
    private String header;
    private String footer;

    public TablistBuilder(String header, String footer){
        this.header = header;
        this.footer = footer;
    }

    public TablistBuilder withHeader(String header) {
        this.header = header;
        return this;
    }

    public TablistBuilder withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public void sendTo(Player... playerList){
        for (Player player : playerList)
            player.setPlayerListHeaderFooter(this.header, this.footer);
    }
}
