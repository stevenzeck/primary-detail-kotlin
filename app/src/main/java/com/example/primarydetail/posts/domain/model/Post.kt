package com.example.primarydetail.posts.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.primarydetail.posts.domain.model.Post.Companion.TABLE_NAME
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * The post data class
 * Database structure also defined here
 */
@Parcelize
@Entity(tableName = TABLE_NAME)
data class Post(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    @field:Json(name = "id")
    val id: Long,
    @ColumnInfo(name = COLUMN_USER_ID)
    @field:Json(name = "userId")
    val userId: Int,
    @ColumnInfo(name = COLUMN_TITLE)
    @field:Json(name = "title")
    val title: String,
    @ColumnInfo(name = COLUMN_BODY)
    @field:Json(name = "body")
    val body: String,
    @ColumnInfo(name = COLUMN_READ)
    var read: Boolean = false
) : Parcelable {
    companion object {

        const val TABLE_NAME = "post"

        const val COLUMN_ID = "id"
        const val COLUMN_USER_ID = "userId"
        const val COLUMN_TITLE = "title"
        const val COLUMN_BODY = "body"
        const val COLUMN_READ = "read"
    }
}
