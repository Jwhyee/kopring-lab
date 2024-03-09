package com.kopring.lab.member

import com.kopring.lab.config.launch
import com.kopring.lab.config.log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.Math.random

@SpringBootTest
class MemberSaveTest{

   @Autowired
   lateinit var repo: MemberRepository

   private fun saveData() = launch {
         val jobs = mutableListOf<Deferred<Member>>()
         for (i in 0 until 100_000) {
            val result = random()
            val mul = result * 10
            val job = async { repo.save(Member(null, mul.toInt().toString(), mul.toInt())) }
            jobs.add(job)
         }
         jobs.awaitAll()
         log.info("RESULT : ${repo.findAll().size}")
   }

   @Test
   @DisplayName("데이터 10만건 저장 - 코루틴")
   fun coroutineTest() {
      log.info("START : data init")

      runBlocking {
         saveData().join()
      }

      log.info("END : data init")
   }

   @Test
   @DisplayName("데이터 10만건 저장 - 일반")
   fun generalTest() {
      log.info("START : data init")

      for (i in 0 until 100_000) {
         val result = random()
         val mul = result * 10
         repo.save(Member(null, mul.toInt().toString(), mul.toInt()))
      }
      log.info("RESULT : ${repo.findAll().size}")

      log.info("END : data init")
   }
}