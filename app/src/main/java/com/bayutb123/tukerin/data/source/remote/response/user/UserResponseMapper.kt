import com.bayutb123.tukerin.data.source.remote.response.user.UserProfileResponse
import com.bayutb123.tukerin.domain.model.User

fun UserProfileResponse.toModel() : User {
    return User(
        id = this.user.id,
        name = this.user.name,
        email = this.user.email,
        token = this.user.apiToken,
        isPremium = this.user.isPremiumUser == "1",
        rating = this.user.rating,
        trxPoints = this.user.trxPoints
    )
}