package br.com.devsrsouza.mcheads.js.commands

import br.com.devsrsouza.mcheads.common.Head

class Minecraft112(head: Head) : MinecraftCommands(head) {
    companion object {
        const val GIVE_CMD = "/give @p skull 1 3 {" +
                "display:{Name:\"$HEAD_NAME_PLACEHOLDER\"}," +
                "SkullOwner:{" +
                "Id:\"$HEAD_UUID_PLACEHOLDER\"," +
                "Properties:{textures:[{Value:\"$HEAD_BASE64_PLACEHOLDER\"}]}" +
                "}" +
                "}"

        const val SET_BLOCK = "/setblock ~ ~1 ~ minecraft:skull 1 replace {" +
                "Owner:{" +
                "Id:\"$HEAD_UUID_PLACEHOLDER\"," +
                "Properties:{textures:[{Value:\"$HEAD_BASE64_PLACEHOLDER\"}]}" +
                "}," +
                "SkullType:3," +
                "Rot:0" +
                "}"

        const val SUMMON_ARMOR_STAND = "/summon minecraft:armor_stand ~ ~1 ~ {" +
                "ShowArms:1," +
                "NoBasePlate:1," +
                "ArmorItems:[" +
                "{id:leather_boots,Count:1b}," +
                "{id:leather_leggings,Count:1b}," +
                "{id:leather_chestplate,Count:1b}," +
                "{id:skull,Damage:3,Count:1b," +
                "tag:{" +
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