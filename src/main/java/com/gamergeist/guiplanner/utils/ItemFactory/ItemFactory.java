package com.gamergeist.guiplanner.utils.ItemFactory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


public class ItemFactory {

    public static void addGlow(ItemStack i){
        ItemMeta im = i.getItemMeta();
        if (im != null) {
            im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
            i.setItemMeta(im);
        }
    }



    public static void removeGlow(ItemStack i) {
        ItemMeta im = i.getItemMeta();
        if (im != null) {
            im.getEnchants().keySet().forEach(im::removeEnchant);
            im.getItemFlags().remove(ItemFlag.HIDE_ENCHANTS);
            im.getItemFlags().remove(ItemFlag.HIDE_ATTRIBUTES);
            i.setItemMeta(im);
        }
    }

    public static ItemStack generateItemStack(String title, Material material, int amount, boolean shiny) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(title);
        if (shiny) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack generateItemStack(String title, Material material, int amount, boolean shiny, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(title);
        meta.setLore(lore);
        if (shiny) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack generateItemStackEnchant(String title, Material material, int amount, boolean shiny, Enchantment ench, int level, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(title);
        meta.setLore(lore);
        meta.addEnchant(ench, level, true);
        if (shiny) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }
}