package com.example.core.di.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoreInfo(val subject:String, val chapter: String) : Parcelable