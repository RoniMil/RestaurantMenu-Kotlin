package com.example.midtermdrill

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to xml layout
        setContentView(R.layout.activity_main)

        // Initialize view components
        initViews()

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
