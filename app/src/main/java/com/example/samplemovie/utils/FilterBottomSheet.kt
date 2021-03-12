package com.example.samplemovie.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.samplemovie.R
import com.example.samplemovie.databinding.FilterListBinding
import com.example.samplemovie.ui.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FilterListBinding
    private lateinit var bottomSheetMovieType: BottomSheetMovieType
    companion object {

        const val TAG = "FilterBottomSheet"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewBottomDialog = inflater.inflate(R.layout.filter_list, container, false)
        bottomSheetMovieType = (activity as MainActivity)
        binding = FilterListBinding.bind(viewBottomDialog)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.firstButton.setOnClickListener {
            val type = binding.firstButton.text.toString()
            Toast.makeText(context, type, Toast.LENGTH_SHORT).show()
            bottomSheetMovieType.onTypeClicked(type)
            dismiss()
        }
        binding.secondButton.setOnClickListener {
            val type = binding.secondButton.text.toString()
            Toast.makeText(context, type, Toast.LENGTH_SHORT).show()
            bottomSheetMovieType.onTypeClicked(type)
            dismiss()
        }
        binding.thirdButton.setOnClickListener {
            val type = binding.thirdButton.text.toString()
            Toast.makeText(context, type, Toast.LENGTH_SHORT).show()
            bottomSheetMovieType.onTypeClicked(type)
            dismiss()
        }
        binding.fourthButton.setOnClickListener {
            val type = binding.fourthButton.text.toString()
            Toast.makeText(context, type, Toast.LENGTH_SHORT).show()
            bottomSheetMovieType.onTypeClicked(type)
            dismiss()
        }

    }

    interface BottomSheetMovieType{
        fun onTypeClicked(type: String)
    }
}

