package com.oreonk.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuInteractCancel implements Listener {
    @EventHandler
    public void interactCancel(InventoryClickEvent event){
        if (event.getClickedInventory() != null && event.getCurrentItem() != null) {
            if (event.getAction() == InventoryAction.HOTBAR_SWAP||event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD||event.isShiftClick()) {
                if (event.getView().getTitle().equalsIgnoreCase("*Введи имя*")) {
                    event.setCancelled(true);
                }
            }
            if (event.getView().getTitle().equalsIgnoreCase("*Введи имя*")){
                event.setCancelled(true);
            }
        }
    }
}
