package com.example.myapplication.drawers

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.myapplication.CELL_SIZE
import com.example.myapplication.enums.Material
import com.example.myapplication.models.Coordinate
import com.example.myapplication.models.Element
import com.example.myapplication.utils.drawElement
import com.example.myapplication.utils.getElementByCoordinates




class ElementsDrawer(val container: FrameLayout) {
    var currentMaterial = Material.EMPTY
     val elementsOnContainer= mutableListOf<Element>()

    fun onTouchContainer(x:Float,y:Float){
        val topMargin=y.toInt()-(y.toInt() % CELL_SIZE)
        val leftMargin=x.toInt()-(x.toInt() % CELL_SIZE)
        val coordinate = Coordinate(topMargin,leftMargin)
        if(currentMaterial == Material.EMPTY){
            eraseView(coordinate)
        } else {
            drawOrReplaceView(coordinate)
        }
    }


    private fun drawOrReplaceView(coordinate: Coordinate){
        val viewOnCoordinate = getElementByCoordinates(coordinate, elementsOnContainer)
        if (viewOnCoordinate==null){
            drawView(coordinate)
            return
        }
        if (viewOnCoordinate.material != currentMaterial){
            replaceView(coordinate)
        }
    }

    fun drawElementsList(elements: List<Element>?){
        if(elements == null){
            return
        }
        for(element in elements){
            currentMaterial = element.material
            drawView(element.coordinate)
        }
    }

    private fun replaceView(coordinate: Coordinate){
        eraseView(coordinate)
        drawView(coordinate)
    }

    private fun eraseView(coordinate: Coordinate){
        removeElement(getElementByCoordinates(coordinate,elementsOnContainer))
        for (element in getElementsUnderCurrentCoordinate(coordinate)){
            removeElement(element)
        }
    }

    private fun removeElement(element: Element?){
        if(element != null){
            val erasingView = container.findViewById<View>(element.viewId)
            container.removeView(erasingView)
            elementsOnContainer.remove(element)
        }
    }

    private fun getElementsUnderCurrentCoordinate(coordinate: Coordinate):List<Element>{
        val elements = mutableListOf<Element>()
        for (element in elementsOnContainer){
            for (height in 0 until currentMaterial.height){
                for (width in 0 until currentMaterial.width){
                    if (element.coordinate == Coordinate(
                            coordinate.top + height * CELL_SIZE,
                            coordinate.left + width * CELL_SIZE
                    )
                    ){
                        elements.add(element)
                    }
                }
            }
        }
        return elements
    }

    private fun removeUnwantedInstances(){
        if(currentMaterial.elementsAmountOnScreen != 0){
            val erasingElements = elementsOnContainer.filter { it.material == currentMaterial }
            if (erasingElements.size >= currentMaterial.elementsAmountOnScreen){
                eraseView(erasingElements[0].coordinate)
            }
        }
    }



    private fun drawView(coordinate: Coordinate){
        removeUnwantedInstances()
        val element = Element(
            material = currentMaterial,
            coordinate = coordinate,
            width = currentMaterial.width,
            height = currentMaterial.height
        )
        element.drawElement(container)
        elementsOnContainer.add(element)

    }


}



