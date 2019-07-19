package com.essensift.mandirihack.engine

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

object HideShowAnim {

    fun showViewSlide(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 0.0f
        view.animate()
            .translationY(0F)
            .alpha(0.6F)
            .setListener(null)
    }

    fun hideViewSlide(view: View) {
        view.animate()
            .translationY(view.height.toFloat())
            .alpha(0.0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.GONE
                }
            })
    }

    fun showViewSlide(view: View, alpha: Float) {
        view.visibility = View.VISIBLE
        view.alpha = 0.0f
        view.animate()
            .translationY(0F)
            .alpha(alpha)
            .setListener(null)
    }

    fun showViewFade(view: View, alpha: Float) {
        view.visibility = View.VISIBLE
        view.alpha = 0.0f
        view.animate()
            .alpha(alpha)
            .setListener(null)
    }

    fun hideViewFade(view: View) {
        view.animate()
            .alpha(0.0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.GONE
                }
            })
    }
}