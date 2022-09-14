package com.ich.reciptopia.presentation.main.search

import androidx.compose.runtime.mutableStateOf
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ich.reciptopia.MainDispatcherRule
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.domain.repository.PostRepository
import com.ich.reciptopia.domain.repository.SearchRepository
import com.ich.reciptopia.domain.use_case.post.*
import com.ich.reciptopia.domain.use_case.search.*
import com.ich.reciptopia.presentation.main.search.util.ChipState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var useCases: SearchUseCases
    lateinit var viewModel: SearchViewModel
    lateinit var searchRepository: SearchRepository
    lateinit var postRepository: PostRepository
    lateinit var application: ReciptopiaApplication

    @Before
    fun setUp() {
        application = ReciptopiaApplication()
        searchRepository = SearchFakeRepository()
        postRepository = PostFakeRepository()
        useCases = SearchUseCases(
            deleteFavorite = DeleteFavoriteUseCase(postRepository),
            getSearchHistories = GetSearchHistoriesUseCase(searchRepository),
            addSearchHistory = AddSearchHistoryUseCase(searchRepository),
            deleteSearchHistory = DeleteSearchHistoryUseCase(searchRepository),
            getPost = GetPostUseCase(searchRepository),
            getOwner = GetOwnerByIdUseCase(postRepository),
            getSearchedPosts = GetSearchedPostsUseCase(searchRepository),
            getFavorites = GetFavoritesUseCase(postRepository),
            favoritePost = FavoritePostUseCase(postRepository),
            unFavoritePost = UnFavoritePostUseCase(postRepository),
            likePost = PostLikeUseCase(postRepository),
            unlikePost = PostUnLikeUseCase(postRepository),
            getPostLikeTags = GetPostLikeTagsUseCase(postRepository),
            getOwnerProfileImage = GetOwnerProfileImageUseCase(postRepository)
        )
        viewModel = SearchViewModel(useCases, application)
    }

    @Test
    fun `재료 추가하지 않고 검색할 경우 토스트 출력 테스트`() = runTest{
        viewModel.eventFlow.test {
            viewModel.onEvent(SearchScreenEvent.DoSearch(listOf()))

            val toast = awaitItem()
            assertThat(toast).isInstanceOf(SearchViewModel.UiEvent.ShowToast::class.java)
        }
    }

    @Test
    fun `재료 추가 후 검색 테스트`() = runTest {
        val testChips = listOf(
            ChipState("test1", mutableStateOf(false)),
            ChipState("test2", mutableStateOf(true)),
            ChipState("test3", mutableStateOf(true))
        )
        viewModel.state.test {
            viewModel.onEvent(SearchScreenEvent.DoSearch(testChips))

            val initial = awaitItem()

            val afterGetPostsByChips = awaitItem()
            assertThat(afterGetPostsByChips.posts.size).isEqualTo(1)
            assertThat(afterGetPostsByChips.posts[0].title).isEqualTo("testtitle1")
            assertThat(afterGetPostsByChips.posts[0].content).isEqualTo("testcontent1")

            val afterGetFavorites = awaitItem()
            assertThat(afterGetFavorites.favorites).isEmpty()

            val afterGetLikeTags = awaitItem()
            assertThat(afterGetLikeTags.likeTags).isEmpty()

            for(i in afterGetPostsByChips.posts.indices){

            }

            val afterGetHistories = awaitItem()
            assertThat(afterGetHistories.searchHistories.size).isEqualTo(1)

            val histories = afterGetHistories.searchHistories
            assertThat(histories.size).isEqualTo(1)
            assertThat(histories[0].id).isEqualTo(1)
            assertThat(histories[0].ingredientNames.size).isEqualTo(3)

            for(i in 0..2){
                assertThat(histories[0].ingredientNames[i]).isEqualTo("test${i + 1}")
                if(i == 0) assertThat(histories[0].isSubIngredient[i]).isFalse()
                else assertThat(histories[0].isSubIngredient[i]).isTrue()
            }
        }

        viewModel.eventFlow.test {
            val afterSearch = awaitItem()
            assertThat(afterSearch).isInstanceOf(SearchViewModel.UiEvent.NavigateToSearchResultScreen::class.java)
        }
    }
}