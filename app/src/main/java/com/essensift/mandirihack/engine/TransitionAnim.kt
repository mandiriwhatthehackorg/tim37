package com.essensift.mandirihack.engine

import android.app.Activity
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet

object TransitionAnim {

    fun performTransitionAnim(activity: Activity,
                              frameId: Int,
                              supportFragmentManager: FragmentManager,
                              targetFragment: Fragment,
                              exitTransition: Transition,
                              enterTransition: Transition) {
        try {
            val previousFragment = supportFragmentManager.findFragmentById(frameId)
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            // 1. Exit for Previous Fragment
            exitTransition.duration = 300
            previousFragment?.exitTransition = exitTransition

            // 2. Shared Elements Transition
            val enterTransitionSet = TransitionSet()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                enterTransitionSet.addTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.move))
            }
            enterTransitionSet.duration = 500
            enterTransitionSet.startDelay = 300
            targetFragment.sharedElementEnterTransition = enterTransitionSet

            // 3. Enter Transition for New Fragment
            enterTransition.startDelay = 500 + 300
            enterTransition.duration = 300
            targetFragment.enterTransition = enterTransition

            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(frameId, targetFragment)
            fragmentTransaction.commit()
        } catch (e: Exception) {

        }
    }

    fun performTransitionAnim(activity: Activity,
                              frameId: Int,
                              supportFragmentManager: FragmentManager,
                              targetFragment: Fragment,
                              enterTransition: Transition) {
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            // 2. Shared Elements Transition
            val enterTransitionSet = TransitionSet()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                enterTransitionSet.addTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.move))
            }
            enterTransitionSet.duration = 300
            enterTransitionSet.startDelay = 0
            targetFragment.sharedElementEnterTransition = enterTransitionSet

            // 3. Enter Transition for New Fragment
            enterTransition.startDelay = 100
            enterTransition.duration = 300
            targetFragment.enterTransition = enterTransition

            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(frameId, targetFragment)
            fragmentTransaction.commit()
        } catch (e: Exception) {

        }
    }

    fun performTransitionAnimWithTag(activity: Activity,
                                     frameId: Int,
                                     supportFragmentManager: FragmentManager,
                                     targetFragment: Fragment,
                                     tag: String,
                                     exitTransition: Transition,
                                     enterTransition: Transition) {
        try {
            val previousFragment = supportFragmentManager.findFragmentById(frameId)
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            // 1. Exit for Previous Fragment
            exitTransition.duration = 300
            previousFragment?.exitTransition = exitTransition

            // 2. Shared Elements Transition
            val enterTransitionSet = TransitionSet()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                enterTransitionSet.addTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.move))
            }
            enterTransitionSet.duration = 500
            enterTransitionSet.startDelay = 300
            targetFragment.sharedElementEnterTransition = enterTransitionSet

            // 3. Enter Transition for New Fragment
            enterTransition.startDelay = 500 + 300
            enterTransition.duration = 300
            targetFragment.enterTransition = enterTransition

            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(frameId, targetFragment, tag)
            fragmentTransaction.commit()
        } catch (e: Exception) {

        }
    }

}