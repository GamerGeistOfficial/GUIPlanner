package com.gamergeist.guiplanner.utils.Messages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageManager {

    public static void SendMessage(String message, Player player){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void ConsoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',message));
    }

}
