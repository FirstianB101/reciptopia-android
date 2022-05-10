package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account

interface ProfileRepository {
    suspend fun patchAccount(accountId: Long, account: Account): Account
}