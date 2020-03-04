package com.mars.models

import com.mars.network.APIResponse

class LoginResponse: APIResponse() {

    lateinit var Login: ArrayList<UserInfo>
}