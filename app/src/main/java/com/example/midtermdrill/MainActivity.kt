package com.example.midtermdrill

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.os.Bundle

import android.content.res.Configuration
import android.view.MotionEvent
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.widget.NestedScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton


// Main activity class
class MainActivity : AppCompatActivity() {
    // Define the attributes from the layout xml, we initialize them later
    private lateinit var menuTitle: TextView
    private lateinit var languageButton: MaterialButton
    private lateinit var foodButton: MaterialButton
    private lateinit var drinksButton: MaterialButton
    private lateinit var reserveSeatsButton: Button
    private lateinit var numOfSeatsButton: ConstraintLayout
    private lateinit var numOfSeatsText: TextView
    private lateinit var increaseButton: MaterialButton
    private lateinit var decreaseButton: MaterialButton
    private lateinit var paymentButton: MaterialButton
    private lateinit var timeButton: MaterialButton
    private lateinit var veganButton: MaterialButton
    private lateinit var recyclerViewFood: RecyclerView
    private lateinit var recyclerViewDrinks: RecyclerView
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var foodAdapter: MenuAdapter
    private lateinit var drinksAdapter: MenuAdapter

    // variable to hold the current language - init to the device's default
    private var currentLocale = Locale.getDefault().language

    // counter for the number of seats selected, initialized to 0
    private var numOfSeats = 0

    // var for keeping track of vegan selection, initalized to false
    private var isVegan = false

    // var for keeping track of user time slot selection, init to empty
    private var reservationTime = ""

    // var for keeping track of user payment method selection, init to empty
    private var reservationPaymentMethod = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to xml layout
        setContentView(R.layout.activity_main)

        // Initialize view components
        initViews()

        // Sets the minimum width of the number of seats text box so that it won't change from initial width
        numOfSeatsText.post {
            val width = numOfSeatsText.width
            numOfSeatsText.minWidth = width
        }

        // Initialize adapters
        initAdapters()

        // Setup scroll view to enable scrolling
        setupScrollView()

        // Setup click listeners for interaction
        setupListeners()

        // Apply the animations for all buttons
        applyAnimationToButtons()
    }

    private fun initViews() {
        // Find each view by its ID and assign it to the corresponding variable
        menuTitle = findViewById(R.id.menuTitle)
        languageButton = findViewById<MaterialButton>(R.id.languageButton)
        foodButton = findViewById<MaterialButton>(R.id.foodButton)
        drinksButton = findViewById<MaterialButton>(R.id.drinksButton)
        reserveSeatsButton = findViewById(R.id.reserveSeatsButton)
        numOfSeatsButton = findViewById(R.id.numOfSeats)
        numOfSeatsText = findViewById(R.id.numOfSeatsText)
        increaseButton = findViewById<MaterialButton>(R.id.increaseButton)
        decreaseButton = findViewById<MaterialButton>(R.id.decreaseButton)
        paymentButton = findViewById<MaterialButton>(R.id.paymentButton)
        timeButton = findViewById<MaterialButton>(R.id.timeButton)
        veganButton = findViewById<MaterialButton>(R.id.veganButton)
        recyclerViewFood = findViewById(R.id.recyclerViewFood)
        recyclerViewDrinks = findViewById(R.id.recyclerViewDrinks)
        nestedScrollView = findViewById(R.id.nestedScrollView)

    }

    // create food menu items and return them in a list
    private fun setupFood(): List<MenuItem> {
        val chickenWings = MenuItem(
            getString(R.string.fried_chicken_wings_name),
            "₪35",
            getString(R.string.fried_chicken_wings_desc),
            R.drawable.fried_chicken_wings.toString()
        )
        val greekSalad = MenuItem(
            getString(R.string.greek_salad_name),
            "₪40",
            getString(R.string.greek_salad_desc),
            R.drawable.greek_salad.toString()
        )
        val burgers = MenuItem(
            getString(R.string.mini_burger_trio_name),
            "₪45",
            getString(R.string.mini_burger_trio_desc),
            R.drawable.mini_burger_trio.toString()
        )
        val pizza = MenuItem(
            getString(R.string.pizza_margherita_name),
            "₪50",
            getString(R.string.pizza_margherita_desc),
            R.drawable.pizza_margherita.toString()
        )
        val springRolls = MenuItem(
            getString(R.string.spring_rolls_name),
            "₪35",
            getString(R.string.spring_rolls_desc),
            R.drawable.spring_rolls.toString()
        )
        return listOf(chickenWings, greekSalad, burgers, pizza, springRolls)
    }

    // create drinks menu items and return them im a list
    private fun setupDrinks(): List<MenuItem> {
        val beer = MenuItem(
            getString(R.string.beer_name),
            "₪25",
            getString(R.string.beer_desc),
            R.drawable.beer.toString()
        )
        val daiquiri = MenuItem(
            getString(R.string.daiquiri_name),
            "₪35",
            getString(R.string.daiquiri_desc),
            R.drawable.daiquiri.toString()
        )
        val margarita = MenuItem(
            getString(R.string.margarita_name),
            "₪40",
            getString(R.string.mini_burger_trio_desc),
            R.drawable.margarita.toString()
        )
        val moscowMule = MenuItem(
            getString(R.string.moscow_mule_name),
            "₪35",
            getString(R.string.moscow_mule_desc),
            R.drawable.moscow_mule.toString()
        )
        val pinaColada = MenuItem(
            getString(R.string.pina_colada_name),
            "₪40",
            getString(R.string.pina_colada_desc),
            R.drawable.pina_colada.toString()
        )
        return listOf(beer, daiquiri, margarita, moscowMule, pinaColada)
    }

    private fun initAdapters() {
        // Setup the food items' objects into a list
        val foodItems = setupFood()

        // Setup the drink items' objects into a list
        val drinkItems = setupDrinks()

        // init the adapters and setup layout manager
        foodAdapter = MenuAdapter(foodItems)
        drinksAdapter = MenuAdapter(drinkItems)

        recyclerViewFood.adapter = foodAdapter
        recyclerViewFood.layoutManager = LinearLayoutManager(this)

        recyclerViewDrinks.adapter = drinksAdapter
        recyclerViewDrinks.layoutManager = LinearLayoutManager(this)


    }

    private fun setupListeners() {
        // Setup listener for the language button to open change language dialogue on click
        languageButton.setOnClickListener {
            showLanguageDialog()
        }

        // Setup listeners for the food and drinks buttons to toggle section display/hiding on click
        foodButton.setOnClickListener {
            toggleSection(recyclerViewFood, foodButton)
        }

        drinksButton.setOnClickListener {
            toggleSection(recyclerViewDrinks, drinksButton)
        }

        // Setup listener for the number of seats increase and decrease buttons
        increaseButton.setOnClickListener {
            handleSeatChange(increment = true)
        }

        decreaseButton.setOnClickListener {
            handleSeatChange(increment = false)
        }


        // Setup listener for payment method button
        paymentButton.setOnClickListener {
            showPaymentMethodDialog()
        }

        // setup listener for time selection button
        timeButton.setOnClickListener {
            showTimeSelectionDialog()
        }


        // setup listener for vegan selection button
        veganButton.setOnClickListener {
            toggleVeganButton()
        }

        // setup listener for reserve seats button
        reserveSeatsButton.setOnClickListener {
            showConfirmReservationDialog()
        }
    }

    // this method will apply the button animations on touch for the buttons
    private fun applyAnimationToButtons() {
        val buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_bounce)
        // define colors for color pulse animation
        val originalColor = ContextCompat.getColor(this, R.color.light_gray)
        val pulseColor = ContextCompat.getColor(this, R.color.dark_gray)

        val touchListener = View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // enable bounce animation for all buttons
                    v.startAnimation(buttonAnimation)

                    // check if the current button is not the veganButton or reserveSeatsButton
                    if (v != veganButton && v != reserveSeatsButton) {
                        // start color pulse animation for the rest of the buttons
                        ValueAnimator.ofObject(ArgbEvaluator(), originalColor, pulseColor).apply {
                            duration = 200
                            addUpdateListener { animator ->
                                (v as? MaterialButton)?.setBackgroundColor(animator.animatedValue as Int)
                            }
                            start()
                        }
                    }

                    // Perform the button's click action
                    v.performClick()
                    return@OnTouchListener true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    if (v != veganButton && v != reserveSeatsButton) {
                        // reverse the color animation to original on button release
                        ValueAnimator.ofObject(ArgbEvaluator(), pulseColor, originalColor).apply {
                            duration = 200
                            addUpdateListener { animator ->
                                (v as? MaterialButton)?.setBackgroundColor(animator.animatedValue as Int)
                            }
                            start()
                        }
                    }
                }
            }
            // ignore other actions
            false
        }

        // Apply the touch listener to all buttons
        listOf(
            languageButton,
            foodButton,
            drinksButton,
            increaseButton,
            decreaseButton,
            paymentButton,
            timeButton,
            veganButton,
            reserveSeatsButton
        ).forEach {
            it.setOnTouchListener(touchListener)
        }
    }


    // listener for the scroll view
    private fun setupScrollView() {
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            // show simple pop up to inform the user they reached the top or the bottom depending on the location
            if (scrollY == 0 && !v.canScrollVertically(-1)) {
                Toast.makeText(this, R.string.top_of_page_text, Toast.LENGTH_SHORT).show()
            }
            if (!v.canScrollVertically(1)) {
                Toast.makeText(this, R.string.bottom_of_page_text, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // this method is responsible for displaying the dialog for changing languages
    private fun showLanguageDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.language_change_dialog, null)
        builder.setView(dialogView)

        // set the title of the dialog according to the current language
        builder.setTitle(getString(R.string.dialog_language_select))

        // get radio buttons' references
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroupLanguages)
        val radioButtonEnglish = dialogView.findViewById<RadioButton>(R.id.radioButtonEnglish)
        val radioButtonHebrew = dialogView.findViewById<RadioButton>(R.id.radioButtonHebrew)

        // set buttons state checked according to the currentLocale
        radioButtonEnglish.isChecked = currentLocale.equals("en", ignoreCase = true)
        radioButtonHebrew.isChecked = !radioButtonEnglish.isChecked

        // define a temporary variable to hold the new selected language
        var selectedLanguage = currentLocale

        // use RadioGroup setOnCheckedChangeListener to handle language changes dynamically
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedLanguage = when (checkedId) {
                R.id.radioButtonEnglish -> "en"
                R.id.radioButtonHebrew -> "iw"
                else -> currentLocale
            }
        }

        // if accepted and language was changed, call on changeLocale method to change language
        builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
            if (currentLocale != selectedLanguage) {
                setLocale(selectedLanguage)
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

    // This method changes the language in which the app is displayed
    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        // update current locale global var
        currentLocale = language

        // Refresh the activity to apply the new locale settings
        recreate()

    }

    // this method is responsible for displaying the dialog for the time selection picker
    private fun showTimeSelectionDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
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
            reservationTime = selectedTime
            timeButton.text = reservationTime
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
    private fun showPaymentMethodDialog() {
        // get payment methods array
        val paymentMethods = resources.getStringArray(R.array.payment_methods)

        // set up an ArrayAdapter to handle the display of payment methods in a ListView
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paymentMethods)

        // init list and adapter
        val listView = ListView(this).apply {
            setAdapter(adapter)
        }

        // create the dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.select_payment_method)
            .setView(listView)
            .create()

        /*
         list view listener to handle user selection
         selection is saved to the global reservationPaymentMethod var and is written on the payment button
         afterward, dismiss dialog
         */
        listView.setOnItemClickListener { _, _, position, _ ->
            reservationPaymentMethod = paymentMethods[position]
            paymentButton.text = reservationPaymentMethod
            dialog.dismiss()
        }

        // present dialog with animations
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()
    }

    // this method is responsible for displaying the dialog for the reserve seats button
    private fun showConfirmReservationDialog() {
        // if one or more details are missing (set to default) display the user with an error message prompting them to fill in the missing details
        if (numOfSeats == 0 || reservationTime.isEmpty() || reservationPaymentMethod.isEmpty()) {
            val error = getString(R.string.reservation_error)
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            return
        }

        // if all details are in place, prepare reservationDetails string with the details filled in by the user and saved in the global vars
        val reservationDetails = getString(
            R.string.reservation_details,
            numOfSeats,
            reservationTime,
            reservationPaymentMethod,
            if (isVegan) getString(R.string.yes) else getString(R.string.no)
        )
        // create dialog for displaying reservationDetails
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.reservation_title))
            .setMessage(reservationDetails)
            // if user clicked accept display success message
            .setPositiveButton(getString(R.string.dialog_accept)) { _, _ ->
                Toast.makeText(this, getString(R.string.reservation_saved), Toast.LENGTH_LONG)
                    .show()
            }
            // if user clicked cancel do nothing
            .setNegativeButton(getString(R.string.dialog_cancel)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()

        // present dialog with animations
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()

    }


    // Helper function to toggle the visibility of the RecyclerView sections for food and drinks
    private fun toggleSection(recyclerView: RecyclerView, button: MaterialButton) {
        // if the section is currently expanded - make it collapse, change button icon to expand
        if (recyclerView.visibility == View.VISIBLE) {
            recyclerView.visibility = View.GONE
            button.setIconResource(R.drawable.arrow_downward)
            // otherwise, the section is currently hidden - make it expand, change button icon to collapse
        } else {
            recyclerView.visibility = View.VISIBLE
            button.setIconResource(R.drawable.arrow_upward)
        }
    }


    // Updates the number of seats according to which button was pressed - increase/decrease
    private fun handleSeatChange(increment: Boolean) {
        // Cap number of available seats at 8
        if (increment && numOfSeats < 8) {
            numOfSeats++
            // Ensure number of seats isnt negative
        } else if (!increment && numOfSeats > 0) {
            numOfSeats--
        }
        // Update the text view only if it's the first interaction or on subsequent valid presses
        if (numOfSeatsText.text.toString() == getString(R.string.num_of_seats_text) || numOfSeats >= 0) {
            numOfSeatsText.text = numOfSeats.toString()
        }
    }

    // method responsible for handling vegan button toggle
    private fun toggleVeganButton() {
        // flip value after toggled
        isVegan = !isVegan
        applyColorTransition(veganButton, isVegan)
    }

    // animate and apply the color changes triggered by clicking the vegan button
    private fun applyColorTransition(button: MaterialButton, toggled: Boolean) {
        /*
         Get color values of button color:
          if toggled off: go from green to gray
          if toggled on: go from gray to green
         */
        val buttonColorFrom = if (toggled) ContextCompat.getColor(
            this,
            R.color.light_gray
        ) else ContextCompat.getColor(this, R.color.green)
        val buttonColorTo =
            if (toggled) ContextCompat.getColor(this, R.color.green) else ContextCompat.getColor(
                this,
                R.color.light_gray
            )

        /*
          Get color values of text color:
          if toggled off: go from white to black
          if toggled on: go from black to white
        */
        val textColorFrom =
            if (toggled) ContextCompat.getColor(this, R.color.black) else ContextCompat.getColor(
                this,
                R.color.white
            )
        val textColorTo =
            if (toggled) ContextCompat.getColor(this, R.color.white) else ContextCompat.getColor(
                this,
                R.color.black
            )

        // Animate background color
        val backgroundColorAnimation =
            ValueAnimator.ofObject(ArgbEvaluator(), buttonColorFrom, buttonColorTo)
        backgroundColorAnimation.duration = 200  // Duration of the color transition
        backgroundColorAnimation.addUpdateListener { animator ->
            button.setBackgroundColor(animator.animatedValue as Int) // Update background color
        }

        // Animate text and icon color
        val textColorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), textColorFrom, textColorTo)
        textColorAnimation.duration = 200  // Duration of the color transition
        textColorAnimation.addUpdateListener { animator ->
            val color =
                animator.animatedValue as Int // set color for use for both text and icon colors
            button.setTextColor(animator.animatedValue as Int) // update text color
            button.iconTint = android.content.res.ColorStateList.valueOf(color) // update icon color
        }

        // Start both animations
        backgroundColorAnimation.start()
        textColorAnimation.start()
    }
}
