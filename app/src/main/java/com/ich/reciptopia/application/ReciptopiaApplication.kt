package com.ich.reciptopia.application

import android.app.Application
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.User
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltAndroidApp
class ReciptopiaApplication: Application(){

    private var _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        instance = this
    }

    fun login(user: User){
        _user.value = user
    }

    fun logout(){
        _user.value = null
    }

    fun editAccount(account: Account){
        if(_user.value?.account != null){
            _user.value = _user.value?.copy(
                account = account
            )
        }
    }

    companion object{
        var instance: ReciptopiaApplication? = null
    }
}