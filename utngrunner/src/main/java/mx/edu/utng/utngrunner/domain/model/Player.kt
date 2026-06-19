package mx.edu.utng.utngrunner.domain.model

data class Player(
    val x: Float = 40f,
    val y: Float = FLOOR_Y,
    val velocityY: Float = 0f,
    val isJumping: Boolean = false,
    val isSliding: Boolean = false,
    val slideFrames: Int = 0,
    val isInvincible: Boolean = false,
    val invincibleFrames: Int = 0
) {
    companion object {
        const val FLOOR_Y = 340f
        const val GRAVITY = 1.2f
        const val JUMP_VELOCITY = -18f
        const val SLIDE_DURATION = 45
        const val INVINCIBLE_FRAMES = 90
    }
}