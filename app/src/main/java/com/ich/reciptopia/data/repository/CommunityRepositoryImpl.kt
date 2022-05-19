package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.SearchDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.repository.RepositoryTestUtils.nextFavoriteId
import com.ich.reciptopia.data.repository.RepositoryTestUtils.nextLikeTagId
import com.ich.reciptopia.data.repository.RepositoryTestUtils.nextPostId
import com.ich.reciptopia.data.repository.RepositoryTestUtils.testFavorites
import com.ich.reciptopia.data.repository.RepositoryTestUtils.testPostLikeTags
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CommunityRepositoryImpl(
    private val api: ReciptopiaApi,
    private val dao: SearchDao
): CommunityRepository {

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return RepositoryTestUtils.testOwner.find{it.id == accountId}!!
    }

    override suspend fun getPostsByTime(): List<Post> {
        return RepositoryTestUtils.testPosts
    }

    override suspend fun getPostsByViews(): List<Post> {
        return RepositoryTestUtils.testPosts.reversed()
    }

    override suspend fun createPost(post: Post): Post {
        RepositoryTestUtils.testPosts.add(
            post.copy(
                id = nextPostId++
            )
        )
        return post
    }

    override suspend fun favoritePostLogin(ownerId: Long?, postId: Long?) {
        val favorite = Favorite(
            id = nextFavoriteId++,
            ownerId = ownerId,
            postId = postId
        )
        testFavorites.add(favorite)
    }

    override suspend fun unFavoritePostLogin(ownerId: Long?, postId: Long?) {
        for(i in testFavorites.indices){
            if(testFavorites[i].ownerId == ownerId && testFavorites[i].postId == postId){
                testFavorites.removeAt(i)
                break
            }
        }
    }

    override suspend fun favoritePostNotLogin(ownerId: Long?, postId: Long?) {
        dao.insertFavorite(Favorite(
            id = nextFavoriteId++,
            postId = postId,
            ownerId = ownerId
        ))
    }

    override suspend fun unFavoritePostNotLogin(postId: Long) {
        val favorites = dao.getFavorites().first()
        val favorite = favorites.find{ it.postId == postId}
        if (favorite != null) {
            dao.deleteFavorite(favorite)
        }
    }

    override suspend fun getFavoritesFromDB(): Flow<List<Favorite>> {
        return dao.getFavorites()
    }

    override suspend fun getFavorites(userId: Long): List<Favorite> {
        return testFavorites.filter{ it.ownerId == userId }
    }

    override suspend fun likePost(ownerId: Long?, postId: Long?) {
        testPostLikeTags.add(PostLikeTag(
            id = nextPostId++,
            ownerId = ownerId,
            postId = postId
        ))
    }

    override suspend fun unLikePost(ownerId: Long?, postId: Long?) {
        for(i in testPostLikeTags.indices){
            if(testPostLikeTags[i].ownerId == ownerId && testPostLikeTags[i].postId == postId){
                testPostLikeTags.removeAt(i)
                break
            }
        }
    }


    override suspend fun getLikeTags(userId: Long): List<PostLikeTag> {
        return testPostLikeTags.filter{ it.ownerId == userId }
    }
}