package com.zapmap.pokemon.model

import android.os.Parcel
import android.os.Parcelable

data class PokemonItem(val name: String, val url: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonItem> {
        override fun createFromParcel(parcel: Parcel): PokemonItem {
            return PokemonItem(parcel)
        }

        override fun newArray(size: Int): Array<PokemonItem?> {
            return arrayOfNulls(size)
        }
    }

    fun getId(): Int = url.removeSuffix("/").substringAfterLast("/").toInt()
}
