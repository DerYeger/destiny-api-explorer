package eu.yeger.destiny_api_explorer.ui.item_overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import eu.yeger.destiny_api_explorer.databinding.ItemOverviewFragmentBinding
import eu.yeger.destiny_api_explorer.ui.ItemGridAdapter
import eu.yeger.destiny_api_explorer.ui.OnClickListener

class ItemOverviewFragment : Fragment() {

    private lateinit var viewModel: ItemOverviewViewModel

    private lateinit var binding: ItemOverviewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemOverviewFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = this@ItemOverviewFragment
            itemGrid.adapter = ItemGridAdapter(OnClickListener {
                findNavController().navigate(
                    ItemOverviewFragmentDirections.actionItemOverviewFragment2ToItemDetailFragment(
                        it
                    )
                )
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ItemOverviewViewModel.Factory(application = activity!!.application)
        viewModel = ViewModelProvider(this, factory).get(ItemOverviewViewModel::class.java)
        binding.viewModel = viewModel
    }
}