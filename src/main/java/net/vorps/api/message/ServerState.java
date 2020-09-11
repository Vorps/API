package net.vorps.api.message;

import lombok.Getter;
import net.vorps.api.API;
import net.vorps.api.databases.Database;
import net.vorps.api.databases.DatabaseManager;

import java.sql.SQLException;

/**
 * Project Lobby Created by Vorps on 04/03/2016 at 18:32.
 */
public enum ServerState {
    WAITING,
    IN_START,
    IN_GAME,
    FINISH,
    STOP;

    public static boolean isState(ServerState state){
        return ServerState.state == state;
    }

    public static void setState(ServerState state) {
        ServerState.state = state;
        try{
            Database.BUNGEE.getDatabase().updateTable("server", "s_name = '" + API.getName() + "'", new DatabaseManager.Values("s_state", ServerState.state.name()));
        } catch (SQLException e){
            e.printStackTrace();
        }
        NetWorkClient.sendData();
    }

    private static @Getter ServerState state = WAITING;


}
