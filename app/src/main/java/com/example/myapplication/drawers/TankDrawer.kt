package com.example.myapplication.drawers

import android.view.View
import android.widget.FrameLayout
import com.example.myapplication.CELL_SIZE
import com.example.myapplication.binding
import com.example.myapplication.enums.Direction
import com.example.myapplication.models.Coordinate
import com.example.myapplication.models.Element

class TankDrawer(val container:FrameLayout) {

    fun move(myTank: View, direction: Direction,elementsOnContainer:List<Element>){
        val layoutParams =myTank.layoutParams as FrameLayout.LayoutParams
        val currentCoordinate = Coordinate(layoutParams.topMargin, layoutParams.leftMargin)
        when(direction){
            Direction.UP ->{
                myTank.rotation = 0f
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += -CELL_SIZE

            }
            Direction.DOWN ->{

                myTank.rotation = 180f
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += CELL_SIZE

            }
            Direction.LEFT ->{

                myTank.rotation = 270f
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin -= CELL_SIZE

            }
            Direction.RIGHT ->{

                myTank.rotation = 90f
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += CELL_SIZE
            }
        }

        val nextCoordinate = Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        if (checkTankCanMoveThroughBorder(
                nextCoordinate,
                myTank
            )&& checkTankCanMoveThroughMaterial(nextCoordinate, elementsOnContainer)
        ){
            binding.container.removeView(binding.myTank)
            binding.container.addView(binding.myTank)
        }else {
            (myTank.layoutParams as FrameLayout.LayoutParams).topMargin=currentCoordinate.top
            (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin=currentCoordinate.left
        }


    }

    private fun checkTankCanMoveThroughMaterial(coordinate: Coordinate,elementsOnContainer:List<Element>):Boolean{
        getTankCoordinates(coordinate).forEach{
            val element = getElementByCoordinates(it,elementsOnContainer)
            if(element!=null&&!element.material.tankCanGoThrough){
                return false
            }
        }
        return true
    }

    private fun checkTankCanMoveThroughBorder(coordinate: Coordinate,myTank: View):Boolean{
        if (coordinate.top >=0 &&
            coordinate.top + myTank.height <= binding.container.height &&
            coordinate.left >= 0 &&
            coordinate.left + myTank.width <= binding.container.width
        ){
            return true
        }
        return false
    }

    private fun getTankCoordinates(topLeftCoordinate:Coordinate):List<Coordinate>{
        val coordinateList= mutableListOf<Coordinate>()
        coordinateList.add(topLeftCoordinate)
        coordinateList.add(Coordinate(topLeftCoordinate.top+ CELL_SIZE,topLeftCoordinate.left))
        coordinateList.add(Coordinate(topLeftCoordinate.top,topLeftCoordinate.left+ CELL_SIZE))
        coordinateList.add(
            Coordinate(
                topLeftCoordinate.top+ CELL_SIZE,
                topLeftCoordinate.left+ CELL_SIZE
            )
        )
        return coordinateList
    }

    private fun getElementByCoordinates(coordinate: Coordinate, elementsOnContainer:List<Element>)=
        elementsOnContainer.firstOrNull { it.coordinate==coordinate }

}