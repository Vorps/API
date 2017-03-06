package fr.herezia.api.data;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import fr.herezia.api.lang.Lang;
import fr.herezia.api.lang.LangSetting;
import fr.herezia.api.objects.*;
import fr.herezia.api.players.Bonus;
import fr.herezia.api.players.Money;
import fr.herezia.api.players.Rank;
import fr.herezia.api.utils.InteractMessage;
import fr.herezia.api.utils.MessageTitle;
import fr.herezia.api.utils.Settings;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

/**
 * Project Bungee Created by Vorps on 20/02/2017 at 15:55.
 */
public class Data {

    public static final SimpleDateFormat FORMAT;
    public static final SimpleDateFormat FORMATALL;
    public static final SimpleDateFormat FORMAT_1;
    private static @Getter
    HashMap<String, UUID> listPlayerString;
    private static @Getter HashMap<UUID, String> listPlayerUUID;

    static {
        Data.listPlayerString = new HashMap<>();
        Data.listPlayerUUID = new HashMap<>();
        FORMAT = new SimpleDateFormat("HH:mm:ss");
        FORMAT_1 = new SimpleDateFormat("dd/MM/yyyy");
        FORMATALL = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    private static Data instance;

    private static Database database;

    private Data(){
        Data.instance = this;
    }

    public static Data getInstance(){
        if(Data.instance == null) new Data();
        return Data.instance;
    }

    public void loadLang(){
        LangSetting.clearLangSetting();
        Lang.clearLang();
        ResultSet results;
        try {
            results = Database.BUNGEE.getDatabase().getData("lang_setting");
            while(results.next()) new LangSetting(results);
            results = Data.database.getDatabase().getData("lang");
            while(results.next()) new Lang(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadMoney(){
        Money.clear();
        try {
            ResultSet results = Database.BUNGEE.getDatabase().getData("money");
            while(results.next()) new Money(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadInteractMessage(){
        InteractMessage.clear();
        try {
            ResultSet resultSet = Data.database.getDatabase().getData("interact_message");
            while (resultSet.next()) new InteractMessage(resultSet);
        } catch (SqlException | SQLException e){
            e.printStackTrace();
        }
    }

    public void loadBonus(){
        Bonus.clear();
        try {
            ResultSet resultSet = Database.BUNGEE.getDatabase().getData("bonus");
            while (resultSet.next()) new Bonus(resultSet);
        } catch (SqlException | SQLException e){
            e.printStackTrace();
        }
    }

    public void loadRank(){
        Rank.clear();
        try {
            ResultSet resultSet = Database.BUNGEE.getDatabase().getData("rank");
            while (resultSet.next()) new Rank(resultSet);
        } catch (SqlException | SQLException e){
            e.printStackTrace();
        }
    }

    public void loadSetting(){
        Settings.clear();
        ResultSet results;
        try {
            results = Data.database.getDatabase().getData("setting");
            while(results.next()) new Settings(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadLocation(){
        Location.clear();
        ResultSet results;
        try {
            results = Data.database.getDatabase().getData("location");
            while (results.next()) {
                new Location(results);
            }
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadLimite(){
        Limite.clear();
        ResultSet results;
        try {
            results = Data.database.getDatabase().getData("limite");
            while (results.next()) {
                new Limite(results);
            }
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadItem(){
        Item.clear();
        ResultSet results;
        try {
            results = Data.database.getDatabase().getData("item");
            while (results.next()) new Item(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadFireWork(){
        Firework.clear();
        try {
            ResultSet results = Data.database.getDatabase().getData("firework");
            while (results.next()) {
                new Firework(results);
            }
        } catch (SQLException e){
            //
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public void loadBookHelp(){
        BookHelp.clear();
        try{
            ResultSet results = Data.database.getDatabase().getData("book");
            while(results.next()) new BookHelp(results, false, Data.database);
        }catch(SQLException e){
            //
        }catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadMessageTitle(){
        MessageTitle.clear();
        try {
            ResultSet results = Data.database.getDatabase().getData("message_title");
            while (results.next()) {
                new MessageTitle(results);
            }
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadListPlayer(){
        Data.listPlayerString.clear();
        try {
            ResultSet resultSet = Database.BUNGEE.getDatabase().getDataColumn("player", "p_uuid, p_name");
            while (resultSet.next()){
                Data.listPlayerString.put(Database.BUNGEE.getDatabase().getString(resultSet, 2), UUID.fromString(Database.BUNGEE.getDatabase().getString(resultSet, 1)));
                Data.listPlayerUUID.put(UUID.fromString(Database.BUNGEE.getDatabase().getString(resultSet, 1)), Database.BUNGEE.getDatabase().getString(resultSet, 2));
            }
        } catch (SqlException | SQLException e){
            e.printStackTrace();
        }
    }

    public static void initData(Database database){
        Data.database = database;
    }

    public static boolean isUUID(String uuid){
        return uuid.split("-").length == 5;
    }

    public static boolean isPlayer(String name){
        return Data.listPlayerString.containsKey(name);
    }

    public static boolean isPlayer(UUID uuid){
        return Data.listPlayerUUID.containsKey(uuid);
    }

    public static UUID getUUIDPlayer(String namePlayer){return Data.listPlayerString.get(namePlayer);}

    public static String getNamePlayer(UUID uuid){return Data.listPlayerUUID.get(uuid);}

    public static String getNamePlayer(String uuid){return Data.listPlayerUUID.get(UUID.fromString(uuid));}
}
