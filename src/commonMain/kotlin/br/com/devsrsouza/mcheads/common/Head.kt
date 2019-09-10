package br.com.devsrsouza.mcheads.common

import kotlinx.serialization.Serializable

@Serializable
data class Head(
    val id: Int,
    val name: String,
    val uuid: String,
    val mojangId: String,
    val category: HeadCategory
)