package br.com.tech.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tech.R
import br.com.tech.application.BaseApplication
import br.com.tech.application.componet.MainActivityComponent
import br.com.tech.feture.data.Page
import br.com.tech.feture.data.User
import br.com.tech.ui.adapter.BaseAdapter
import br.com.tech.ui.adapter.UserAdapter
import br.com.tech.ui.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BaseAdapter.Delegate {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var mainActivityComponent: MainActivityComponent
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel

    private var hasNextPage = false

    override fun onCreate(savedInstanceState: Bundle?) {

        mainActivityComponent = (applicationContext as BaseApplication)
            .appComponent.mainActivityComponent().create()

        mainActivityComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userAdapter = UserAdapter(delegate = this)
        val recyclerView: RecyclerView = recycler_view_users
        recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        }

        userViewModel = viewModelProvider.create(UserViewModel::class.java)
        userViewModel.serviceState().observe(this, Observer(::loading))

        getUsers()

        new_user.apply {
            setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        CreateUserActivity::class.java
                    )
                )
            }
        }
    }

    private fun getUsers() {
        userViewModel.getUser().observe(this, Observer(::loadUser))
    }


    private fun loadUser(page: Page<User>?) {
        page?.let {
            hasNextPage = it.page < it.totalPages

            it.data?.let { users ->
                userAdapter.addItems(items = users)
            }
        }
    }

    private fun loading(isLoading: Boolean) {
        val loading: ProgressBar = loading
        loading.visibility = when (isLoading) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    override fun nextPage() {
        getUsers()
    }

    override fun hasNextPage(): Boolean {
        return hasNextPage
    }
}
