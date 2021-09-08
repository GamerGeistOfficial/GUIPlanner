package com.gamergeist.guiplanner.GUIPlan;

import com.gamergeist.guiplanner.GUIPlanner;
import com.gamergeist.guiplanner.utils.ConfigManager;
import com.gamergeist.guiplanner.utils.ItemFactory.ItemFactory;
import com.gamergeist.guiplanner.utils.Messages.Variables;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Collections;

public class GUICreator implements Listener {

    private static FileConfiguration guiDataConfig;
    private final GUIPlanner plugin;
    private final Variables msg;
    private final ConfigManager config;
    private static GUICreator instance;
    private static GUIEditor editor;

    public GUICreator(GUIPlanner plugin) {
        GUICreator instance = this;
        this.plugin =  plugin;
        this.msg = Variables.getInstance();
        this.config = ConfigManager.getinstance();
        editor = GUIEditor.getInstance();
        guiDataConfig = config.getConfig("GuiData.yml").getConfig();
    }

    public static GUICreator getInstance() {
        return instance;
    }

    public static String testInventoryName = "§aTest §6GUI";
    public static String testItem1Name = "§aTest §6Item §b1";
    public static String testItem2Name = "§aTest §6Item §b2";
    public static String testItem3Name = "§aTest §6Item §b3";
    public static int testInventorySize = 18;

    public static Inventory testMenu(Player p) {

        Inventory testInventory = Bukkit.createInventory(null, testInventorySize, testInventoryName);

        testInventory.setItem(1, ItemFactory.generateItemStack(testItem1Name, Material.REDSTONE, 1, false));
        testInventory.setItem(2, ItemFactory.generateItemStack(testItem2Name, Material.EMERALD, 1, true, Collections.singletonList("§aI am an emerald! :D")));
        testInventory.setItem(3, ItemFactory.generateItemStackEnchant(testItem3Name, Material.DIAMOND, 1, false, Enchantment.DAMAGE_ALL, 20, Arrays.asList("§aI am an enchanted Diamond!", "§eI am also blue")));

        return testInventory;
    }

    /*
    public static void createGUI(int GUINumber, String GUIName, int GUISize) {

        Inventory inventory = Bukkit.createInventory(null, GUISize, GUIName);



    }*/
}
