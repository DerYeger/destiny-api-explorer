package eu.yeger.destiny_api_explorer.ui.xur

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import eu.yeger.destiny_api_explorer.R
import eu.yeger.destiny_api_explorer.databinding.XurFragmentBinding
import eu.yeger.destiny_api_explorer.truely
import eu.yeger.destiny_api_explorer.ui.ItemGridAdapter
import eu.yeger.destiny_api_explorer.ui.OnClickListener

class XurFragment : Fragment() {

    private lateinit var viewModel: XurViewModel

    private lateinit var binding: XurFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = XurFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@XurFragment
            itemGrid.adapter = ItemGridAdapter(OnClickListener {
                findNavController().navigate(
                    XurFragmentDirections.actionXurFragmentToItemDetailFragment(
                        it
                    )
                )
            })
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = XurViewModel.Factory(application = activity!!.application)
        viewModel = ViewModelProvider(this, factory).get(XurViewModel::class.java)
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
