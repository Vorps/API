package fr.herezia.api.nms;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class TitleBuilder {
    private String title;
    private String subtitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;

    public TitleBuilder(String title, String subtitle){
        this.title = title;
        this.subtitle = subtitle;
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

    private IChatBaseComponent buildChatComponent(String msg) {
        return IChatBaseComponent.ChatSerializer.a("{'text':'" + msg + "'}");
    }

    public void sendTo(Player... playerList) {
        if(this.title != null) {
            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, buildChatComponent(this.title), this.fadeIn, this.stay,
                    this.fadeOut);
            for(Player player : playerList) ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
        }

        if(this.subtitle != null) {
            PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, buildChatComponent(this.subtitle), this.fadeIn, this.stay,
                    this.fadeOut);
            for(Player player : playerList) ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitlePacket);
        }
    }
}
