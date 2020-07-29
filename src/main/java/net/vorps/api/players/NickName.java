package net.vorps.api.players;

import lombok.Getter;

import java.util.HashMap;

/**
 * Project Bungee Created by Vorps on 18/03/2017 at 16:06.
 */
public class NickName {

    private @Getter
    String namePlayer;
    private @Getter
    String nickName;

    public NickName(String player) {
        if (NickName.playerNickName.containsKey(player)) {
            this.namePlayer = player;
            this.nickName = NickName.playerNickName.get(player);
        } else if (NickName.nickNamePlayer.containsKey(player)) {
            this.namePlayer = NickName.playerNickName.get(player);
            this.nickName = player;
        } else {
            this.namePlayer = player;
            this.nickName = player;
        }
    }

    public static void nickName(String player, String nickName) {
        NickName.playerNickName.put(player, nickName);
        NickName.nickNamePlayer.put(nickName, player);
    }

    public static void remove(String player, String nickName) {
        NickName.playerNickName.remove(player);
        NickName.nickNamePlayer.remove(nickName);
    }


    private static HashMap<String, String> playerNickName;
    private static HashMap<String, String> nickNamePlayer;

    static {
        NickName.playerNickName = new HashMap<>();
        NickName.nickNamePlayer = new HashMap<>();
    }

    public static void clear() {
        NickName.playerNickName.clear();
        NickName.nickNamePlayer.clear();
    }


    public static String getNickName(String player) {
        return NickName.playerNickName.containsKey(player) ? NickName.playerNickName.get(player) : player;
    }

    public static String getNamePlayer(String nickName) {
        return NickName.nickNamePlayer.containsKey(nickName) ? NickName.nickNamePlayer.get(nickName) : nickName;
    }

    public static boolean isNickName(String player) {
        return NickName.playerNickName.containsKey(player);
    }

    public static boolean isNamePlayer(String namePlayer) {
        return NickName.nickNamePlayer.containsKey(namePlayer);
    }
}
