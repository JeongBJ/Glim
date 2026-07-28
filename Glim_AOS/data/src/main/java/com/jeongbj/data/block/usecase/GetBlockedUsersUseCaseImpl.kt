package com.jeongbj.data.block.usecase

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.domain.block.repository.BlockRepository
import com.jeongbj.domain.block.usecase.GetBlockedUsersUseCase
import com.jeongbj.domain.user.model.User
import javax.inject.Inject

class GetBlockedUsersUseCaseImpl @Inject constructor(
    private val blockRepository: BlockRepository
): GetBlockedUsersUseCase {
    override suspend fun invoke(cursor: Cursor): CursorPage<User, Long> =
        blockRepository.getBlockedUsers(cursor)
}