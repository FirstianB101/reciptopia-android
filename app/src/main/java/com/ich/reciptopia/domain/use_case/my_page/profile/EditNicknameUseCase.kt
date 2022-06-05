package com.ich.reciptopia.domain.use_case.my_page.profile

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EditNicknameUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(account: Account): Flow<Resource<Account>> = flow{
        try{
            emit(Resource.Loading<Account>())

            val edited = repository.patchAccount(account.id!!, account)
            emit(Resource.Success<Account>(edited))
        }catch (e: HttpException){
            emit(Resource.Error<Account>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Account>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}