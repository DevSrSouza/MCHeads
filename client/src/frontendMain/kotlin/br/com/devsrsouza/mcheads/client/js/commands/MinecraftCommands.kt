package br.com.devsrsouza.mcheads.client.js.commands

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.MOJANG_TEXTURE_API
import kotlin.browser.window

abstract class MinecraftCommands(val head: Head) {
    companion object {
        const val HEAD_NAME_PLACEHOLDER = "{{head_name}}"
        const val HEAD_UUID_PLACEHOLDER = "{{head_uuid}}"
        const val HEAD_BASE64_PLACEHOLDER = "{{head_base64}}"
    }
    abstract fun give(): String
    abstract fun setBlock(): String
    abstract fun summonArmorStand(): String

    protected fun textureBase64(): String {
        val headJson = "{\"textures\":{\"SKIN\":{\"url\":\"$MOJANG_TEXTURE_API${head.mojangId}\"}}}"
        return window.atob(headJson)
    }
}