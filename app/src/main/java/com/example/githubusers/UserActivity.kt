package com.example.githubusers

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubusers.data.UserFullInfo
import com.example.githubusers.ui.UserAdapter
import com.example.githubusers.viewmodels.UserInfoViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user.avatar
import kotlinx.android.synthetic.main.activity_user.bio
import kotlinx.android.synthetic.main.activity_user.blog
import kotlinx.android.synthetic.main.activity_user.company
import kotlinx.android.synthetic.main.activity_user.email
import kotlinx.android.synthetic.main.activity_user.followers_text_view
import kotlinx.android.synthetic.main.activity_user.location
import kotlinx.android.synthetic.main.activity_user.name
import kotlinx.android.synthetic.main.activity_user.recycler_view
import kotlinx.android.synthetic.main.activity_user.scroll_view
import kotlinx.android.synthetic.main.custom_toolbar.toolbar
import kotlinx.android.synthetic.main.custom_toolbar.toolbar_text_view
import retrofit2.HttpException


class UserActivity : AppCompatActivity() {
    companion object {
        private const val KEY = "com.example.githubusers.userinfo"

        fun createIntent(context: Context, data: UserFullInfo): Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(KEY, data)
            return intent
        }
    }

    private lateinit var userViewModel: UserInfoViewModel
    private lateinit var data: UserFullInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        data = getDataFromIntent()!!
        initViews()
        createViewModel()
    }

    private fun initViews() {
        setData()
        initToolbar()
        initRecyclerView()
        initScrollView()
    }

    private fun setData() {
        if (data.avatar.isNotEmpty())
            Picasso.get().load(data.avatar).into(avatar)
        if (data.name.isNotEmpty())
            name.text = data.name
        if (data.company.isNotEmpty())
            company.text = data.company
        if (data.email.isNotEmpty())
            email.text = data.email
        if (data.blog.isNotEmpty())
            blog.text = data.blog
        if (data.location.isNotEmpty())
            location.text = data.location
        bio.text = data.bio
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_text_view.text = data.login
    }

    private fun initRecyclerView() {
        recycler_view.isNestedScrollingEnabled = false
        recycler_view.layoutManager = GridLayoutManager(this, Constants.SPAN_COUNT)
    }

    private fun createViewModel() {
        userViewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]

        userViewModel.getStateLiveData(data.login).observe(this) {
            when(it.action) {
                Action.INIT -> {
                    val userAdapter = UserAdapter(userViewModel.getUserList(), null, baseContext)
                    recycler_view.adapter = userAdapter
                }
                Action.POSITION_CHANGED -> {
                    recycler_view.adapter?.notifyItemChanged(it.changedPosition)
                }
            }
        }

        userViewModel.getExceptionLiveData().observe(this) {
            val ex = it as HttpException
            Toast.makeText(
                baseContext,
                "${ex.message} - probably, the maximum number of available requests has been reached",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun initScrollView() {
        scroll_view.viewTreeObserver.addOnScrollChangedListener {
            val location = IntArray(2)
            followers_text_view.getLocationOnScreen(location)

            val typedValue = TypedValue()
            var actionBarSize = 0
            if (theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                actionBarSize = TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
            }

            if (location[1] < actionBarSize) {
                toolbar_text_view.text = followers_text_view.text.toString()
            } else {
                toolbar_text_view.text = data.login
            }
        }
    }

    private fun getDataFromIntent(): UserFullInfo? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KEY, UserFullInfo::class.java)
        } else {
            intent.getParcelableExtra(KEY)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
