package com.cosmogalaxy.b2btest.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cosmogalaxy.b2btest.R
import com.cosmogalaxy.b2btest.databinding.ActivityMainBinding
import com.cosmogalaxy.b2btest.interaction.CurrencySelectionInteraction
import com.cosmogalaxy.b2btest.ui.fragment.NbuFragment
import com.cosmogalaxy.b2btest.ui.fragment.PrivatFragment

class MainActivity : AppCompatActivity(), CurrencySelectionInteraction {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI(savedInstanceState)
    }

    private fun initUI(savedInstanceState: Bundle?) {
        initToolbar()
        initFragments(savedInstanceState)

    }

    private fun initToolbar() {
        with(binding.toolbar) {
            inflateMenu(R.menu.toolbar_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.graphic -> {
                    }
                }
                true
            }
        }
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container1, PrivatFragment.newInstance(), PrivatFragment.tag)
                .replace(R.id.fl_container2, NbuFragment.newInstance(), NbuFragment.tag)
                .commit()
        }
    }

    override fun onCurrencySelectChanged(currency: String) {
        val fragment =supportFragmentManager.findFragmentByTag(NbuFragment.tag)
        if (fragment!=null && fragment is CurrencySelectionInteraction)
            fragment.onCurrencySelectChanged(currency)
    }
}