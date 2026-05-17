package dev.rijeka.rjevent

enum class EventPriority(val value: Int) {
    HIGHEST(200),
    HIGH(100),
    MEDIUM(0),
    LOW(-100),
    LOWEST(-200)
}