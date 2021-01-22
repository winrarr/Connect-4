package connect4.view

import connect4.model.Player

interface GameObserver {
    fun updateTileAt(x: Int, y: Int, color: Player)
    fun updateWinner(player: Player?)
}
