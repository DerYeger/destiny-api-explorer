package eu.yeger.destiny_api_explorer.ui.xur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import eu.yeger.destiny_api_explorer.databinding.XurFragmentBinding
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = XurViewModel.Factory(application = activity!!.application)
        viewModel = ViewModelProvider(this, factory).get(XurViewModel::class.java)
        binding.viewModel = viewModel
    }
}
