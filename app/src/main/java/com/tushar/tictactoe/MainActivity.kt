package com.tushar.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.tushar.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var gameBoard: Array<Array<Button>>

    var Player = true
    var TOTAL_TURN = 0
    var gameStatus = Array(3) {IntArray(3)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameBoard = arrayOf(
            arrayOf(binding.button1, binding.button2, binding.button3),
            arrayOf(binding.button4, binding.button5, binding.button6),
            arrayOf(binding.button7, binding.button8, binding.button9),
        )

        for (buttonArray in gameBoard)
            for(button in buttonArray)
                button.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)

        initializeGameStatus()
    }

    private fun initializeGameStatus() {
        for(i in 0..2)
            for(j in 0..2) {
                gameStatus[i][j] = -1
                gameBoard[i][j].apply {
                    isEnabled = true
                    text = ""
                }
            }
        binding.tvPlayerTurn.text = turn(msg = "X")
    }

    private fun turn(msg: String): String {
        return "Player $msg Turn"
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            binding.button1.id -> {
                playerClick(row=0, col=0, player= Player)
            }
            binding.button2.id -> {
                playerClick(row=0, col=1, player= Player)
            }
            binding.button3.id -> {
                playerClick(row=0, col=2, player= Player)
            }
            binding.button4.id -> {
                playerClick(row=1, col=0, player= Player)
            }
            binding.button5.id -> {
                playerClick(row=1, col=1, player= Player)
            }
            binding.button6.id -> {
                playerClick(row=1, col=2, player= Player)
            }
            binding.button7.id -> {
                playerClick(row=2, col=0, player= Player)
            }
            binding.button8.id -> {
                playerClick(row=2, col=1, player= Player)
            }
            binding.button9.id -> {
                playerClick(row=2, col=2, player= Player)
            }
            binding.btnReset.id -> {
                reset()
            }
        }
    }

    fun reset() {
        Player = true
        TOTAL_TURN = 0
        initializeGameStatus()
    }

    private fun playerClick(row: Int, col: Int, player: Boolean) {
        val XO = if (player) "X" else "O"
        gameBoard[row][col].apply {
            isEnabled = false
            text = XO
        }
        gameStatus[row][col] = if(player) 1 else 0
        Player = !player
        TOTAL_TURN++
        if(TOTAL_TURN == 9)
            binding.tvPlayerTurn.text = "DRAW"
        else
            binding.tvPlayerTurn.text = if(Player) turn("X") else turn("O")
        checkWinner()
    }

    private fun checkWinner() {
        for(i in 0..2) {
            if(gameStatus[i][0] == 1 && gameStatus[i][1] == 1 && gameStatus[i][2] == 1)
            {
                winnerMessage("X")
                break
            }
            if(gameStatus[i][0] == 0 && gameStatus[i][1] == 0 && gameStatus[i][2] == 0){
                winnerMessage("0")
                break
            }
            if(gameStatus[0][i] == 1 && gameStatus[1][i] == 1 && gameStatus[2][i] == 1)
            {
                winnerMessage("X")
                break
            }
            if(gameStatus[0][i] == 0 && gameStatus[1][i] == 0 && gameStatus[2][i] == 0){
                winnerMessage("0")
                break
            }
        }
        //Diagonal Check
        if(gameStatus[0][0] == 0 && gameStatus[1][1] == 0 && gameStatus[2][2] == 0){
            winnerMessage("0")
        }
        if(gameStatus[0][0] == 1 && gameStatus[1][1] == 1 && gameStatus[2][2] == 1){
            winnerMessage("1")
        }
        if(gameStatus[0][2] == 0 && gameStatus[1][1] == 0 && gameStatus[2][0] == 0){
            winnerMessage("0")
        }
        if(gameStatus[0][2] == 1 && gameStatus[1][1] == 1 && gameStatus[2][0] == 1){
            winnerMessage("1")
        }
    }

    fun winnerMessage(msg : String) {
        binding.tvPlayerTurn.text = "Player $msg wins"
        disableGameBoardButtons()
    }

    fun disableGameBoardButtons() {
        for(buttonArray in gameBoard) {
            for (button in buttonArray) {
                button.isEnabled = false
            }
        }
    }

}

