package com.example.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var playerOneScore: TextView? = null
    private var playerTwoScore: TextView? = null
    private var playerStatus: TextView? = null
    private val buttons = arrayOfNulls<Button>(9)
    private var resetGame: Button? = null
    private var playerOneScoreCount = 0
    private var playerTwoScoreCount = 0
    private var runtCount = 0
    var activePlayer = false
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    var winningPosition = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerOneScore = findViewById(R.id.playerOneScore) as TextView
        playerTwoScore = findViewById(R.id.playerTwoScore) as TextView
        playerStatus = findViewById(R.id.playerStatus) as TextView
        resetGame = findViewById(R.id.resetButton) as Button
        buttons[0] = findViewById(R.id.button1) as Button
        buttons[1] = findViewById(R.id.button2) as Button
        buttons[2] = findViewById(R.id.button3) as Button
        buttons[3] = findViewById(R.id.button4) as Button
        buttons[4] = findViewById(R.id.button5) as Button
        buttons[5] = findViewById(R.id.button6) as Button
        buttons[6] = findViewById(R.id.button7) as Button
        buttons[7] = findViewById(R.id.button8) as Button
        buttons[8] = findViewById(R.id.button9) as Button
        for (i in buttons.indices) {
            buttons[i]!!.setOnClickListener(this)
        }
        runtCount = 0
        playerOneScoreCount = 0
        playerTwoScoreCount = 0
        activePlayer = true
    }

    override fun onClick(v: View) {
        //Log.i("test","button is clicked");
        if ((v as Button).text.toString() != "") {
            return
        }
        val buttonID = v.getResources().getResourceEntryName(v.getId())
        val gameStatePointer = buttonID.substring(buttonID.length - 1, buttonID.length).toInt()
        if (activePlayer) {
            v.text = "X"
            v.setTextColor(Color.parseColor("#1F09F3"))
            gameState[gameStatePointer] = 0
        } else {
            v.text = "O"
            v.setTextColor(Color.parseColor("#09F3F1"))
            gameState[gameStatePointer] = 1
        }
        runtCount++
        if (checkWinner()) {
            if (activePlayer) {
                playerOneScoreCount++
                updatePlayerScore()
                Toast.makeText(this, "Player One Won !", Toast.LENGTH_SHORT).show()
                playAgain()
            } else {
                playerTwoScoreCount++
                updatePlayerScore()
                Toast.makeText(this, "Player Two Won !", Toast.LENGTH_SHORT).show()
                playAgain()
            }
        } else if (runtCount == 9) {
            playAgain()
            Toast.makeText(this, "No Winner !", Toast.LENGTH_SHORT).show()
        } else {
            activePlayer = !activePlayer
        }
        if (playerOneScoreCount > playerTwoScoreCount) {
            playerStatus!!.text = "Player One is Winning"
        } else if (playerTwoScoreCount > playerOneScoreCount) {
            playerStatus!!.text = "Player Two is Winning"
        } else {
            playerStatus!!.text = ""
        }
        resetGame!!.setOnClickListener {
            playAgain()
            playerOneScoreCount = 0
            playerTwoScoreCount = 0
            playerStatus!!.text = ""
            updatePlayerScore()
        }
    }

    fun checkWinner(): Boolean {
        var winnerResult = false
        for (winningPosition in winningPosition) {
            if (gameState[winningPosition[0]] ==
                gameState[winningPosition[1]] && gameState[winningPosition[1]] ==
                gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2
            ) {
                winnerResult = true
            }
        }
        return winnerResult
    }

    fun updatePlayerScore() {
        playerOneScore!!.text = Integer.toString(playerOneScoreCount)
        playerTwoScore!!.text = Integer.toString(playerTwoScoreCount)
    }

    fun playAgain() {
        runtCount = 0
        activePlayer = true
        for (i in buttons.indices) {
            gameState[i] = 2
            buttons[i]!!.text = ""
        }
    }
}