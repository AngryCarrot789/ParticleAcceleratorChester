package particleacceleratorchester;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticleAcceleratorChester extends JavaPlugin {
	public void onEnable() {
		getLogger().info("Particle Accelerator Chester has been enabled!");
	}
	
	public void onDisable() {
		getLogger().info("Particle Accelerator Chester has been disabled!");
	}
	
	public void ChestParticleAccelerator(Location location, Integer blockID) {
		location.getBlock().setType(Material.CHEST);
		Chest chest = (Chest)location.getBlock().getState();
		Inventory inv = chest.getInventory();
		ItemStack paStack = new ItemStack(Material.getMaterial(blockID.intValue()), 1);
		inv.setItem(26, paStack);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		Player playerSender = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("info")) {
			playerSender.sendMessage(ChatColor.YELLOW + "Version 1.2");
			playerSender.sendMessage(ChatColor.BLUE + "and made by carrot :))");
		}
		
		else if (cmd.getName().equalsIgnoreCase("chestpa")) {
			Boolean passwordCorrect = false;
			if (args.length > 0) {
				//String password = "LadyGagaCanBeShutUpIfYouPokerFace".toLowerCase().trim();
				String password = "yourpasswordhere".toLowerCase().trim();
				String inputPassword = args[0].toLowerCase().trim();
				Boolean checkAllChunks = false;//Boolean.parseBoolean(args[1]);
				if (inputPassword.equalsIgnoreCase(password)) {
					playerSender.sendMessage("Password Correct!");
					passwordCorrect = true;
				}
				else {
					playerSender.sendMessage("Incorrect Password! You put in: '" + inputPassword + "'");
					return true;
				}
				playerSender.sendMessage("Checking all loaded chunks for Particle Accelerators");
				
				if (passwordCorrect) {
					Integer blockID = 1404;
					// Check if item ID is valid...					
					try {
						List<World> worlds = Bukkit.getServer().getWorlds();
						Integer totalPAsChested = 0;
						playerSender.sendMessage("Scanning " + worlds.size() + " worlds for Particle Accelerators");
						for(World world : worlds) {
							int chestedPAsInWorld = 0;
							// 640x640 chunks
							if (checkAllChunks) {
								// this will crash every server
								//for(int x = -320; x < 320; x++) {
								//	for(int y = -320; y < 320; y++) {
								//		try {
								//			Chunk c = world.getChunkAt(x + 9, y + 9);
								//			for(BlockState entity : c.getTileEntities()) {
								//				if (!c.isLoaded()) {
								//					if(!c.load()) {
								//						break;
								//					}
								//				}
								//				if (entity.getType().getId() == blockID.intValue()) {
								//					Location paLocation = entity.getBlock().getLocation();
								//					ChestParticleAccelerator(paLocation, blockID);
								//					chestedPAsInWorld++;
								//					totalPAsChested++;
								//				}
								//			}
								//		}
								//		catch(Exception e) {
								//			
								//		}
								//	}
								//}	
							}
							else {
								Chunk[] loadedChunks = world.getLoadedChunks();
								for(Chunk chunk : loadedChunks) {
									for(BlockState entity : chunk.getTileEntities()) {
										if (entity.getType().getId() == blockID.intValue()) {
											Block b = entity.getBlock();
											//ItemStack woodenPickaxe = new ItemStack(Material.WOOD_PICKAXE);
											//b.breakNaturally(tool);
											Location paLocation = b.getLocation();
											ChestParticleAccelerator(paLocation, blockID);
											chestedPAsInWorld++;
											totalPAsChested++;
										}
									}
								}
							}
							
							if (blockID.intValue() == 1404) {
								playerSender.sendMessage(
										"Chested " + 
										String.valueOf(chestedPAsInWorld) + 
										" Particle Accelerators in world '" + 
										world.getName() + "'");
							}
							else {
								playerSender.sendMessage(
										"Chested " + 
										String.valueOf(chestedPAsInWorld) + 
										" Tiles with ID " + 
										String.valueOf(blockID) +
										" in world '" + 
										world.getName() + "'");
							}
						}
						if (blockID.intValue() == 1404) {
							playerSender.sendMessage("Chested " + String.valueOf(totalPAsChested) + " Particle Accelerators in total");
						}
						else {
							playerSender.sendMessage("Chested " + String.valueOf(totalPAsChested) + " Tiles in total");
						}
					}
					catch (Exception e){
						getLogger().info("Exception: " + e.getMessage());
					}
					finally {
						
					}
				}
			}
			else {
				playerSender.sendMessage("what? you cant do that maet");
			}
		}
		return true;
	}
}
