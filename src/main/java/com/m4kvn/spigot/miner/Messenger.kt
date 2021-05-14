package com.m4kvn.spigot.miner

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Messenger(
    private val plugin: JavaPlugin
) {

    privfun prefix(obj: Any) = "[${plugin.name}] $obj"

    fun log(obj: Any) = Bukkit.getServer().consoleSender.sendMessage(prefix(obj))
}