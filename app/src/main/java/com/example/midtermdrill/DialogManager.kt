package com.example.midtermdrill

import android.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

// class for managing dialog functionalities
class DialogManager(private val activity: MainActivity) {

    // this method is responsible for displaying the dialog for changing languages
    fun showLanguageDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.language_change_dialog, null)
        builder.setView(dialogView)

        // set the title of the dialog according to the current language
        builder.setTitle(activity.getString(R.string.dialog_language_select))

        // get radio buttons' references
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroupLanguages)
        val radioButtonEnglish = dialogView.findViewById<RadioButton>(R.id.radioButtonEnglish)
        val radioButtonHebrew = dialogView.findViewById<RadioButton>(R.id.radioButtonHebrew)

        // set buttons state checked according to the currentLocale
        radioButtonEnglish.isChecked = activity.currentLocale.equals("en", ignoreCase = true)
        radioButtonHebrew.isChecked = !radioButtonEnglish.isChecked

        // define a temporary variable to hold the new selected language
        var selectedLanguage = activity.currentLocale

        // use RadioGroup setOnCheckedChangeListener to handle language changes dynamically
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedLanguage = when (checkedId) {
                R.id.radioButtonEnglish -> "en"
                R.id.radioButtonHebrew -> "iw"
                else -> activity.currentLocale
            }
        }

        // if accepted and language was changed, call on changeLocale method to change language
        builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
            if (activity.currentLocale != selectedLanguage) {
                activity.uiManager.setLocale(selectedLanguage)
            }
            dialog.dismiss()
        }
        // if cancelled, do nothing
        builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }

        // present dialog with animation
        val dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()
    }

    // this method is responsible for displaying the dialog for the time selection picker
    fun showTimeSelectionDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.time_selection_dialog, null)
        builder.setView(dialogView)

        // get reference to the time picker
        val picker: NumberPicker = dialogView.findViewById(R.id.timePicker)

        // set the title of the dialog according to the current language
        builder.setTitle(R.string.time_text)

        // Function to generate time slots for use in the time picker within the dialog
        val generateTimeSlots = { startTime: String, endTime: String, interval: Int ->
            // This list will hold the generated time slots
            val resultList = mutableListOf<String>()
            // Parse starting and ending times from the input
            var hour = startTime.substringBefore(":").toInt()
            var minute = startTime.substringAfter(":").toInt()
            val endHour =
                if (endTime.substringBefore(":").toInt() == 1) 25 else endTime.substringBefore(":")
                    .toInt() // Adjust for times past midnight
            val endMinute = endTime.substringAfter(":").toInt()

            // Continue generating time slots in 15 minute intervals until we reach the end hour
            while (hour < endHour || (hour == endHour && minute <= endMinute)) {
                resultList.add(
                    String.format(
                        "%02d:%02d",
                        if (hour < 24) hour else hour - 24,
                        minute
                    )
                )
                minute += interval
                // Hour calculation
                if (minute >= 60) {
                    hour += minute / 60
                    minute %= 60
                }
            }
            resultList.toTypedArray()
        }

        // Array to hold the time slots, generate time slots in 15 mins intervals from 18pm to 1am
        val times = generateTimeSlots("18:00", "01:00", 15)
        picker.minValue = 0
        picker.maxValue = times.size - 1
        picker.displayedValues = times
        picker.wrapSelectorWheel = false

        // init temp selectedTime variable as the first timeslot for the case where user doesn't scroll
        var selectedTime = times[0]

        // Set time picker listener to save selected time in the local selectedTime var
        picker.setOnValueChangedListener { _, _, newVal ->
            selectedTime = times[newVal]
        }

        // event handling for user pressing accept button
        builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
            // change the global reservationTime var and text on the time button to the selected hour
            activity.reservationTime = selectedTime
            activity.timeButton.text = activity.reservationTime
            // dismiss dialog
            dialog.dismiss()
        }

        // event handling for user pressing cancel button (do nothing)
        builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
            dialog.dismiss()
        }

        // present dialog with animations
        val dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()
    }

    // this method is responsible for displaying the dialog for the time selection button
    fun showPaymentMethodDialog() {
        // get payment methods array
        val paymentMethods = activity.resources.getStringArray(R.array.payment_methods)

        // set up an ArrayAdapter to handle the display of payment methods in a ListView
        val adapter =
            ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, paymentMethods)

        // init list and adapter
        val listView = ListView(activity).apply {
            setAdapter(adapter)
        }

        // create the dialog
        val dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.select_payment_method)
            .setView(listView)
            .create()

        /*
         list view listener to handle user selection
         selection is saved to the global reservationPaymentMethod var and is written on the payment button
         afterward, dismiss dialog
         */
        listView.setOnItemClickListener { _, _, position, _ ->
            activity.reservationPaymentMethod = paymentMethods[position]
            activity.paymentButton.text = activity.reservationPaymentMethod
            dialog.dismiss()
        }

        // present dialog with animations
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()
    }

    // this method is responsible for displaying the dialog for the reserve seats button
    fun showConfirmReservationDialog() {
        // if one or more details are missing (set to default) display the user with an error message prompting them to fill in the missing details
        if (activity.numOfSeats == 0 || activity.reservationTime.isEmpty() || activity.reservationPaymentMethod.isEmpty()) {
            val error = activity.getString(R.string.reservation_error)
            Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
            return
        }

        // if all details are in place, prepare reservationDetails string with the details filled in by the user and saved in the global vars
        val reservationDetails = activity.getString(
            R.string.reservation_details,
            activity.numOfSeats,
            activity.reservationTime,
            activity.reservationPaymentMethod,
            if (activity.isVegan) activity.getString(R.string.yes) else activity.getString(R.string.no)
        )
        // create dialog for displaying reservationDetails
        val dialog = AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.reservation_title))
            .setMessage(reservationDetails)
            // if user clicked accept display success message
            .setPositiveButton(activity.getString(R.string.dialog_accept)) { _, _ ->
                Toast.makeText(
                    activity,
                    activity.getString(R.string.reservation_saved),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            // if user clicked cancel do nothing
            .setNegativeButton(activity.getString(R.string.dialog_cancel)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()

        // present dialog with animations
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()

    }
}