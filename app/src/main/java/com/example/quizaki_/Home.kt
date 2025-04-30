package com.example.quizaki_

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val rv2 = view.findViewById<RecyclerView>(R.id.homerecyclerView2)
        val rv3 = view.findViewById<RecyclerView>(R.id.homerecycler3)
        val placeholder = view.findViewById<View>(R.id.emptyPlaceholder)

        rv1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv2.layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        rv3.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        rv1.adapter = banner_rv1_adapter(emptyList(), object : banner_rv1_adapter.Itemclicklistener {
            override fun onclickingitem(position: Int){
                Toast.makeText(context, "Item $position clicked now emptyone", Toast.LENGTH_SHORT).show()
            }
        })
        rv2.adapter = banner_rv2_adapter(emptyList(),object : banner_rv2_adapter.ItemclickListener2 {
            override fun onclickingitem2(position: Int){
                Toast.makeText(context, "Item $position clicked now emptyone", Toast.LENGTH_SHORT).show()

            }
        })

        rv3.adapter = banner_rv3_adapter(emptyList())

        populateRecyclerView1(rv1)
        populateRecyclerView2(rv2)
        populateRecyclerView3(rv3, placeholder)
        return view


    }



    // this is the first recycler view

    fun getDummyData1(): ArrayList<banner_rv1_dc> {
        return ArrayList(
            listOf(
            banner_rv1_dc("Python", "https://images.unsplash.com/photo-1649180556628-9ba704115795?q=80&w=2924&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            banner_rv1_dc("C++", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8jC6qQ9fqGdWJdwEE5D_HqlRBseo1SywiaQ&s"),
            banner_rv1_dc("Java", "https://media.istockphoto.com/id/1335247101/photo/computer-with-elements-of-program-code-on-the-screen-and-the-inscription-java-and-a-keyboard.jpg?s=1024x1024&w=is&k=20&c=0IP_iwBohfc2PoC628I2PZynVUSkJImd3_U_UYN5VWs=")
        )
        )
    }

    private fun populateRecyclerView1(rv: RecyclerView) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(home_rv_interface::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val items = apiService.getItems()
                withContext(Dispatchers.Main) {
                    if (items.isNotEmpty()) {
                        rv.adapter = banner_rv1_adapter(items, object : banner_rv1_adapter.Itemclicklistener {
                            override fun onclickingitem(position: Int) {
                                Toast.makeText(context, "Item $position clicked api ", Toast.LENGTH_SHORT).show()

                                val intent = Intent (context,Quiztemplate::class.java)
                                intent.putExtra("category", items[position].textView)
                                startActivity(intent)

                            }
                        })
                    } else {
                        // Use dummy data if API returns no data
                        rv.adapter = banner_rv1_adapter(getDummyData1(),object : banner_rv1_adapter.Itemclicklistener {
                            override fun onclickingitem(position: Int){
                                val intent = Intent (context,Quiztemplate::class.java)
                                intent.putExtra("category", items[position].textView)
                                startActivity(intent)
                            }
                        })
                    }
                }
            } catch (e: Exception) {
               val dummydata1 = getDummyData1()
                // Use dummy data in case of error
                withContext(Dispatchers.Main) {
                    rv.adapter = banner_rv1_adapter(getDummyData1(),object : banner_rv1_adapter.Itemclicklistener {
                        override fun onclickingitem(position: Int){
                            val intent = Intent (context,Quiztemplate::class.java)
                            intent.putExtra("category",dummydata1[position].textView )
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }


    // this is the second recycler view
    fun getDummyData2(): ArrayList<banner_rv2_dc> {
        return ArrayList(
            listOf(
                banner_rv2_dc("Ruby", "https://media.istockphoto.com/id/182662385/photo/loose-ruby-stone.jpg?s=1024x1024&w=is&k=20&c=vdXrtAzBcl3bhAgvLCKA1clLqKig8E5Nl8fZw1OE0l4="),
                banner_rv2_dc("GoLang", "https://plus.unsplash.com/premium_photo-1669075651762-59f2247ac733?q=80&w=2680&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                banner_rv2_dc("JavaScript", "https://images.unsplash.com/photo-1667372393086-9d4001d51cf1?q=80&w=2832&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
            )
        )
    }

    private fun populateRecyclerView2(rv: RecyclerView) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(home_rv_interface::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val items2 = apiService.getItems2()
                withContext(Dispatchers.Main) {
                    if (items2.isNotEmpty()) {
                        rv.adapter = banner_rv2_adapter(items2,object : banner_rv2_adapter.ItemclickListener2 {
                           override  fun onclickingitem2(position: Int) {
                                Toast.makeText(context, "Item $position clicked api ", Toast.LENGTH_SHORT).show()

                                val intent = Intent (context,Quiztemplate::class.java)
                                intent.putExtra("category", items2[position].name)
                                startActivity(intent)

                            }
                        })
                    } else {
                        // Use dummy data if API returns no data
                        rv.adapter = banner_rv2_adapter(getDummyData2(),object : banner_rv2_adapter.ItemclickListener2 {
                            override fun onclickingitem2(position: Int){
                                val intent = Intent (context,Quiztemplate::class.java)
                                intent.putExtra("category", items2[position].name)
                                startActivity(intent)
                            }
                        })
                    }
                }
            } catch (e: Exception) {
                // Use dummy data in case of error
                withContext(Dispatchers.Main) {
                    rv.adapter = banner_rv2_adapter(getDummyData2(),object : banner_rv2_adapter.ItemclickListener2 {
                        override fun onclickingitem2(position: Int){
                            val intent = Intent (context,Quiztemplate::class.java)
                            intent.putExtra("category",getDummyData2()[position].name )
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }

// the third recycler view.

    fun populateRecyclerView3(rv: RecyclerView, placeholder: View) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(home_rv_interface::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val items3 = apiService.getItems3()
                withContext(Dispatchers.Main) {
                    if (items3.isNotEmpty()) {
                        placeholder.visibility = View.GONE
                        rv.adapter = banner_rv3_adapter(items3)
                    } else {
                        placeholder.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    placeholder.visibility = View.VISIBLE
                }
            }
        }
    }
}
