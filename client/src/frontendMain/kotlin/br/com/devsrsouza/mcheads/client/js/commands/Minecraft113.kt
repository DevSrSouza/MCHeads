package br.com.devsrsouza.mcheads.client.js.commands

import br.com.devsrsouza.mcheads.common.Head

class Minecraft113(head: Head) : MinecraftCommands(head) {
    companion object {
        const val GIVE_CMD = "/give @p minecraft:player_head{" +
                "display:{" +
                "Name:\"{\\\"text\\\":\\\"$HEAD_NAME_PLACEHOLDER\\\"}\"}," +
                "SkullOwner:{" +
                "Id:\"$HEAD_UUID_PLACEHOLDER\"," +
                "Properties:{textures:[{Value:\"$HEAD_BASE64_PLACEHOLDER\"}]}" +
                "}" +
                "} 1"

        const val SET_BLOCK = "/setblock ~ ~1 ~ minecraft:player_head[rotation=0]{" +
                "Owner:{" +
                "Id:\"$HEAD_UUID_PLACEHOLDER\"," +
                "Properties:{textures:[{Value:\"$HEAD_BASE64_PLACEHOLDER\"}]}" +
                "}" +
                "} replace"

        const val SUMMON_ARMOR_STAND = "/summon minecraft:armor_stand ~ ~1 ~ {" +
                "ShowArms:1b," +
                "NoBasePlate:1b," +
                "ArmorItems:[" +
                "{id:\"minecraft:leather_boots\",Count:1b}," +
                "{id:\"minecraft:leather_leggings\",Count:1b}," +
                "{id:\"minecraft:leather_chestplate\",Count:1b}," +
                "{id:\"minecraft:player_head\",Count:1b,tag:{" +
                "SkullOwner:{" +
                "Id:\"$HEAD_UUID_PLACEHOLDER\"," +
                "Properties:{textures:[{Value:\"$HEAD_BASE64_PLACEHOLDER\"}]}" +
                "}" +
                "}" +
                "}" +
                "]" +
                "}"
    }

    override fun give(): String {
        return GIVE_CMD
            .replace(HEAD_NAME_PLACEHOLDER, head.name)
            .replace(HEAD_UUID_PLACEHOLDER, head.uuid)
            .replace(HEAD_BASE64_PLACEHOLDER, textureBase64())
    }

    override fun setBlock(): String {
        return SET_BLOCK
            .replace(HEAD_UUID_PLACEHOLDER, head.uuid)
            .replace(HEAD_BASE64_PLACEHOLDER, textureBase64())
    }

    override fun summonArmorStand(): String {
        return SUMMON_ARMOR_STAND
            .replace(HEAD_UUID_PLACEHOLDER, head.uuid)
            .replace(HEAD_BASE64_PLACEHOLDER, textureBase64())
    }

}