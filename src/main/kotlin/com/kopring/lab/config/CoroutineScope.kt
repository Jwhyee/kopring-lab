package com.kopring.lab.config

import kotlinx.coroutines.*

// Dispatchers.Default
//fun <T> launch(block: suspend CoroutineScope.() -> T) = runBlocking {
//   block.invoke(this)
//}

val scope = CoroutineScope(Dispatchers.Default)

fun <T> launch(block: suspend CoroutineScope.() -> T): Job {
   return scope.launch {
      block.invoke(this)
   }
}


