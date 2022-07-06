package com.ich.reciptopia.presentation.my_page.profile

import android.graphics.Bitmap
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.ProfileImageResponse
import com.ich.reciptopia.domain.repository.ProfileRepository
import io.mockk.mockk
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class ProfileFakeRepository: ProfileRepository {
    val accounts = mutableListOf<Account>()

    fun addAccount(account: Account){
        accounts.add(account)
    }

    override suspend fun patchAccount(accountId: Long, account: Account): Account {
        var findAccount = false
        for(i in accounts.indices){
            if(accounts[i].id == accountId){
                accounts[i] = account
                findAccount = true
                break
            }
        }
        return if(findAccount) account else
        throw HttpException(Response.error<ResponseBody>(404 ,ResponseBody.create("plain/text".toMediaTypeOrNull(),"account not found")))
    }

    override suspend fun uploadProfileImg(ownerId: Long, img: Bitmap): ProfileImageResponse {
        return ProfileImageResponse()
    }

    override suspend fun getAccountProfileImage(ownerId: Long): Bitmap? {
        return if(accounts.find { it.id == ownerId} != null)
            mockk()
        else null
    }
}