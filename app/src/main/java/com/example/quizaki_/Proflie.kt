package com.example.quizaki_

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class Proflie : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proflie, container, false)
    }
    //if in case no view to deliver.

    //TextView emptyView = findViewById(R.id.empty_view);
    //if (yourDataset.isEmpty()) {
    //    emptyView.setVisibility(View.VISIBLE);
    //    recyclerView.setVisibility(View.GONE);
    //    recentlyPlayedLayout.setVisibility(View.GONE);
    //} else {
    //    emptyView.setVisibility(View.GONE);
    //    recyclerView.setVisibility(View.VISIBLE);
    //    recentlyPlayedLayout.setVisibility(View.VISIBLE);
    //}
}