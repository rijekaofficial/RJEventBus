package dev.rijeka.rjevent

class Listener<T>(val target: Class<T>, val priority: EventPriority, val executor: (T) -> Unit) {

    constructor(target: Class<T>, executor: (T) -> Unit) : this(target, EventPriority.MEDIUM, executor)

    @Suppress("UNCHECKED_CAST")
    fun call(event: Any) {
        executor(event as T)
    }
}