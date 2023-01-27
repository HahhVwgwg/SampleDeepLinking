package project.sample.deeplinking.data.model

import com.google.gson.annotations.SerializedName


data class Response (
  @SerializedName("type"    ) var type    : String?  = null,
  @SerializedName("payload" ) var payload : Payload? = Payload()
)