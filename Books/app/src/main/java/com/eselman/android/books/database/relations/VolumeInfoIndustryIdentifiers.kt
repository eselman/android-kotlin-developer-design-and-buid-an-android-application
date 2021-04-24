package com.eselman.android.books.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.eselman.android.books.database.entities.IndustryIdentifierEntity
import com.eselman.android.books.database.entities.VolumeInfoEntity
import com.eselman.android.books.model.IndustryIdentifier

data class VolumeInfoIndustryIdentifiers(
    @Embedded val volumeInfoEntity: VolumeInfoEntity,
    @Relation(
        parentColumn = "volumeInfoId",
        entityColumn = "volumeInfoId"
    )
    val industryIdentifiers: List<IndustryIdentifierEntity>
)

fun List<VolumeInfoIndustryIdentifiers>.asDomainModel(): MutableList<IndustryIdentifier> {
    val identifiersList = mutableListOf<IndustryIdentifier>()

    forEach {
        it.industryIdentifiers.map { identifierEntity ->
            identifiersList.add(
                IndustryIdentifier(
                    type = identifierEntity.type,
                    identifier = identifierEntity.identifier
                )
            )
        }
    }

    return identifiersList
}
