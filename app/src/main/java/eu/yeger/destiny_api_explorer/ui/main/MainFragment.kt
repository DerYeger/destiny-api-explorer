package eu.yeger.destiny_api_explorer.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.yeger.destiny_api_explorer.R
import eu.yeger.destiny_api_explorer.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@MainFragment
            itemGrid.adapter = ItemGridAdapter()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = MainViewModel.Factory(application = activity!!.application)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        binding.viewModel = viewModel
    }
}
