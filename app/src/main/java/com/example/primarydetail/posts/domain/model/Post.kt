package com.example.primarydetail.posts.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.primarydetail.posts.domain.model.Post.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The post data class
 * Database structure also defined here
 */
@Parcelize
@Serializable
@Entity(tableName = TABLE_NAME)
data class Post(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    @SerialName("id")
    val id: Long,
    @ColumnInfo(name = COLUMN_USER_ID)
    @SerialName("userId")
    val userId: Int,
    @ColumnInfo(name = COLUMN_TITLE)
    @SerialName("title")
    val title: String,
    @ColumnInfo(name = COLUMN_BODY)
    @SerialName("body")
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
