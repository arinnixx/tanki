package com.example.myapplication

import android.graphics.text.MeasuredText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.example.myapplication.enums.Direction.UP
import com.example.myapplication.enums.Direction.DOWN
import com.example.myapplication.enums.Direction.LEFT
import com.example.myapplication.enums.Direction.RIGHT
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.drawers.*
import com.example.myapplication.enums.Direction
import com.example.myapplication.enums.Material
import com.example.myapplication.enums.Material.PLAYER_TANK
import com.example.myapplication.models.Coordinate
import com.example.myapplication.models.Element
import com.example.myapplication.models.Tank
import com.example.myapplication.enums.Material.EAGLE

const val CELL_SIZE=50

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var editMode = false

    private lateinit var playerTank:Tank
    private lateinit var eagle:Element

    private fun createTank(elementWidth:Int,elementHeight:Int):Tank{
        playerTank = Tank(
            Element(
                material = PLAYER_TANK,
                coordinate = getPlayerTankCoordinate(elementWidth,elementHeight)
            ),UP,
            BulletDrawer(binding.container,elementsDrawer.elementsOnContainer)
        )
        return playerTank
    }



    private fun createEagle(elementWidth: Int,elementHeight: Int):Element{
        eagle= Element(
            material = EAGLE,
            coordinate = getEagleCoordinate(elementWidth,elementHeight)
        )
        return eagle
    }

    private fun getPlayerTankCoordinate(width:Int,height:Int)=Coordinate(
        top = (height-height % 2)
            -(height-height % 2) % CELL_SIZE
            -PLAYER_TANK.height * CELL_SIZE,
        left = (width-width % (2 * CELL_SIZE))/2
            -EAGLE.width/2 * CELL_SIZE
            -PLAYER_TANK.width * CELL_SIZE
    )

    private fun getEagleCoordinate(width: Int,height: Int)=Coordinate(
        top = (height-height % 2)
                -(height-height % 2) % CELL_SIZE
                -EAGLE.height * CELL_SIZE,
        left = (width-width % (2 * CELL_SIZE))/2
                -EAGLE.width/2 * CELL_SIZE
    )

    private val gridDrawer by lazy {
        GridDrawer(binding.container)
    }


    private val elementsDrawer by lazy {
        ElementsDrawer(binding.container)
    }


    private val levelStorage by lazy {
        LevelStorage(this)
    }

    private val enemyDrawer by lazy {
        EnemyDrawer(binding.container,elementsDrawer.elementsOnContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Menu"

        binding.editorClear.setOnClickListener { elementsDrawer.currentMaterial=Material.EMPTY }
        binding.editorBrick.setOnClickListener { elementsDrawer.currentMaterial=Material.BRICK }
        binding.editorConcrete.setOnClickListener { elementsDrawer.currentMaterial=Material.CONCRETE }
        binding.editorGrass.setOnClickListener { elementsDrawer.currentMaterial=Material.GRASS }



        binding.container.setOnTouchListener { _, event ->
            if (!editMode){
                return@setOnTouchListener true
            }
            elementsDrawer.onTouchContainer(event.x, event.y)
        return@setOnTouchListener true
        }
        elementsDrawer.drawElementsList(levelStorage.loadLevel())
        hideSettings()
        countWidthHeight()

    }

    private fun countWidthHeight(){
        val frameLayout = binding.container
        frameLayout.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    frameLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val elementWidth=frameLayout.width
                    val elementHeight=frameLayout.height

                    playerTank=createTank(elementWidth,elementHeight)
                    eagle=createEagle(elementWidth,elementHeight)

                    elementsDrawer.drawElementsList(listOf(playerTank.element,eagle))
                }
            })
    }

    private fun switchEditMode(){
        editMode = !editMode
        if(editMode) {
            showSettings()
        } else {
            hideSettings()
        }

    }

    private fun showSettings(){
        gridDrawer.drawGrid()
        binding.materialsContainer.visibility = VISIBLE
    }

    private fun hideSettings(){
        gridDrawer.removeGrid()
        binding.materialsContainer.visibility = INVISIBLE
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_settings -> {
                gridDrawer.drawGrid()
                switchEditMode()
                return true
            }

            R.id.menu_save -> {
                levelStorage.saveLevel(elementsDrawer.elementsOnContainer)
                return true
            }

            R.id.menu_play ->{
                startTheGame()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startTheGame(){
        if(editMode){
            return
        }
        enemyDrawer.startEnemyCreation()
        enemyDrawer.moveEnemyTank()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode)
        {
            KEYCODE_DPAD_UP ->move(UP)
            KEYCODE_DPAD_DOWN -> move(DOWN)
            KEYCODE_DPAD_LEFT -> move(LEFT)
            KEYCODE_DPAD_RIGHT -> move(RIGHT)
            KEYCODE_SPACE -> playerTank.bulletDrawer.makeBulletMove(
                playerTank
            )
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun move(direction: Direction){
        playerTank.move(direction, binding.container,elementsDrawer.elementsOnContainer)
    }







}