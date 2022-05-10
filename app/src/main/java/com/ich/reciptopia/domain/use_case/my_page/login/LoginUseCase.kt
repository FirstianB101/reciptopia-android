package com.ich.reciptopia.domain.use_case.my_page.login

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Auth
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    operator fun invoke(auth: Auth): Flow<Resource<User>> = flow{
        try{
            emit(Resource.Loading<User>())

            val loginUser = repository.loginUser(auth)
            emit(Resource.Success<User>(loginUser))
        }catch (e: HttpException){
            emit(Resource.Error<User>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<User>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}