package com.nerdery.rtaza.jobtracker.data.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Worker(
    @PrimaryKey var id: Long,
    var firstName: String,
    var lastName: String,
    var online: Boolean
)