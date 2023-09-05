package com.sw.navcomprepeat.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sw.navcomprepeat.core.constants.Constants
import com.sw.navcomprepeat.core.utils.isImage
import com.sw.navcomprepeat.databinding.FragmentHomeBinding
import com.sw.navcomprepeat.ui.adapter.ProductAdapter
import java.io.File

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var adapter: ProductAdapter
    private lateinit var navController: NavController

    val listOfImages: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        setupAdapter()

        val path = if(args.path != "null") {
            args.path!!
        } else {
            Environment.getExternalStorageDirectory().path
        }

        val root = File(path)

        root.listFiles()?.let {
            adapter.setItem(it.toList())
        }

        scan(File(Environment.getExternalStorageDirectory().path))

        listOfImages.forEach {
            Log.d("iamges",it)
        }



//        binding.run {
//            btnNext.setOnClickListener {
//                val navController = NavHostFragment.findNavController(this@HomeFragment)
//                val action = HomeFragmentDirections.actionHomeToNext("Greetings")
//                navController.navigate(action)
//            }
//        }

//        setFragmentResultListener("from_next_fragment") { _, result ->
//            val msg = result.getString("msg") ?: "Default String"
//            binding.tv1.text = msg
//        }



    }

    fun scan(file: File) {
        if(Regex(Constants.imageReg).containsMatchIn(file.name)) {
            listOfImages.add(file.name)
            return
        }
        if(file.isDirectory) {
            file.listFiles()?.toList()?.let {files ->
                files.forEach{scan(it)}

            }
        }
    }

    private fun setupAdapter() {
        adapter = ProductAdapter(emptyList()) { file ->
            if(file.isDirectory) {
//                val navController = NavHostFragment.findNavController(this)
                val action = HomeFragmentDirections.actionHomeSelf(file.path)
                navController.navigate(action)
            } else if(file.isImage()) {
                val action = HomeFragmentDirections.actionToImageViewer(file.path)
                navController.navigate(action)
            } else {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.fromFile(file))
                    startActivity(intent)
                } catch (e:Exception) {
                    val snackbar = Snackbar.make(
                        binding.root,
                        "File Format is not supported",
                        Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
        }

        binding.rvItem.adapter=adapter
        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
    }



}