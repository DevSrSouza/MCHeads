package br.com.devsrsouza.mcheads.server.database.exposed

import br.com.devsrsouza.mcheads.common.Head
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import javax.sql.rowset.serial.SerialBlob

data class HeadImage(
    val id: Int,
    val image: ByteArray,
    val head: Head
)

object HeadImageTable : IntIdTable() {
    val image = blob("image")
    val head_id = reference("head", HeadTable).uniqueIndex()
}

class HeadImageDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HeadImageDAO>(HeadImageTable) {

        fun findByHead(head: Head): HeadImage? {
            return findByHeadId(head.id)
        }

        fun findByHeadId(id: Int): HeadImage? {
            return find { HeadImageTable.head_id eq id }
                .firstOrNull()
                ?.asHeadImage()
        }

        // null if head doesn't exist
        fun newHead(image: ByteArray, head: Head): HeadImage? {
            return HeadDAO.findById(head.id)?.let {
                newHead(image, it)
            }
        }

        fun newHead(image: ByteArray, head: HeadDAO): HeadImage {
            return new {
                this.image = SerialBlob(image)
                this.head = head
            }.asHeadImage()
        }
    }

    private var image by HeadImageTable.image
    private var head by HeadDAO referencedOn HeadImageTable.head_id

    fun asHeadImage() = HeadImage(
        id.value,
        image.binaryStream.readBytes(),
        head.asHead()
    )
}