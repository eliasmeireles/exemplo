package br.com.tech.feture.data

import br.com.tech.ui.adapter.ItemViewAdapter
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class User : ItemViewAdapter {

    var id: Int? = null
    var email: String? = null

    @JsonProperty(value = "first_name")
    var firstName: String? = null
    @JsonProperty(value = "last_name")
    var lastName: String? = null
    var avatar: String? = null
    var createdAt: Date? = null

    override fun getViewType(): Int {
        return 0
    }
}