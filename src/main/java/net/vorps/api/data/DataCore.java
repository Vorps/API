package net.vorps.api.data;

import net.vorps.api.databases.Database;
import net.vorps.api.databases.DatabaseManager;
import net.vorps.api.lang.Lang;
import net.vorps.api.lang.LangSetting;
import net.vorps.api.objects.*;
import net.vorps.api.particles.Particle;
import net.vorps.api.objects.Money;
import net.vorps.api.objects.Rank;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataCore extends Data{


    private static DatabaseManager database;

    public static void setDatabase(DatabaseManager database){
        DataCore.database = database;
    }


    @DataReload
    public static void loadParticle(){
        Particle.clear();
        ResultSet resultSet;
        try {
            resultSet = DataCore.database.getData("particle");
            while(resultSet != null && resultSet.next()) new Particle(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @DataReload
    public static void loadLangSetting(){
        LangSetting.clearLangSetting();
        ResultSet resultSet;
        try {
            resultSet = Database.BUNGEE.getDatabase().getData("lang_setting");
            while(resultSet != null && resultSet.next()) new LangSetting(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @DataReload
    public static void loadLang(){
        Lang.clearLang();
        ResultSet resultSet;
        try {
            resultSet = DataCore.database.getData("lang");
            while(resultSet != null && resultSet.next()) new Lang(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @DataReload
    public static void loadMoney(){
        Money.clear();
        try {
            ResultSet resultSet = Database.BUNGEE.getDatabase().getData("money");
            while(resultSet != null && resultSet.next()) new Money(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @DataReload
    public static void loadInteractMessage(){
        InteractMessage.clear();
        try {
            ResultSet resultSet = DataCore.database.getData("interact_message");
            while (resultSet != null && resultSet.next()) new InteractMessage(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @DataReload
    public static void loadRank(){
        Rank.clear();
        try {
            ResultSet resultSet = Database.BUNGEE.getDatabase().getData("rank");
            while (resultSet != null && resultSet.next()) new Rank(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    @DataReload
    public static void loadSetting(){
        Settings.clear();
        ResultSet resultSet;
        try {
            resultSet = DataCore.database.getData("setting");
            while(resultSet != null && resultSet.next()) new Settings(resultSet);
            if(DataCore.database != Database.BUNGEE.getDatabase()){
                resultSet = Database.BUNGEE.getDatabase().getData("setting");
                while(resultSet != null && resultSet.next()) new Settings(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        SettingCore.initSettings();
    }

    @DataReload
    public static void loadLocation(){
        Location.clear();
        ResultSet resultSet;
        try {
            resultSet = DataCore.database.getData("location");
            while (resultSet != null && resultSet.next()) {
                new Location(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @DataReload
    public static void loadLimite(){
        Limite.clear();
        ResultSet resultSet;
        try {
            resultSet = DataCore.database.getData("limite");
            while (resultSet != null && resultSet.next()) {
                new Limite(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @DataReload
    public static void loadItem(){
        Item.clear();
        ResultSet resultSet;
        try {
            resultSet = DataCore.database.getData("item");
            while (resultSet != null && resultSet.next()) new Item(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @DataReload
    public static void loadFireWork(){
        Firework.clear();
        try {
            ResultSet resultSet = DataCore.database.getData("firework");
            while (resultSet != null && resultSet.next()) {
                new Firework(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void loadBookHelp(){
        BookHelp.clear();
        try{
            ResultSet resultSet = DataCore.database.getData("book");
            while(resultSet != null && resultSet.next()) new BookHelp(resultSet, DataCore.database);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @DataReload
    public static void loadMessageTitle(){
        MessageTitle.clear();
        try {
            ResultSet resultSet = DataCore.database.getData("message_title");
            while (resultSet != null && resultSet.next()) {
                new MessageTitle(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
