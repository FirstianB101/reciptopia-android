package com.ich.reciptopia.domain.use_case.my_page.sign_up

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val repository: SignUpRepository
) {
    operator fun invoke(account: Account): Flow<Resource<Account>> = flow {
        try {
            emit(Resource.Loading<Account>())

            val newAccount = repository.createAccount(account)
            emit(Resource.Success<Account>(newAccount))
        } catch (e: HttpException) {
            emit(Resource.Error<Account>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        } catch (e: IOException) {
            emit(Resource.Error<Account>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}