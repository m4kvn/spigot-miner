package com.m4kvn.spigot.miner.nms

import net.minecraft.core.BlockPosition
import net.minecraft.server.level.EntityPlayer
import net.minecraft.server.level.PlayerInteractManager
import net.minecraft.server.level.WorldServer
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.item.EntityItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.GameRules
import org.bukkit.entity.Player

val EntityPlayer.playerInteractManager: PlayerInteractManager get() = d

fun EntityItem.resetPickupDelay() = o()

fun PlayerInteractManager.breakBlock(blockPosition: BlockPosition) = a(blockPosition)

val Player.position: BlockPosition
    get() = BlockPosition(location.x, location.y, location.z)

val BlockPosition.x: Int get() = u()
val BlockPosition.y: Int get() = v()
val BlockPosition.z: Int get() = w()

val WorldServer.isClientSide: Boolean get() = y
val WorldServer.gameRules: GameRules get() = W()
val WorldServer.random: RandomSource get() = w
val RandomSource.nextFloat: Float get() = i()

val ItemStack.isEmpty: Boolean get() = b()

fun GameRules.getBoolean(gameRuleKey: GameRules.GameRuleKey<GameRules.GameRuleBoolean>) = b(gameRuleKey)

object GameRuleKey {
    val DO_ITEM_DROPS: GameRules.GameRuleKey<GameRules.GameRuleBoolean>
        get() = GameRules.g
}