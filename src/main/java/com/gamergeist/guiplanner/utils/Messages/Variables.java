package com.gamergeist.guiplanner.utils.Messages;

import com.gamergeist.guiplanner.GUIPlanner;

public class Variables {

    private final GUIPlanner plugin;
    public static Variables instance;

    public String noperm;
    public String prefix;
    public String ShinyStringName;
    public String unknownArg;
    public String syntaxError;
    public String warning;

    public Variables(GUIPlanner plugin) {
        this.plugin = plugin;
        instance = this;
        setMessages();
    }

    public static Variables getInstance(){
        return instance;
    }


    public void setMessages() {
        prefix = "§7[§bGUI&6|&dPlanner§7] ";
        noperm = prefix + "§aYou do not have permission§6!";
        ShinyStringName = "§7[§bItemCustomizer§7] ";
        unknownArg = prefix + "&aUnknown argument! ";
        syntaxError = prefix + "&7[&cSyntax Error&7] ";
        warning = prefix + "&7[&cWarning&7] ";
    }
}