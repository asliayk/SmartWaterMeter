package com.example.smartwatermeter.models

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BillModel(
    var aboneNo_0: Int?,
    var cost_0: Double?,
    var multiplier_0: Double?,
    var flow_0: Double?,
    var date_0: String?
): Parcelable