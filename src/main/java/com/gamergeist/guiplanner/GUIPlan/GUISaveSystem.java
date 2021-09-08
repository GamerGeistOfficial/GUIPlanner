package com.gamergeist.guiplanner.GUIPlan;

import com.gamergeist.guiplanner.GUIPlanner;
import com.gamergeist.guiplanner.utils.ConfigManager;
import com.gamergeist.guiplanner.utils.Messages.MessageManager;
import com.gamergeist.guiplanner.utils.Messages.Variables;
import com.gamergeist.guiplanner.utils.pair;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUISaveSystem {

    private final Variables msg;
    private final ConfigManager.config config;
    private static GUISaveSystem instance;
    private static GUIEditor editor;
    



    public void save(String inventory){
        pair<Inventory, String> p =GUIEditor.getMap().get(inventory);
        if(config.getConfig().getConfigurationSection("GUI") == null){
            config.getConfig().createSection("GUI");
        }
        ConfigurationSection c = config.getConfig().getConfigurationSection("GUI");
        if(c.getConfigurationSection(inventory) == null){
        c.createSection(inventory);
        }
        saveGui(c.getConfigurationSection(inventory),p.getFirst(),p.getSecond());
        config.saveConfig();
    }

    public void saveAll(){
        deleteOld();
        GUIEditor.getMap().keySet().forEach(this::save);
    }

    public void deleteOld(){
        if(config.getConfig().getConfigurationSection("GUI") == null){
            config.getConfig().createSection("GUI");
        }
        ConfigurationSection c = config.getConfig().getConfigurationSection("GUI");
        c.getKeys(false).forEach(key ->{
            if(!GUIEditor.getMap().containsKey(key)){
                MessageManager.ConsoleMessage("deleting "+key);
                c.set(key,null);
            }
        });
        config.saveConfig();

        
    }


    public static GUISaveSystem getInstance() {
        return instance;
    }

    public static void saveGui(ConfigurationSection c,Inventory gui,String invName){
        c.set("GUIName",invName);
        c.set("Size",gui.getSize());
        c.createSection("Items");
        for (int i = 0;i<gui.getSize();i++) {
            ItemStack item = gui.getItem(i);
            if(item != null){
            c.createSection("Items.Item"+i);
            saveItem(c.getConfigurationSection("Items.Item"+i),item,i);
            }
        }
    }

    public static void saveItem(ConfigurationSection c, ItemStack item,int Slot){
        c.set("Material", item.getType().toString());
        c.set("Slot",Slot);
        c.set("Amount",item.getAmount());
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            c.set("Name",meta.getDisplayName());
            if(meta.getLore() != null){
                if(!meta.getLore().isEmpty()){
                    c.set("Lore",meta.getLore());
                }
            }
            if(meta.isUnbreakable()){
                c.set("Unbreakable",true);
            }
            if(meta.hasEnchants()){
                for (Map.Entry<Enchantment, Integer> set : meta.getEnchants().entrySet()) {
                    c.set("Enchantment." + set.getKey().getName(), set.getValue());
                }
            }
            if(!meta.getItemFlags().isEmpty()){
                List<String> flags = new ArrayList<>();
                 meta.getItemFlags().iterator().forEachRemaining(flag -> flags.add(flag.name()));
                    c.set("ItemFlags",flags);
            }

        }


    }

    public GUISaveSystem(GUIPlanner plugin) {
        instance = this;
        this.msg =  Variables.getInstance();
        this.config = ConfigManager.getinstance().getConfig("GuiData.yml");
        editor = GUIEditor.getInstance();
    }
}

