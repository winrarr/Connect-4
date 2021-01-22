package connect4.model

import connect4.view.GameObserver
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread

class GuestGame(private val observer: GameObserver, ip: String) : Game {
    private val socket = Socket(ip, 1337)
    private val writer = PrintWriter(socket.getOutputStream(), true)
    private val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

    private val board: Array<Array<Player?>> = Array(7) { arrayOfNulls<Player>(6) }
    private val color: Player = Player.valueOf(reader.readLine())

    init {
        if (color == Player.RED) {
            getHostMove()
        }
    }

    override fun insertDisc(x: Int, y: Int) {
        val response = request("insertDisc $x $y")
        if (response == "ok") {
            observer.updateTileAt(x, y, color)
        }
        thread {
            getHostMove()
        }
    }

    private fun getHostMove() {
        val hostMove = reader.readLine()
        val tokens = hostMove.split(" ")
        if (tokens.size == 3 && tokens[0] == "insertDisc") {
            val x = tokens[1].toInt()
            val y = tokens[2].toInt()
            board[x][y] = color.other()
            observer.updateTileAt(x, y, color.other())
            observer.updateWinner(getWinner())
        }
    }

    override fun getWinner(): Player? = request("getWinner").let { if (it == "null") null else Player.valueOf(it) }

    private fun request(request: String): String {
        writer.println(request)
        return reader.readLine()
    }
}