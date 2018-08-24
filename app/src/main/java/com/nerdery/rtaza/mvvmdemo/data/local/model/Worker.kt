package com.nerdery.rtaza.mvvmdemo.data.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Worker(
    @PrimaryKey var id: String,
    var firstName: String,
    var lastName: String,
    var online: Boolean
)