package connect4.controller

import connect4.model.Game
import connect4.model.GuestGame
import connect4.model.HostGame
import connect4.view.MainView
import tornadofx.*

class Client : Controller() {
    private val mainView: MainView by inject()
    lateinit var game: Game

    fun createGame() {
        game = HostGame(mainView)
    }

    fun joinGame(ip: String) {
        game = GuestGame(mainView, ip)
    }

    fun insertDisc(x: Int, y: Int) {
        if (!::game.isInitialized) return
        game.insertDisc(x, y)
    }
}