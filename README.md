# spigot-miner

This is Minecraft Spigot Plugin for mining ores simply.

https://user-images.githubusercontent.com/18133161/183315923-69ccf72e-8aa6-4b4c-ba56-dcbf1177f995.mp4

## Features

- Reduces the durability of the pickaxe
- Change Player statistics
- Compatible with durability enchantments
- Drop the items to the player's position
- Drop the experience orbs to the player's position
- Do not run during sneak
- Can change supported ores by config
- Can change supported pickaxes by config

## Default Ores

- COAL_ORE
- COPPER_ORE
- DEEPSLATE_COAL_ORE
- DEEPSLATE_COPPER_ORE
- DEEPSLATE_DIAMOND_ORE
- DEEPSLATE_EMERALD_ORE
- DEEPSLATE_GOLD_ORE
- DEEPSLATE_IRON_ORE
- DEEPSLATE_LAPIS_ORE
- DEEPSLATE_REDSTONE_ORE
- DIAMOND_ORE
- EMERALD_ORE
- GLOWSTONE
- GOLD_ORE
- IRON_ORE
- LAPIS_ORE
- NETHER_GOLD_ORE
- NETHER_QUARTZ_ORE
- OBSIDIAN
- REDSTONE_ORE

## Default Pickaxes

- DIAMOND_PICKAXE
- GOLDEN_PICKAXE
- IRON_PICKAXE
- NETHERITE_PICKAXE
- STONE_PICKAXE
- WOODEN_PICKAXE

## Support Spigot Versions

| spigot | miner |
| :-- | :-- |
| 1.16.* | [0.1.2](https://github.com/m4kvn/spigot-miner/releases/tag/0.1.2) |
| 1.19.* | [1.1.0](https://github.com/m4kvn/spigot-miner/releases/tag/1.1.0) |

## Download

Download from [Releases](https://github.com/m4kvn/spigot-miner/releases).

## Development

### Setup

Create craftbukkit jar into libs for NMS development.

1. `wget -O ./server/BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar`
2. `cd ./server && java -jar BuildTools.jar --rev 1.19 --compile craftbukkit`
3. `mv ./CraftBukkit/target/craftbukkit-1.19-R0.1-SNAPSHOT.jar ../libs`
