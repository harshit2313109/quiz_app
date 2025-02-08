package com.example.quizaki_

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.appdistribution.gradle.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class Home : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val rv1 = view.findViewById<RecyclerView>(R.id.homerecyclerView1)
        rv1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        populateRecyclerView(rv1)

        return view


    }
    fun getDummyData(): ArrayList<banner_rv_dc> {
        return ArrayList(
            listOf(
            banner_rv_dc("Python", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.geekedu.org%2Fblogs%2Fpython-for-kids-python-quiz-game&psig=AOvVaw3cP-kNmvhu0dGBxKzpddAT&ust=1739120238684000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCKjE3vPFtIsDFQAAAAAdAAAAABAE"),
            banner_rv_dc("C++", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8jC6qQ9fqGdWJdwEE5D_HqlRBseo1SywiaQ&s"),
            banner_rv_dc("Java", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.proprofs.com%2Fquiz-school%2Fstory.php%3Ftitle%3Djava-basics_3&psig=AOvVaw0m8YfyLCyF4R_BDEF4novK&ust=1739120441424000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCIDH9tLGtIsDFQAAAAAdAAAAABAE")
        )
        )
    }
    private fun populateRecyclerView(rv: RecyclerView) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(api_interface::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val items = apiService.getItems()
                withContext(Dispatchers.Main) {
                    if (items.isNotEmpty()) {
                        rv.adapter = banner_rv_adapter(items)
                    } else {
                        // Use dummy data if API returns no data
                        rv.adapter = banner_rv_adapter(getDummyData())
                    }
                }
            } catch (e: Exception) {
                // Use dummy data in case of error
                withContext(Dispatchers.Main) {
                    rv.adapter = banner_rv_adapter(getDummyData())
                }
            }
        }
    }
}
