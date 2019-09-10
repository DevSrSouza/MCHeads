package br.com.devsrsouza.mcheads.js.commands

import br.com.devsrsouza.mcheads.common.Head

class Minecraft112(head: Head) : MinecraftCommands(head) {
    override fun give(): String {
        return "/give @p skull 1 3 " +
                "{display:{Name:\"${head.name}\"}," +
                "SkullOwner:{Id:\"${head.uuid}\"," +
                "Properties:{textures:[{Value:\"${textureBase64()}\"}]}}}"
    }

    override fun setBlock(): String {
        return "/setblock ~ ~1 ~ minecraft:skull 1 replace " +
                "{Owner:{Id:\"${head.uuid}\"," +
                "Properties:{textures:[{Value:\"${textureBase64()}\"}]}},SkullType:3,Rot:0}"
    }

    override fun summonArmorStand(): String {
        return "/summon minecraft:armor_stand ~ ~1 ~ " +
                "{ShowArms:1,NoBasePlate:1,ArmorItems:[" +
                "{id:leather_boots,Count:1b}," +
                "{id:leather_leggings,Count:1b}," +
                "{id:leather_chestplate,Count:1b}," +
                "{id:skull,Damage:3,Count:1b,tag:{" +
                "SkullOwner:{Id:\"${head.uuid}\"," +
                "Properties:{textures:[{Value:\"${textureBase64()}\"}]}}" +
                "}}]}"
    }

}