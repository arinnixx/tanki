package com.example.myapplication.drawers

import android.widget.FrameLayout
import com.example.myapplication.CELL_SIZE
import com.example.myapplication.models.Coordinate

class EnemyDrawer (private val container: FrameLayout){

    private val respawnList: List<Coordinate>

    init {
        respawnList = getRespawnList()
    }

    private fun getRespawnList(): List<Coordinate>{
        val respawnList = mutableListOf<Coordinate>()
        respawnList.add(Coordinate(0,0))
        respawnList.add(Coordinate(0,container.width/2 - CELL_SIZE))
        respawnList.add(Coordinate(0,container.width - CELL_SIZE))
        return respawnList
    }
}