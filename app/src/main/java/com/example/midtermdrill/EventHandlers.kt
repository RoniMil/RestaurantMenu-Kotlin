package com.example.midtermdrill

import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.NestedScrollView

// class for handling event handling. mainly direct user interactions
class EventHandlers(private val activity: MainActivity) {
    // Setup all listeners for user interaction
    fun setupListeners() {
        // Setup scroll view listener
        setupScrollView()

        activity.apply {

            // Setup listener for the language button to open change language dialogue on click
            languageButton.setOnClickListener {
                activity.dialogManager.showLanguageDialog()
            }

            // Setup listeners for the food and drinks buttons to toggle section display/hiding on click
            foodButton.setOnClickListener {
                activity.uiManager.toggleSection(recyclerViewFood, foodButton)
            }

            drinksButton.setOnClickListener {
                activity.uiManager.toggleSection(recyclerViewDrinks, drinksButton)
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
                activity.dialogManager.showPaymentMethodDialog()
            }

            // setup listener for time selection button
            timeButton.setOnClickListener {
                activity.dialogManager.showTimeSelectionDialog()
            }


            // setup listener for vegan selection button
            veganButton.setOnClickListener {
                toggleVeganButton()
            }

            // setup listener for reserve seats button
            reserveSeatsButton.setOnClickListener {
                activity.dialogManager.showConfirmReservationDialog()
            }
        }
    }

    // Updates the number of seats according to which button was pressed - increase/decrease
    private fun handleSeatChange(increment: Boolean) {
        // Cap number of available seats at 8
        if (increment && activity.numOfSeats < 8) {
            activity.numOfSeats++
            // Ensure number of seats isnt negative
        } else if (!increment && activity.numOfSeats > 0) {
            activity.numOfSeats--
        }
        // Update the text view only if it's the first interaction or on subsequent valid presses
        if (activity.numOfSeatsText.text.toString() == activity.getString(R.string.num_of_seats_text) || activity.numOfSeats >= 0) {
            activity.numOfSeatsText.text = activity.numOfSeats.toString()
        }
    }

    // Toggle vegan selection
    private fun toggleVeganButton() {
        activity.isVegan = !activity.isVegan
        activity.uiManager.applyColorTransition(activity.veganButton, activity.isVegan)
    }

    // listener for the scroll view
    private fun setupScrollView() {
        activity.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            // show simple pop up to inform the user they reached the top or the bottom depending on the location
            if (scrollY == 0 && !v.canScrollVertically(-1)) {
                Toast.makeText(activity, R.string.top_of_page_text, Toast.LENGTH_SHORT).show()
            }
            if (!v.canScrollVertically(1)) {
                Toast.makeText(activity, R.string.bottom_of_page_text, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Apply animations on touch for all buttons
    fun applyAnimationToButtons() {
        val buttonAnimation = AnimationUtils.loadAnimation(activity, R.anim.button_bounce)
        val touchListener = View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.startAnimation(buttonAnimation)
                    v.performClick()
                    return@OnTouchListener true
                }
            }
            false
        }

        listOf(
            activity.languageButton,
            activity.foodButton,
            activity.drinksButton,
            activity.increaseButton,
            activity.decreaseButton,
            activity.paymentButton,
            activity.timeButton,
            activity.veganButton,
            activity.reserveSeatsButton
        ).forEach {
            it.setOnTouchListener(touchListener)
        }
    }
}



