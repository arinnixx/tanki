package com.example.myapplication.utils

import android.view.View
import com.example.myapplication.CELL_SIZE
import com.example.myapplication.binding
import com.example.myapplication.models.Coordinate
import com.example.myapplication.models.Element

fun View.checkViewCanMoveThroughBorder(coordinate: Coordinate):Boolean{
    return coordinate.top >=0 &&
            coordinate.top + this.height <= binding.container.height &&
            coordinate.left >= 0 &&
            coordinate.left + this.width <= binding.container.width
}

fun getElementByCoordinates(
    coordinate: Coordinate,
    elementsOnContainer:List<Element>
):Element?{
    for (element in elementsOnContainer){
        for(height in 0 until  element.height){
            for (width in 0 until element.width){
                val searchingCoordinate = Coordinate(
                    top=element.coordinate.top+height * CELL_SIZE,
                    left=element.coordinate.left+width * CELL_SIZE
                )
                if (coordinate==searchingCoordinate){
                    return element
                }
            }
        }
    }
return null
}