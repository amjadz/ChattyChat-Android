package com.dbzfan200gmail.firebasemessenger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserModel(val uid: String, val password: String ,val email: String, val username: String, val profileImage: String): Parcelable{
    constructor() : this("", "","","","")
}
