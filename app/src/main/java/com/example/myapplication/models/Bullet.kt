package com.example.myapplication.models


import android.view.View
import com.example.myapplication.enums.Direction

data class Bullet (
    val view:View,
    val direction: Direction,
    val tank:Tank,
    var canMoveFurther:Boolean=true
)
