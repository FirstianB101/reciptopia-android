package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.SearchDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
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

    private var nextPostId = 4L
    private var nextTagId = 4L
    private var nextFavoriteId = 0L

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return RepositoryTestUtils.testOwner.find{it.id == accountId}!!
    }

    override suspend fun postLike(postLikeTag: PostLikeTag): PostLikeTag {
        RepositoryTestUtils.testPostLikeTags.add(
            postLikeTag.copy(
                id = nextTagId++
            )
        )
        return postLikeTag
    }

    override suspend fun getPostLikeTags(): List<PostLikeTag> {
        return RepositoryTestUtils.testPostLikeTags
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
            id = RepositoryTestUtils.nextFavoriteId++,
            ownerId = ownerId,
            postId = postId
        )
        RepositoryTestUtils.testFavorites.add(favorite)
    }

    override suspend fun unFavoritePostLogin(ownerId: Long?, postId: Long?) {
        for(i in RepositoryTestUtils.testFavorites.indices){
            if(RepositoryTestUtils.testFavorites[i].ownerId == ownerId && RepositoryTestUtils.testFavorites[i].postId == postId){
                RepositoryTestUtils.testFavorites.removeAt(i)
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
        return RepositoryTestUtils.testFavorites.filter{ it.ownerId == userId }
    }
}