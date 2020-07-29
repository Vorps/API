package net.vorps.api.nms;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class ActionBarBuilder {
    private String message;
    private int stay;

    public ActionBarBuilder(String message) {
        this.message = message;
    }

    public ActionBarBuilder withStay(int stay) {
        this.stay = stay;
        return this;
    }

    public ActionBarBuilder withMessage(String message) {
        this.message = message;
        return this;
    }


    public void sendTo(Player... playerList) {
        for(Player player : playerList){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(this.message));
        }
    }
}
