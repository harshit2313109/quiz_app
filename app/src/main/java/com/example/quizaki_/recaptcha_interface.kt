
import com.example.quizaki_.registerPlayerRequest
import com.example.quizaki_.registerPlayerResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface recaptcha_interface {
    @POST("player/registerPlayer")

    suspend fun registerPlayer(
        @Body request: registerPlayerRequest
    ): registerPlayerResponse

}