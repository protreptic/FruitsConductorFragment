package example.fruits.presentation.fruit

import example.fruits.data.model.Fruit
import example.fruits.data.remote.fruit.FruitsApi
import example.fruits.data.repository.FruitsRepository
import example.fruits.presentation.fruit.FruitsPresenter.FruitsView
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class FruitsPresenterImplTest {

    private val view: FruitsView = mock(FruitsView::class.java)
    private val repository: FruitsRepository = mock(FruitsRepository::class.java)
    private val fruitsApi: FruitsApi = mock(FruitsApi::class.java)

    private val fruitsPresenter by lazy {
        FruitsPresenterImpl(view, repository, fruitsApi)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun checkIfAttachThenAttachView() {
        fruitsPresenter.attach()

        Mockito.inOrder(view).apply {
            verify(view, times(1)).attach()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun checkIfRepositoryIsNotEmptyCallViewDisplay() {
        val fruits = listOf(Fruit(), Fruit(), Fruit())

        Mockito.`when`(repository.fetch())
                .thenReturn(fruits)
        Mockito.`when`(fruitsApi.fruits())
                .thenReturn(Single.just(listOf()))

        fruitsPresenter.displayFruits()

        Mockito.inOrder(view, repository, fruitsApi).apply {
            verify(repository, times(1)).fetch()
            verifyNoMoreInteractions(repository)
            verify(view, times(1)).displayFruits(ArgumentMatchers.anyList())
            verifyNoMoreInteractions(view)
            verify(fruitsApi, times(1)).fruits()
            verifyNoMoreInteractions(fruitsApi)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun checkIfFetchByNameWorksAsExpected() {
        val fruits = listOf(Fruit(), Fruit(), Fruit())

        Mockito.`when`(repository.fetchByName(ArgumentMatchers.anyString()))
                .thenReturn(fruits)

        fruitsPresenter.filterFruits("елад")

        Mockito.inOrder(view, repository).apply {
            verify(repository, times(1)).fetchByName(ArgumentMatchers.anyString())
            verify(view, times(1)).displayFruits(ArgumentMatchers.anyList())
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun checkIfFetchByNameWorksAsExpected2() {
        val fruits = listOf<Fruit>()

        Mockito.`when`(repository.fetchByName(ArgumentMatchers.anyString()))
                .thenReturn(fruits)

        fruitsPresenter.filterFruits("елад")

        Mockito.inOrder(view, repository).apply {
            verify(repository, times(1)).fetchByName(ArgumentMatchers.anyString())
            verify(view, times(1)).displayEmpty()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun checkIfRepositoryIsEmptyCallViewEmpty() {
        val fruits = listOf<Fruit>()

        Mockito.`when`(repository.fetch())
                .thenReturn(fruits)
        Mockito.`when`(fruitsApi.fruits())
                .thenReturn(Single.create {  })

        fruitsPresenter.displayFruits()

        Mockito.inOrder(view, repository, fruitsApi).apply {
            verify(repository, times(1)).fetch()
            verify(view, times(1)).displayLoading()
            verify(fruitsApi, times(1)).fruits()
            verifyNoMoreInteractions(fruitsApi)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun checkIfDetachThenDetachView() {
        fruitsPresenter.detach()

        Mockito.inOrder(view).apply {
            verify(view, times(1)).detach()
            verifyNoMoreInteractions()
        }
    }

}