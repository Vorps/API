package fr.herezia.api.databases;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.herezia.api.API;
import fr.herezia.api.Exceptions.SqlException;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public enum Database{
    SERVER("bd_server"),
    RUSH_VOLCANO("bd_rush_volcano"),
    BUNGEE("bd_bungee"),
    SKYWARS("bd_skywars");

    private DatabaseManager database;
    private String nameDataBase;

    public DatabaseManager getDatabase(){
        return this.database;
    }

    Database(String nameDataBase){
        if(API.getInstance() != null){
            this.nameDataBase = nameDataBase;
            try {
                this.database = new DatabaseManager(nameDataBase);
            } catch (SqlException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Test connection DataBase
     */
    public void tryConnectionDatabase(){
        try {
            this.database.sendRequest("SHOW tables");
        } catch (Exception e) {
            try {
                this.database.getConnection().close();
                this.database = new DatabaseManager(this.nameDataBase);
            } catch (SqlException | SQLException err){
                err.printStackTrace();
            }
        }
    }

    /**
     * Close all current session
     */
    public static void closeAllDataBases(){
        for(Database database :  Database.values()) if(database.getDatabase() != null) database.getDatabase().closeDataBase();
    }
}