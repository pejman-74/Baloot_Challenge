package com.baloot.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey
    val articleTitle: String,
    val prevKey: Int?,
    val nextKey: Int?
)