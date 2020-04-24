package br.com.tech.feture.repository

import androidx.lifecycle.MutableLiveData
import br.com.tech.feture.data.Page
import br.com.tech.feture.data.User
import br.com.tech.feture.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService
) {

    val serviceState = MutableLiveData<Boolean>()

    fun getUsers(page: Int, userLiveData: MutableLiveData<Page<User>>) {
        serviceState.postValue(true)
        userService.listUser(page = page).enqueue(object : Callback<Page<User>> {
            override fun onFailure(call: Call<Page<User>>, t: Throwable) {
                serviceState.postValue(false)
            }

            override fun onResponse(call: Call<Page<User>>, response: Response<Page<User>>) {
                if (response.isSuccessful) {
                    userLiveData.postValue(response.body())
                }
                serviceState.postValue(false)
            }
        })
    }

    fun newUser(user: User, userLiveData: MutableLiveData<User>) {
        serviceState.postValue(true)
        userService.newUser(user = user).enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
                serviceState.postValue(false)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                serviceState.postValue(false)
                if (response.isSuccessful) {
                    userLiveData.postValue(response.body())
                }
            }
        })
    }
}