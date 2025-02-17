package com.oreonk.events;

import com.oreonk.Anarchy;
import com.oreonk.Msg;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class PrivateBlockBreak implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void breakEvent(BlockBreakEvent event){
        if (event.getBlock().getType().equals(Material.IRON_BLOCK) || event.getBlock().getType().equals(Material.IRON_ORE) || event.getBlock().getType().equals(Material.DIAMOND_ORE) || event.getBlock().getType().equals(Material.GOLD_ORE) || event.getBlock().getType().equals(Material.DIAMOND_BLOCK) || event.getBlock().getType().equals(Material.EMERALD_BLOCK) || event.getBlock().getType().equals(Material.EMERALD_ORE)){
            Player player = event.getPlayer();
            Location location = new Location(BukkitAdapter.adapt(player.getWorld()), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            RegionManager regions = container.get(BukkitAdapter.adapt(player.getWorld()));
            ApplicableRegionSet set = query.getApplicableRegions(location);
            if (!set.getRegions().isEmpty() && Anarchy.getInstance().getDatabase().privateBlockExists(event.getBlock().getLocation())){
                String id = Anarchy.getInstance().getDatabase().getPrivateBlockIDFromLocation(event.getBlock().getLocation());
                regions.removeRegion(id);
                Msg.send(player, ChatColor.GREEN + "Вы сломали блок привата!");
                Anarchy.getInstance().getDatabase().privateBlockDelete(event.getBlock().getLocation());

            }
        }
    }
    //Вроде должно работать для гастов
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void witherExplosion(EntityExplodeEvent event){
        List<Block> blocks = event.blockList();
        if (event.getEntityType().equals(EntityType.WITHER_SKULL) || event.getEntityType().equals(EntityType.FIREBALL)) {
            for (Block block : blocks) {
                Material type = block.getType();
                //Проверка является ли блок в списке взрыва одним из блоков привата, после чего идёт проверка на локацию блока в бд
                if ((type.equals(Material.IRON_BLOCK) || type.equals(Material.IRON_ORE) || type.equals(Material.DIAMOND_ORE) || type.equals(Material.GOLD_ORE) || type.equals(Material.DIAMOND_BLOCK) || type.equals(Material.EMERALD_BLOCK) || type.equals(Material.EMERALD_ORE)) && Anarchy.getInstance().getDatabase().privateBlockExists(block.getLocation())) {
                    blocks.remove(block);
                }
            }
        } else if (event.getEntityType().equals(EntityType.PRIMED_TNT) || event.getEntityType().equals(EntityType.MINECART_TNT)) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
            for (Block block : blocks) {
                Material type = block.getType();
                //Проверка является ли блок в списке взрыва одним из блоков привата, после чего идёт проверка на локацию блока в бд
                if ((type.equals(Material.IRON_BLOCK) || type.equals(Material.IRON_ORE) || type.equals(Material.DIAMOND_ORE) || type.equals(Material.GOLD_ORE) || type.equals(Material.DIAMOND_BLOCK) || type.equals(Material.EMERALD_BLOCK) || type.equals(Material.EMERALD_ORE)) && Anarchy.getInstance().getDatabase().privateBlockExists(block.getLocation())) {
                    String id = Anarchy.getInstance().getDatabase().getPrivateBlockIDFromLocation(block.getLocation());
                    regions.removeRegion(id);
                    Anarchy.getInstance().getDatabase().privateBlockDelete(block.getLocation());
                }
            }
        }
    }
}
