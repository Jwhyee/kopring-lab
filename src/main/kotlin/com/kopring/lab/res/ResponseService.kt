package com.kopring.lab.res

import com.kopring.lab.config.log
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class ResponseService {
   private val webClient = WebClient.builder()
      .baseUrl("http://localhost:8080")
      .build()

   fun printResponse(cnt: Int) {
      Flux.range(0, cnt)
         .parallel(cnt)
         .runOn(Schedulers.newParallel("REQ", cnt))
         .flatMap { v -> getResponse() }
         .subscribe { r -> log.info("RES : {}", r) }
   }

   fun getResponse() = webClient.get()
      .uri("/test/res")
      .accept(MediaType.ALL)
      .retrieve()
      .bodyToMono(Int::class.java)
}