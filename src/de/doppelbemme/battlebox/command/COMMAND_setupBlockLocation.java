package de.doppelbemme.battlebox.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.doppelbemme.battlebox.main.BattleBox;

public class COMMAND_setupBlockLocation implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(BattleBox.main.NoConsoleAllowed);
			return false;
		} 
		else
		{
			Player player = (Player) sender;
			if (!player.hasPermission("battlebox.setup")) {
				player.sendMessage(BattleBox.main.NoPermissions);
				return false;
			}
			else
			{
				if(args.length != 1) {
					player.sendMessage("§cNutze: §7/§csetupblocklocation §7<§c1-9§7>");
					return false;
				} 
				else
				{
					int count = Integer.parseInt(args[0]);
					if(count < 0 || count > 9){
						player.sendMessage("§cNutze: §7/§csetupblocklocation §7<§c1-9§7>");
					}else{
						BattleBox.filemanager.saveBlockLocation(player.getLocation(), player, count);
					}
				}
			}
		}
		return false;
	}
}
