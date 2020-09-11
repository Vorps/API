package net.vorps.api.databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class DatabaseManager {
    private Connection connection;
    private String nameDatabase;

    public Connection getConnection(){
        return connection;
    }

    /**
     * Connect DataBase
     * @param nameDatabase String
     * @throws SQLException
     */
    public DatabaseManager(String nameDatabase) throws SQLException {
        this.nameDatabase = nameDatabase;
        String typeBDD = "mysql";
        String ip = "localhost";
        String port = "3306";
        String user = "plugin";
        String pass = "12345";
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", user);
            connectionProps.put("password", pass);
            Class.forName("com."+typeBDD+".jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:"+typeBDD+"://"+ip+":"+port+"/"+nameDatabase+"?noAccessToProcedureBodies=true", connectionProps);
        } catch(ClassNotFoundException e){
            throw new SQLException("Driver not found Please install driver "+typeBDD, new Throwable("Error, server no connected to database"));
        } catch (SQLException e){
            throw new SQLException("Error, Server not connect to database", new Throwable("Error, server no connected to database"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isTable(String table) throws SQLException{
        boolean state;
        try {
            DatabaseMetaData dmd = this.connection.getMetaData();
            ResultSet tables = dmd.getTables(this.connection.getCatalog(),null, table,null);
            state = tables.next();
            tables.close();
        } catch (SQLException e){
            throw new SQLException("Error, Server not connect to database", new Throwable("Error, server no connected to database"));
        }
        return state;
    }


    private Object[][] getColumnName(String table) throws SQLException {
        ResultSet result =  this.connection.getMetaData().getColumns(this.connection.getCatalog(), null, table, "%");
        ArrayList<Object[]> tmp = new ArrayList<>();
        while (result.next()) {
            tmp.add(new Object[]{result.getObject(4), result.getObject(5)});
        }
        Object[][] infoColumn = new Object[tmp.size()][2];
        int i = 0;
        for(Object[] objects : tmp){
            infoColumn[i][0] = objects[0];
            infoColumn[i][1] = objects[1];
            i++;
        }
        return infoColumn;
    }

    public ResultSet getDataUnique(final String table, final String condition) {
        ResultSet resultSet = null;
        try {
            resultSet = this.getData(table, condition);
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Insert table
     * @param table
     * @param values
     * @throws SQLException
     */
    public void insertTable(String table, Object... values) throws SQLException{
        try {
            Object[][] objects = getColumnName(table);
            String msgName = "";
            String msgValue = "";
            for(Object[] name : objects){
                msgName+= name[0]+",";
                msgValue+="?,";
            }
            PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO "+table+" ("+msgName.substring(0, msgName.length()-1)+") VALUES("+msgValue.substring(0, msgValue.length()-1)+")");
            for(int i = 0; i < objects.length; i++){
                if(values[i] == null){
                    preparedStatement.setNull(i+1, (int) objects[i][1]);
                } else {
                    preparedStatement.setObject(i+1, values[i], (int) objects[i][1]);
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }

    }

    /**
     * Delete value
     * @param table String
     * @throws SQLException
     */
    public void delete(String table) throws SQLException{
        try {
            Statement state = this.connection.createStatement();
            state.executeUpdate("DELETE FROM "+table);
            state.close();
        } catch(SQLException err) {
            throw new SQLException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    /**
     * Delete value with condition
     * @param table String
     * @param condition String
     * @throws SQLException
     */
    public void delete(String table, String condition) throws SQLException{
        try {
            Statement state = this.connection.createStatement();
            state.executeUpdate("DELETE FROM "+table+" WHERE "+condition);
            state.close();
        } catch(SQLException err) {
            throw new SQLException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    public static class Values{

        private String nameColumn;
        private Object value;

        public Values(String nameColumn, Object value){
            this.nameColumn = nameColumn;
            this.value = value;
        }

        public String getNameColumn(){
            return this.nameColumn;
        }

        public Object getValue(){
            return this.value;
        }
    }

    private int infoColumnType(String table, String column) throws SQLException{
        ResultSet result = this.connection.getMetaData().getColumns(this.connection.getCatalog(), null, table, "%");
        while (result.next()) {
            if(result.getObject(4).equals(column)){
                return (int) result.getObject(5);
            }
        }
        return 0;
    }

    /**
     * Update Table with condition
     * @param table String
     * @param condition String
     * @param values Values...
     * @throws SQLException
     */
    public void updateTable(String table, String condition, String operator,Values... values) throws SQLException{
        try {
            String msg = "";
            for(Values values1 : values){
                msg += values1.nameColumn+" = "+values1.nameColumn+" "+operator+ "?,";
            }
            String cond = "";
            if(condition != null){
                cond = " WHERE "+condition;
            }
            this.updateTable(this.connection.prepareStatement("UPDATE "+table+" SET "+msg.substring(0, msg.length()-1)+cond), table, values).executeUpdate();
        } catch(SQLException err) {
            throw new SQLException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    private PreparedStatement updateTable(PreparedStatement preparedStatement, String table, Values... values) throws SQLException{
        int i = 0;
        for(Values values1 : values){
            int type = infoColumnType(table, values1.nameColumn);
            if(values1.value == null){
                preparedStatement.setNull(i+1, type);
            } else {
                preparedStatement.setObject(i+1, values1.value, type);
            }
            i++;
        }
        return preparedStatement;
    }
    /**
     * Update Table with condition
     * @param table String
     * @param condition String
     * @param values Values...
     * @throws SQLException
     */
    public void updateTable(String table, String condition, Values... values) throws SQLException{
        try {
            String msg = "";
            for(Values values1 : values){
                msg += values1.nameColumn+" = ?,";
            }
            String cond = "";
            if(condition != null){
                cond = " WHERE "+condition;
            }
            this.updateTable(this.connection.prepareStatement("UPDATE "+table+" SET "+msg.substring(0, msg.length()-1)+cond), table, values).executeUpdate();
        } catch(SQLException err) {
            err.printStackTrace();
            throw new SQLException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    /**
     * Update Table
     * @param table String
     * @param values Values...
     * @throws SQLException
     */
    public void updateTable(String table, Values... values) throws SQLException{
        updateTable(table, null, values);
    }


    /**
     * Return data
     * @param table String
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet getData(String table) throws SQLException {
        ResultSet results;
        try {
            if(this.connection.getMetaData().getTables(null, null, table, null).next())
                return results = this.connection.createStatement().executeQuery("SELECT * FROM "+table);
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("Error, impossible to recover the data", new Throwable("Error, server no connected to database"));
        }
        return null;
    }
    /**
     * Return data
     * @param table String
     * @param condition String
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet getData(String table, String condition) throws SQLException {
        ResultSet results;
        try {
            results = this.connection.createStatement().executeQuery("SELECT * FROM "+table+" WHERE "+condition);
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("Error, impossible to recover the data", new Throwable("Error, server no connected to database"));
        }
        return results;
    }

    /**
     * Return data
     * @param table String
     * @param column String
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet getDataColumn(String table, String column) throws SQLException {
        ResultSet results;
        try {
            results = this.connection.createStatement().executeQuery("SELECT "+column+" FROM "+table);
        } catch (SQLException e){
            throw new SQLException("Error, impossible to recover the data", new Throwable("Error, server no connected to database"));
        }
        return results;
    }

    /**
     * Return data
     * @param table String
     * @param column String
     * @param condition String
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet getData(String table, String column, String condition) throws SQLException {
        ResultSet results;
        try {
            results = this.connection.createStatement().executeQuery("SELECT "+column+" FROM "+table+" WHERE "+condition);
        } catch (SQLException e){
            throw new SQLException("Error, impossible to recover the data", new Throwable("Error, server no connected to database"));
        }
        return results;
    }

    /**
     * Send data to DataBase
     * @param request String
     * @throws SQLException
     */
    public void sendRequest(String request) throws SQLException {
        try
        {
            Statement state = this.connection.createStatement();
            state.executeUpdate(request);
            state.close();
        } catch(SQLException err) {
            throw new SQLException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    public void closeDataBase(){
        try {
            this.connection.close();
        } catch (SQLException e){
            //
        }
    }
}
