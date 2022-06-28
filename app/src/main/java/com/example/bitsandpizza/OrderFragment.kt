package com.example.bitsandpizza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bitsandpizza.databinding.FragmentOrderBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class OrderFragment : Fragment() {
    //this is being made null as before onCreateView(), methods of the fragment lifecycle
    //does not have the access of views and we don't want these method to throw null pointer exception at
    //runtime.
    private var _binding: FragmentOrderBinding? = null

    // The following code is doing some kind of null check. But still I can't understand the nature of the
    // following code. ???????know about !!, binding get()??????
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val view = _binding!!.root


        //necessary references for the views and viewGroups
        /*val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbarId)
        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingId)
        val pizzaOptionRadioGroup = view.findViewById<RadioGroup>(R.id.pizzaOptionRadioGroupId)*/


        //????????know more about this 'as' and such codes
        //Fragments don't have setSupportActionBar() so we will get a reference to the activity
        //which is displaying the fragment and we will cast it to AppCompatActivity and then we will
        //be able to access setSupportActionBar.
        (activity as AppCompatActivity).setSupportActionBar(_binding!!.toolbarId)

        _binding!!.floatingId.setOnClickListener {
            val selectedRadioButton = _binding!!.pizzaOptionRadioGroupId.checkedRadioButtonId
            var message = ""
            if (selectedRadioButton == -1) {
                message = "Please select a Pizza first"
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                message = when (selectedRadioButton) {
                    R.id.margarettaOptionId -> _binding!!.margarettaOptionId.text.toString()
                    else -> _binding!!.farmhouseId.text.toString()
                }
                message += if (_binding!!.cheeseChipsId.isChecked) ", with extra Cheese" else ""
                message += if (_binding!!.sauceChipsId.isChecked) ", with extra sauce" else ""

                Snackbar.make(view, "$message", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Undo") {
                        Toast.makeText(activity, "Undone", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // this is being done so after this method if any fragment lifecycle method tries to use
        // view binding, it will save us from run time null pointer exception
        _binding = null
    }

}