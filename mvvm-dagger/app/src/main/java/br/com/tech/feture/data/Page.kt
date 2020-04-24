package br.com.tech.feture.data

import com.fasterxml.jackson.annotation.JsonProperty

class Page<T> {
    val data: List<T>? = null
    val page: Int = 0
    val total: Int = 0

    @JsonProperty(value = "total_pages")
    val totalPages: Int = 0
}