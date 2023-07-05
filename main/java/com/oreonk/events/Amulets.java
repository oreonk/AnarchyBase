package com.oreonk.events;

import com.oreonk.Anarchy;
import com.oreonk.Msg;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Amulets implements Listener {
    ArrayList<UUID> potion = new ArrayList<>();
    ArrayList<UUID> fire = new ArrayList<>();
    ArrayList<UUID> luck = new ArrayList<>();
    ArrayList<UUID> hunger = new ArrayList<>();
    @EventHandler
    public void holdEvent(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();
        if (event.getOffHandItem() != null && event.getOffHandItem().hasItemMeta() && event.getOffHandItem().getItemMeta().hasDisplayName() && event.getOffHandItem().getItemMeta().getDisplayName().contains("Амулет грифона")){ //&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 999999, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0));
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!event.getPlayer().getInventory().getItemInOffHand().hasItemMeta() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasDisplayName() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains("Амулет грифона")){//&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
                        player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        this.cancel();
                    }
                }
            }.runTaskTimer(Anarchy.getInstance(), 0,20);
        } else if (event.getOffHandItem() != null && event.getOffHandItem().hasItemMeta() && event.getOffHandItem().getItemMeta().hasDisplayName() && event.getOffHandItem().getItemMeta().getDisplayName().contains("Амулет змеи")){ //&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
            potion.add(player.getUniqueId());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!event.getPlayer().getInventory().getItemInOffHand().hasItemMeta() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasDisplayName() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains("Амулет змеи")){//&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
                        potion.remove(player.getUniqueId());
                        this.cancel();
                    }
                }
            }.runTaskTimer(Anarchy.getInstance(), 0,20);
        } else if (event.getOffHandItem() != null && event.getOffHandItem().hasItemMeta() && event.getOffHandItem().getItemMeta().hasDisplayName() && event.getOffHandItem().getItemMeta().getDisplayName().contains("Амулет дракона")) { //&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
            fire.add(player.getUniqueId());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!event.getPlayer().getInventory().getItemInOffHand().hasItemMeta() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasDisplayName() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains("Амулет дракона")){//&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
                        fire.remove(player.getUniqueId());
                        this.cancel();
                    }
                }
            }.runTaskTimer(Anarchy.getInstance(), 0,20);
        } else if (event.getOffHandItem() != null && event.getOffHandItem().hasItemMeta() && event.getOffHandItem().getItemMeta().hasDisplayName() && event.getOffHandItem().getItemMeta().getDisplayName().contains("Амулет удачи")) { //&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
            luck.add(player.getUniqueId());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!event.getPlayer().getInventory().getItemInOffHand().hasItemMeta() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasDisplayName() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains("Амулет удачи")){//&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
                        luck.remove(player.getUniqueId());
                        this.cancel();
                    }
                }
            }.runTaskTimer(Anarchy.getInstance(), 0,20);
        } else if (event.getOffHandItem() != null && event.getOffHandItem().hasItemMeta() && event.getOffHandItem().getItemMeta().hasDisplayName() && event.getOffHandItem().getItemMeta().getDisplayName().contains("Амулет лепрекона")) { //&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
            hunger.add(player.getUniqueId());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!event.getPlayer().getInventory().getItemInOffHand().hasItemMeta() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasDisplayName() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains("Амулет лепрекона")){//&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
                        hunger.remove(player.getUniqueId());
                        this.cancel();
                    }
                }
            }.runTaskTimer(Anarchy.getInstance(), 0,20);
        } else if (event.getOffHandItem() != null && event.getOffHandItem().hasItemMeta() && event.getOffHandItem().getItemMeta().hasDisplayName() && event.getOffHandItem().getItemMeta().getDisplayName().contains("Амулет банкира")) { //&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
            Anarchy.getInstance().money.add(player.getUniqueId());
            player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 999999, 4));
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!event.getPlayer().getInventory().getItemInOffHand().hasItemMeta() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasDisplayName() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains("Амулет банкира")){//&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
                        hunger.remove(player.getUniqueId());
                        player.removePotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE);
                        this.cancel();
                    }
                }
            }.runTaskTimer(Anarchy.getInstance(), 0,20);
        }
    }
    @EventHandler
    public void damageEvent(EntityDamageEvent event){
        //Без урона от падения
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getInventory().getItemInOffHand().hasItemMeta() && Objects.requireNonNull(player.getInventory().getItemInOffHand().getItemMeta()).getDisplayName().contains("кота")) {//&& player.getInventory().getItemInOffHand().getItemMeta().hasCustomModelData()) {
                if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void clickEvent(InventoryClickEvent event){
        if (event.getClickedInventory() != null && event.getClickedInventory().getType().equals(InventoryType.PLAYER) && event.getSlot() == 40){
            new BukkitRunnable() {
                @Override
                public void run() {
                    ItemStack item = event.getClickedInventory().getItem(40);
                    if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет грифона")) {
                        event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 999999, 0));
                        event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (event.getWhoClicked().getInventory().getItem(40) == null || !event.getWhoClicked().getInventory().getItem(40).equals(item)) {
                                    event.getWhoClicked().removePotionEffect(PotionEffectType.SLOW_FALLING);
                                    event.getWhoClicked().removePotionEffect(PotionEffectType.NIGHT_VISION);
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет змеи")){
                        potion.add(event.getWhoClicked().getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (event.getWhoClicked().getInventory().getItem(40) == null || !event.getWhoClicked().getInventory().getItem(40).equals(item)) {
                                    potion.remove(event.getWhoClicked().getUniqueId());
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет дракона")){
                        fire.add(event.getWhoClicked().getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (event.getWhoClicked().getInventory().getItem(40) == null || !event.getWhoClicked().getInventory().getItem(40).equals(item)) {
                                    fire.remove(event.getWhoClicked().getUniqueId());
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет удачи")){
                        luck.add(event.getWhoClicked().getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (event.getWhoClicked().getInventory().getItem(40) == null || !event.getWhoClicked().getInventory().getItem(40).equals(item)) {
                                    luck.remove(event.getWhoClicked().getUniqueId());
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет лепрекона")){
                        hunger.add(event.getWhoClicked().getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (event.getWhoClicked().getInventory().getItem(40) == null || !event.getWhoClicked().getInventory().getItem(40).equals(item)) {
                                    hunger.remove(event.getWhoClicked().getUniqueId());
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет банкира")){
                        Anarchy.getInstance().money.add(event.getWhoClicked().getUniqueId());
                        event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 999999, 4));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (event.getWhoClicked().getInventory().getItem(40) == null || !event.getWhoClicked().getInventory().getItem(40).equals(item)) {
                                    Anarchy.getInstance().money.remove(event.getWhoClicked().getUniqueId());
                                    event.getWhoClicked().removePotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE);
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    }
                }
            }.runTaskLater(Anarchy.getInstance(), 1);

        }
    }
    //Отравление и огонь
    @EventHandler
    public void poisonEvent(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            if (potion.contains(player.getUniqueId())){
                LivingEntity entity = (LivingEntity) event.getEntity();
                entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 0));
            }
            if (fire.contains(player.getUniqueId())){
                LivingEntity entity = (LivingEntity) event.getEntity();
                entity.setFireTicks(60);
            }
        }
    }
    //Дроп с растений
    @EventHandler
    public void breakEvent(BlockDropItemEvent event){
        if (luck.contains(event.getPlayer().getUniqueId())) {
            int random1 = ThreadLocalRandom.current().nextInt(0, 100);
            if (random1 < 99) {
                Location location = event.getBlock().getLocation();
                Location location2 = event.getBlock().getLocation();
                Location location3 = event.getBlock().getLocation();
                Location location4 = event.getBlock().getLocation();
                location.setY(location.getY()+0.5);
                location.setZ(location.getZ()+0.5);
                location.setX(location.getX()+0.5);
                location2.setY(location2.getY()+0.4);
                location2.setZ(location2.getZ()+0.5);
                location2.setX(location2.getX()+0.4);
                location3.setY(location3.getY()+0.4);
                location3.setZ(location3.getZ()+0.5);
                location3.setX(location3.getX()+0.5);
                location4.setY(location4.getY()+0.5);
                location4.setZ(location4.getZ()+0.5);
                location4.setX(location4.getX()+0.4);
                location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 4);
                location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location2, 4);
                location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location3, 4);
                location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location4, 4);
                for (Item stack : event.getItems()) {
                    Material material = stack.getItemStack().getType();
                    if (material.equals(Material.WHEAT) || material.equals(Material.CARROT) || material.equals(Material.POTATO) || material.equals(Material.NETHER_WART) || material.equals(Material.BEETROOT) || material.equals(Material.MELON_SLICE) || material.equals(Material.COCOA_BEANS)) {
                        if (material.equals(Material.WHEAT)){
                            int amount = stack.getItemStack().getAmount() * 2;
                            stack.getItemStack().setAmount(amount);
                        } else {
                            if (stack.getItemStack().getAmount() > 1) {
                                int amount = stack.getItemStack().getAmount() * 2;
                                stack.getItemStack().setAmount(amount);
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void mobDrop(EntityDeathEvent event){
        if (event.getEntity().getKiller() != null && !(event.getEntity() instanceof Player)){
            Player player = event.getEntity().getKiller();
            if (luck.contains(player.getUniqueId())){
                int random1 = ThreadLocalRandom.current().nextInt(0, 100);
                if (random1 < 99) {
                    Location location = event.getEntity().getLocation();
                    location.setY(location.getY()+1);
                    Location location2 = location.clone();
                    Location location3 = location.clone();
                    Location location4 = location.clone();
                    location2.setX(location.getX()-0.1);
                    location3.setX(location.getX()+0.1);
                    location4.setX(location.getX()+0.1);
                    location4.setZ(location.getZ()+0.1);
                    player.spawnParticle(Particle.VILLAGER_HAPPY, location,4);
                    player.spawnParticle(Particle.VILLAGER_HAPPY, location2,4);
                    player.spawnParticle(Particle.VILLAGER_HAPPY, location3,4);
                    player.spawnParticle(Particle.VILLAGER_HAPPY, location4,4);
                    for (ItemStack itemStack : event.getDrops()){
                        int amount = itemStack.getAmount() * 2;
                        itemStack.setAmount(amount);
                    }
                }
            }
        }
    }
    @EventHandler
    public void hungerEvent(FoodLevelChangeEvent event){
        if (hunger.contains(event.getEntity().getUniqueId())){
            event.setCancelled(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    Player player = (Player) event.getEntity();
                    player.setFoodLevel(20);
                }
            }.runTaskLater(Anarchy.getInstance(), 1);
        }
    }
    @EventHandler
    public void joinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack item = event.getPlayer().getInventory().getItem(40);
                if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет грифона")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 999999, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!event.getPlayer().getInventory().getItemInOffHand().hasItemMeta() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasDisplayName() || !event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains("Амулет грифона")){//&& event.getOffHandItem().getItemMeta().hasCustomModelData()){
                                player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(Anarchy.getInstance(), 0,20);
                } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет змеи")){
                    if (!potion.contains(player.getUniqueId())) {
                        potion.add(player.getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (player.getInventory().getItem(40) == null || !player.getInventory().getItem(40).equals(item)) {
                                    potion.remove(player.getUniqueId());
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    }
                } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет дракона")){
                    if (!fire.contains(player.getUniqueId())) {
                        fire.add(player.getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (player.getInventory().getItem(40) == null || !player.getInventory().getItem(40).equals(item)) {
                                    fire.remove(player.getUniqueId());
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    }
                } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет удачи")) {
                    if (!luck.contains(player.getUniqueId())) {
                        luck.add(player.getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (player.getInventory().getItem(40) == null || !player.getInventory().getItem(40).equals(item)) {
                                    luck.remove(player.getUniqueId());
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    }
                } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет лепрекона")) {
                    if (!hunger.contains(player.getUniqueId())) {
                        hunger.add(player.getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (player.getInventory().getItem(40) == null || !player.getInventory().getItem(40).equals(item)) {
                                    hunger.remove(player.getUniqueId());
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    }
                } else if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Амулет лепрекона")) {
                    if (!Anarchy.getInstance().money.contains(player.getUniqueId())) {
                        Anarchy.getInstance().money.add(player.getUniqueId());
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 999999, 4));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (player.getInventory().getItem(40) == null || !player.getInventory().getItem(40).equals(item)) {
                                    Anarchy.getInstance().money.remove(player.getUniqueId());
                                    player.removePotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE);
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Anarchy.getInstance(), 0, 20);
                    }
                }
            }
        }.runTaskLater(Anarchy.getInstance(), 1);
    }

}
