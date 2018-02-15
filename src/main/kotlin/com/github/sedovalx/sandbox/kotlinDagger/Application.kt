package com.github.sedovalx.sandbox.kotlinDagger


fun main(args: Array<String>) {
    val coffeeShop = DaggerCoffeeShop.builder().build()
    coffeeShop.maker().brew()
    println("OK!")

    val carShop = DaggerCarShop.builder().build()
    val driver = Driver("Anakin Skywalker")
    carShop.hatchback().run(driver)
    println("OK!")
    carShop.sportcar().run(driver)
    println("OK!")
}