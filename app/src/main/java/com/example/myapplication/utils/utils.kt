package com.example.myapplication.utils

import android.view.View
import com.example.myapplication.binding
import com.example.myapplication.models.Coordinate
import com.example.myapplication.models.Element

fun View.checkViewCanMoveThroughBorder(coordinate: Coordinate):Boolean{
    return coordinate.top >=0 &&
            coordinate.top + this.height <= binding.container.height &&
            coordinate.left >= 0 &&
            coordinate.left + this.width <= binding.container.width
}

fun getElementByCoordinates(coordinate: Coordinate, elementsOnContainer:List<Element>):Element?{

}
    elementsOnContainer.firstOrNull { it.coordinate==coordinate }