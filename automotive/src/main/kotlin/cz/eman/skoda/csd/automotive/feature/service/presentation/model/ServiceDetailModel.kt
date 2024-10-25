package cz.eman.skoda.csd.automotive.feature.service.presentation.model

data class ServiceDetailModel(
    val data: Data? = null,
) {

    data class Data(
        val name: String,
        val description: String,
        val bulletPoints: List<String>,
        val status: Status,
        val expiresIn: String?,
        val isSoonExpiration: Boolean,
    )
}
