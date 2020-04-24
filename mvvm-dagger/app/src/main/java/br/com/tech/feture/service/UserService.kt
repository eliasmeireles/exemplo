package br.com.tech.feture.service

import br.com.tech.feture.data.Page
import br.com.tech.feture.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface UserService {

    @GET(value = "users")
    fun listUser(
        @Query(value = "page") page: Int
    ): Call<Page<User>>

    @POST(value = "users")
    fun newUser(
        @Body user: User
    ): Call<User>
}