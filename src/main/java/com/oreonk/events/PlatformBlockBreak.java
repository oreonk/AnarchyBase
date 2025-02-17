package com.oreonk.events;

import com.oreonk.Anarchy;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class PlatformBlockBreak implements Listener {
    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        if (event.getBlock().getType().equals(Material.SLIME_BLOCK) && Anarchy.getInstance().platformNoBreakTime.contains(event.getBlock().getLocation())){
            event.setCancelled(true);
        }
        if ((event.getBlock().getType().equals(Material.RAW_IRON_BLOCK) || event.getBlock().getType().equals(Material.IRON_BARS)) && Anarchy.getInstance().trapNoBreak.containsKey(event.getBlock().getLocation())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void blockExplode(EntityExplodeEvent event){
        event.blockList().removeIf(b -> Anarchy.getInstance().platformNoBreakTime.contains(b.getLocation()));
        event.blockList().removeIf(b -> Anarchy.getInstance().trapNoBreak.containsKey(b.getLocation()));
    }
}

