package com.nerdery.rtaza.jobtracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Worker(
    @PrimaryKey var id: Long,
    var firstName: String,
    var lastName: String,
    var online: Boolean
)