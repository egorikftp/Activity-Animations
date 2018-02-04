package com.egoriku.activityanimations

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View


class MoveViewTouchListener(view: View) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    init {
        val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
            private var mMotionDownX = 0f
            private var mMotionDownY = 0f

            override fun onDown(e: MotionEvent): Boolean {
                mMotionDownX = e.rawX - view.translationX
                mMotionDownY = e.rawY - view.translationY
                return true
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                view.translationX = e2.rawX - mMotionDownX
                view.translationY = e2.rawY - mMotionDownY
                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                view.performClick()
                return true
            }
        }

        gestureDetector = GestureDetector(view.context, mGestureListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?) = gestureDetector.onTouchEvent(event)
}