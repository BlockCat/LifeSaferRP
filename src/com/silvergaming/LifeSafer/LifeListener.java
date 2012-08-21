package com.silvergaming.LifeSafer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LifeListener implements Listener{

	private List<Player> defeated = new ArrayList<Player>();

	public LifeListener (LifeSafer plugin) {

		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {

				for (Player player : defeated) { 
					Utils.dropBlood(player);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 3), true);
					
				}
			}

		}, 0, 10);
	}

	@EventHandler
	public void onEntityDamageByEntity (EntityDamageByEntityEvent event) {		

		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		if (!(event.getDamager() instanceof Player))
			return;



		Player attacker = (Player)event.getDamager();
		Player player = (Player)event.getEntity();

		if (defeated.contains(player)) {
			attacker.sendMessage(ChatColor.RED + "This player is already defeated.");
			event.setCancelled(true);
			return;
		}
		if (defeated.contains(attacker)) {
			attacker.sendMessage("You are defeated!");
			event.setCancelled(true);
			return;
		}

		if (event.getDamage() >= player.getHealth()) {
			player.setHealth(2);
			event.setDamage(0);
		}

		if (player.getHealth() <= 2) {
			player.setHealth(2);
			event.setDamage(0);
			if (!defeated.contains(player)) {
				player.sendMessage(ChatColor.DARK_RED + "You have been defeated.");
				
				player.setSneaking(true);
				
				attacker.sendMessage(ChatColor.GREEN + "You have defeated: " + player.getDisplayName());
				defeated.add(player);
			}
		}
	}

	@EventHandler
	public void onEntityRegainHealth (EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;

		Player player = (Player)event.getEntity();

		if (defeated.contains(player)) {
			if (player.getHealth() >=16) {		
				player.sendMessage(ChatColor.GREEN + "Now you can fight again!");
				defeated.remove(player);
			}
		}
	}

	//TODO 

	@EventHandler
	public void onPlayerMove (PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (defeated.contains(player)) {
			Location locto = event.getTo();
			Location locfr = event.getFrom();
			int xf = (int) locfr.getX();
			int zf = (int) locfr.getZ();
			Location loc = player.getLocation();
			loc.setZ(zf);
			loc.setX(xf);



		}
	}





}
