package com.m4kvn.spigot.miner

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import java.util.concurrent.atomic.AtomicInteger

data class MiningData(
    private val player: Player,
    private val blockMaterial: Material,
) {
    private val oldStatistic = player.getStatistic(Statistic.MINE_BLOCK, blockMaterial)
    private val oldDurability = player.inventory.itemInMainHand.remainingDurability
    private val brokenBlocks = mutableSetOf<Block>()
    private val expHolder = AtomicInteger(0)

    val asString: String
        get() {
            val newStatistic = player.getStatistic(Statistic.MINE_BLOCK, blockMaterial)
            val newDurability = player.inventory.itemInMainHand.remainingDurability
            return buildString {
                append("[Blocks=${ChatColor.BLUE}${brokenBlocks.size}${ChatColor.RESET}] ")
                append("[Exp=${ChatColor.GREEN}${expHolder.get()}${ChatColor.RESET}] ")
                append("[Statistic=${ChatColor.AQUA}$oldStatistic→$newStatistic${ChatColor.RESET}] ")
                append("[Durability=${ChatColor.GOLD}$oldDurability→$newDurability${ChatColor.RESET}] ")
            }
        }

    fun addBrokenBlock(block: Block) {
        brokenBlocks.add(block)
    }

    fun addExp(amount: Int) {
        expHolder.addAndGet(amount)
    }

    private val ItemStack.remainingDurability: Int
        get() = type.maxDurability - ((itemMeta as? Damageable)?.damage ?: 0)
}