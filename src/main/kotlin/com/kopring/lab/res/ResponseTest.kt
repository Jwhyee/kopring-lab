package com.kopring.lab.res

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ResponseTest(
   private val service: ResponseService
) {
   private var RESPONSE_COUNT = 0

   @GetMapping("/test/req")
   fun response(@RequestParam("cnt") cnt: Int): String {
      service.printResponse(cnt)
      return "COMPLETE"
   }

   @GetMapping("/test/res")
   fun callTest() = ResponseEntity(RESPONSE_COUNT++, HttpStatus.OK)

}