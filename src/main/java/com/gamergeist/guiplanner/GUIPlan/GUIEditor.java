package com.gamergeist.guiplanner.GUIPlan;

import com.gamergeist.guiplanner.GUIPlanner;
import com.gamergeist.guiplanner.utils.ConfigManager;
import com.gamergeist.guiplanner.utils.Messages.Variables;
import com.gamergeist.guiplanner.utils.pair;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Iterator;

public class GUIEditor {

    private final Variables msg;
    private final ConfigManager config;
    private static GUIEditor instance;
    private static GUIPlanner plugin;
    private static final HashMap<String, pair<Inventory,String>> guimap = new HashMap<>();

    public GUIEditor(GUIPlanner plugin) {
        this.plugin = plugin;
        instance = this;
        this.msg = Variables.getInstance();
        this.config = ConfigManager.getinstance();
    }

    public static GUIEditor getInstance() {
        return instance;
    }

    public static HashMap<String,pair<Inventory,String>> getMap(){return guimap;}

    public static void removeAllMap(){
        if(!guimap.isEmpty()){
            guimap.clear();
      /*      Iterator<String> iter =guimap.keySet().iterator();
            while(iter.hasNext()){
                guimap.remove(iter.next());
            }*/
        }
    }

    public void openGUI(String guiname, Player p){
        p.openInventory(guimap.get(guiname).getFirst());
    }

    public void rename(String guiname,String newName){
        if(guimap.containsKey(guiname)){
            guimap.replace(guiname,new pair<>(guimap.get(guiname).getFirst(),newName));
            plugin.saveNGet();
        }
    }

}
        
