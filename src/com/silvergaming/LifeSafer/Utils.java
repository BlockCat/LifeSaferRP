package com.silvergaming.LifeSafer;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {

	public static void dropBlood(Player player) {
		Location loc = player.getLocation();
		loc.setX(loc.getX() - 0.5D);
		loc.setZ(loc.getZ() - 0.5D);
		World world = player.getWorld();

		for (int i = 0; i < 30; i++) {
			ItemStack items = new ItemStack(Material.WOOL, 1, (short) 14);
			Item item = world.dropItemNaturally(loc, items);
			item.setPickupDelay(999999999);
			//Entity item = (Entity)items;
			//items.setType(Material.AIR);
			
			Timer time = new Timer();
			
			time.schedule(new Task(item), 3000);
		}
		for (int i = 0; i < 10; i++) {
			ItemStack items = new ItemStack(Material.REDSTONE, 1);
			Item item = world.dropItemNaturally(loc, items);
			item.setPickupDelay(999999999);
			//Entity item = (Entity)items;
			//items.setType(Material.AIR);
			
			Timer time = new Timer();
			
			time.schedule(new Task(item), 3000);
			
		}
	}
	public static class Task extends TimerTask {
		
		Item item;
		
		public Task (Item item) {
			this.item = item;
		}

		@Override
		public void run() {
			item.remove();
		}
		
	}

}
