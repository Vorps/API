package fr.herezia.api.players;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.data.Data;
import fr.herezia.api.databases.Database;
import lombok.Getter;

import java.sql.ResultSet;
import java.util.HashMap;

public class Bonus {

	private @Getter String bonus;
	private @Getter double coefficient;
	private @Getter String money;

    public static Bonus getBonus(String nameBonus){
        return listBonus.get(nameBonus);
    }

	public Bonus(ResultSet results) throws SqlException {
		this.bonus = Database.BUNGEE.getDatabase().getString(results, 1);
        this.coefficient = Database.BUNGEE.getDatabase().getDouble(results, 2);
        this.money = Database.BUNGEE.getDatabase().getString(results, 3);
        Bonus.listBonus.put(this.bonus, this);
	}

    private static @Getter HashMap<String, Bonus> listBonus;

    static {
        Bonus.listBonus = new HashMap<>();
        Data.getInstance().loadBonus();
    }

    public static void clear(){
        Bonus.listBonus.clear();
    }
}
