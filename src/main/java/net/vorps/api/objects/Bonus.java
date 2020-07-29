package net.vorps.api.objects;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.data.DataCore;
import lombok.Getter;
import net.vorps.api.databases.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Bonus {

	private @Getter
    final String bonus;
	private @Getter
    final double coefficient;
	private @Getter
    final String money;

    public static Bonus getBonus(String nameBonus){
        return listBonus.get(nameBonus);
    }

	public Bonus(ResultSet result) throws SQLException {
		this.bonus = result.getString(1);
        this.coefficient = result.getDouble(2);
        this.money = result.getString(3);
        Bonus.listBonus.put(this.bonus, this);
	}

    private static @Getter HashMap<String, Bonus> listBonus;

    static {
        Bonus.listBonus = new HashMap<>();
        DataCore.getInstance().loadBonus();
    }

    public static void clear(){
        Bonus.listBonus.clear();
    }
}
