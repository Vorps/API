package net.vorps.api.databases;

import java.sql.SQLException;

import net.vorps.api.Exceptions.SqlException;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public enum Database{
    BUNGEE("bd_bungee"),
    CORE("bd_core");

    private DatabaseManager database;
    private String nameDataBase;
    private boolean state;

    public DatabaseManager getDatabase(){
        return this.database;
    }

    Database(String nameDataBase){
        this.nameDataBase = nameDataBase;
        try {
            this.database = new DatabaseManager(nameDataBase);
            this.state = true;
        } catch (SqlException e){
            this.state = false;
            System.out.println("Impossible de se connecter à la base de donnée : "+nameDataBase+".\nVeuillez créer la base de données : "+nameDataBase+".");
            e.printStackTrace();
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
        for(Database database : Database.values()) if(database.getDatabase() != null) database.getDatabase().closeDataBase();
    }
}