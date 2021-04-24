package com.eselman.android.books.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eselman.android.books.database.entities.AccessInfoEntity
import com.eselman.android.books.database.entities.DownloadAccessEntity
import com.eselman.android.books.database.entities.EpubEntity
import com.eselman.android.books.database.entities.PDFEntity
import com.eselman.android.books.model.AccessInfo
import com.eselman.android.books.model.mappers.asDatabaseModel
import com.eselman.android.books.model.mappers.downloadAccessAsDatabaseModel
import com.eselman.android.books.model.mappers.epubAsDatabaseModel
import com.eselman.android.books.model.mappers.pdfAsDatabaseModel

@Dao
abstract class AccessInfoDao {
    fun insertFullAccessInfo(accessInfo: AccessInfo, bookId: String) {
        insertAccessInfo(accessInfo.asDatabaseModel(bookId))
        val accessInfoEntity = getAccessInfoByBookId(bookId)
        val epub = accessInfo.epubAsDatabaseModel(accessInfoEntity.accessInfoId)
        epub?.let {
            insertEpub(it)
        }

        val pdf = accessInfo.pdfAsDatabaseModel(accessInfoEntity.accessInfoId)
        pdf?.let {
            insertPDF(it)
        }

        val downloadAccess = accessInfo.downloadAccessAsDatabaseModel(accessInfoEntity.accessInfoId)
        downloadAccess?.let {
            insertDownloadAccess(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAccessInfo(accessInfo: AccessInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEpub(epub: EpubEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPDF(pdf: PDFEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDownloadAccess(downloadAccess: DownloadAccessEntity)

    @Query("SELECT * FROM access_info WHERE bookId = :bookId")
    abstract fun getAccessInfoByBookId(bookId: String): AccessInfoEntity

    @Query("SELECT * FROM epub WHERE accessInfoId = :accessInfoId")
    abstract fun getEpub(accessInfoId: Long): EpubEntity

    @Query("SELECT * FROM pdf WHERE accessInfoId = :accessInfoId")
    abstract fun getPDF(accessInfoId: Long): PDFEntity

    @Query("SELECT * FROM download_access WHERE accessInfoId = :accessInfoId")
    abstract fun getDownloadAccess(accessInfoId: Long): DownloadAccessEntity?

    @Query("DELETE FROM access_info")
    abstract fun deleteAccessInfo()

    @Query("DELETE FROM epub")
    abstract fun deleteEpub()

    @Query("DELETE FROM pdf")
    abstract fun deletePDF()

    @Query("DELETE FROM download_access")
    abstract fun deleteDownloadAccess()
}
