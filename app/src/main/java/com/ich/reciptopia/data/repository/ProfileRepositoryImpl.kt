package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.remote.dto.toAccount
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val api: ReciptopiaApi
): ProfileRepository {
    override suspend fun patchAccount(accountId: Long, account: Account): Account {
        return api.patchAccount(accountId, account).toAccount()
    }
}