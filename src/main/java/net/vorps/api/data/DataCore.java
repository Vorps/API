package net.vorps.api.data;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.databases.DatabaseManager;
import net.vorps.api.lang.Lang;
import net.vorps.api.lang.LangSetting;
import net.vorps.api.objects.*;
import net.vorps.api.particles.Particle;
import net.vorps.api.objects.Bonus;
import net.vorps.api.objects.Money;
import net.vorps.api.objects.Rank;
import net.vorps.api.utils.Settings;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataCore extends Data{


    private static DatabaseManager database;


    public static void setDatabase(DatabaseManager database){
        DataCore.database = database;
    }

    private static DataCore instance;

    public static DataCore getInstance() {
        if (DataCore.instance == null) DataCore.instance = new DataCore();
        return DataCore.instance;
    }

    public static void loadParticle(){
        Particle.clear();
        ResultSet results;
        try {
            results = DataCore.database.getData("particle");
            while(results.next()) new Particle(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadLangSetting(){
        LangSetting.clearLangSetting();
        ResultSet results;
        try {
            results = DataCore.database.getData("lang_setting");
            while(results.next()) new LangSetting(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadLang(){
        Lang.clearLang();
        ResultSet results;
        try {
            results = DataCore.database.getData("lang");
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
            ResultSet results = DataCore.database.getData("money");
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
            ResultSet resultSet = DataCore.database.getData("interact_message");
            while (resultSet.next()) new InteractMessage(resultSet);
        } catch (SqlException | SQLException e){
            e.printStackTrace();
        }
    }

    public void loadBonus(){
        Bonus.clear();
        try {
            ResultSet resultSet = DataCore.database.getData("bonus");
            while (resultSet.next()) new Bonus(resultSet);
        } catch (SqlException | SQLException e){
            e.printStackTrace();
        }
    }

    public void loadRank(){
        Rank.clear();
        try {
            ResultSet resultSet = DataCore.database.getData("rank");
            while (resultSet.next()) new Rank(resultSet);
        } catch (SqlException | SQLException e){
            e.printStackTrace();
        }
    }

    public void loadSetting(){
        Settings.clear();
        ResultSet results;
        try {
            results = DataCore.database.getData("setting");
            while(results.next()) new Settings(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
        Settings.initSettings();
    }

    public void loadLocation(){
        Location.clear();
        ResultSet results;
        try {
            results = DataCore.database.getData("location");
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
            results = DataCore.database.getData("limite");
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
            results = DataCore.database.getData("item");
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
            ResultSet results = DataCore.database.getData("firework");
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
            ResultSet results = DataCore.database.getData("book");
            while(results.next()) new BookHelp(results, false, DataCore.database);
        }catch(SQLException e){
            //
        }catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void loadMessageTitle(){
        MessageTitle.clear();
        try {
            ResultSet results = DataCore.database.getData("message_title");
            while (results.next()) {
                new MessageTitle(results);
            }
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }
}
