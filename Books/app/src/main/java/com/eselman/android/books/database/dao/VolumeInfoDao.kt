package com.eselman.android.books.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.eselman.android.books.database.entities.AuthorEntity
import com.eselman.android.books.database.entities.CategoryEntity
import com.eselman.android.books.database.entities.VolumeInfoEntity
import com.eselman.android.books.database.entities.DimensionsEntity
import com.eselman.android.books.database.entities.ImageLinksEntity
import com.eselman.android.books.database.entities.IndustryIdentifierEntity
import com.eselman.android.books.database.relations.VolumeInfoAuthors
import com.eselman.android.books.database.relations.VolumeInfoCategories
import com.eselman.android.books.database.relations.VolumeInfoIndustryIdentifiers
import com.eselman.android.books.model.VolumeInfo
import com.eselman.android.books.model.mappers.asDatabaseModel
import com.eselman.android.books.model.mappers.authorsAsDatabaseModel
import com.eselman.android.books.model.mappers.categoriesAsDatabaseModel
import com.eselman.android.books.model.mappers.dimensionsAsDatabaseModel
import com.eselman.android.books.model.mappers.imageLinksAsDatabaseModel
import com.eselman.android.books.model.mappers.industryIdentifiersAsDatabaseModel

@Dao
abstract class VolumeInfoDao {
    fun insertFullVolumeInfo(volumeInfo: VolumeInfo, bookId: String) {
        insertVolumeInfo(volumeInfo.asDatabaseModel(bookId))
        val volumeInfoEntity = getVolumeInfoByBookId(bookId)

          volumeInfo.authorsAsDatabaseModel(volumeInfoEntity.volumeInfoId)?.forEach { author ->
              insertAuthor(author)
          }

        volumeInfo.categoriesAsDatabaseModel(volumeInfoEntity.volumeInfoId)?.forEach { category ->
            insertCategory(category)
        }

        val dimensions = volumeInfo.dimensionsAsDatabaseModel(volumeInfoEntity.volumeInfoId)
        dimensions?.let {
            insertDimensions(it)
        }

        val imageLinks = volumeInfo.imageLinksAsDatabaseModel(volumeInfoEntity.volumeInfoId)
        imageLinks?.let {
            insertImageLinks(it)
        }

        volumeInfo.industryIdentifiersAsDatabaseModel(volumeInfoEntity.volumeInfoId)?.forEach { industryIdentifier ->
            insertIndustryIdentifiers(industryIdentifier)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertVolumeInfo(volumeInfo: VolumeInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAuthor(author: AuthorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDimensions(dimensions: DimensionsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImageLinks(imageLinks: ImageLinksEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertIndustryIdentifiers(industryIdentifier: IndustryIdentifierEntity)

    @Query("SELECT * FROM volume_info WHERE bookId = :bookId")
    abstract fun getVolumeInfoByBookId(bookId: String): VolumeInfoEntity

    @Transaction
    @Query("SELECT * FROM volume_info WHERE volumeInfoId = :volumeInfoId")
    abstract fun getVolumeInfoAuthors(volumeInfoId: Long): List<VolumeInfoAuthors>

    @Transaction
    @Query("SELECT * FROM volume_info WHERE volumeInfoId = :volumeInfoId")
    abstract fun getVolumeInfoCategories(volumeInfoId: Long): List<VolumeInfoCategories>

    @Query("SELECT * FROM dimensions WHERE volumeInfoId = :volumeInfoId")
    abstract fun getDimensions(volumeInfoId: Long): DimensionsEntity?

    @Query("SELECT * FROM image_links WHERE volumeInfoId = :volumeInfoId")
    abstract fun getImageLinks(volumeInfoId: Long): ImageLinksEntity?

    @Transaction
    @Query("SELECT * FROM volume_info WHERE volumeInfoId = :volumeInfoId")
    abstract fun getVolumeInfoIndustryIdentifiers(volumeInfoId: Long): List<VolumeInfoIndustryIdentifiers>

    @Query("DELETE FROM volume_info")
    abstract fun deleteVolumeInfo()

    @Query("DELETE FROM author")
    abstract fun deleteAuthors()

    @Query("DELETE FROM category")
    abstract fun deleteCategories()

    @Query("DELETE FROM dimensions")
    abstract fun deleteDimensions()

    @Query("DELETE FROM image_links")
    abstract fun deleteImageLinks()

    @Query("DELETE FROM industry_identifier")
    abstract fun deleteIndustryIdentifiers()
}
