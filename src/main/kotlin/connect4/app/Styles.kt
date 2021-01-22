package connect4.app

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val bg by cssclass()
        val line by cssclass()
        val tile by cssclass()
    }

    init {
        bg {
            backgroundColor += Color.rgb(1, 74, 137)
            padding = box(20.px)
        }
        line {
            spacing = 10.px
        }
        tile {
            stroke = Color.rgb(0, 41, 77)
            strokeWidth = 3.px
        }
    }
}