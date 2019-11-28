package com.exemplo.mediaplayer

import android.os.Parcel
import android.os.Parcelable

class DataSource(
    val sources: String?,
    val thumb: String?,
    val title: String?
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(sources)
        writeString(thumb)
        writeString(title)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DataSource> = object : Parcelable.Creator<DataSource> {
            override fun createFromParcel(source: Parcel): DataSource = DataSource(source)
            override fun newArray(size: Int): Array<DataSource?> = arrayOfNulls(size)
        }
    }
}
