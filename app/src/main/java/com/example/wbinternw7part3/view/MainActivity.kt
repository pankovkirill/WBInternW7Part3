package com.example.wbinternw7part3.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.wbinternw7part3.R
import com.example.wbinternw7part3.databinding.ActivityMainBinding
import com.example.wbinternw7part3.model.AppState
import com.example.wbinternw7part3.model.remote.dto.ImageResponse
import com.example.wbinternw7part3.model.remote.dto.MessageResponse
import com.example.wbinternw7part3.model.remote.dto.VoteRequest
import com.example.wbinternw7part3.view.favorite.FavoriteFragment
import com.example.wbinternw7part3.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var post: ImageResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        buttonClick()
    }

    private fun buttonClick() {
        binding.cardViewLike.setOnClickListener {
            viewModel.postVote(createBody(LIKE))
            viewModel.saveFavoriteCatToDB(post)
            viewModel.getData()
        }

        binding.cardViewDislike.setOnClickListener {
            viewModel.postVote(createBody(DISLIKE))
            viewModel.getData()
        }
    }

    private fun createBody(params: Int): VoteRequest {
        return VoteRequest(
            post.id,
            SUB_ID,
            params
        )
    }

    private fun initViewModel() {
        viewModel = MainViewModel()
        viewModel.liveDataPost.observe(this) { renderData(it) }
        viewModel.liveDataVote.observe(this) { showToast(it) }
        viewModel.getData()
    }

    private fun showToast(messageRequest: MessageResponse) {
        Log.d("POST", messageRequest.message)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessMain -> {
                post = appState.imageResponse[FIRST]
                val uri = Uri.parse(post.url)
                binding.image.setImageURI(uri)
            }
            is AppState.Loading -> {
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(this, appState.e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, FavoriteFragment())
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val FIRST = 0
        private const val SUB_ID = "from-phone"
        private const val LIKE = 1
        private const val DISLIKE = 0
    }
}