package net.vorps.api.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Objects;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public abstract class ScoreBoard {

    private Scoreboard scoreboard;
    private Objective objective;
    private HashMap<String, Score> value;
    private HashMap<String, Team> teamDisplayName;

    /**
     * Constructor abstract
     * @param slot DisplaySlot
     * @param name String
     */
    public ScoreBoard(final DisplaySlot slot, final String name){
        this.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("Nom", "mort");
        this.value = new HashMap<>();
        this.objective.setDisplaySlot(slot);
        this.objective.setDisplayName(name);
        this.teamDisplayName = new HashMap<>();
    }

    /**
     * Change name ScoreBoard
     * @param name String
     */
    public void changeName(final String name){
        this.objective.setDisplayName(name);
    }

    /**
     * Add objective
     * @param id String
     * @param value String
     * @param place int
     */
    public void add(final String id, final String value, final int place){
        if(!this.value.containsKey(id)){
            this.value.put(id, this.objective.getScore(value));
            this.value.get(id).setScore(place);
        } else this.updateValue(id, value);
    }

    /**
     * Remove objective
     * @param id String
     */
    public void remove(final String id){
        if(this.value.containsKey(id)) Objects.requireNonNull(this.objective.getScoreboard()).resetScores(this.value.get(id).getEntry());
    }

    /**
     * Create Team
     * @param name String
     * @param displayName String
     */
    @SuppressWarnings("deprecation")
	public void createTeam(final String name, final String displayName){
        Team team;
        team = this.scoreboard.registerNewTeam(name);
        team.setPrefix(displayName);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        this.teamDisplayName.put(name, team);
    }

    /**
     * Remove Team
     * @param name String
     */
    public void removeTeam(final String name){
        Objects.requireNonNull(this.scoreboard.getTeam(name)).unregister();
        this.teamDisplayName.remove(name);
    }

    /**
     * Update objective
     * @param id String
     * @param value String
     */
    public void updateValue(final String id, final String value){
        int place = this.value.get(id).getScore();
        this.remove(id);
        this.value.replace(id, this.objective.getScore(value));
        this.value.get(id).setScore(place);
    }

    /**
     * Get Scoreboard
     * @return Scoreboard
     */
    public Scoreboard getScoreBoard(){
        return this.scoreboard;
    }

    /**
     * Add PlayerInterract team
     * @param nameTeam String
     * @param player PlayerInterract
     */
    @SuppressWarnings("deprecation")
	public void addPlayerTeam(final String nameTeam, final Player player){
        this.teamDisplayName.get(nameTeam).addPlayer(player);
    }

    /**
     * Remove PlayerInterract team
     * @param nameTeam String
     * @param player PlayerInterract
     */
    @SuppressWarnings("deprecation")
	public void removePlayerTeam(final String nameTeam, final Player player){
        this.teamDisplayName.get(nameTeam).removePlayer(player);
    }

    public boolean containTeam(String team){
        return this.teamDisplayName.containsKey(team);
    }


}
