package dev.nars.helato

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelatoApplication

fun main(args: Array<String>) {
    runApplication<HelatoApplication>(*args)
}
