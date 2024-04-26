package com.example.midtermdrill

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.Configuration
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.util.Locale

// class responsible for ui elements
class UiManager(private val activity: MainActivity) {

    // This method changes the language in which the app is displayed
    fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        activity.resources.updateConfiguration(config, activity.resources.displayMetrics)

        // update current locale global var
        activity.currentLocale = language

        // Refresh the activity to apply the new locale settings
        activity.recreate()

    }

    // Helper function to toggle the visibility of the RecyclerView sections for food and drinks
    fun toggleSection(recyclerView: RecyclerView, button: MaterialButton) {
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

    // animate and apply the color changes triggered by clicking the vegan button
    fun applyColorTransition(button: MaterialButton, toggled: Boolean) {
        /*
         Get color values of button color:
          if toggled off: go from green to gray
          if toggled on: go from gray to green
         */
        val buttonColorFrom = if (toggled) ContextCompat.getColor(
            activity,
            R.color.light_gray
        ) else ContextCompat.getColor(activity, R.color.green)
        val buttonColorTo =
            if (toggled) ContextCompat.getColor(
                activity,
                R.color.green
            ) else ContextCompat.getColor(
                activity,
                R.color.light_gray
            )

        /*
          Get color values of text color:
          if toggled off: go from white to black
          if toggled on: go from black to white
        */
        val textColorFrom =
            if (toggled) ContextCompat.getColor(
                activity,
                R.color.black
            ) else ContextCompat.getColor(
                activity,
                R.color.white
            )
        val textColorTo =
            if (toggled) ContextCompat.getColor(
                activity,
                R.color.white
            ) else ContextCompat.getColor(
                activity,
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