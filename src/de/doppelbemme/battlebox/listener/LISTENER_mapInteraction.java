package de.doppelbemme.battlebox.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import de.doppelbemme.battlebox.main.BattleBox;
import de.doppelbemme.battlebox.main.GameState;

public class LISTENER_mapInteraction implements Listener{

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		
		Location targetBlockLocation = event.getBlock().getLocation();
		if(BattleBox.maputil.isInside(targetBlockLocation) && BattleBox.main.state == GameState.INGAME)
		{
			event.setCancelled(false);
			BattleBox.maputil.checkTargetForWinner();
			BattleBox.itemUtil.refillWool(event.getPlayer());
		}
		else
		{
			event.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Location targetBlockLocation = event.getBlock().getLocation();
		
		if(BattleBox.maputil.isInside(targetBlockLocation) && BattleBox.main.state == GameState.INGAME)
		{
			event.setCancelled(false);
			event.setDropItems(false);
		}
		else
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		
		if(BattleBox.main.state == GameState.GRACE && BattleBox.main.graceTime <= 14 && BattleBox.main.graceTime >= 0)
		{
			event.setTo(event.getFrom());
		}
	}
	
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event){
		
		event.setCancelled(true);
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event){
		if(BattleBox.main.state != GameState.INGAME){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event){
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
		Player damaged = (Player)event.getEntity();
		Player damager = (Player)event.getDamager();
		if(BattleBox.main.state != GameState.INGAME){
			event.setCancelled(true);
			return;
		}else{
			if(BattleBox.main.TeamRed.contains(damaged) && BattleBox.main.TeamRed.contains(damager)){
				event.setCancelled(true);
				return;
			}else if(BattleBox.main.TeamBlue.contains(damaged) && BattleBox.main.TeamBlue.contains(damager)){
				event.setCancelled(true);
				return;
			}else{
				event.setCancelled(false);
				return;
			}
		}
	}
	}
}
