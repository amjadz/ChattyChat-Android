package com.dbzfan200gmail.firebasemessenger

class UserModel(val uid: String, val password: String ,val username: String, val email: String, val profileImage: String){
    constructor() : this("", "","","","")
}
