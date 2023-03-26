package com.example.innobuzzassignment.data.Room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Post(
    @PrimaryKey
    var id :Int = 0,
    @ColumnInfo(name = "user_id")
    var userId: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "body")
    var body: String,
): Parcelable
