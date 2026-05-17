package dev.rijeka.rjevent

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

object EventBus {
    private val listenerMap = ConcurrentHashMap<Class<*>, CopyOnWriteArrayList<Listener<*>>>()

    fun subscribe(listener: Listener<*>) {
        val list = listenerMap.getOrPut(listener.target) { CopyOnWriteArrayList() }
        val index = list.indexOfFirst { listener.priority.value > it.priority.value }
        if (index == -1) list.add(listener)
        else list.add(index, listener)
    }

    fun unsubscribe(listener: Listener<*>) {
        listenerMap[listener.target]?.remove(listener)
    }

    fun <T : Any> post(event: T): T {
        listenerMap[event::class.java]?.forEach { it.call(event) }
        return event
    }

    fun <T : CancellableEvent> post(event: T): T {
        val listeners = listenerMap[event::class.java] ?: return event
        for (listener in listeners) {
            listener.call(event)
            if (event.cancelled) break
        }
        return event
    }

    fun isListening(klass: Class<*>): Boolean {
        return listenerMap[klass]?.isNotEmpty() ?: false
    }

    inline fun <reified T : Event> on(
        priority: EventPriority = EventPriority.MEDIUM,
        noinline handler: (T) -> Unit
    ): Listener<T> = Listener(T::class.java, priority, handler).also { subscribe(it) }
}