package com.ich.reciptopia.domain.use_case.my_page.sign_up

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EmailExistsUseCase @Inject constructor(
    private val repository: SignUpRepository
) {
    operator fun invoke(email: String): Flow<Resource<Boolean>> = flow{
        try{
            emit(Resource.Loading<Boolean>())

            val exist = repository.accountExists(email)
            emit(Resource.Success<Boolean>(exist.exist))
        }catch (e: HttpException){
            emit(Resource.Error<Boolean>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Boolean>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}