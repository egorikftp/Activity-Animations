package com.egoriku.activityanimations.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.egoriku.activityanimations.MoveViewTouchListener
import com.egoriku.activityanimations.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goButton.apply {
            setOnClickListener(::openActivityWithAnimation)
            setOnTouchListener(MoveViewTouchListener(this))
        }
    }

    private fun openActivityWithAnimation(view: View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, view, "transition")

        val revealX = (view.x + view.width / 2).toInt()
        val revealY = (view.y + view.height / 2).toInt()

        ActivityCompat.startActivity(
                this,
                Intent(this, SecondActivity::class.java).apply {
                    putExtra(SecondActivity.EXTRA_CIRCULAR_REVEAL_X, revealX)
                    putExtra(SecondActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY)
                },
                options.toBundle()
        )
    }
}