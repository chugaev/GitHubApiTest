package com.example.githubusers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubusers.data.UserInfo
import com.example.githubusers.ui.RecyclerViewClickListener
import com.example.githubusers.ui.UserAdapter
import com.example.githubusers.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.android.synthetic.main.custom_toolbar.toolbar
import kotlinx.android.synthetic.main.custom_toolbar.toolbar_text_view
import retrofit2.HttpException


class MainActivity : AppCompatActivity(), RecyclerViewClickListener {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecyclerView()
        createViewModel()

        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_text_view.text = resources.getString(R.string.users)
    }

    private fun createRecyclerView() {
        recycler_view.layoutManager = GridLayoutManager(this, Constants.SPAN_COUNT)
    }

    private fun createViewModel() {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.getUserListLiveData().observe(this) {
            val userAdapter = UserAdapter(it, this, baseContext)
            recycler_view.adapter = userAdapter
        }
        userViewModel.getExceptionLiveData().observe(this) {
            val ex = (it as HttpException)
            Toast.makeText(
                baseContext,
                "${ex.message} - probably, the maximum number of available requests has been reached",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onItemClick(userInfo: UserInfo) {
        val intent = UserActivity.createIntent(baseContext, userInfo)
        startActivity(intent)
    }
}