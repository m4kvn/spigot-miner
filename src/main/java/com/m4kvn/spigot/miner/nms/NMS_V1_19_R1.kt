package com.m4kvn.spigot.miner.nms

import net.minecraft.core.BlockPosition
import net.minecraft.server.level.WorldServer
import net.minecraft.world.entity.EntityExperienceOrb
import net.minecraft.world.entity.item.EntityItem
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.inventory.ItemStack
import net.minecraft.world.item.ItemStack as NMSItemStack

@Suppress("ClassName")
class NMS_V1_19_R1 : NMS {

    override fun breakBlock(player: Player, block: Block) {
        val craftPlayer = player as CraftPlayer
        val blockPosition = BlockPosition(block.x, block.y, block.z)
        craftPlayer.handle.playerInteractManager.breakBlock(blockPosition)
    }

    override fun dropExperience(player: Player, amount: Int) {
        val position = player.position
        val world = (player.world as CraftWorld).handle
        val expEntity = EntityExperienceOrb(
            world,
            position.x + 0.5,
            position.y + 0.5,
            position.z + 0.5,
            amount,
        )
        world.addFreshEntity(expEntity, CreatureSpawnEvent.SpawnReason.DEFAULT)
    }

    override fun dropItemStack(player: Player, itemStack: ItemStack) {
        val craftItem = CraftItemStack.asNMSCopy(itemStack)
        val world = (player.world as CraftWorld).handle
        if (world.isClientSide) return
        if (craftItem.isEmpty) return
        if (world.gameRules.getBoolean(GameRuleKey.DO_ITEM_DROPS).not()) return
        val entityItem = createEntityItem(world, player.position, craftItem)
        world.addFreshEntity(entityItem, CreatureSpawnEvent.SpawnReason.DEFAULT)
    }

    private fun createEntityItem(
        world: WorldServer,
        dropPosition: BlockPosition,
        itemStack: NMSItemStack,
    ): EntityItem {
        val d0 = (world.random.nextFloat * 0.5f).toDouble() + 0.25
        val d1 = (world.random.nextFloat * 0.5f).toDouble() + 0.25
        val d2 = (world.random.nextFloat * 0.5f).toDouble() + 0.25
        val entityItem = EntityItem(
            world,
            dropPosition.x + d0,
            dropPosition.y + d1,
            dropPosition.z + d2,
            itemStack,
        )
        entityItem.n()
        return entityItem
    }
}