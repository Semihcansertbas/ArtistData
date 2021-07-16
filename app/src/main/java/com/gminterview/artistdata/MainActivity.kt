package com.gminterview.artistdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gminterview.artistdata.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    //view binding
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var api: RemoteApi //remote api Instance
    private lateinit var rvAdapter: TracksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //initialized view binding
        setContentView(binding.root)

        //initialize api
        api = RemoteApi.invoke()

        setupRecyclerView()

        //initialize button click function
        binding.apply {
            button.setOnClickListener {
                progressBar.visibility = View.VISIBLE //make progress bar visible
                //in case of previous error we need to hide the error text view
                binding.textViewFetchError.visibility = View.GONE
                fetchData()
            }
        }
    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        rvAdapter = TracksAdapter()
        adapter = rvAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun fetchData() {
        //get the entered text
        val artistName = binding.tilSearchText.editText?.text.toString()

        if (artistName.isNotEmpty()) {
            //get data in background thread
            lifecycleScope.launch { 
                val result = api.getTracks(artistName)
                
                binding.progressBar.visibility = View.GONE //hide the progress bar

                //if network call is succeed then display in list
                if(result.isSuccessful && result.body() != null){
                    val data = result.body()!!
                    Log.d(TAG, "fetchData: ${data.resultCount}")
                    rvAdapter.tracks = data.results //received data
                }else{
                    //in case of any kind of failure this block will handle it
                    rvAdapter.tracks = emptyList()
                    Log.d(TAG, "fetchData: ${result.message()}")
                    withContext(Dispatchers.Main){
                        binding.textViewFetchError.visibility = View.VISIBLE
                    }
                }
            }
        } else {
            binding.progressBar.visibility = View.GONE //hide the progress bar
            Toast.makeText(this, "Please Enter Name for search", Toast.LENGTH_SHORT)
                .show() //show a toast message to alert user
        }
    }
}