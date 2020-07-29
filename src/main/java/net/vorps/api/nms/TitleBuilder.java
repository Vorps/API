package net.vorps.api.nms;


import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class TitleBuilder {
    private String title;
    private String subtitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;

    public TitleBuilder(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = 5;
        this.stay = 1;
        this.fadeOut = 5;
    }

    public TitleBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TitleBuilder withSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public TitleBuilder withFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public TitleBuilder withStay(int stay) {
        this.stay = stay;
        return this;
    }

    public TitleBuilder withFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    public void sendTo(Player... playerList) {
        for (Player player : playerList)
            player.sendTitle(this.title, this.subtitle, this.fadeIn, this.stay, this.fadeOut);
    }
}
