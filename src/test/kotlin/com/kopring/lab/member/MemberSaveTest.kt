package com.kopring.lab.member

import com.kopring.lab.config.launch
import com.kopring.lab.config.log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.Math.random

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class MemberSaveTest{

   @Autowired
   lateinit var repo: MemberRepository

   val limit = 100_000

   @BeforeEach
   fun clearRepo() {
      repo.deleteAll()
   }

   private fun saveData() = launch {
         val jobs = mutableListOf<Deferred<Member>>()
         for (i in 0 until limit) {
            val result = random()
            val mul = result * 10
            val job = async { repo.save(Member(null, mul.toInt().toString(), mul.toInt())) }
            jobs.add(job)
         }
         jobs.awaitAll()
         log.info("RESULT : ${repo.findAll().size}")
   }

   @Test
   @Order(1)
   @DisplayName("데이터 저장 - 일반")
   fun generalTest() {
      log.info("START : data init")

      for (i in 0 until limit) {
         val result = random()
         val mul = result * 10
         repo.save(Member(null, mul.toInt().toString(), mul.toInt()))
      }
      log.info("RESULT : ${repo.findAll().size}")

      log.info("END : data init")
   }

   @Test
   @Order(2)
   @DisplayName("데이터 저장 - 코루틴")
   fun coroutineTest() {
      log.info("START : data init")

      runBlocking {
         saveData().join()
      }

      log.info("END : data init")
   }
}