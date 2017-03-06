package fr.herezia.api.players;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.data.Data;
import fr.herezia.api.databases.Database;
import lombok.Getter;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project Bungee Created by Vorps on 01/02/2016 at 01:41.
 */
public class Money{

	private @Getter String money;
	private @Getter String alias;
	private @Getter String color;
	
	public Money(ResultSet results) throws SqlException {
		this.money = Database.SERVER.getDatabase().getString(results, 1);
        this.alias = Database.SERVER.getDatabase().getString(results, 2);
        this.color = Database.SERVER.getDatabase().getString(results, 3);
        Money.listMoney.put(this.money, this);
	}

    @Override
    public String toString(){
        return color+money;
    }

    private static @Getter HashMap<String, Money> listMoney;

    static {
        Money.listMoney = new HashMap<>();
        Data.getInstance().loadMoney();
    }

    public static Money getMoney(String nameMoney){
        return Money.listMoney.get(nameMoney);
    }

    public static boolean isMoney(String money){
       return Money.listMoney.containsKey(money);
    }

    public static void clear(){
        Money.listMoney.clear();
    }
}
