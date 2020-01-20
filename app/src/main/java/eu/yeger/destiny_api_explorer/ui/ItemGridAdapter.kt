package eu.yeger.destiny_api_explorer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import eu.yeger.destiny_api_explorer.databinding.ItemDefinitionViewBinding
import eu.yeger.destiny_api_explorer.domain.ItemDefinition

class ItemGridAdapter(private val onClickListener: OnClickListener<ItemDefinition>) :
    ListAdapter<ItemDefinition, ItemGridAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(
        private val binding: ItemDefinitionViewBinding,
        private val onClickListener: OnClickListener<ItemDefinition>
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemDefinition: ItemDefinition) {
            binding.itemDefinition = itemDefinition
            binding.itemIconImage.setOnClickListener {
                onClickListener.onClick(itemDefinition)
            }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ItemDefinition>() {
        override fun areItemsTheSame(oldItem: ItemDefinition, newItem: ItemDefinition): Boolean {
            return oldItem.hash == newItem.hash
        }

        override fun areContentsTheSame(oldItem: ItemDefinition, newItem: ItemDefinition): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemDefinitionViewBinding.inflate(LayoutInflater.from(parent.context)),
            onClickListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemDefinition = getItem(position)
        holder.bind(itemDefinition)
    }
}
