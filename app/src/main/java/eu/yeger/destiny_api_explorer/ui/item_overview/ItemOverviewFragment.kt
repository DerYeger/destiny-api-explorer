package eu.yeger.destiny_api_explorer.ui.item_overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import eu.yeger.destiny_api_explorer.R
import eu.yeger.destiny_api_explorer.databinding.ItemOverviewFragmentBinding
import eu.yeger.destiny_api_explorer.truely
import eu.yeger.destiny_api_explorer.ui.ItemGridAdapter
import eu.yeger.destiny_api_explorer.ui.OnClickListener

class ItemOverviewFragment : Fragment() {

    private lateinit var viewModel: ItemOverviewViewModel

    private lateinit var binding: ItemOverviewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemOverviewFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@ItemOverviewFragment
            itemGrid.adapter = ItemGridAdapter(OnClickListener {
                findNavController().navigate(
                    ItemOverviewFragmentDirections.actionItemOverviewFragment2ToItemDetailFragment(
                        it
                    )
                )
            })
        }
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ItemOverviewViewModel.Factory(application = activity!!.application)
        viewModel = ViewModelProviders.of(this, factory).get(ItemOverviewViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh_data_menu -> viewModel.refresh().truely()
            R.id.clear_data_menu -> viewModel.clear().truely()
            else -> false
        }
    }
}