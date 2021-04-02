package de.doppelbemme.battlebox.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileManager {

		File file = new File("plugins/BattleBox", "locations.yml");
		public FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		public void saveCfg() {
			try {
				cfg.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void saveBlockLocation(Location loc, Player player, int count) {
		
			String countAsString = String.valueOf(count);
			String name = "block"+countAsString;
			
			cfg.set(name+".world", loc.getWorld().getName());
			cfg.set(name+".x", loc.getX());
			cfg.set(name+".y", loc.getY()-1);
			cfg.set(name+".z", loc.getZ());
			player.sendMessage("§aBlock " + count + " wurde markiert. §7(§e" + loc.getBlockX() + "§7/§e" + (loc.getBlockY()-1) + "§7/§e" + loc.getBlockZ()+"§7)");
			player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);
				
			saveCfg();
		}
		
		public Location getBlockLocation(int count) {
			String countAsString = String.valueOf(count);
			String name = "block"+countAsString;
			World w = Bukkit.getWorld(cfg.getString(name+".world"));
			double x = cfg.getDouble(name+".x");
			double y = cfg.getDouble(name+".y");
			double z = cfg.getDouble(name+".z");
			Location loc = new Location(w, x, y, z);
			return loc;
		}
		
		public void saveLocation(Location loc, String locationName, Player player) {
			
			String name = locationName;
			
			cfg.set(name+".world", loc.getWorld().getName());
			cfg.set(name+".x", loc.getX());
			cfg.set(name+".y", loc.getY());
			cfg.set(name+".z", loc.getZ());
			
			cfg.set(name+".yaw", loc.getYaw());
			cfg.set(name+".pitch", loc.getPitch());
			
			player.sendMessage("§7Die Location §e" + locationName + " §7wurde gespeichert.");
			player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
				
			saveCfg();
		}
		
		public Location getLocation(String locationName) {
			String name = locationName;
			World w = Bukkit.getWorld(cfg.getString(name+".world"));
			double x = cfg.getDouble(name+".x");
			double y = cfg.getDouble(name+".y");
			double z = cfg.getDouble(name+".z");
			Location loc = new Location(w, x, y, z);
			loc.setYaw(cfg.getInt(name+".yaw"));
			loc.setPitch(cfg.getInt(name+".pitch"));
			return loc;
		}
	
}
