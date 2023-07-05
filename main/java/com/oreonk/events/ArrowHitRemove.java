package com.oreonk.events;

import com.oreonk.Msg;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Objects;

public class ArrowHitRemove implements Listener {
    HashMap<Entity, String> entity = new HashMap<>();
    HashMap<Entity, Player> entityPlayer = new HashMap<>();
    HashMap<Player, ItemStack> arrowRemove = new HashMap<>();
    @EventHandler
    public void arrowShot(EntityShootBowEvent event){
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getInventory().getItemInOffHand().getType().equals(Material.ARROW)) {
                ItemStack arrowItem = player.getInventory().getItemInOffHand();
                if (arrowItem.getItemMeta() != null && arrowItem.getItemMeta().hasDisplayName()){
                    if (arrowItem.getItemMeta().getDisplayName().contains("Взрывная стрела")) {
                        player.setCooldown(Material.BOW, 3000);
                        player.setCooldown(Material.CROSSBOW, 3000);
                        entity.put(event.getProjectile(), "explosive");
                        entityPlayer.put(event.getProjectile(), player);
                        if (Objects.requireNonNull(Objects.requireNonNull(event.getBow()).getItemMeta()).hasEnchants() && event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)){
                            arrowItem.setAmount(1);
                            player.getInventory().removeItem(arrowItem);
                        }
                    } else if (arrowItem.getItemMeta().getDisplayName().contains("Стрела телепортации")){
                        player.setCooldown(Material.BOW, 1800);
                        player.setCooldown(Material.CROSSBOW, 1800);
                        entity.put(event.getProjectile(), "teleport");
                        entityPlayer.put(event.getProjectile(), player);
                        if (Objects.requireNonNull(Objects.requireNonNull(event.getBow()).getItemMeta()).hasEnchants() && event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)){
                            arrowItem.setAmount(1);
                            player.getInventory().removeItem(arrowItem);
                        }
                    } else if (arrowItem.getItemMeta().getDisplayName().contains("Магнитная стрела")){
                        player.setCooldown(Material.BOW, 1400);
                        player.setCooldown(Material.CROSSBOW, 1400);
                        entity.put(event.getProjectile(), "magnet");
                        entityPlayer.put(event.getProjectile(), player);
                        if (Objects.requireNonNull(Objects.requireNonNull(event.getBow()).getItemMeta()).hasEnchants() && event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)){
                            arrowItem.setAmount(1);
                            player.getInventory().removeItem(arrowItem);
                        }
                    } else if (arrowItem.getItemMeta().getDisplayName().contains("Стрела сверления")){
                        player.setCooldown(Material.BOW, 400);
                        player.setCooldown(Material.CROSSBOW, 400);
                        entity.put(event.getProjectile(), "break");
                        entityPlayer.put(event.getProjectile(), player);
                        if (Objects.requireNonNull(Objects.requireNonNull(event.getBow()).getItemMeta()).hasEnchants() && event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)){
                            arrowItem.setAmount(1);
                            player.getInventory().removeItem(arrowItem);
                        }
                    }
                }
            } else {
                //Задать рандомные большие значения
                int arrow = 300;
                int spectralArrow = 300;
                int tippedArrow = 300;

                if (player.getInventory().contains(Material.ARROW)) {
                    arrow = player.getInventory().first(Material.ARROW);
                }
                if (player.getInventory().contains(Material.SPECTRAL_ARROW)) {
                    spectralArrow = player.getInventory().first(Material.SPECTRAL_ARROW);
                }
                if (player.getInventory().contains(Material.TIPPED_ARROW)) {
                    tippedArrow = player.getInventory().first(Material.TIPPED_ARROW);
                }

                if (arrow != 300) {
                    if (arrow < spectralArrow && arrow < tippedArrow) {
                        ItemStack arrowItem = player.getInventory().getItem(arrow);
                        //Добавить чеки на модель дату
                        if (arrowItem.getItemMeta() != null && arrowItem.getItemMeta().hasDisplayName() && arrowItem.getItemMeta().hasCustomModelData()) {
                            if (arrowItem.getItemMeta().getDisplayName().contains("Взрывная стрела")) {
                                player.setCooldown(Material.BOW, 3000);
                                player.setCooldown(Material.CROSSBOW, 3000);
                                entity.put(event.getProjectile(), "explosive");
                                entityPlayer.put(event.getProjectile(), player);
                                if (Objects.requireNonNull(Objects.requireNonNull(event.getBow()).getItemMeta()).hasEnchants() && event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)){
                                    arrowItem.setAmount(1);
                                    player.getInventory().removeItem(arrowItem);
                                }
                            } else if (arrowItem.getItemMeta().getDisplayName().contains("Стрела телепортации")){
                                player.setCooldown(Material.BOW, 1800);
                                player.setCooldown(Material.CROSSBOW, 1800);
                                entity.put(event.getProjectile(), "teleport");
                                entityPlayer.put(event.getProjectile(), player);
                                if (Objects.requireNonNull(Objects.requireNonNull(event.getBow()).getItemMeta()).hasEnchants() && event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)){
                                    arrowItem.setAmount(1);
                                    player.getInventory().removeItem(arrowItem);
                                }
                            } else if (arrowItem.getItemMeta().getDisplayName().contains("Магнитная стрела")){
                                player.setCooldown(Material.BOW, 1400);
                                player.setCooldown(Material.CROSSBOW, 1400);
                                entity.put(event.getProjectile(), "magnet");
                                entityPlayer.put(event.getProjectile(), player);
                                if (Objects.requireNonNull(Objects.requireNonNull(event.getBow()).getItemMeta()).hasEnchants() && event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)){
                                    arrowItem.setAmount(1);
                                    player.getInventory().removeItem(arrowItem);
                                }
                            } else if (arrowItem.getItemMeta().getDisplayName().contains("Стрела сверления")){
                                player.setCooldown(Material.BOW, 400);
                                player.setCooldown(Material.CROSSBOW, 400);
                                entity.put(event.getProjectile(), "break");
                                entityPlayer.put(event.getProjectile(), player);
                                if (Objects.requireNonNull(Objects.requireNonNull(event.getBow()).getItemMeta()).hasEnchants() && event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)){
                                    arrowItem.setAmount(1);
                                    player.getInventory().removeItem(arrowItem);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void arrowHit(ProjectileHitEvent event) {
        if (entityPlayer.containsKey(event.getEntity()) && !entityPlayer.get(event.getEntity()).isOnline()) {
            event.setCancelled(true);
            entity.remove(event.getEntity());
            entityPlayer.remove(event.getEntity());
            event.getEntity().remove();
        } else {
            if (entity.containsKey(event.getEntity())) {
                //Добавить чеки на CustomModelData
                if (entity.get(event.getEntity()).equals("teleport") && event.getHitBlock() != null) {
                    Location location = event.getHitBlock().getLocation();
                    location.setY(location.getBlockY() + 1);
                    location.setPitch(entityPlayer.get(event.getEntity()).getLocation().getPitch());
                    location.setYaw(entityPlayer.get(event.getEntity()).getLocation().getYaw());
                    Player player = entityPlayer.get(event.getEntity());
                    player.teleport(location);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 260, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 2));
                } else if (entity.get(event.getEntity()).equals("teleport") && event.getHitEntity() != null) {
                    Location location = event.getHitEntity().getLocation();
                    location.setPitch(entityPlayer.get(event.getEntity()).getLocation().getPitch());
                    location.setYaw(entityPlayer.get(event.getEntity()).getLocation().getYaw());
                    Player player = entityPlayer.get(event.getEntity());
                    player.teleport(location);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 260, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 2));
                }
                if (entity.get(event.getEntity()).equals("explosive")) {
                    Location location;
                    if (event.getHitEntity() == null) {
                        location = event.getHitBlock().getLocation();
                    } else {
                        location = event.getEntity().getLocation();
                    }
                    int amount = 0;
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (player.getLocation().distanceSquared(location) <= 10 * 10) {
                            //Добавить комбатлог
                            amount++;
                        }
                    }
                    if (amount > 0) {
                        //Добавить комбатлог стрелку
                    }
                }
                if (entity.get(event.getEntity()).equals("magnet") && event.getHitEntity() != null) {
                    Player player = entityPlayer.get(event.getEntity());
                    Entity entity = event.getHitEntity();
                    Vector direction = player.getLocation().toVector().subtract(entity.getLocation().toVector()).normalize();
                    direction.add(new Vector(0, 2, 0));
                    direction.multiply(6);
                    entity.setVelocity(direction);
                }
                if (entity.get(event.getEntity()).equals("break") && event.getHitBlock() != null) {
                    Player player = entityPlayer.get(event.getEntity());
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = container.createQuery();
                    com.sk89q.worldedit.util.Location locationPrivate = new com.sk89q.worldedit.util.Location(BukkitAdapter.adapt(player.getWorld()), event.getHitBlock().getLocation().getBlockX(), event.getHitBlock().getLocation().getBlockY(), event.getHitBlock().getLocation().getBlockZ());
                    ApplicableRegionSet set = query.getApplicableRegions(locationPrivate);
                    if (set.getRegions().isEmpty() && !event.getHitBlock().getType().equals(Material.BEDROCK)) {
                        event.getHitBlock().setType(Material.AIR);
                    }
                }
                entity.remove(event.getEntity());
                entityPlayer.remove(event.getEntity());
                event.getEntity().remove();
            }
        }
    }
}
