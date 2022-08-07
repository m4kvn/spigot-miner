package com.m4kvn.spigot.miner

import com.m4kvn.spigot.miner.nms.NMS
import com.m4kvn.spigot.miner.nms.NMS_V1_19_R1
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

@Suppress("Unused")
class Miner : JavaPlugin(), Listener {
    private val messenger by lazy {
        Messenger(this)
    }
    private val nms: NMS by lazy {
        NMS_V1_19_R1()
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onOreBreak(event: BlockBreakEvent) {
        when {
            event.isCancelled -> return
            !event.block.isOre -> return
            event.player.isSneaking -> return
            !event.player.inventory.itemInMainHand.isPickaxe -> return
        }

        val metadataKeyBreak = event.player.metadataKeyBreak
        if (event.block.hasMetadata(metadataKeyBreak)) {
            val data = event.block.getMetadata(metadataKeyBreak).first().value() as MiningData
            if (event.player.inventory.itemInMainHand.isPickaxe) {
                nms.dropExperience(event.player, event.expToDrop)
                data.addBrokenBlock(event.block)
                data.addExp(event.expToDrop)
                event.expToDrop = 0
                return
            }
            event.isCancelled = true
            return
        }

        val unCheckedBlocks = mutableSetOf(event.block)
        val checkedBlocks = mutableSetOf<Block>()
        while (checkedBlocks.size < 64 && unCheckedBlocks.isNotEmpty()) {
            val checkingBlock = unCheckedBlocks.first()
            unCheckedBlocks.remove(checkingBlock)
            checkedBlocks.add(checkingBlock)
            val relativeBlocks = checkingBlock.getRelativeBlocks(1)
                .filter { it.type == checkingBlock.type }
                .filterNot { checkedBlocks.contains(it) }
            unCheckedBlocks.addAll(relativeBlocks)
        }

        val miningData = MiningData(event.player, event.block.type)
        checkedBlocks.forEach { ore ->
            if (event.player.inventory.itemInMainHand.isPickaxe) {
                val metadataValue = FixedMetadataValue(this, miningData)
                ore.setMetadata(metadataKeyBreak, metadataValue)
                if (ore.drops.isNotEmpty()) {
                    val metadataDropValue = FixedMetadataValue(this, miningData)
                    ore.setMetadata(event.player.metadataKeyDrop, metadataDropValue)
                }
                nms.breakBlock(event.player, ore)
                ore.removeMetadata(metadataKeyBreak, this)
            }
        }

        messenger.send(event.player, miningData.asString)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockDropItem(event: BlockDropItemEvent) {
        val metadataKey = event.player.metadataKeyDrop
        if (event.block.hasMetadata(metadataKey)) {
            val items = event.items.toList()
            event.items.clear()
            event.block.removeMetadata(metadataKey, this)
            items.forEach { item ->
                nms.dropItemStack(event.player, item.itemStack)
            }
        }
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {}

    private val Player.metadataKeyBreak: String
        get() = "${this@Miner.name}_${name}_break"

    private val Player.metadataKeyDrop: String
        get() = "${this@Miner.name}_${name}_drop"

    private val ItemStack.isPickaxe: Boolean
        get() = pickaxes.contains(type)

    private val pickaxes = listOf(
        Material.DIAMOND_PICKAXE,
        Material.GOLDEN_PICKAXE,
        Material.IRON_PICKAXE,
        Material.NETHERITE_PICKAXE,
        Material.STONE_PICKAXE,
        Material.WOODEN_PICKAXE,
    )

    private val Block.isOre: Boolean
        get() = ores.contains(type)

    private val ores = listOf(
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
    )

    private fun Block.getRelativeBlocks(distance: Int): List<Block> {
        val blocks = mutableListOf<Block>()
        val range = -distance..distance
        for (x in range) for (y in range) for (z in range) {
            if (x != 0 || y != 0 || z != 0) {
                blocks.add(getRelative(x, y, z))
            }
        }
        return blocks
    }
}