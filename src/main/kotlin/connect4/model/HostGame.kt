package connect4.model

import connect4.view.GameObserver
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.util.*
import kotlin.concurrent.thread

class HostGame(private val observer: GameObserver) : Game {
    private val server = ServerSocket(1337)
    private val client = server.accept()
    private val writer = PrintWriter(client.getOutputStream(), true)
    private val reader = BufferedReader(InputStreamReader(client.getInputStream()))

    private val board: Array<Array<Player?>> = Array(7) { arrayOfNulls<Player>(6) }
    private val color = Player.YELLOW // Player.values().let { it[Random().nextInt(it.size - 1)] }
    private val playerInTurn = Player.YELLOW
    @get:JvmName("getWinnerJvm") var winner: Player? = null

    init {
        writer.println(color.other())
        println("Sent color")
    }

    override fun insertDisc(x: Int, y: Int) {
        println("Insert disc")
        if (playerInTurn != color) return
        if (board[x][y] != null) return

        board[x][y] = color
        writer.write("$x $y")
        checkWinner()
        observer.updateTileAt(x, y, color)
        observer.updateWinner(winner)

        thread {
            val opponentMove = reader.readLine()
            val tokens = opponentMove.split(" ")

            when {
                tokens.size == 3 && tokens[0] == "insertDisc" -> {
                    val x = tokens[1].toInt()
                    val y = tokens[2].toInt()
                    board[x][y] = color.other()
                    checkWinner()
                    observer.updateTileAt(x, y, color.other())
                    observer.updateWinner(getWinner())
                    writer.write("ok")
                }
                tokens.size == 1 && tokens[0] == "getWinner" -> {
                    writer.write(winner.toString())
                }
            }
        }
    }

    override fun getWinner(): Player? {
        return winner
    }

    private fun checkWinner() {
        for (x in 0 until 7) {
            for (y in 0 until 6) {
                val player = board[x][y] ?: continue
                if (y < 4
                    && player == board[x][y + 1]
                    && player == board[x][y + 2]
                    && player == board[x][y + 3]
                ) winner = player
                if (x < 3) {
                    if (player == board[x + 1][y]
                        && player == board[x + 2][y]
                        && player == board[x + 3][y]
                    ) winner = player
                    if (y < 4
                        && player == board[x + 1][y + 1]
                        && player == board[x + 2][y + 2]
                        && player == board[x + 3][y + 3]
                    ) winner = player
                    if (y >= 3
                        && player == board[x + 1][y - 1]
                        && player == board[x + 2][y - 2]
                        && player == board[x + 3][y - 3]
                    ) winner = player
                }
            }
        }
    }
}