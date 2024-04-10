package com.example.contactspdm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    var id: Int = -1,
    var name: String = "",
    var address: String ="",
    var phone: String ="",
    var email: String ="",
): Parcelable
