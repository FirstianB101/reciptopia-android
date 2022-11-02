package com.ich.reciptopia.data.repository

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.remote.dto.toPostList
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.RecipePost
import com.ich.reciptopia.domain.repository.CommunityRepository
import kotlinx.coroutines.tasks.await

class CommunityRepositoryImpl(
    private val api: ReciptopiaApi
): CommunityRepository {

    private val storage: FirebaseStorage by lazy{ Firebase.storage }

    override suspend fun getPostsByTime(searchQuery: String): List<Post> {
        return api.getPostsByTitleLike(searchQuery, "id,desc").toPostList()
    }

    override suspend fun getPostsByViews(searchQuery: String): List<Post> {
        return api.getPostsByTitleLike(searchQuery, "views,desc").toPostList()
    }

    override suspend fun createRecipePost(recipePost: RecipePost): RecipePost {
        api.createRecipePost(recipePost)
        return RecipePost()
    }

    override suspend fun uploadPostImage(uri: Uri): String{
        val fileName = "${System.currentTimeMillis()}.png"
        val uploadTask = storage.reference.child("post/thumbnail").child(fileName)
            .putFile(uri)
            .await()

        val downloadUrlTask = storage.reference.child("post/thumbnail").child(fileName)
            .downloadUrl
            .await()

        return downloadUrlTask.toString()
    }

    override suspend fun uploadStepImage(uri: Uri): String {
        val fileName = "${System.currentTimeMillis()}.png"
        val uploadTask = storage.reference.child("post/step/image").child(fileName)
            .putFile(uri)
            .await()

        val downloadUrlTask = storage.reference.child("post/step/image").child(fileName)
            .downloadUrl
            .await()

        return downloadUrlTask.toString()
    }
}