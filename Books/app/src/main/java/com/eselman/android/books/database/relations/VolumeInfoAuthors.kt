package com.eselman.android.books.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.eselman.android.books.database.entities.AuthorEntity
import com.eselman.android.books.database.entities.VolumeInfoEntity

data class VolumeInfoAuthors(
    @Embedded val volumeInfoEntity: VolumeInfoEntity,
    @Relation(
        parentColumn = "volumeInfoId",
        entityColumn = "volumeInfoId"
    )
    val authors: List<AuthorEntity>
)

fun List<VolumeInfoAuthors>.asDomainModel(): MutableList<String?> {
    val authorsList = mutableListOf<String?>()

    forEach {
        it.authors.map { authorEntity ->
            authorsList.add(authorEntity.author)
        }
    }

    return authorsList
}
