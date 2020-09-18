package net.vorps.api.objects;

import net.vorps.api.data.DataCore;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Project Bungee Created by Vorps on 01/02/2016 at 01:41.
 */
public class Money{

	private @Getter final String money;
	private @Getter final String alias;
	private @Getter final String color;
	
	public Money(ResultSet result) throws SQLException {
		this.money = result.getString(1);
        this.alias = result.getString(2);
        this.color = result.getString(3);
        Money.listMoney.put(this.money, this);
	}

    @Override
    public String toString(){
        return color+money;
    }

    private static HashMap<String, Money> listMoney;

    static {
        Money.listMoney = new HashMap<>();
        DataCore.loadMoney();
    }

    public static List<String> getMoneys(){
        return new ArrayList<>(Money.listMoney.keySet());
    }
    public static Money getMoney(String nameMoney){
        return Money.listMoney.get(nameMoney);
    }

    public static void clear(){
        Money.listMoney.clear();
    }
}
