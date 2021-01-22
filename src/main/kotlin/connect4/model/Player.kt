package connect4.model

enum class Player {
    YELLOW { override fun other() = RED },
    RED { override fun other() = YELLOW };

    abstract fun other(): Player
}