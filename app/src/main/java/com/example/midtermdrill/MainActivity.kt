package com.example.midtermdrill

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.View
import androidx.core.widget.NestedScrollView
import android.widget.Toast
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to xml layout
        setContentView(R.layout.activity_main)

        // Initialize view components
        initViews()

        // Initialize adapters
        initAdapters()

        // Setup scroll view to enable scrolling
        setupScrollView()

        // Setup click listeners for interaction
        setupListeners()
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
    private fun setupFood() : List<MenuItem> {
        val chickenWings = MenuItem(getString(R.string.fried_chicken_wings_name), "₪35", getString(R.string.fried_chicken_wings_desc), R.drawable.fried_chicken_wings.toString())
        val greekSalad = MenuItem(getString(R.string.greek_salad_name), "₪40", getString(R.string.greek_salad_desc), R.drawable.greek_salad.toString())
        val burgers = MenuItem(getString(R.string.mini_burger_trio_name), "₪45", getString(R.string.mini_burger_trio_desc), R.drawable.mini_burger_trio.toString())
        val pizza = MenuItem(getString(R.string.pizza_margherita_name), "₪50", getString(R.string.pizza_margherita_desc), R.drawable.pizza_margherita.toString())
        val springRolls = MenuItem(getString(R.string.spring_rolls_name), "₪35", getString(R.string.spring_rolls_desc), R.drawable.spring_rolls.toString())
        return listOf(chickenWings, greekSalad, burgers, pizza, springRolls)
    }

    // create drinks menu items and return them im a list
    private fun setupDrinks() : List<MenuItem> {
        val beer = MenuItem(getString(R.string.beer_name), "₪25", getString(R.string.beer_desc), R.drawable.beer.toString())
        val daiquiri = MenuItem(getString(R.string.daiquiri_name), "₪35", getString(R.string.daiquiri_desc), R.drawable.daiquiri.toString())
        val margarita = MenuItem(getString(R.string.margarita_name), "₪40", getString(R.string.mini_burger_trio_desc), R.drawable.margarita.toString())
        val moscowMule = MenuItem(getString(R.string.moscow_mule_name), "₪35", getString(R.string.moscow_mule_desc), R.drawable.moscow_mule.toString())
        val pinaColada = MenuItem(getString(R.string.pina_colada_name), "₪40", getString(R.string.pina_colada_desc), R.drawable.pina_colada.toString())
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



        // Setup listeners for the food and drinks buttons to toggle section display/hiding on click
        foodButton.setOnClickListener {
            toggleSection(recyclerViewFood, foodButton)
        }

        drinksButton.setOnClickListener {
            toggleSection(recyclerViewDrinks, drinksButton)
        }

        // Setup listener for the number of seats increase and decrease buttons


        // Setup listener for payment method button


        // setup listener for time selection button


        // setup listener for vegan selection button


        // setup listener for reserve seats button
    }

    private fun setupScrollView() {
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                Toast.makeText(this, "Scrolling down", Toast.LENGTH_SHORT).show()
            }
            if (scrollY < oldScrollY) {
                Toast.makeText(this, "Scrolling up", Toast.LENGTH_SHORT).show()
            }
            if (scrollY == 0) {
                Toast.makeText(this, "Reached the top", Toast.LENGTH_SHORT).show()
            }
            if (!v.canScrollVertically(1)) {
                Toast.makeText(this, "Reached the bottom", Toast.LENGTH_SHORT).show()
            }
        })
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
}
