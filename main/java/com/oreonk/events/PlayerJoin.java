package com.oreonk.events;

import com.oreonk.Anarchy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoin implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        Anarchy plugin = Anarchy.getInstance();
        Player player = event.getPlayer();
        int streak = plugin.getDatabase().getStreak(player);
        plugin.getDatabase().playerDailyInsert(event.getPlayer());
        if (streak <= 30) {
            plugin.dailyStreak.put(player, streak);
        } else {
            plugin.getDatabase().resetStreak(player);
        }
        plugin.dailyTime.put(player,plugin.getDatabase().getDailyDate(player));
        plugin.weeklyTime.put(player,plugin.getDatabase().getWeeklyDate(player));
        plugin.monthlyTime.put(player,plugin.getDatabase().getMonthlyDate(player));
        Anarchy.getInstance().getMySQL().playerOverallTopInsert(player);
    }
}
