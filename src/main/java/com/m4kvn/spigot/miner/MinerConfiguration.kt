package com.m4kvn.spigot.miner

import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin

class MinerConfiguration(
    private val plugin: JavaPlugin,
) {

    val displayMiningData: Boolean
        get() = plugin.config.getBoolean(Key.DISPLAY_MINING_DATA.name)

    fun isSupportOre(type: Material) = plugin.config
        .getStringList(Key.SUPPORT_ORES.name)
        .contains(type.name)

    fun isSupportPickaxe(type: Material) = plugin.config
        .getStringList(Key.SUPPORT_PICKAXES.name)
        .contains(type.name)

    fun initialize() {
        plugin.config.apply {
            addDefault(Key.DISPLAY_MINING_DATA.name, false)
            addDefault(Key.SUPPORT_ORES.name, defaultSupportOres)
            addDefault(Key.SUPPORT_PICKAXES.name, defaultSupportPickaxes)
            options().copyDefaults(true)
        }
        plugin.saveConfig()
    }

    private val defaultSupportOres: List<String>
        get() = listOf(
            Material.COAL_ORE,
            Material.COPPER_ORE,
            Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.DEEPSLATE_EMERALD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.GLOWSTONE,
            Material.GOLD_ORE,
            Material.IRON_ORE,
            Material.LAPIS_ORE,
            Material.NETHER_GOLD_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.OBSIDIAN,
            Material.REDSTONE_ORE,
        ).map { it.name }

    private val defaultSupportPickaxes: List<String>
        get() = listOf(
            Material.DIAMOND_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.IRON_PICKAXE,
            Material.NETHERITE_PICKAXE,
            Material.STONE_PICKAXE,
            Material.WOODEN_PICKAXE,
        ).map { it.name }

    companion object {

        private enum class Key {
            DISPLAY_MINING_DATA,
            SUPPORT_ORES,
            SUPPORT_PICKAXES,
        }
    }
}