package com.cosmogalaxy.b2btest.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cosmogalaxy.b2btest.R
import com.cosmogalaxy.b2btest.data.NbuCurrency
import com.cosmogalaxy.b2btest.databinding.ItemNbuBinding
import com.cosmogalaxy.b2btest.extends.formatNum
import com.cosmogalaxy.b2btest.interaction.CurrencySelectionInteraction

class NBUAdapter : RecyclerView.Adapter<NBUAdapter.ViewHolder>(), CurrencySelectionInteraction {

    class ViewHolder(val item: ItemNbuBinding) : RecyclerView.ViewHolder(item.root)

    private var data: List<NbuCurrency> = listOf()
    private var selectedIndex: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNbuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = data[position]
        with(holder) {
            with(item) {
                root.setBackgroundColor(
                    if (position % 2 == 0)
                        root.resources.getColor(R.color.teal_700_10)
                    else
                        Color.TRANSPARENT)
                placeHolder.setBackgroundColor(
                    if (position == selectedIndex)
                        root.resources.getColor(R.color.teal_700_20)
                    else
                        Color.TRANSPARENT
                )
                tvNbyCurrencyName.text = itemList.txt
                tvNbyCurrencyPrice.text = "%s UAH ".format(itemList.rate.formatNum(3))
                tvNbyCurrencyAlone.text = "1 %s".format(itemList.cc)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun swapData(list: List<NbuCurrency>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCurrencySelectChanged(currency: String) {
        selectedIndex = data.indexOfFirst { it.cc == currency }
        notifyDataSetChanged()
    }

    fun getCurrentSelectedPosition(): Int =
        if (selectedIndex == -1) 0 else selectedIndex
}