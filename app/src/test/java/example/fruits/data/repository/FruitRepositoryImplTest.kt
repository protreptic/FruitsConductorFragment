package example.fruits.data.repository

import org.junit.Before
import org.mockito.MockitoAnnotations

class FruitRepositoryImplTest {

    private val fruitRepository by lazy {
        FruitsRepositoryImpl()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

}