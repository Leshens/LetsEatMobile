package com.leshen.letseatmobile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

class RangeSelectorDialogFragment : DialogFragment() {

    interface RangeSelectorListener {
        fun onRangeSelected(range: Int)
    }

    private var rangeSelectorListener: RangeSelectorListener? = null

    fun setRangeSelectorListener(listener: RangeSelectorListener) {
        this.rangeSelectorListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_range_selector, null)

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        val buttonApply = view.findViewById<Button>(R.id.buttonApply)

        builder.setView(view)
            .setTitle("Wybór zasięgu")
            .setNegativeButton("Anuluj") { _, _ -> }
            .setCancelable(true)

        val dialog = builder.create()

        // Domyślnie ustawia zasięg na "Mały" (1)
        radioGroup.check(R.id.radioSmall)

        buttonApply.setOnClickListener {
            val selectedRange = when (radioGroup.checkedRadioButtonId) {
                R.id.radioSmall -> 1
                R.id.radioMedium -> 2
                R.id.radioLarge -> 3
                else -> 1
            }

            rangeSelectorListener?.onRangeSelected(selectedRange)
            dialog.dismiss()
        }

        return dialog
    }
}
