package connect4.view

import connect4.controller.Client
import connect4.model.Player
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.*

class MainView : View("Connect Four"), GameObserver {
    private val client: Client by inject()

    private val board: Array<Array<Circle?>> = Array(7) { arrayOfNulls<Circle>(7) }

    override val root = borderpane {
        top {
            menubar {
                menu("Game") {
                    item("Create").action { client.createGame() }
                    item("Join").action { client.joinGame("localhost") }
                }
            }
        }
        bottom {
            hbox {
                addClass("bg", "line")
                for (x in 0 until 7) {
                    vbox {
                        addClass("line")
                        for (y in 0 until 6) {
                            circle(radius = 50) {
                                addClass("tile")
                                setOnMouseClicked { client.insertDisc(x, y) }
                                fill = Color.rgb(42, 46, 50)
                                board[x][y] = this
                            }
                        }
                    }
                }
            }
        }
    }

    override fun updateTileAt(x: Int, y: Int, color: Player) {
        println("$x $y $color")
        board[x][y]!!.fill = if (color == Player.YELLOW) Color.YELLOW else Color.RED
    }

    override fun updateWinner(player: Player?) {
        println(player)
    }
}