package com.egoriku.activityanimations.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.egoriku.activityanimations.R
import com.egoriku.corelib_kt.dsl.afterMeasured
import com.egoriku.corelib_kt.dsl.fromApi
import com.egoriku.corelib_kt.dsl.invisible
import com.egoriku.corelib_kt.dsl.show
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    companion object ExtraConstants {
        const val EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X"
        const val EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y"

        const val ANIMATION_DURATION = 400L
    }

    private var revealX: Int = 0
    private var revealY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        if (savedInstanceState == null) {
            fromApi(Build.VERSION_CODES.LOLLIPOP, true) {
                revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
                revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)

                rootLayout.afterMeasured {
                    revealActivity(revealX, revealY)
                }
            }
        } else {
            rootLayout.show()
        }
    }

    private fun revealActivity(revealX: Int, revealY: Int) {
        val finalRadius = (Math.max(rootLayout.width, rootLayout.height) * 1.1).toFloat()

        ViewAnimationUtils.createCircularReveal(rootLayout, revealX, revealY, 0f, finalRadius).apply {
            duration = ANIMATION_DURATION
            interpolator = AccelerateInterpolator()

            rootLayout.show()
            start()
        }
    }

    override fun onBackPressed() {
        unRevealActivity()
    }

    private fun unRevealActivity() {
        val finalRadius = (Math.max(rootLayout.width, rootLayout.height) * 1.1).toFloat()

        ViewAnimationUtils.createCircularReveal(rootLayout, revealX, revealY, finalRadius, 0f).apply {
            duration = ANIMATION_DURATION
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    rootLayout.invisible()
                    finish()
                }
            })
            start()
        }
    }
}
