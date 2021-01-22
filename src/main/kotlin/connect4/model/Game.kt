package connect4.model

interface Game {
    fun insertDisc(x: Int, y: Int)
    fun getWinner(): Player?
}
