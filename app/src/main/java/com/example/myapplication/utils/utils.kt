package com.example.myapplication.utils

import android.view.View
import com.example.myapplication.binding
import com.example.myapplication.models.Coordinate

fun View.checkViewCanMoveThroughBorder(coordinate: Coordinate):Boolean{
    return coordinate.top >=0 &&
            coordinate.top + this.height <= binding.container.height &&
            coordinate.left >= 0 &&
            coordinate.left + this.width <= binding.container.width
}