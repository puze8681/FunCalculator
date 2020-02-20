package kr.puze.calculation

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import java.text.DecimalFormat

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {
    private var isActionUp = false
    private var Arithmetic = 0
    private var ADD = 1
    private var MINUS = 2
    private var TIME = 3
    private var DEVIDE = 4
    private var PRE = 0
    private var VERTICAL = 11
    private var HORIZONTAL2 = 33
    private var SLASHLEFT = 55
    private var SLASHRIGHT = 66
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handleTouch = View.OnTouchListener { v, event ->
            v.tag
            val x = event.x.toInt()
            val y = event.y.toInt()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i("TAG", "touched down")
                    v.setBackgroundColor(Color.BLUE)
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.i("TAG", "moving: ($x, $y)")
                    when (v.tag) {
                        1 -> if (direction(event.x, event.y, v.measuredWidth, v.measuredHeight) == 5) onSlashLeft()
                        2 -> if (direction(event.x, event.y, v.measuredWidth, v.measuredHeight) == 1) onVertical()
                        3 -> if (direction(event.x, event.y, v.measuredWidth, v.measuredHeight) == 8) onSlashRight()
                        4 -> if (direction(event.x, event.y, v.measuredWidth, v.measuredHeight) == 4) onHorizontal2()
                        7 -> if (direction(event.x, event.y, v.measuredWidth, v.measuredHeight) == 4) onHorizontal3()
                        else -> {
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    isActionUp = true
                    Log.i("TAG", "touched up")
                    reset()
                    if((event.x > 0) && (event.x < v.measuredWidth) && ((event.y > 0) && (event.y < v.measuredHeight))){
                        when (v.tag) {
                            10 -> {
                                val string = resulttext.text.toString()
                                if (string != "") {
                                    when (string[string.length - 1]) {
                                        '+' -> {
                                        }
                                        '-' -> {
                                        }
                                        'x' -> {
                                        }
                                        '/' -> {
                                        }
                                        '.' -> {
                                        }
                                        else -> {
                                            resulttext.text = resulttext.text.toString() + "."
                                        }
                                    }
                                }
                            }
                            11 -> {
                                resulttext.text = ""; Arithmetic = 0
                            }
                            12 -> {
                                val string = resulttext.text.toString()
                                if (string != "") {
                                    when (string[string.length - 1]) {
                                        '+' -> Arithmetic = 0
                                        '-' -> Arithmetic = 0
                                        'x' -> Arithmetic = 0
                                        '/' -> Arithmetic = 0
                                        else -> {
                                        }
                                    }
                                    resulttext.text = string.dropLast(1)
                                }
                            }
                            13 -> {
                            }
                            else -> {
                                resulttext.text = resulttext.text.toString() + v.tag.toString()
                            }
                        }
                    }
                }
            }
            true
        }

        button0.tag = 0
        button1.tag = 1
        button2.tag = 2
        button3.tag = 3
        button4.tag = 4
        button5.tag = 5
        button6.tag = 6
        button7.tag = 7
        button8.tag = 8
        button9.tag = 9
        buttondot.tag = 10
        buttonclear.tag = 11
        buttonerase.tag = 12
        buttonrest.tag = 13
        button0.setOnTouchListener(handleTouch)
        button1.setOnTouchListener(handleTouch)
        button2.setOnTouchListener(handleTouch)
        button3.setOnTouchListener(handleTouch)
        button4.setOnTouchListener(handleTouch)
        button5.setOnTouchListener(handleTouch)
        button6.setOnTouchListener(handleTouch)
        button7.setOnTouchListener(handleTouch)
        button8.setOnTouchListener(handleTouch)
        button9.setOnTouchListener(handleTouch)
        buttondot.setOnTouchListener(handleTouch)
        buttonerase.setOnTouchListener(handleTouch)
        buttonrest.setOnTouchListener(handleTouch)
        buttonclear.setOnTouchListener(handleTouch)
    }

    fun direction(eventX: Float, eventY: Float, viewX: Int, viewY: Int): Int{
        Log.i("TAG", "direction")
        return if(((eventX > 0) && (eventX < viewX)) && eventY > (viewY * 2f)){
            Log.i("TAG", "direction : 1 두칸 아래")
            1
        }else if(((eventX > 0) && (eventX < viewX)) && eventY < (0 - viewY)){
            Log.i("TAG", "direction : 2 두칸 위")
            2
        }else if(((eventY > 0) && (eventY < viewY)) && eventX < (0 - viewX)){
            Log.i("TAG", "direction : 3 두칸 왼쪽")
            3
        }else if(((eventY > 0) && (eventY < viewY)) && eventX > (viewX * 2f)){
            Log.i("TAG", "direction : 4 두칸 오른쪽")
            4
        }else if(eventY > (viewY * 1.2f) && eventX > (viewX * 1.2f)){
            Log.i("TAG", "direction : 5 오른쪽 아래")
            5
        }else if(eventY < (0 - viewY * 0.8f) && eventX > (viewX * 1.2f)){
            Log.i("TAG", "direction : 6 오른쪽 위")
            6
        }else if(eventY < (0 - viewY * 0.8f) && eventX < (0 - viewX * 0.8f)){
            Log.i("TAG", "direction : 7 왼쪽 위")
            7
        }else if(eventY > (viewY * 1.2f) && eventX < (0 - viewX * 0.8f)){
            Log.i("TAG", "direction : 8 왼쪽 아래")
            8
        } else{
            Log.i("TAG", "direction : 0")
            0
        }
    }

    fun add() {
        if (resulttext.text != "") {
            if (Arithmetic == 0) {
                resulttext.text = resulttext.text.toString() + "+"
                Arithmetic = ADD
            } else if (Arithmetic == MINUS) {
                var string = resulttext.text.toString()
                string = string.dropLast(1) + "+"
                resulttext.text = string
                Arithmetic = ADD
            }
        }
    }

    fun minus() {
        if (resulttext.text != "") {
            if (Arithmetic == 0) {
                resulttext.text = resulttext.text.toString() + "-"
                Arithmetic = MINUS
            }
        }
    }

    fun time() {
        if (resulttext.text != "") {
            if (Arithmetic == 0) {
                resulttext.text = resulttext.text.toString() + "x"
                Arithmetic = TIME
            } else if (Arithmetic == DEVIDE) {
                var string = resulttext.text.toString()
                string = string.dropLast(1) + "x"
                resulttext.text = string
                Arithmetic = TIME
            }
        }
    }

    fun devide() {
        if (resulttext.text != "") {
            if (Arithmetic == 0) {
                resulttext.text = resulttext.text.toString() + "/"
                Arithmetic = DEVIDE
            }
        }
    }

    fun calculate() {
        var result = resulttext.text.toString()
        if (result != "") {
            if (Arithmetic != 0) {
                val nums = when (Arithmetic) {
                    ADD -> result.split("\\+".toRegex())
                    MINUS -> result.split("-".toRegex())
                    TIME -> result.split("x".toRegex())
                    DEVIDE -> result.split("/".toRegex())
                    else -> result.split("\\+".toRegex())
                }
                if (nums.size > 1) {
                    result(nums[0], nums[1])
                } else {
                    Toast.makeText(this@MainActivity, "두번쨰 수를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "계산할 부호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@MainActivity, "첫번쨰 수를 입력해주세요", Toast.LENGTH_SHORT).show()
        }

    }

    private fun result(string1: String, string2: String) {
        val dec = DecimalFormat("#.##")

        if (string1.contains(".") || string2.contains(".")) {
            var num1 = string1.toFloat()
            var num2 = string2.toFloat()
            resulttext.text = dec.format(when (Arithmetic) {
                ADD -> num1 + num2
                MINUS -> num1 - num2
                TIME -> num1 * num2
                DEVIDE -> num1 / num2
                else -> num1 + num2
            })
        } else {
            var num1 = string1.toInt()
            var num2 = string2.toInt()
            resulttext.text = dec.format(when (Arithmetic) {
                ADD -> num1 + num2
                MINUS -> num1 - num2
                TIME -> num1 * num2
                DEVIDE -> num1.toFloat() / num2.toFloat()
                else -> num1 + num2
            })
        }
        Arithmetic = 0
        PRE = 0
    }

    fun onVertical() {
        if(isActionUp){
            if (PRE == HORIZONTAL2) {
                button2.setBackgroundColor(Color.BLUE)
                button4.setBackgroundColor(Color.BLUE)
                button5.setBackgroundColor(Color.BLUE)
                button6.setBackgroundColor(Color.BLUE)
                button8.setBackgroundColor(Color.BLUE)
                PRE = 0
                add()
            } else {
                button2.setBackgroundColor(Color.BLUE)
                button5.setBackgroundColor(Color.BLUE)
                button8.setBackgroundColor(Color.BLUE)
                PRE = VERTICAL
            }
            isActionUp = false
        }
    }

    fun onHorizontal2() {
        if(isActionUp){
            if (PRE == VERTICAL) {
                button2.setBackgroundColor(Color.BLUE)
                button4.setBackgroundColor(Color.BLUE)
                button5.setBackgroundColor(Color.BLUE)
                button6.setBackgroundColor(Color.BLUE)
                button8.setBackgroundColor(Color.BLUE)
                PRE = 0
                add()
            } else {
                button4.setBackgroundColor(Color.BLUE)
                button5.setBackgroundColor(Color.BLUE)
                button6.setBackgroundColor(Color.BLUE)
                PRE = HORIZONTAL2
                minus()
            }
            isActionUp = false
        }
    }

    fun onHorizontal3() {
        if(isActionUp){
            if (PRE == HORIZONTAL2) {
                button1.setBackgroundColor(Color.BLUE)
                button2.setBackgroundColor(Color.BLUE)
                button3.setBackgroundColor(Color.BLUE)
                button7.setBackgroundColor(Color.BLUE)
                button8.setBackgroundColor(Color.BLUE)
                button9.setBackgroundColor(Color.BLUE)
                PRE = 0
                calculate()
            }
            isActionUp = false
        }
    }

    fun onSlashLeft() {
        if(isActionUp){
            if (PRE == SLASHRIGHT) {
                button1.setBackgroundColor(Color.BLUE)
                button3.setBackgroundColor(Color.BLUE)
                button5.setBackgroundColor(Color.BLUE)
                button7.setBackgroundColor(Color.BLUE)
                button9.setBackgroundColor(Color.BLUE)
                PRE = 0
                time()
            } else {
                button1.setBackgroundColor(Color.BLUE)
                button5.setBackgroundColor(Color.BLUE)
                button9.setBackgroundColor(Color.BLUE)
                PRE = SLASHLEFT
            }
            isActionUp = false
        }
    }

    private fun onSlashRight() {
        if(isActionUp){
            if (PRE == SLASHLEFT) {
                button1.setBackgroundColor(Color.BLUE)
                button3.setBackgroundColor(Color.BLUE)
                button5.setBackgroundColor(Color.BLUE)
                button7.setBackgroundColor(Color.BLUE)
                button9.setBackgroundColor(Color.BLUE)
                PRE = 0
                time()
            } else {
                button3.setBackgroundColor(Color.BLUE)
                button5.setBackgroundColor(Color.BLUE)
                button7.setBackgroundColor(Color.BLUE)
                PRE = SLASHRIGHT
                devide()
            }
            isActionUp = false
        }
    }

    private fun reset() {
        button0.setBackgroundColor(Color.BLACK)
        button1.setBackgroundColor(Color.BLACK)
        button2.setBackgroundColor(Color.BLACK)
        button3.setBackgroundColor(Color.BLACK)
        button4.setBackgroundColor(Color.BLACK)
        button5.setBackgroundColor(Color.BLACK)
        button6.setBackgroundColor(Color.BLACK)
        button7.setBackgroundColor(Color.BLACK)
        button8.setBackgroundColor(Color.BLACK)
        button9.setBackgroundColor(Color.BLACK)
        buttondot.setBackgroundColor(Color.BLACK)
        buttonrest.setBackgroundColor(Color.BLACK)
        buttonerase.setBackgroundColor(Color.BLACK)
        buttonclear.setBackgroundColor(Color.BLACK)
    }
}
