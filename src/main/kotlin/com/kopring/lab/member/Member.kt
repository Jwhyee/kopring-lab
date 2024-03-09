package com.kopring.lab.member

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Long? = null,
   val name: String,
   val age: Int
) {
}