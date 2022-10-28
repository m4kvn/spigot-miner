package com.m4kvn.spigot.miner

import com.github.m4kvn.spigotnms.Nms
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
        Messenger(plugin = this)
    }
    private val nms: Nms by lazy {
        Nms.instance
    }
    private val config by lazy {
        MinerConfiguration(plugin = this)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onOreBreak(event: BlockBreakEvent) {
        when {
            event.isCancelled -> return
            !event.block.isSupportOre -> return
            event.player.isSneaking -> return
            !event.player.inventory.itemInMainHand.isSupportPickaxe -> return
        }

        val metadataKeyBreak = event.player.metadataKeyBreak
        if (event.block.hasMetadata(metadataKeyBreak)) {
            val data = event.block.getMetadata(metadataKeyBreak).first().value() as MiningData
            if (event.player.inventory.itemInMainHand.isSupportPickaxe) {
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
            if (event.player.inventory.itemInMainHand.isSupportPickaxe) {
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

        if (config.displayMiningData) {
            messenger.send(event.player, miningData.asString)
        }
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
        config.initialize()
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {}

    private val Player.metadataKeyBreak: String
        get() = "${this@Miner.name}_${name}_break"

    private val Player.metadataKeyDrop: String
        get() = "${this@Miner.name}_${name}_drop"

    private val ItemStack.isSupportPickaxe: Boolean
        get() = config.isSupportPickaxe(type)

    private val Block.isSupportOre: Boolean
        get() = config.isSupportOre(type)

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