package com.tan.login.DiaLogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tan.login.Interfaces.IClickFlight
import com.tan.login.Models.FlightPlace.SelectPeople
import com.tan.login.R
import kotlinx.android.synthetic.main.select_people_dialog.view.*

class SelectPeoPleDialog(var iClickFlight: IClickFlight): BottomSheetDialogFragment() {

    private lateinit var selectPeople: SelectPeople

    fun setSelectPeople(mSelectPeople: SelectPeople) {
        this.selectPeople = mSelectPeople
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = LayoutParams.MATCH_PARENT
            val height = LayoutParams.WRAP_CONTENT
            dialog!!.window!!.setLayout(width, height)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.select_people_dialog,container,false)
        initValue(view)
        bindingValue(view)
        eventClick(view)
        return view
    }

    private fun bindingValue(view: View) {
        view.np_adult.value = selectPeople.adult
        view.np_child.value = selectPeople.child
        view.np_baby.value = selectPeople.baby
    }

    private fun eventClick(view: View) {
        view.btn_select_cancel.setOnClickListener {
            this.dismiss()
        }
        view.btn_select_pick.setOnClickListener {
            var selectPeople = SelectPeople(view.np_adult.value,
                                            view.np_child.value,
                                            view.np_baby.value)

            iClickFlight.clickBtnYesSelectPeople(selectPeople)
            this.dismiss()
        }
    }

    private fun initValue(view: View) {
        view.np_adult.minValue = 1
        view.np_adult.maxValue = 6

        view.np_child.minValue = 0
        view.np_child.maxValue = 5

        view.np_baby.minValue = 0
        view.np_baby.maxValue = 2
    }
}