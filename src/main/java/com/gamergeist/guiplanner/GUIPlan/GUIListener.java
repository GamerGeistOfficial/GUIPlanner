package com.gamergeist.guiplanner.GUIPlan;

import com.gamergeist.guiplanner.utils.pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Iterator;

public class GUIListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        
        if(containsInv(e.getInventory()) != null){
            HashMap<String, pair<Inventory, String>> map = GUIEditor.getMap();
            String guiname = containsInv(e.getInventory());
            pair<Inventory, String> pa = map.get(guiname);
            map.replace(guiname,new pair<Inventory,String>(e.getInventory(),pa.getSecond()));
        }
    }


    public String containsInv(Inventory inv){
        HashMap<String, pair<Inventory, String>> map  =GUIEditor.getMap();
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            String invname = iter.next();
            if(map.get(invname).getFirst().equals(inv)){
                return invname;
            }
        }
        return null;
    }

}
