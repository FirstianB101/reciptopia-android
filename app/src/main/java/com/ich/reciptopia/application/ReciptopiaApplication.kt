package com.ich.reciptopia.application

import android.app.Application
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.User
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReciptopiaApplication: Application(){
    private var user: User? = null

    init {
        instance = this
    }

    fun login(user: User){
        this.user = user
    }

    fun logout(){
        user = null
    }

    fun editAccount(account: Account){
        if(user?.account != null){
            user = user?.copy(
                account = account
            )
        }
    }

    fun getCurrentUser() = user

    companion object{
        var instance: ReciptopiaApplication? = null
    }
}