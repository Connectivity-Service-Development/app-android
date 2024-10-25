package cz.eman.skoda.csd.mobile.feature.service.presentation.model

data class ServiceDetailModel(
    val data: Data? = null,
) {

    data class Data(
        val name: String,
        val description: String,
        val bulletPoints: List<String>,
        val type: Type,
        val expiresIn: String?,
        val isSoonExpiration: Boolean,
    )
}
