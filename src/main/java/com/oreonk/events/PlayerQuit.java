package com.oreonk.events;

import com.oreonk.Anarchy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        Anarchy plugin = Anarchy.getInstance();
        Player player = event.getPlayer();
        plugin.monthlyTime.remove(player);
        plugin.dailyTime.remove(player);
        plugin.weeklyTime.remove(player);
        plugin.dailyStreak.remove(player);
    }
}
