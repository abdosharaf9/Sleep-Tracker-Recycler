package com.abdosharaf.sleeptrackerrecycler.dialogs

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.abdosharaf.sleeptrackerrecycler.databinding.DeleteDialogBinding

class DeleteDialog(context: Context): Dialog(context) {

    lateinit var binding: DeleteDialogBinding
    var deleteCall: (() -> Unit) ?= null

    fun showDialog() {
        binding = DeleteDialogBinding.inflate(LayoutInflater.from(context))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        if (window != null) {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            window!!.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            val back = ColorDrawable(Color.TRANSPARENT)
            val margin = 30
            val inset = InsetDrawable(back, margin)
            window!!.setBackgroundDrawable(inset)
        }

        binding.btnDialogDelete.setOnClickListener {
            deleteCall?.invoke()
            dismiss()
        }

        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }

        show()
    }
}