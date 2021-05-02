package com.example.rssparcer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rssparcer.databinding.FragmentWebBinding


class WebFragment : Fragment() {

    private lateinit var binding: FragmentWebBinding
    private var link = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_web, container, false)

        binding = FragmentWebBinding.bind(root)

        link = arguments?.getString("link") ?: ""

        binding.webView.loadUrl(link)

        return root
    }
}