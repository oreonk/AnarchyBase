package com.oreonk.events;

import com.oreonk.Anarchy;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BowCooldownLogin implements Listener {
    @EventHandler
    public void bowLogin(PlayerJoinEvent event){
        if (Anarchy.getInstance().bowCD.containsKey(event.getPlayer().getUniqueId())){
            new BukkitRunnable() {
                public void run() {
                    event.getPlayer().setCooldown(Material.BOW, Anarchy.getInstance().bowCD.get(event.getPlayer().getUniqueId()));
                    event.getPlayer().setCooldown(Material.CROSSBOW, Anarchy.getInstance().bowCD.get(event.getPlayer().getUniqueId()));
                    Anarchy.getInstance().bowCD.remove(event.getPlayer().getUniqueId());
                }
            }.runTaskLater(Anarchy.getInstance(), 20);
        }
    }
}
