package com.example.myapplication.models

import android.view.View
import com.example.myapplication.enums.Material

data class Element constructor(
    val viewId:Int = View.generateViewId(),
    val material: Material,
    var coordinate: Coordinate,
    val width: Int,
    val height: Int
    )
