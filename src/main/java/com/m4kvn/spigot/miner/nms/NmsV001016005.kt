package com.m4kvn.spigot.miner.nms

import net.minecraft.server.v1_16_R3.*
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import net.minecraft.server.v1_16_R3.ItemStack as NMSItemStack

class NmsV001016005 : NMS {

    override fun breakBlock(player: Player, block: Block) {
        val craftPlayer = player as CraftPlayer
        val blockPosition = BlockPosition(block.x, block.y, block.z)
        craftPlayer.handle.playerInteractManager.breakBlock(blockPosition)
    }

    override fun dropItemStack(player: Player, itemStack: ItemStack) {
        val craftItemStack = CraftItemStack.asNMSCopy(itemStack)
        val world = (player.world as CraftWorld).handle
        if (canDrop(world, craftItemStack)) {
            val entityItem = createEntityItem(world, player.position, craftItemStack)
            world.addEntity(entityItem)
        }
    }

    override fun dropExperience(player: Player, amount: Int) {
        val position = player.position
        val world = (player.world as CraftWorld).handle
        val expEntity = EntityExperienceOrb(
            world,
            position.x + 0.5,
            position.y + 0.5,
            position.z + 0.5,
            amount
        )
        world.addEntity(expEntity)
    }

    private fun createEntityItem(
        world: WorldServer,
        dropPosition: BlockPosition,
        craftItemStack: NMSItemStack,
    ): EntityItem {
        val d0 = (world.random.nextFloat() * 0.5f).toDouble() + 0.25
        val d1 = (world.random.nextFloat() * 0.5f).toDouble() + 0.25
        val d2 = (world.random.nextFloat() * 0.5f).toDouble() + 0.25
        val entityItem = EntityItem(
            world,
            dropPosition.x + d0,
            dropPosition.y + d1,
            dropPosition.z + d2,
            craftItemStack
        )
        entityItem.n()
        return entityItem
    }

    private fun canDrop(
        world: WorldServer,
        craftItemStack: NMSItemStack,
    ): Boolean {
        return !world.isClientSide
                && !craftItemStack.isEmpty
                && world.gameRules.getBoolean(GameRules.DO_TILE_DROPS)
    }

    private val Player.position: BlockPosition
        get() = BlockPosition(location.x, location.y, location.z)
}