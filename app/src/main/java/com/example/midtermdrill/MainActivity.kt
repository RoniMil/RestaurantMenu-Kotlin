package com.example.midtermdrill

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.util.Locale

class MainActivity : AppCompatActivity() {
    // init instances of classes that manage different aspect of the app
    private lateinit var initializer: Initializer
    lateinit var eventHandlers: EventHandlers
    lateinit var dialogManager: DialogManager
    lateinit var uiManager: UiManager

    // Define the attributes from the layout xml, we initialize them later
    lateinit var menuTitle: TextView
    lateinit var languageButton: MaterialButton
    lateinit var foodButton: MaterialButton
    lateinit var drinksButton: MaterialButton
    lateinit var reserveSeatsButton: Button
    lateinit var numOfSeatsButton: ConstraintLayout
    lateinit var numOfSeatsText: TextView
    lateinit var increaseButton: MaterialButton
    lateinit var decreaseButton: MaterialButton
    lateinit var paymentButton: MaterialButton
    lateinit var timeButton: MaterialButton
    lateinit var veganButton: MaterialButton
    lateinit var recyclerViewFood: RecyclerView
    lateinit var recyclerViewDrinks: RecyclerView
    lateinit var nestedScrollView: NestedScrollView
    lateinit var foodAdapter: MenuAdapter
    lateinit var drinksAdapter: MenuAdapter

    // variable to hold the current language - init to the device's default
    var currentLocale: String = Locale.getDefault().language

    // counter for the number of seats selected, initialized to 0
    var numOfSeats = 0

    // var for keeping track of vegan selection, initalized to false
    var isVegan = false

    // var for keeping track of user time slot selection, init to empty
    var reservationTime = ""

    // var for keeping track of user payment method selection, init to empty
    var reservationPaymentMethod = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializer = Initializer(this)
        eventHandlers = EventHandlers(this)
        dialogManager = DialogManager(this)
        uiManager = UiManager(this)

        initializer.initViews()
        initializer.initAdapters()
        eventHandlers.setupListeners()
    }
}

