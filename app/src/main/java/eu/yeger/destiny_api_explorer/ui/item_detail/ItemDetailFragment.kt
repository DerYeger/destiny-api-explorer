package eu.yeger.destiny_api_explorer.ui.item_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.yeger.destiny_api_explorer.databinding.ItemDetailFragmentBinding

class ItemDetailFragment : Fragment() {

    private lateinit var binding: ItemDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemDetailFragmentBinding.inflate(inflater)
        binding.itemDefinition = ItemDetailFragmentArgs.fromBundle(arguments!!).itemDefinition
        return binding.root
    }
}
