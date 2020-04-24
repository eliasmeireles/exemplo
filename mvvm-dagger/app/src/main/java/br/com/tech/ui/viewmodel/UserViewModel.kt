package br.com.tech.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tech.feture.data.Page
import br.com.tech.feture.data.User
import br.com.tech.feture.repository.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var page: Int = -1
    fun serviceState() = userRepository.serviceState

    fun getUser(): MutableLiveData<Page<User>> {
        page++
        val userLiveData = MutableLiveData<Page<User>>()
        userRepository.getUsers(userLiveData = userLiveData, page = page)
        return userLiveData
    }

    fun newUser(user: User): MutableLiveData<User> {
        val userLiveData: MutableLiveData<User> = MutableLiveData()
        userRepository.newUser(user = user, userLiveData = userLiveData)
        return userLiveData
    }


}