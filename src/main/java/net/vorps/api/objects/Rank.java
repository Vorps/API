package net.vorps.api.objects;

import net.vorps.api.data.DataCore;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Project Bungee Created by Vorps on 01/02/2016 at 01:44.
 */
public class Rank extends Object{

	private @Getter
    final String rank;
	private @Getter
    final String rankDisplay;
	private @Getter
    final String rankAlias;
	private @Getter
    final String colorRank;
	private @Getter
    final String colorChat;
	private @Getter
    final boolean visibleRank;
	private @Getter
    final int levelRank;
	private @Getter
    final int numbersFriends;
	private @Getter
    final int numbersMembers;
    private @Getter
    final int pendingGame;

	public Rank(ResultSet result) throws SQLException {
        this.rank = result.getString(1);
        this.rankDisplay = result.getString(2);
        this.rankAlias = result.getString(3);
        this.colorRank = result.getString(4);
        this.colorChat = result.getString(5);
        this.visibleRank = result.getBoolean(6);
        this.levelRank = result.getInt(7);
        this.numbersFriends = result.getInt(8);
        this.numbersMembers = result.getInt(9);
        this.pendingGame = result.getInt(10);
        Rank.rankList.put(this.rank, this);
    }

    @Override
	public String toString() {
        return this.rankDisplay.isEmpty() ? this.colorRank+"["+this.rank+"]" : this.colorRank+this.rankDisplay+colorChat;
	}

    private static @Getter HashMap<String, Rank> rankList;

    static {
        Rank.rankList = new HashMap<>();
        DataCore.loadRank();
    }

    public static Rank getRank(String nameRank){
        return Rank.rankList.get(nameRank);
    }

    public static boolean isRank(String rank){
        return Rank.getRankList().containsKey(rank);
    }
    public static void clear(){
        Rank.rankList.clear();
    }


}


