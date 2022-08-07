# spigot-miner

This is Minecraft Spigot Plugin for mining ores simply.

## Features

- Reduces the durability of the pickaxe
- Change Player statistics
- Compatible with durability enchantments
- Drop the items to the player's position
- Drop the experience orbs to the player's position
- Do not run during sneak

## Default Ores

- OBSIDIAN
- GLOWSTONE
- COAL_ORE
- IRON_ORE
- GOLD_ORE
- REDSTONE_ORE
- LAPIS_ORE
- NETHER_QUARTZ_ORE
- NETHER_GOLD_ORE
- EMERALD_ORE
- DIAMOND_ORE

## Spigot Versions

| spigot | miner |
| :-- | :-- |
| 1.16.* | [0.1.2](https://github.com/m4kvn/spigot-miner/releases/tag/0.1.2) |
| 1.19.* | [1.0.0](https://github.com/m4kvn/spigot-miner/releases/tag/1.0.0) |

## Download

Download from [Releases](https://github.com/m4kvn/spigot-miner/releases).

## Development

### Setup

Create craftbukkit jar into libs for NMS development.

1. `wget -O ./server/BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar`
2. `cd ./server && java -jar BuildTools.jar --rev 1.19 --compile craftbukkit`
3. `mv ./CraftBukkit/target/craftbukkit-1.19-R0.1-SNAPSHOT.jar ../libs`
