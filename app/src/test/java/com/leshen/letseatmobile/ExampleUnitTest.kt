package com.leshen.letseatmobile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.leshen.letseatmobile.restaurantPanel.RestaurantPanelModel
import com.leshen.letseatmobile.restaurantPanel.Menu
import com.leshen.letseatmobile.restaurantPanel.Review
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.HttpException
import retrofit2.Response
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RestaurantPanelViewModelTest {

//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    val mockitoRule: MockitoRule = MockitoJUnit.rule()
//
//    @Mock
//    lateinit var apiService: ApiService
//
//    @Mock
//    lateinit var loggerMock: Logger
//
//    @InjectMocks
//    lateinit var viewModel: RestaurantPanelViewModel
//
//    @Before
//    fun setup() {
//    }
//
//    @Test
//    fun `fetchDataFromApi should update restaurantData on successful API call`() = runBlocking {
//
//        val restaurantId = 1
//        val mockRestaurantPanelModel = RestaurantPanelModel(
//            averageAtmosphere = 2.8,
//            averageFood = 3.0,
//            averageService = 3.4,
//            latitude = 54.3897102,
//            location = "Sebastiana Klonowicza 71A, 80-408 Gdansk",
//            longitude = 18.6311092,
//            menu = listOf(Menu(1, "Pizza", 10.2, "null", "4qhPErsAELXKnx5tVuI1zEHRQJv1"), Menu(2, "Pizza", 10.2, "null", "4qhPErsAELXKnx5tVuI1zEHRQJv1"), Menu(3, "Pizza", 10.2, "null", "4qhPErsAELXKnx5tVuI1zEHRQJv1"), Menu(4, "Pizza", 10.2, "null", "4qhPErsAELXKnx5tVuI1zEHRQJv1"), Menu(5, "Pizza", 10.2, "null", "4qhPErsAELXKnx5tVuI1zEHRQJv1")),
//            openingHours = "1AM - 6PM",
//            phoneNumber = "6025076729",
//            photoLink = "https://scontent-waw1-1.xx.fbcdn.net/v/t39.30808-6/304523503_446178180866644_452580988567986392_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=efb6e6&_nc_ohc=67IFiSa_ZO0AX-WVvIY&_nc_ht=scontent-waw1-1.xx&oh=00_AfAWZYCBlE2XARUmY2I_VX69eGW0jUIrzP7qia1zVYVPTw&oe=65B5960B",
//            restaurantId = 1,
//            restaurantName = "Kamm Restrant",
//            reviews = listOf(Review(3, "excellent", "null", 5, "null", 1, 4, "11234567899002"), Review(5, "excellent", "null", 4, "null", 5, 5, "11234567899002"), Review(1, "bad", "[2024.0, 1.0, 23.0]", 1, "null", 7, 1, "11a234v5678b99009"), Review(2, "bad", "[2024.0, 1.0, 23.0]", 1, "null", 8, 2, "11a234v5678b9900"), Review(3, "bad", "[2024.0, 1.0, 23.0]", 4, "null", 9, 5, "11a234v5678b9903")),
//            stars=3.1,
//            websiteLink = "https://mapquest.com"
//        )
//        `when`(apiService.getRestaurantPanelData(restaurantId)).thenReturn(
//            Response.success(mockRestaurantPanelModel)
//        )
//
//        viewModel.fetchDataFromApi(restaurantId)
//
//        assertEquals(mockRestaurantPanelModel, viewModel.restaurantData.value)
//        assertNull(viewModel.errorMessage.value)
//    }
//
//    @Test
//    fun `fetchDataFromApi should update errorMessage on HTTP 404 error`() = runBlocking {
//
//        val restaurantId = 1
//        `when`(apiService.getRestaurantPanelData(restaurantId)).thenThrow(
//            HttpException(
//                Response.error<RestaurantPanelModel>(
//                    404,
//                    "not found".toResponseBody("text/plain".toMediaTypeOrNull())
//                )
//            )
//        )
//
//
//        viewModel.fetchDataFromApi(restaurantId)
//
//
//        assertNull(viewModel.restaurantData.value)
//        assertEquals("Failed to fetch restaurant details", viewModel.errorMessage.value)
//    }
//
//    @Test
//    fun `fetchDataFromApi should update errorMessage on generic exception`() = runBlocking {
//
//        val restaurantId = 1
//        `when`(apiService.getRestaurantPanelData(restaurantId)).thenThrow(Exception("Some generic exception"))
//
//        viewModel.fetchDataFromApi(restaurantId)
//
//        assertNull(viewModel.restaurantData.value)
//        assertEquals("Failed to connect to the server", viewModel.errorMessage.value)
//    }
}
