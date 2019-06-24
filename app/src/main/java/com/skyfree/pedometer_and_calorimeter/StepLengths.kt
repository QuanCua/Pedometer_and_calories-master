package com.skyfree.pedometer_and_calorimeter

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.NumberPicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_step_length.*

class StepLengths : AppCompatActivity(){
    var stepLength = 67

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_length)


        setTitle("Chiều dài bước chạy")

        txtStepLengthValues.setText(stepLength.toString() + " cm")

        imgPlus.setOnClickListener {
            if(stepLength >= 100)
            {
                stepLength = 100
                txtStepLengthValues.setText(stepLength.toString() + " cm")
                Toast.makeText(this@StepLengths, "Siêu nhân à?", Toast.LENGTH_SHORT).show()
            }
            else{
                stepLength++
                txtStepLengthValues.setText(stepLength.toString() + " cm")
            }
        }

        imgMinus.setOnClickListener {
            if(stepLength <= 1){
                stepLength = 1
                txtStepLengthValues.setText(stepLength.toString() + " cm")
                Toast.makeText(this@StepLengths, "Nghịch khỏe vc!", Toast.LENGTH_SHORT).show()
            }
            else {
                stepLength--
                txtStepLengthValues.setText(stepLength.toString() + " cm")
            }
        }

        txtHeightValues.setOnClickListener {
            numberPickerCustom()
        }


    }

    fun numberPickerCustom() {
        val d = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.number_picker_dialog, null)
        d.setTitle("Chiều cao")
        d.setView(dialogView)
        val numberPicker = dialogView.findViewById<NumberPicker>(R.id.dialog_number_picker)
        numberPicker.maxValue = 100
        numberPicker.minValue = 250
        numberPicker.wrapSelectorWheel = false
        numberPicker.setOnValueChangedListener { numberPicker, i, i1 -> println("onValueChange: ") }
        d.setPositiveButton("Done") { dialogInterface, i ->
            println("onClick: " + numberPicker.getValue())
            txtHeightValues.setText("          "+numberPicker.getValue().toString() + "cm")

        }
        d.setNegativeButton("Cancel") { dialogInterface, i ->

        }
        val alertDialog = d.create()
        alertDialog.show()
    }
}