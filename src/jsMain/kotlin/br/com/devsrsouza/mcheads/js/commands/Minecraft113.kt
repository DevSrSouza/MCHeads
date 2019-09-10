package br.com.devsrsouza.mcheads.js.commands

import br.com.devsrsouza.mcheads.common.Head

class Minecraft113(head: Head) : MinecraftCommands(head) {
    override fun give(): String {
        return "/give @p minecraft:player_head" +
                "{display:{Name:\"{\\\"text\\\":\\\"${head.name}\\\"}\"}," +
                "SkullOwner:{Id:\"${head.uuid}\"," +
                "Properties:{textures:[{Value:\"${textureBase64()}\"}]}}} 1"
    }

    override fun setBlock(): String {
        return "/setblock ~ ~1 ~ minecraft:player_head[rotation=0]" +
                "{Owner:{Id:\"${head.uuid}\"," +
                "Properties:{textures:[{Value:\"${textureBase64()}\"}]}}} replace"
    }

    override fun summonArmorStand(): String {
        return "/summon minecraft:armor_stand ~ ~1 ~ " +
                "{ShowArms:1b,NoBasePlate:1b," +
                "ArmorItems:[" +
                "{id:\"minecraft:leather_boots\",Count:1b}," +
                "{id:\"minecraft:leather_leggings\",Count:1b}," +
                "{id:\"minecraft:leather_chestplate\",Count:1b}," +
                "{id:\"minecraft:player_head\",Count:1b," +
                "tag:{" +
                "SkullOwner:{Id:\"${head.uuid}\"," +
                "Properties:{textures:[{Value:\"${textureBase64()}\"}]}}" +
                "}}]}"

    }

}