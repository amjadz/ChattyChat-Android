package com.dbzfan200gmail.firebasemessenger

class UserModel(val uid: String, val password: String ,val email: String, val username: String, val profileImage: String){
    constructor() : this("", "","","","")
}
