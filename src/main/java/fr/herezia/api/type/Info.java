package fr.herezia.api.type;

import java.io.IOException;

import fr.herezia.api.utils.Serialize;

public class Info {
	
	public static void setInfo(boolean canPlay, boolean canSpectator, String map, boolean online){
        try {
            Serialize.serializable(new Parameter(GameState.getState(), map, canPlay, canSpectator, online), System.getProperty("user.dir")+System.getProperty("file.separator")+"game_parameter"+System.getProperty("file.separator")+"parameter.ser");
        } catch (IOException e){
            e.printStackTrace();
        }
	}
}