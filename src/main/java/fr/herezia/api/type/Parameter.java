package fr.herezia.api.type;

import java.io.Serializable;

/**
 * Project GameManager Created by Vorps on 05/04/2016 at 00:08.
 */
public class Parameter implements Serializable{

    private GameState status;
    private String map;
    private boolean canPlay;
    private boolean canSpect;
    private boolean online;

    public Parameter(GameState status, String map, boolean canPlay, boolean canSpect, boolean online){
        this.status = status;
        this.map = map;
        this.canPlay = canPlay;
        this.canSpect = canSpect;
        this.online = online;
    }

    public GameState getStatus(){
        return status;
    }

    public String getMap(){
        return map;
    }

    public boolean isCanPlay(){
        return canPlay;
    }

    public boolean isCanSpect(){
        return canSpect;
    }

    public boolean isOnline(){
        return online;
    }

    private static final long serialVersionUID = -4072024704477723941L;
}