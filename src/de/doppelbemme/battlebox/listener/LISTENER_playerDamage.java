package de.doppelbemme.battlebox.listener;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

import de.doppelbemme.battlebox.main.BattleBox;
import de.doppelbemme.battlebox.main.GameState;

public class LISTENER_playerDamage implements Listener{

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		if(BattleBox.main.state != GameState.INGAME){
			event.setCancelled(true);
		}else if(BattleBox.main.state == GameState.INGAME){
			Player damager;
			Player damaged = (Player)event.getEntity();
			
			if(event.getDamager() instanceof Arrow){
				Arrow arrow = (Arrow) event.getDamager();
				damager = (Player) arrow.getShooter();
			}else{
				damager = (Player) event.getDamager();
			}
			
			if(BattleBox.main.TeamBlue.contains(damager) & BattleBox.main.TeamBlue.contains(damaged)){
				event.setDamage(0.0);
			}else if(BattleBox.main.TeamRed.contains(damager) & BattleBox.main.TeamRed.contains(damaged)){
				event.setDamage(0.0);
			}else{
				event.setCancelled(false);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(EntityDeathEvent event){
		
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			Player killer = player.getKiller();
			
			event.getDrops().clear();
			
			killer.sendTitle("§f[§a⚔§f] " + player.getDisplayName(), "");
			killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
			
			Bukkit.getScheduler().runTaskLater(BattleBox.main, new Runnable() {
				@Override
				public void run() {
					player.spigot().respawn();
					player.teleport(BattleBox.filemanager.getLocation("spectator"));
					player.getInventory().clear();
				}
			}, 5);
		}
	}
	
	@EventHandler
	public void onRegen(EntityRegainHealthEvent event){
		if(BattleBox.main.state == GameState.INGAME){
			event.setCancelled(true);
		}
	}
}
