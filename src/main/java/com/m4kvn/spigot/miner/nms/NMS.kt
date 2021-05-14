package com.m4kvn.spigot.miner.nms

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface NMS {
    fun breakBlock(player: Player, block: Block)
    fun dropItemStack(player: Player, itemStack: ItemStack)
    fun dropExperience(player: Player, amount: Int)
}