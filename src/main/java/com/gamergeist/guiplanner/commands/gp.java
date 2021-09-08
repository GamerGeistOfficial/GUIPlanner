package com.gamergeist.guiplanner.commands;

import com.gamergeist.guiplanner.GUIPlan.GUIEditor;
import com.gamergeist.guiplanner.GUIPlan.GUISaveSystem;
import com.gamergeist.guiplanner.GUIPlanner;
//import com.gamergeist.guiplanner.utils.ItemFactory.ItemFactory;
import com.gamergeist.guiplanner.utils.ItemFactory.ItemFactory;
import com.gamergeist.guiplanner.utils.Messages.MessageManager;
import com.gamergeist.guiplanner.utils.Messages.Variables;
import com.gamergeist.guiplanner.utils.pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class gp implements CommandExecutor {

    private final Variables msg;
    private final GUISaveSystem GUISave;
    private final GUIEditor editor;

    public gp(GUIPlanner plugin) {
        gp instance = this;
        this.msg = Variables.getInstance();
        this.GUISave = GUISaveSystem.getInstance();
        this.editor = GUIEditor.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)) {
            MessageManager.ConsoleMessage(msg.noperm);
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            MessageManager.SendMessage("&e-----------------------------------", p);
            MessageManager.SendMessage(msg.prefix + "&cFor help&5,&c type &6/gp help", p);
            MessageManager.SendMessage("&e-----------------------------------", p);
        } else {
            if (args[0].equalsIgnoreCase("help")) {

                MessageManager.SendMessage(msg.prefix + "&aGui Planner Commands&6:", p);
                MessageManager.SendMessage("&cTo rename&6, &aplease use &6/&cgp item rename <New Name> &6- &dRenames the item", p);
                MessageManager.SendMessage("", p);
                MessageManager.SendMessage("&cTo add an enchant&6, &aPlease use &6/&cgp item enchant <Enchantment> <Enchantment level>", p);
                MessageManager.SendMessage("", p);
                MessageManager.SendMessage("&cTo add or remove glow&6, &aplease use &6/&cgp item glow <true|false> &6- &dMakes item enchanted and hides the enchant", p);
                MessageManager.SendMessage("", p);
                MessageManager.SendMessage("&cTo add a Lore&6, &aplease use &6/&cgp item lore &7<&cLore Line #&7> &7<&cLore Name&7> &6- &dAdds a lore to an Item", p);
                MessageManager.SendMessage("", p);
                MessageManager.SendMessage("&cFor all gui planning command&6, &aplease use &6/&cgp gui", p);
                MessageManager.SendMessage("", p);

            }
            if (args[0].equalsIgnoreCase("item")) {
                if (p.hasPermission("guiplanner.item")) {
                    if (args.length >= 2) {
                        if (args[1].equalsIgnoreCase("rename")) {
                            if (args.length >= 3) {
                                StringBuilder stringBuilder = new StringBuilder(50);
                                for (int I = 2; I < args.length; I++) {
                                    stringBuilder.append(args[I]);
                                    if (args.length - 1 != I) {
                                        stringBuilder.append(" ");
                                    }
                                }
                                String renameName = stringBuilder.toString();
                                ItemStack i = p.getInventory().getItemInMainHand();
                                ItemMeta im = i.getItemMeta();
                                if (im != null) {
                                    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', renameName));
                                    i.setItemMeta(im);
                                }
                            }else{
                                MessageManager.SendMessage(msg.prefix+"&aPlease specify new name.",p);
                            }

                        } else if (args[1].equalsIgnoreCase("enchant")) {
                            //syntax: /gp item enchant <EnchantmentName> <EnchantmentLevel>
                            if (args.length >= 4) {
                                if (Enchantment.getByKey(NamespacedKey.minecraft(args[2])) != null) {
                                    Enchantment ench = Enchantment.getByKey(NamespacedKey.minecraft(args[2]));
                                    try {
                                        int lvl = Integer.parseInt(args[3]);
                                        //here
                                        ItemStack item = p.getInventory().getItemInMainHand();
                                        ItemMeta meta = item.getItemMeta();
                                        if (meta != null) {
                                            if (ench.canEnchantItem(item)) {
                                                meta.addEnchant(ench, lvl, true);
                                                item.setItemMeta(meta);
                                                p.getInventory().setItemInMainHand(item);
                                                MessageManager.SendMessage(msg.prefix + "&aSuccessfully enchanted with &b" + args[2] + " &aand a level of &b" + lvl + "&6!", p);
                                            } else {
                                                MessageManager.SendMessage(msg.warning + "Enchant cannot be added to this item", p);
                                            }
                                        } else {
                                            MessageManager.SendMessage(msg.warning + "No item selected", p);
                                        }
                                    } catch (Exception E) {
                                        MessageManager.SendMessage(msg.warning + "Invalid Number", p);
                                    }

                                } else {
                                    MessageManager.SendMessage(msg.warning + "&cEnchantment doesn't exist", p);
                                }
                            } else {
                                MessageManager.SendMessage(msg.syntaxError + "/gp enchant <EnchantmentName> <EnchantmentLevel>", p);
                            }

                        } else if (args[1].equalsIgnoreCase("glow")) {
                            // /gp item glow <True|false>

                            if (args.length > 2) {
                                try {
                                    boolean choice = Boolean.parseBoolean(args[2]);
                                    if (choice) {
                                        ItemFactory.addGlow(p.getInventory().getItemInMainHand());
                                        MessageManager.SendMessage(msg.prefix + "&aItem has been affected with Glowing&6!", p);
                                    } else {
                                        ItemFactory.removeGlow(p.getInventory().getItemInMainHand());
                                        MessageManager.SendMessage(msg.prefix + "&aGlowing has been removed from the item&6!", p);
                                    }
                                } catch (Exception E) {
                                    MessageManager.SendMessage(msg.warning + "&aPlease select true or false", p);
                                }
                            } else {
                                ItemFactory.addGlow(p.getInventory().getItemInMainHand());
                                MessageManager.SendMessage(msg.prefix + "&aItem has been affected with Glowing&6!", p);
                            }
                        } else if (args[1].equalsIgnoreCase("lore")) {
                            // /gp item lore <line> <new lore>
                            if (args.length >= 4) {
                                StringBuilder sbLore = new StringBuilder();
                                for (int I = 3; I < args.length; I++) {
                                    sbLore.append(args[I]);
                                    if (args.length - 1 != I) {
                                        sbLore.append(" ");
                                    }
                                }
                                String loreName = sbLore.toString().replaceAll("&", "§");
                                ItemStack i = p.getInventory().getItemInMainHand();
                                ItemMeta im = i.getItemMeta();
                                if (im != null) {
                                    List<String> lore;
                                    if (im.getLore() != null) {
                                        lore = im.getLore();
                                    } else {
                                        lore = new ArrayList<>();
                                    }
                                    for (int j = lore.size(); j < Integer.parseInt(args[2]); j++) {
                                        lore.add(" ");
                                    }
                                    lore.set(Integer.parseInt(args[2]) - 1, loreName);
                                    im.setLore(lore);
                                    i.setItemMeta(im);
                                }
                            } else {
                                MessageManager.SendMessage(msg.prefix + " &cspecify a lore number", p);
                            }
                        }
                    } else {
                        MessageManager.SendMessage("&e-----------------------------------", p);
                        MessageManager.SendMessage(msg.prefix + "&cFor help&5,&c type &6/gp help", p);
                        MessageManager.SendMessage("&e-----------------------------------", p);
                    }
                } else {
                    MessageManager.SendMessage(msg.noperm, p);
                }
            }
            if (p.hasPermission("guiplanner.gui")) {
                if (args[0].equalsIgnoreCase("gui")) {
                    if (args.length >= 2) {
                        if (args[1].equalsIgnoreCase("create")) {
                            if (args.length <= 4) {
                                MessageManager.SendMessage(msg.prefix + "&cSyntax Error:use ic gui create <inv size> <inv name> <inv display name>", p);
                                return true;
                            }
                            StringBuilder sb = new StringBuilder(50);
                            for (int I = 5; I <= args.length; I++) {
                                sb.append(args[I - 1]);
                                if (args.length != I) {
                                    sb.append(" ");
                                }
                            }
                            // /gp gui1 create2 <inv size>3 <inv name>4 <inv display name>5
                            if (!GUIEditor.getMap().containsKey(args[3])) {
                                int invsize = Integer.parseInt(args[2]);
                                if (invsize % 9 == 0) {
                                    Inventory gui = Bukkit.createInventory(null, invsize, sb.toString().replaceAll("&", "§"));
                                    GUIEditor.getMap().putIfAbsent(args[3], new pair<>(gui, sb.toString().replaceAll("&", "§")));
                                    MessageManager.SendMessage(msg.prefix + "&aInventory &c" + args[3] + " &ahas been created with a display name of " +
                                            sb.toString(), p);
                                } else {
                                    MessageManager.SendMessage(msg.prefix + "&aPlease choose a multiple of 9&6!" +
                                            "&b9&6, &b18&6, &b27&6, &b36&6, &b45&6, &b54", p);
                                }
                            } else {
                                //if inv already exist.
                                MessageManager.SendMessage(msg.prefix + msg.warning + "&aInventory already exists&6!", p);
                            }


                        } else if (args[1].equalsIgnoreCase("rename")) {
                            //Syntax : ic gui rename <name> <NewDisplayname>
                            if (args.length <= 3) {
                                MessageManager.SendMessage(msg.prefix + msg.syntaxError + "&aPlease use &6/&cic gui rename <invname> <NewDisplayName>", p);
                            } else {
                                if (GUIEditor.getMap().containsKey(args[2])) {
                                    StringBuilder sb = new StringBuilder(50);
                                    for (int i = 3; i < args.length; i++) {
                                        sb.append(args[i]);
                                        if (i != args.length - 1) {
                                            sb.append(" ");
                                        }
                                    }
                                    GUIEditor.getInstance().rename(args[2], sb.toString().replaceAll("&", "§"));
                                    MessageManager.SendMessage(msg.prefix + "&aSuccessfully changed display name for &b" + args[2] + " &ato " + sb.toString() + "&6!", p);
                                } else {
                                    MessageManager.SendMessage(msg.prefix + "&7[&cWarning&7] &cGUI Does not exist", p);
                                }

                            }

                        } else if (args[1].equalsIgnoreCase("editor")) {
                            if (args.length <= 2) {
                                MessageManager.SendMessage(msg.prefix + "&aplease provide the inventory name to edit&6.&a View this with &6/&cic gui list", p);
                            }
                            if (args.length >= 3) {
                                if (GUIEditor.getMap().containsKey(args[2])) {
                                    if (GUIEditor.getMap().get(args[2]).getFirst().getViewers().isEmpty()) {
                                        GUIEditor.getInstance().openGUI(args[2], p);
                                    } else {
                                        MessageManager.SendMessage(msg.prefix + "&7[&cWarning&7] &aA player is already editing that GUI", p);
                                    }
                                } else {
                                    MessageManager.SendMessage(msg.prefix + "&aInventory does not exist&6. &aPlease use &6/&cic gui list", p);
                                }
                            }
                        } else if (args[1].equalsIgnoreCase("list")) {
                            StringBuilder b = new StringBuilder(500);

                            MessageManager.SendMessage(msg.prefix + "&aList of all GUIs below&6:", p);

                            GUIEditor.getMap().keySet().forEach(invname -> b.append(invname).append("\n"));

                            MessageManager.SendMessage(b.toString(), p);

                        } else if (args[1].equalsIgnoreCase("delete")) {
                            if (args.length >= 3) {
                                //Delete the Inventory based on "InvName" in the create section
                                if (GUIEditor.getMap().containsKey(args[2])) {
                                    GUIEditor.getMap().remove(args[2]);
                                    MessageManager.SendMessage(msg.prefix + "&cInventory deleted", p);

                                } else {
                                    MessageManager.SendMessage(msg.prefix + "&7[&cWarning&7] &aInventory does not exist&6!", p);
                                }
                            }
                        } else {
                            MessageManager.SendMessage(msg.unknownArg + "&aPlease use &6/&cgp help &afor more help", p);
                        }
                    } else {
                        MessageManager.SendMessage(msg.prefix + "&aGUI Commands&6:", p);
                        MessageManager.SendMessage("&6/&cgp gui create &6<&aInventory Size&6> &6<&aInventory name for GuiData.yml " +
                                "&7(&4NO COLORS OR SPACES&7)&7>&6<&aGUI Name&6>&dCreates a new GUI planner", p);
                        MessageManager.SendMessage("", p);
                        MessageManager.SendMessage("&6/&cgp gui editor &6<&aInventory Name&6> &dOpens a GUI planner already created", p);
                        MessageManager.SendMessage("", p);
                        MessageManager.SendMessage("&6/&cgp gui list&dLists all GUI planners created", p);
                        MessageManager.SendMessage("", p);
                        MessageManager.SendMessage("&6/&cgp gui rename <inv name> <new DisplayName> &dLists all GUI planners created", p);
                        MessageManager.SendMessage("", p);
                        MessageManager.SendMessage("&6/&cgp gui delete &6<&aInventory Name&6>&dDeletes a GUI planner already created", p);
                        MessageManager.SendMessage("", p);
                    }
                }
            } else {
                MessageManager.SendMessage(msg.noperm, p);
            }
        }
        return false;
    }
}
