package com.gamergeist.guiplanner.GUIPlan;

import com.gamergeist.guiplanner.utils.ConfigManager;
import com.gamergeist.guiplanner.utils.Messages.MessageManager;
import com.gamergeist.guiplanner.utils.Messages.Variables;
import com.gamergeist.guiplanner.utils.pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class GUIRetrieveSystem {

    private final Variables msg;
    private final ConfigManager.config config;
    private static GUIRetrieveSystem instance;
    private static GUIEditor editor;


    public GUIRetrieveSystem(){
        instance = this;
        this.msg =  Variables.getInstance();
        this.config = ConfigManager.getinstance().getConfig("GuiData.yml");
        editor = GUIEditor.getInstance();
    }

    public void getAll(){
        FileConfiguration config1 = ConfigManager.getinstance().getConfig("GuiData.yml").getConfig();
        if(config1.getConfigurationSection("GUI") == null){
            config1.createSection("GUI");
        }
        ConfigurationSection config = config1.getConfigurationSection("GUI");
        config.getKeys(false).forEach(gui ->{
            ConfigurationSection c = config.getConfigurationSection(gui);
            assert c != null;
            GUIEditor.getMap().putIfAbsent(gui,new pair<>(getGui(c),c.getString("GUIName")));
        });
    }

    public Inventory getGui(ConfigurationSection c){
            Inventory gui = Bukkit.createInventory(null,c.getInt("Size"), Objects.requireNonNull(c.getString("GUIName")));
            c.getConfigurationSection("Items").getKeys(false).forEach(item ->{
                ConfigurationSection c2 = c.getConfigurationSection("Items."+item);
                gui.setItem(c2.getInt("Slot"),getItem(c2));
            });
            return gui;
    }



    public ItemStack getItem(ConfigurationSection c){
        ItemStack item = new ItemStack(Material.AIR);
        item.setType(Material.matchMaterial(c.getString("Material")));
        item.setAmount(c.getInt("Amount"));
        ItemMeta meta = item.getItemMeta();
        if(c.getString("Name") != null){
                meta.setDisplayName(c.getString("Name"));
        }
        if(c.get("Lore") != null){
            meta.setLore(c.getStringList("Lore"));
        }
        if(c.get("Unbreakable") != null){
            meta.setUnbreakable(c.getBoolean("Unbreakable"));
        }
        if(c.getConfigurationSection("Enchantment") != null){
            c.getConfigurationSection("Enchantment").getKeys(false).forEach(key ->{
                meta.addEnchant(Enchantment.getByName(key),c.getInt("Enchantment."+key),false);
            });
        }
        if(c.get("ItemFlags") != null){
            c.getStringList("ItemFlags").forEach(flag ->{
                meta.addItemFlags(ItemFlag.valueOf(flag));
            });
        }
        item.setItemMeta(meta);
        return item;
    }
}
