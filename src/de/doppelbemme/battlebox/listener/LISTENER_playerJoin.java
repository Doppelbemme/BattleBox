package de.doppelbemme.battlebox.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import de.doppelbemme.battlebox.main.BattleBox;
import de.doppelbemme.battlebox.main.GameState;

public class LISTENER_playerJoin implements Listener{

	@EventHandler
	public void playerJoin(PlayerJoinEvent event)
	{
		
		Player player = event.getPlayer();
		int onlinePlayers = Bukkit.getOnlinePlayers().size();
		
		event.setJoinMessage(null);
		
		if(BattleBox.main.state == GameState.LOBBY){
		Bukkit.broadcastMessage("§7" + player.getName() + " hat den Server betreten (§f" + onlinePlayers + "§7/§f" + BattleBox.main.maximumPlayers + "§7)");
	
		if(onlinePlayers == BattleBox.main.minimumPlayers){
			BattleBox.countdown.startLobbyCD();
		}
		Bukkit.getScheduler().runTaskLater(BattleBox.main, new Runnable() {
			@Override
			public void run() {
				BattleBox.itemUtil.giveLobbyItems(player);
				player.teleport(BattleBox.filemanager.getLocation("lobby"));
				player.setHealth(20);
				player.setFoodLevel(20);
				}
			}, 5);
		}
		else
		{
			Bukkit.getScheduler().runTaskLater(BattleBox.main, new Runnable() {
				@Override
				public void run() {
					player.getInventory().clear();
					player.teleport(BattleBox.filemanager.getLocation("spectator"));
					}
				}, 5);
		}
	}
}
