package de.doppelbemme.battlebox.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.doppelbemme.battlebox.main.BattleBox;
import de.doppelbemme.battlebox.main.GameState;

public class LISTENER_itemInteraction implements Listener{

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event)
	{
		if(BattleBox.main.state != GameState.INGAME)
		{
			event.setCancelled(true);
		}
	}
}
