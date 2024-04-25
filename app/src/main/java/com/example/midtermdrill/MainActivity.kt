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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
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

    // counter for the number of seats selected, initialized to 0
    private var numOfSeats = 0

    // var for keeping track of vegan selection, initalized to false
    private var isVegan = false


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


        // setup listener for time selection button


        // setup listener for vegan selection button
        veganButton.setOnClickListener {
            toggleVeganButton()
        }


        // setup listener for reserve seats button
    }

    // this method will apply the button scale down animation on touch for all buttons
    private fun applyAnimationToButtons() {
        val buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        // create the listener
        val touchListener = View.OnTouchListener { v, event ->
            // if a button is pressed start playing the animation
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.startAnimation(buttonAnimation)
                // trigger any OnClickListeners for the pressed button
                v.performClick()
                // mark event as handled
                return@OnTouchListener true
            }
            // otherwise, ignore
            false
        }

        // apply touch listener to all buttons
        listOf(languageButton, foodButton, drinksButton, increaseButton, decreaseButton, paymentButton, timeButton, veganButton, reserveSeatsButton).forEach {
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

    // this method is responsible for showing the dialog for changing languages
    private fun showLanguageDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.language_change_dialog, null)
        builder.setView(dialogView)

        // set the title of the dialog according to the current language
        builder.setTitle(R.string.dialog_language_select)

        val radioButtonEnglish = dialogView.findViewById<RadioButton>(R.id.radioButtonEnglish)
        val radioButtonHebrew = dialogView.findViewById<RadioButton>(R.id.radioButtonHebrew)

        // Check current locale and set buttons according to the user selection
        val currentLocale = Locale.getDefault().language
        if (currentLocale.equals("iw", ignoreCase = true) || currentLocale.equals(
                "he", ignoreCase = true
            )
        ) {
            radioButtonHebrew.isChecked = true
        } else {
            radioButtonEnglish.isChecked = true
        }

        // change language if user confirmed selection
        builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
            if (radioButtonEnglish.isChecked) {
                setLocale("en")
            } else {
                setLocale("iw")
            }
            dialog.dismiss()
        }
        // if user canceled selection don't do anything
        builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }

        // present dialog
        val dialog = builder.create()
        dialog.show()
    }


    // this method is responsible for changing the language displayed in the app
    private fun setLocale(language: String) {
        val currentLocale = Locale.getDefault().language
        // changes to the language if the current language is different
        if (!currentLocale.equals(language, ignoreCase = true)) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

            // refresh the activity to apply the new locale
            recreate()
        }
    }


    // Helper function to toggle the visibility of the RecyclerView sections for food and drinks
    private fun toggleSection(recyclerView: RecyclerView, button: MaterialButton) {
        // if the section is currently expanded - make it collapse, change button icon to expand
        if (recyclerView.visibility == View.VISIBLE) {
            val slideUp = AnimationUtils.loadAnimation(recyclerView.context, R.anim.slide_up)
            slideUp.setAnimationListener(object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animation?) {
                    recyclerView.visibility = View.GONE
                }
            })
            recyclerView.startAnimation(slideUp)
            button.setIconResource(R.drawable.arrow_downward)

            // otherwise, the section is currently hidden - make it expand, change button icon to collapse
        } else {

            val slideDown = AnimationUtils.loadAnimation(recyclerView.context, R.anim.slide_down)
            slideDown.setAnimationListener(object : SimpleAnimationListener() {
                override fun onAnimationStart(animation: Animation?) {
                    recyclerView.visibility = View.VISIBLE
                }
            })

            recyclerView.startAnimation(slideDown)
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
    private fun toggleVeganButton(){
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
        val buttonColorFrom = if (toggled) ContextCompat.getColor(this, R.color.light_gray) else ContextCompat.getColor(this, R.color.green)
        val buttonColorTo = if (toggled) ContextCompat.getColor(this, R.color.green) else ContextCompat.getColor(this, R.color.light_gray)

        /*
          Get color values of text color:
          if toggled off: go from white to black
          if toggled on: go from black to white
        */
        val textColorFrom = if (toggled) ContextCompat.getColor(this, R.color.black) else ContextCompat.getColor(this, R.color.white)
        val textColorTo = if (toggled) ContextCompat.getColor(this, R.color.white) else ContextCompat.getColor(this, R.color.black)

        // Animate background color
        val backgroundColorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), buttonColorFrom, buttonColorTo)
        backgroundColorAnimation.duration = 200  // Duration of the color transition
        backgroundColorAnimation.addUpdateListener { animator ->
            button.setBackgroundColor(animator.animatedValue as Int) // Update background color
        }

        // Animate text and icon color
        val textColorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), textColorFrom, textColorTo)
        textColorAnimation.duration = 200  // Duration of the color transition
        textColorAnimation.addUpdateListener { animator ->
            val color = animator.animatedValue as Int // set color for use for both text and icon colors
            button.setTextColor(animator.animatedValue as Int) // update text color
            button.iconTint = android.content.res.ColorStateList.valueOf(color) // update icon color
        }

        // Start both animations
        backgroundColorAnimation.start()
        textColorAnimation.start()
    }
}
