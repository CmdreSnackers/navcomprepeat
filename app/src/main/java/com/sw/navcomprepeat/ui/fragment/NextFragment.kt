package com.sw.navcomprepeat.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.sw.navcomprepeat.R
import com.sw.navcomprepeat.databinding.FragmentHomeBinding
import com.sw.navcomprepeat.databinding.FragmentNextBinding


class NextFragment : Fragment() {
    private val args: NextFragmentArgs by navArgs()
    private lateinit var binding: FragmentNextBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNextBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {

            tv2.text = args.msg

            btnBack.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("msg", "Greetings from Next Frag")
                setFragmentResult("from_next_fragment", bundle)
                NavHostFragment.findNavController(this@NextFragment).popBackStack()
            }
        }
    }

}