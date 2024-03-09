package com.kopring.lab.config

import com.kopring.lab.config.Logger.logger
import org.slf4j.LoggerFactory

val log = Logger.logger()

object Logger {
   inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!
}