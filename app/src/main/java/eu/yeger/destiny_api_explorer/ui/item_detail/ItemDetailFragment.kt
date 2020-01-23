package eu.yeger.destiny_api_explorer.ui.item_detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.yeger.destiny_api_explorer.R
import eu.yeger.destiny_api_explorer.databinding.ItemDetailFragmentBinding
import eu.yeger.destiny_api_explorer.truely

class ItemDetailFragment : Fragment() {

    private lateinit var viewModel: ItemDetailViewModel

    private lateinit var binding: ItemDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemDetailFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val itemDefinition = ItemDetailFragmentArgs.fromBundle(arguments!!).itemDefinition
        val factory = ItemDetailViewModel.Factory(
            itemDefinition = itemDefinition,
            application = activity!!.application
        )
        viewModel = ViewModelProvider(this, factory).get(ItemDetailViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.navigateBack.observe(viewLifecycleOwner, Observer {
            activity!!.supportFragmentManager.popBackStackImmediate()
            viewModel.navigatedBack()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.remove_menu -> viewModel.removeItemDefintion().truely()
            else -> false
        }
    }
}
