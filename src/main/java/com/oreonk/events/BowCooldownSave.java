package com.oreonk.events;

import com.oreonk.Anarchy;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class BowCooldownSave implements Listener {
    @EventHandler
    public void bowCd(PlayerQuitEvent event){
        if (event.getPlayer().hasCooldown(Material.BOW)){
            Anarchy.getInstance().bowCD.put(event.getPlayer().getUniqueId(), event.getPlayer().getCooldown(Material.BOW));
        }
    }
}
