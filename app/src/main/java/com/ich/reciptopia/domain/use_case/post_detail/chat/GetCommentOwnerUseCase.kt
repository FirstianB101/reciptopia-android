package com.ich.reciptopia.domain.use_case.post_detail.chat

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.repository.PostDetailChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCommentOwnerUseCase @Inject constructor(
    private val repository: PostDetailChatRepository
) {
    operator fun invoke(accountId: Long): Flow<Resource<Account>> = flow{
        try{
            emit(Resource.Loading<Account>())

            val account = repository.getOwner(accountId)
            emit(Resource.Success<Account>(account))
        }catch (e: HttpException){
            emit(Resource.Error<Account>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Account>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}