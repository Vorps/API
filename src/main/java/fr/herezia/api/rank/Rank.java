package fr.herezia.api.rank;

import lombok.Getter;

import java.sql.ResultSet;
import java.util.HashMap;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:44.
 */
public class Rank {
	private @Getter String rank;
	private @Getter String rankDisplay;
	private @Getter String rankAlias;
	private @Getter String colorRank;
	private @Getter String colorChat;
	private @Getter boolean visibleRank;
	private @Getter int levelRank;
	private @Getter int numbersFriends;
	private @Getter int numbersMembers;
    private @Getter int pendingGame;

	public Rank(ResultSet results) throws SqlException {
        this.rank = Database.SERVER.getDatabase().getString(results, 1);
        this.rankDisplay = Database.SERVER.getDatabase().getString(results, 2);
        this.rankAlias = Database.SERVER.getDatabase().getString(results, 3);
        this.colorRank = Database.SERVER.getDatabase().getString(results, 4);
        this.colorChat = Database.SERVER.getDatabase().getString(results, 5);
        this.visibleRank = Database.SERVER.getDatabase().getBoolean(results, 6);
        this.levelRank = Database.SERVER.getDatabase().getInt(results, 7);
        this.numbersFriends = Database.SERVER.getDatabase().getInt(results, 8);
        this.numbersMembers = Database.SERVER.getDatabase().getInt(results, 9);
        this.pendingGame = Database.SERVER.getDatabase().getInt(results, 10);
        Rank.rankList.put(this.rank, this);
    }

    @Override
	public String toString() {
        return this.rankDisplay.isEmpty() ? this.colorRank+"["+this.rank+"]" : this.colorRank+this.rankDisplay;
	}

    private static @Getter HashMap<String, Rank> rankList;

    static {
        Rank.rankList = new HashMap<>();
    }

    public static Rank getRank(String nameRank){
        return Rank.rankList.get(nameRank);
    }

    public static void clear(){
        Rank.rankList.clear();
    }


}


