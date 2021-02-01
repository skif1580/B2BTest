package com.cosmogalaxy.b2btest.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cosmogalaxy.b2btest.adapters.PrivatBankAdapter
import com.cosmogalaxy.b2btest.data.PrivatCurrency
import com.cosmogalaxy.b2btest.databinding.FragmentPrivatBinding
import com.cosmogalaxy.b2btest.interaction.CurrencySelectionInteraction
import com.cosmogalaxy.b2btest.viewmodels.CurrencyViewModel
import com.cosmogalaxy.b2btest.viewmodels.State
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class PrivatFragment : Fragment() {
    companion object {
        val tag: String = "PrivatFragment"
        fun newInstance(): PrivatFragment = PrivatFragment()
    }

    private val viewModel: CurrencyViewModel by inject()
    private var _binding: FragmentPrivatBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = PrivatBankAdapter()
    private val date = Calendar.getInstance()
    private val onDateChangeListener =
        DatePickerDialog.OnDateSetListener { p0, year, monthOfYear, dayOfMonth ->
            date.set(Calendar.YEAR, year)
            date.set(Calendar.MONTH, monthOfYear)
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadListCurrencyBankPrivat()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CurrencySelectionInteraction)
            mAdapter.setCurrencySelectionListener(context)
    }

    override fun onDetach() {
        super.onDetach()
        mAdapter.setCurrencySelectionListener(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        initUi(view)
        viewModel.privateState.observe(viewLifecycleOwner, this::setState)
    }

    private fun initUi(view: View) {
        binding.recycler.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        }
        binding.tvData.paintFlags = binding.tvData.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.imageView.setOnClickListener {
            showDateDialog(view, onDateChangeListener)
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

    private fun updateDateInView() {
        val formatDate = SimpleDateFormat("dd.MM.yyyy")
        val date = formatDate.format(date.time)
        binding.tvData.text = date
        viewModel.getArchiveCurrencyPrivate(date)
    }


    private fun setState(state: State) = when (state) {
        is State.Default -> {
            showDefaultState()
        }
        is State.LoadData -> {
            showLoadState()
        }
        is State.SuccessPrivat -> {
            showSuccessState(state.data)
        }
        is State.Error -> {
            showErrorState(state.message)
        }
        else -> throw Exception("wrong state.success")
    }

    private fun showDefaultState() {
        binding.progressBar.visibility = GONE
        binding.recycler.visibility = GONE
    }

    private fun showLoadState() {
        binding.recycler.isVisible = true
    }

    private fun showSuccessState(data: List<PrivatCurrency>) {
        binding.progressBar.visibility = GONE
        binding.recycler.visibility = VISIBLE
        mAdapter.showData(data)
        showCurrentDate()
    }

    @SuppressLint("SimpleDateFormat")
    private fun showCurrentDate() {
        val formatDate = SimpleDateFormat("dd.MM.yyyy")
        val date = formatDate.format(date.time)
        binding.tvData.text = date
    }

    private fun showErrorState(error: String) {
        binding.recycler.isVisible = false
        binding.progressBar.isVisible = false
        Toast.makeText(this.context, error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}