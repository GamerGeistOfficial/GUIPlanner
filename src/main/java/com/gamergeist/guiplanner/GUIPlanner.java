package com.gamergeist.guiplanner;

import com.gamergeist.guiplanner.GUIPlan.*;
import com.gamergeist.guiplanner.commands.gp;
import com.gamergeist.guiplanner.commands.gpComplete;
import com.gamergeist.guiplanner.utils.ConfigManager;
import com.gamergeist.guiplanner.utils.Messages.Variables;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class GUIPlanner extends JavaPlugin {

    private static GUIPlanner instance;

    public static GUIPlanner getInstance() {
        return instance;
    }

    public static Variables msg;
    public static ConfigManager cm;
    public static GUIEditor guiedit;
    public static GUISaveSystem guisave;
    public static GUICreator guicreator;
    public static GUIRetrieveSystem guiget;

    private static int savetime = 30*60*20;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        msg = new Variables(this);
        cm = new ConfigManager(this);
        guiedit = new GUIEditor(this);
        guicreator = new GUICreator(this);
        guisave = new GUISaveSystem(this);
        guiget = new GUIRetrieveSystem();
        guiget.getAll();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::saveNGet,savetime,savetime);
        
        RegisterCommands();
        RegisterEvents();


    }

    @Override
    public void onDisable() {
        guisave.saveAll();
    }

    public void RegisterEvents() {
        register(new GUICreator(this));
        register(new GUIListener());
    }

    public void RegisterCommands() {
        register("gp", new gp(this));
        this.getCommand("gp").setTabCompleter(new gpComplete());
    }

    public void register(Listener l) {
        Bukkit.getPluginManager().registerEvents(l,this);
    }

    public void register(String n, CommandExecutor ce) {
        Objects.requireNonNull(getCommand(n)).setExecutor(ce);
    }

    public void saveNGet(){
        guisave.saveAll();
        GUIEditor.removeAllMap();
        guiget.getAll();
    }

}
