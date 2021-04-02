package de.doppelbemme.battlebox.main;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Countdown {

	public boolean lobbystarted = false;
	public boolean restartstarted = false; 
	
	String prefix = BattleBox.main.prefix;
	int minimumPlayers = BattleBox.main.minimumPlayers;
	
	public static int lobbycd;
	public int informcd;
	public int gracecd;
	public int ingamecd;
	int restartcd;
	
	@SuppressWarnings("deprecation")
	public void startPlayerLeftBroadcast(){
		informcd = Bukkit.getScheduler().scheduleAsyncRepeatingTask(BattleBox.main, new Runnable() {
					
			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().size() < minimumPlayers && Bukkit.getOnlinePlayers().size() > 0){
					Integer playerNeeded = minimumPlayers-Bukkit.getOnlinePlayers().size();
					if(playerNeeded == 1){
						Bukkit.broadcastMessage(prefix + "§7Damit die Runde startet, muss noch §fein §7Spieler die Runde betreten.");
					}else{
						Bukkit.broadcastMessage(prefix + "§7Damit die Runde startet, müssen noch §f" + playerNeeded + " §7Spieler die Runde betreten.");
					}
				}
			}
		}, 0, 20*60);
	}
	
	public void startLobbyCD()
	{
	if(lobbystarted == false) 
	{
		lobbystarted = true;
		lobbycd = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) BattleBox.main, new Runnable() 
		{

			@Override
			public void run()
				{
				Bukkit.getScheduler().cancelTask(informcd);
					
					if(BattleBox.main.lobbyTime == 60){
						broadcastLobbyTime();
						setPlayerLevel();
					}else if(BattleBox.main.lobbyTime <= 59 && BattleBox.main.lobbyTime >= 31){
						setPlayerLevel();
					}else if(BattleBox.main.lobbyTime == 30){
						broadcastLobbyTime();
						setPlayerLevel();
					}else if(BattleBox.main.lobbyTime <= 29 && BattleBox.main.lobbyTime >= 6){
						setPlayerLevel();
					}else if(BattleBox.main.lobbyTime <= 5 && BattleBox.main.lobbyTime > 1){
						broadcastLobbyTime();
						setPlayerLevel();
					}else if(BattleBox.main.lobbyTime == 1){
						Bukkit.broadcastMessage(prefix + "§7Die Spielphase beginnt in §feiner §7Sekunde.");
						setPlayerLevel();
					}else if(BattleBox.main.lobbyTime == 0){
						Bukkit.broadcastMessage(prefix + "§7Die Spielphase beginnt §fjetzt§7.");
						setPlayerLevel();
						
						for(Player currentPlayer:Bukkit.getOnlinePlayers()){
							BattleBox.playerUtil.pushPlayersInTeam(currentPlayer);
						}
						BattleBox.playerUtil.teleportPlayersInBase();
						
						BattleBox.main.state = GameState.GRACE;
						startGraceCD();
						Bukkit.getScheduler().cancelTask(lobbycd);
					}
					BattleBox.main.lobbyTime--;
				}
			}, 0, 20L);
		
		}
	}
	
	/*Bei erneutem Aufrufen wieder auf 30 Sekunden setzen!!*/
	
	public void startGraceCD(){
		
		gracecd = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) BattleBox.main, new Runnable() {
			
			@Override
			public void run() {				
				if(BattleBox.main.graceTime == 30){
					if(BattleBox.main.gameInProgress == false){
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage(prefix + "§7Erobere die Mitte und ersetze alle Wolleblöcke vor dem Gegnerteam um die Runde zu gewinnen.");
					Bukkit.broadcastMessage(prefix + "§7Das Team welches zuerst 3 Punkte erreicht gewinnt!");
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("");
					BattleBox.main.gameInProgress = true;
					}
					for(Player currentPlayer:Bukkit.getOnlinePlayers()){
						if(BattleBox.main.TeamBlue.contains(currentPlayer) || BattleBox.main.TeamRed.contains(currentPlayer)){
							BattleBox.itemUtil.giveIngameItems(currentPlayer);
						}
					}
					setPlayerLevel();
				}else if(BattleBox.main.graceTime <= 30 && BattleBox.main.graceTime >= 16){
					setPlayerLevel();
				}else if(BattleBox.main.graceTime == 15){
	
					BattleBox.playerUtil.teleportPlayersInStartPosition();
					setPlayerLevel();
					
				}else if(BattleBox.main.graceTime <= 14 && BattleBox.main.graceTime >= 4){
					setPlayerLevel();
				}else if(BattleBox.main.graceTime == 3){
					setPlayerLevel();
					for(Player currentPlayer:Bukkit.getOnlinePlayers()){
						currentPlayer.sendTitle("§c§l»3«", null);
					}
				}else if(BattleBox.main.graceTime == 2){
					setPlayerLevel();
					for(Player currentPlayer:Bukkit.getOnlinePlayers()){
						currentPlayer.sendTitle("§e§l»2«", null);
					}
				}else if(BattleBox.main.graceTime == 1){
					setPlayerLevel();
					for(Player currentPlayer:Bukkit.getOnlinePlayers()){
						currentPlayer.sendTitle("§a§l»1«", null);
					}
				}else if(BattleBox.main.graceTime == 0){
					setPlayerLevel();
					for(Player currentPlayer:Bukkit.getOnlinePlayers()){
						currentPlayer.sendTitle("§b§lGO!", null);
					}
					BattleBox.main.state = GameState.INGAME;
					//startIngameCD
					Bukkit.getScheduler().cancelTask(gracecd);
				}
				BattleBox.main.graceTime--;
			}
		}, 0, 20L);		
	}
	
/*	public void startIngameCD() {
		ingamecd = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Bewerbung.main, new Runnable() {

			@Override
			public void run() {

				if(Bewerbung.main.ingame == 60*60){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e60 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*30){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e30 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*15){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e15 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*10){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e10 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*5){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e5 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*3){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e3 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*2){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e2 §7Minuten");
				}else if(Bewerbung.main.ingame == 60){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e60 §7Sekunden");
				}else if(Bewerbung.main.ingame <= 15 && Bewerbung.main.ingame >= 2){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e" + Bewerbung.main.ingame + "§7Sekunden");
				}else if(Bewerbung.main.ingame == 1){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e1 §7Sekunde");
				}else if(Bewerbung.main.ingame == 0){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde ist jetzt vorbei");
					Bukkit.getScheduler().cancelTask(ingamecd);
					startRestartCD();
				}
				
				if(Bewerbung.main.alive.size() == 1){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde ist jetzt vorbei");
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§a" + Bewerbung.main.alive.get(0).getDisplayName() + " §ahat das Spiel gewonnen!");
					MySQLStats.addWins(Bewerbung.main.alive.get(0).getPlayer().getUniqueId(), 1);
					Bukkit.getScheduler().cancelTask(ingamecd);
					startRestartCD();
					for(Player current : Bukkit.getOnlinePlayers()){
						current.playSound(current.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
						}
					}else if(Bewerbung.main.alive.size() == 0){
						Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde ist jetzt vorbei");
						Bukkit.getScheduler().cancelTask(ingamecd);
						startRestartCD();
					}
				Bewerbung.main.ingame --;
				}
		}, 0, 20L);
		
	}
	
	public void startRestartCD() {
		if(restartstarted == false) {
			restartstarted = true;
			restartcd = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Bewerbung.main, new Runnable() {

				@Override
				public void run() {
					
					if(Bewerbung.main.restart <=15 && Bewerbung.main.restart >= 2){
						Bukkit.broadcastMessage(Bewerbung.main.prefix + "§4Der Server startet in §e" + Bewerbung.main.restart + " §4Sekunden neu!");
					}else if(Bewerbung.main.restart == 1){
						Bukkit.broadcastMessage(Bewerbung.main.prefix + "§4Der Server startet in §e" + Bewerbung.main.restart + " §4Sekunde neu!");
					}else if(Bewerbung.main.restart == 0){
						Bukkit.broadcastMessage(Bewerbung.main.prefix + "§4Der Server startet jetzt neu!");
						Bukkit.getServer().shutdown();
					}
				
					Bewerbung.main.restart --;
				}
			}, 0, 20L);
		}	
		}
	} 																											*/

	public void setPlayerLevel(){
		if(BattleBox.main.state == GameState.LOBBY){
		for(Player currentPlayer:Bukkit.getOnlinePlayers()){
			currentPlayer.setLevel(BattleBox.main.lobbyTime);
			}
		}else if(BattleBox.main.state == GameState.GRACE)
		for(Player currentPlayer:Bukkit.getOnlinePlayers()){
			currentPlayer.setLevel(BattleBox.main.graceTime);
			}
	}
	
	public void broadcastLobbyTime(){
		Bukkit.broadcastMessage(prefix + "§7Die Spielphase beginnt in §f" + BattleBox.main.lobbyTime + " §7Sekunden.");
	}
}