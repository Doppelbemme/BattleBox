package de.doppelbemme.battlebox.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.doppelbemme.battlebox.command.COMMAND_setLocation;
import de.doppelbemme.battlebox.command.COMMAND_setupBlockLocation;
import de.doppelbemme.battlebox.command.COMMAND_start;
import de.doppelbemme.battlebox.listener.LISTENER_itemInteraction;
import de.doppelbemme.battlebox.listener.LISTENER_mapInteraction;
import de.doppelbemme.battlebox.listener.LISTENER_playerDamage;
import de.doppelbemme.battlebox.listener.LISTENER_playerJoin;
import de.doppelbemme.battlebox.util.UTIL_itemManager;
import de.doppelbemme.battlebox.util.UTIL_mapManager;
import de.doppelbemme.battlebox.util.UTIL_playerManager;

public class BattleBox extends JavaPlugin{

	public static Countdown countdown;
	
	public String prefix = "§6BattleBox §8┃ ";
	public String NoPermissions = "§cDu hast keine Rechte auf diesen Befehl!";
	public String NoConsoleAllowed = "§cOnly a player may use this command!";
	
	public int minimumPlayers = 2;
	public int maximumPlayers = 8;
	public int lobbyTime;
	public int graceTime;
	
	public int scoreTeamBlue;
	public int scoreTeamRed;
	
	public static BattleBox main;
	public static FileManager filemanager;
	public GameState state;
	
	public boolean gameInProgress;
	
	public static UTIL_mapManager maputil;
	public static UTIL_itemManager itemUtil;
	public static UTIL_playerManager playerUtil;
	
	public ArrayList<Player> TeamBlue = new ArrayList<Player>();
	public ArrayList<Player> TeamRed = new ArrayList<Player>();

	@Override
	public void onEnable()
	{		
		scoreTeamRed = 0;
		scoreTeamBlue = 0;
		
		main = this;
		countdown = new Countdown();
		itemUtil = new UTIL_itemManager();
		maputil = new UTIL_mapManager();
		playerUtil = new UTIL_playerManager();
		filemanager = new FileManager();
		
		gameInProgress = false;
		
		state = GameState.LOBBY;
		
		lobbyTime = 60;
		graceTime = 30;
		
		countdown.startPlayerLeftBroadcast();
		
		getCommand("start").setExecutor(new COMMAND_start());
		getCommand("setupblocklocation").setExecutor(new COMMAND_setupBlockLocation());
		getCommand("setlocation").setExecutor(new COMMAND_setLocation());
		
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new LISTENER_playerJoin(), this);
		pm.registerEvents(new LISTENER_mapInteraction(), this);
		pm.registerEvents(new LISTENER_itemInteraction(), this);
		pm.registerEvents(new LISTENER_playerDamage(), this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
}
