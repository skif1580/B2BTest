package com.cosmogalaxy.b2btest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cosmogalaxy.b2btest.data.PrivatCurrency
import com.cosmogalaxy.b2btest.databinding.ItemPrivateBinding
import com.cosmogalaxy.b2btest.interaction.CurrencySelectionInteraction

class PrivatBankAdapter : RecyclerView.Adapter<PrivatBankAdapter.ViewHolder>() {

    class ViewHolder(val itemPrivat: ItemPrivateBinding) :
        RecyclerView.ViewHolder(itemPrivat.root) {
    }

    private var listCurrency = listOf<PrivatCurrency>()
    private var clickListener: CurrencySelectionInteraction? = null

    fun setCurrencySelectionListener(po: CurrencySelectionInteraction?) {
        clickListener = po
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPrivateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = listCurrency[position]
        with(holder) {
            with(itemPrivat) {
                tvPrivatCurrencyName.text = itemList.ccy ?: "No Empty"
                tvPrivatCurrencyBuy.text = itemList.buy
                tvPrivatCurrencySell.text = itemList.sale
                itemPrivat.root.setOnClickListener {
                    itemList.ccy?.let { currency -> clickListener?.onCurrencySelectChanged(currency) }
                }
            }
        }
    }

    override fun getItemCount(): Int = listCurrency.size

    fun showData(list: List<PrivatCurrency>) {
        listCurrency = list.take(5)
        notifyDataSetChanged()
    }
}