package com.gamergeist.guiplanner.commands;

import com.gamergeist.guiplanner.GUIPlan.GUIEditor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class gpComplete implements TabCompleter {

    List<String> gp = Arrays.asList("gui","help","item");
    List<String> item = Arrays.asList("enchant","glow","lore","rename");
    List<String> gui = Arrays.asList("create","editor","rename","list","delete");
    List<String> glow = Arrays.asList("true","false");
    List<String> lore = Arrays.asList("1","2","3","4","5","6","7","8","9","10");
    List<String> create = Arrays.asList("9","18","27","36","45","54");
    List<String> enchant = Arrays.asList("aqua_affinity","bane_of_arthropods","blast_protection","channeling","curse_of_binding","curse_of_vanishing","depth_strider","efficiency",
                                        "feather_falling","fire_aspect","fire_protection","flame","fortune","frost_walker","impaling","infinity",
                                        "knockback","looting","loyalty","luck_of_the_sea","lure","mending","multishot","piercing",
                                        "power","projectile_protection","protection","punch","quick_charge","respiration","riptide","sharpness",
                                        "silk_touch","smite","sweeping_edge","thorns","unbreaking");

 public gpComplete(){ }

 public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
     final List<String> completions = new ArrayList<>();
     if(cmd.getName().equalsIgnoreCase("gp")){
        if(args.length == 1){
            StringUtil.copyPartialMatches(args[0],gp, completions);
        }
        else if(args.length == 2){
            if(args[0].equalsIgnoreCase("item")){
               StringUtil.copyPartialMatches(args[1],item, completions);
            }
            else if(args[0].equalsIgnoreCase("gui")){
                StringUtil.copyPartialMatches(args[1],gui, completions);
            }
        }
        else if(args.length == 3){
            if(args[0].equalsIgnoreCase("item")){
                switch(args[1].toLowerCase()){
                    case "enchant":
                        StringUtil.copyPartialMatches(args[2],enchant,completions);
                        break;
                    case "glow":
                        StringUtil.copyPartialMatches(args[2],glow,completions);
                        break;
                    case "lore":
                         StringUtil.copyPartialMatches(args[2],lore,completions);
                         break;
                    case "rename":
                         StringUtil.copyPartialMatches(args[2],getGUIList(),completions);
                         break;
                }
            }
            else if(args[0].equalsIgnoreCase("gui")){
                switch(args[1].toLowerCase()){
                    case "create":
                        StringUtil.copyPartialMatches(args[2],create,completions);
                        break;
                    case "editor":
                        StringUtil.copyPartialMatches(args[2],getGUIList(),completions);
                        break;
                    case "rename":
                         StringUtil.copyPartialMatches(args[2],getGUIList(),completions);
                         break;
                    case "delete":
                         StringUtil.copyPartialMatches(args[2],getGUIList(),completions);
                         break;
                }
            }
        }
     }

     return completions;
 }

 public List<String> getGUIList(){
    List<String> newList = new ArrayList<>();
    GUIEditor.getMap().keySet().forEach(gui ->{
        newList.add(gui);
    });
    return newList;
 }

}

