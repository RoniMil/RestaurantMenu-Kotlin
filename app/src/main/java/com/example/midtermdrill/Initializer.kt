package com.example.midtermdrill

import androidx.recyclerview.widget.LinearLayoutManager

// class responsible for initializing main elements
class Initializer(private val activity: MainActivity) {
    fun initViews() {
        // Find each view by its ID and assign it to the corresponding variable
        activity.apply {
            menuTitle = findViewById(R.id.menuTitle)
            languageButton = findViewById(R.id.languageButton)
            foodButton = findViewById(R.id.foodButton)
            drinksButton = findViewById(R.id.drinksButton)
            reserveSeatsButton = findViewById(R.id.reserveSeatsButton)
            numOfSeatsButton = findViewById(R.id.numOfSeats)
            numOfSeatsText = findViewById(R.id.numOfSeatsText)
            increaseButton = findViewById(R.id.increaseButton)
            decreaseButton = findViewById(R.id.decreaseButton)
            paymentButton = findViewById(R.id.paymentButton)
            timeButton = findViewById(R.id.timeButton)
            veganButton = findViewById(R.id.veganButton)
            recyclerViewFood = findViewById(R.id.recyclerViewFood)
            recyclerViewDrinks = findViewById(R.id.recyclerViewDrinks)
            nestedScrollView = findViewById(R.id.nestedScrollView)

            // Setting the minimum width of the number of seats text to prevent layout change
            numOfSeatsText.post {
                val width = numOfSeatsText.width
                numOfSeatsText.minWidth = width
            }

            // apply animations to the buttons
            eventHandlers.applyAnimationToButtons()
        }
    }

    // Method to initialize adapters
    fun initAdapters() {
        activity.apply {
            // Creating and setting up the food and drinks adapters
            foodAdapter = MenuAdapter(setupFood())
            drinksAdapter = MenuAdapter(setupDrinks())

            recyclerViewFood.apply {
                adapter = foodAdapter
                layoutManager = LinearLayoutManager(activity)
            }

            recyclerViewDrinks.apply {
                adapter = drinksAdapter
                layoutManager = LinearLayoutManager(activity)
            }
        }
    }

    // create food menu items and return them in a list
    private fun setupFood(): List<MenuItem> {
        val chickenWings = MenuItem(
            activity.getString(R.string.fried_chicken_wings_name),
            "₪35",
            activity.getString(R.string.fried_chicken_wings_desc),
            R.drawable.fried_chicken_wings.toString()
        )
        val greekSalad = MenuItem(
            activity.getString(R.string.greek_salad_name),
            "₪40",
            activity.getString(R.string.greek_salad_desc),
            R.drawable.greek_salad.toString()
        )
        val burgers = MenuItem(
            activity.getString(R.string.mini_burger_trio_name),
            "₪45",
            activity.getString(R.string.mini_burger_trio_desc),
            R.drawable.mini_burger_trio.toString()
        )
        val pizza = MenuItem(
            activity.getString(R.string.pizza_margherita_name),
            "₪50",
            activity.getString(R.string.pizza_margherita_desc),
            R.drawable.pizza_margherita.toString()
        )
        val springRolls = MenuItem(
            activity.getString(R.string.spring_rolls_name),
            "₪35",
            activity.getString(R.string.spring_rolls_desc),
            R.drawable.spring_rolls.toString()
        )
        return listOf(chickenWings, greekSalad, burgers, pizza, springRolls)
    }

    // create drinks menu items and return them im a list
    private fun setupDrinks(): List<MenuItem> {
        val beer = MenuItem(
            activity.getString(R.string.beer_name),
            "₪25",
            activity.getString(R.string.beer_desc),
            R.drawable.beer.toString()
        )
        val daiquiri = MenuItem(
            activity.getString(R.string.daiquiri_name),
            "₪35",
            activity.getString(R.string.daiquiri_desc),
            R.drawable.daiquiri.toString()
        )
        val margarita = MenuItem(
            activity.getString(R.string.margarita_name),
            "₪40",
            activity.getString(R.string.mini_burger_trio_desc),
            R.drawable.margarita.toString()
        )
        val moscowMule = MenuItem(
            activity.getString(R.string.moscow_mule_name),
            "₪35",
            activity.getString(R.string.moscow_mule_desc),
            R.drawable.moscow_mule.toString()
        )
        val pinaColada = MenuItem(
            activity.getString(R.string.pina_colada_name),
            "₪40",
            activity.getString(R.string.pina_colada_desc),
            R.drawable.pina_colada.toString()
        )
        return listOf(beer, daiquiri, margarita, moscowMule, pinaColada)
    }


}
