package com.egoriku.activityanimations.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.INVISIBLE
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.egoriku.activityanimations.R
import com.egoriku.corelib_kt.dsl.afterMeasured
import com.egoriku.corelib_kt.dsl.fromApi
import com.egoriku.corelib_kt.dsl.show
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X"
        const val EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y"
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

                root_layout.afterMeasured {
                    revealActivity(revealX, revealY)
                }
            }
        } else {
            root_layout.show()
        }
    }

    private fun revealActivity(revealX: Int, revealY: Int) {
        val finalRadius = (Math.max(root_layout.width, root_layout.height) * 1.1).toFloat()

        ViewAnimationUtils.createCircularReveal(root_layout, revealX, revealY, 0f, finalRadius).apply {
            duration = 400
            interpolator = AccelerateInterpolator()

            root_layout.show()
            start()
        }
    }

    override fun onBackPressed() {
        unRevealActivity()
    }

    private fun unRevealActivity() {
        val finalRadius = (Math.max(root_layout.width, root_layout.height) * 1.1).toFloat()

        ViewAnimationUtils.createCircularReveal(root_layout, revealX, revealY, finalRadius, 0f).apply {
            duration = 400
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    root_layout.visibility = INVISIBLE
                    finishAfterTransition()
                }
            })
            start()
        }
    }
}
