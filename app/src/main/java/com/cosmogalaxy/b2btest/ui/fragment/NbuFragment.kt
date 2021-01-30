package com.cosmogalaxy.b2btest.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cosmogalaxy.b2btest.adapters.NBUAdapter
import com.cosmogalaxy.b2btest.data.NbuCurrency
import com.cosmogalaxy.b2btest.databinding.FragmentNbyBinding
import com.cosmogalaxy.b2btest.interaction.CurrencySelectionInteraction
import com.cosmogalaxy.b2btest.viewmodels.CurrencyViewModel
import com.cosmogalaxy.b2btest.viewmodels.State
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class NbuFragment : Fragment(), CurrencySelectionInteraction {
    companion object {
        val tag: String = "NbuFragment"

        fun newInstance(): NbuFragment =
            NbuFragment()
    }
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by inject()
    private var _binding: FragmentNbyBinding? = null
    private val mAdapter = NBUAdapter()
    private var date = Calendar.getInstance()
    private val dpdListener = DatePickerDialog.OnDateSetListener { p0, year, monthOfYear, dayOfMonth ->
        date.set(Calendar.YEAR, year)
        date.set(Calendar.MONTH, monthOfYear)
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateCurrentDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadListCurrencyBankNBU()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        initUi(view)
        viewModel.nbuState.observe(viewLifecycleOwner, this::setState)
    }

    private fun initUi(view: View) {
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        }

        binding.tvDataNbu.paintFlags = binding.tvDataNbu.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.imageView.setOnClickListener {
            showDateDialog(view, dpdListener)
        }
    }

    private fun showDateDialog(
        view: View,
        dpdListener: DatePickerDialog.OnDateSetListener
    ) {
        DatePickerDialog(
            view.context,
            dpdListener,
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setState(state: State) = when (state) {
        is State.Default -> showDefaultState()
        is State.LoadData -> showLoadState()
        is State.SuccessNBU -> showSuccessState(state.data)
        is State.Error -> showErrorState(state.message)
        else -> throw Exception("wrong state.success")
    }

    private fun showDefaultState() {
        binding.prNby.isVisible = false
        binding.recyclerView.isVisible = false
    }

    private fun showLoadState() {
        binding.prNby.isVisible = true
        binding.recyclerView.isVisible = false
    }

    private fun showSuccessState(data: List<NbuCurrency>) {
        binding.prNby.visibility = GONE
        binding.recyclerView.isVisible = true
        mAdapter.swapData(data)
        showCurrentDate()
    }

    private fun showCurrentDate() {
        val formatDate = SimpleDateFormat("dd.MM.yyyy")
        val date = formatDate.format(date.time)
        binding.tvDataNbu.text = date
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateCurrentDate() {
        val formatDate = SimpleDateFormat("dd.MM.yyyy")
        val date = formatDate.format(date.time)
        binding.tvDataNbu.text = date
        viewModel.loadArchiveNBUCurrency(date)
    }

    private fun showErrorState(error: String) {
        binding.recyclerView.isVisible = false
        binding.prNby.visibility = GONE
        Toast.makeText(this.context, error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCurrencySelectChanged(currency: String) {
        mAdapter.onCurrencySelectChanged(currency)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.recyclerView.smoothScrollToPosition(mAdapter.getCurrentSelectedPosition())
        }, 50)
    }
}