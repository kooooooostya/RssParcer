package com.example.rssparcer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssparcer.databinding.FragmentMainBinding
import com.example.rssparcer.tools.RssItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var fragmentMainBinding: FragmentMainBinding
    private lateinit var controller: MainController

    private lateinit var recyclerAdapter: RVAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        controller = MainController()

        val data = ArrayList<RssItem>()
        recyclerAdapter = RVAdapter(data)

        GlobalScope.launch(Dispatchers.IO){
            data.addAll(controller.getArrayList())
            launch(Dispatchers.Main) { recyclerAdapter.notifyDataSetChanged() }
        }

        return LayoutInflater.from(context).inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentMainBinding = FragmentMainBinding.bind(view)
        fragmentMainBinding.recyclerView.adapter = recyclerAdapter
        fragmentMainBinding.recyclerView.layoutManager = LinearLayoutManager(context)

    }
}