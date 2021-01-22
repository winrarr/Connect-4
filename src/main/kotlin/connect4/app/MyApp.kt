package connect4.app

import connect4.view.MainView
import tornadofx.*

class MyApp: App(MainView::class, Styles::class)

fun main(args: Array<String>) {
    launch<MyApp>()
}