package project.sample.deeplinking.data.model

import com.google.gson.annotations.SerializedName


data class Payload(

    @SerializedName("code") var code: Int? = null,
    @SerializedName("message") var message: String? = null

)