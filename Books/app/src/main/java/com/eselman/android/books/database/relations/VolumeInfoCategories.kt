package com.eselman.android.books.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.eselman.android.books.database.entities.CategoryEntity
import com.eselman.android.books.database.entities.VolumeInfoEntity

data class VolumeInfoCategories(
    @Embedded val volumeInfoEntity: VolumeInfoEntity,
    @Relation(
        parentColumn = "volumeInfoId",
        entityColumn = "volumeInfoId"
    )
    val categories: List<CategoryEntity>
)

fun List<VolumeInfoCategories>.asDomainModel(): MutableList<String?> {
    val categoriesList = mutableListOf<String?>()

    forEach {
        it.categories.map { categoryEntity ->
            categoriesList.add(categoryEntity.category)
        }
    }

    return categoriesList
}
