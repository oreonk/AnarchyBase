package com.oreonk;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class Placeholders extends PlaceholderExpansion {
    @Override
    public @NotNull
    String getIdentifier() {return "Anarchy";}

    @Override
    public @NotNull String getAuthor() {return "oreonk";}

    @Override
    public @NotNull String getVersion() {return "1.0";}

    @Override
    public boolean canRegister() {return true;}

    @Override
    public boolean persist() {return true;}

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }
        if (params.equalsIgnoreCase("timer")) {
            LocalDateTime now = LocalDateTime.now();
            now = now.minusMinutes(1);
            if (now.getHour() >= 10 && now.getHour() < 14){
                LocalDate date = LocalDate.now();
                String nextdatetime = date+"T14:00:00.0";
                LocalDateTime next = LocalDateTime.parse(nextdatetime);
                return String.valueOf(ChronoUnit.MINUTES.between(now,next));
            } else if (now.getHour() >= 14 && now.getHour() < 18){
                LocalDate date = LocalDate.now();
                String nextdatetime = date+"T18:00:00.0";
                LocalDateTime next = LocalDateTime.parse(nextdatetime);
                return String.valueOf(ChronoUnit.MINUTES.between(now,next));
            } else if (now.getHour() >= 18 && now.getHour() < 22){
                LocalDate date = LocalDate.now();
                String nextdatetime = date+"T22:00:00.0";
                LocalDateTime next = LocalDateTime.parse(nextdatetime);
                return String.valueOf(ChronoUnit.MINUTES.between(now,next));
            } else {
                LocalDate date = LocalDate.now();
                date = date.plusDays(1);
                LocalDateTime tempdate = LocalDateTime.now();
                String nextdatetime = date+"T10:00:00.0";
                LocalDateTime next = LocalDateTime.parse(nextdatetime);
                return String.valueOf(ChronoUnit.MINUTES.between(now, next));
            }
        }
        if (params.equalsIgnoreCase("location")) {
            if(!Anarchy.getInstance().bossLocation.isEmpty()){
                return ChatColor.GREEN + "Находится на: " + Anarchy.getInstance().bossLocation.get(0);
            } else {
                return ChatColor.RED + "Нет живого босса..";
            }
        }
        return null;
    }
}
