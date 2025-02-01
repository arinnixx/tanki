package com.example.myapplication.models

import com.example.myapplication.enums.Material

data class Element (
    val viewId:Int,
    val material: Material,
    val coordinate: Coordinate,
    val width: Int,
    val height: Int
        )
{
}