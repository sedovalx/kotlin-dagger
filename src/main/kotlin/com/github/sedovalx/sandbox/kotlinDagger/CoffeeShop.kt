package com.github.sedovalx.sandbox.kotlinDagger

import dagger.*
import javax.inject.Inject
import javax.inject.Singleton


interface Pump {
    fun pump()
}

interface Heater {
    fun on()
    fun off()
    fun isHot(): Boolean
}

class ElectricHeater : Heater {
    private var heating: Boolean = false

    override fun on() {
        println("~ ~ ~ heating ~ ~ ~")
        this.heating = true
    }

    override fun off() {
        this.heating = false
    }

    override fun isHot() = heating
}

class Thermosiphon @Inject constructor(val heater: Heater) : Pump {
    override fun pump() {
        println("=> => pumping => =>")
    }
}

class CoffeeMaker @Inject constructor(val heater: Lazy<Heater>, val pump: Pump) {
    fun brew() {
        heater.get().on()
        pump.pump()
        println(" [_]P coffee! [_]P ")
        heater.get().off()
    }
}

@Module
abstract class PumpModule {
    @Binds
    abstract fun providePump(pump: Thermosiphon): Pump
}

@Module(includes = [PumpModule::class])
class DripCoffeeModule {
    @Provides
    @Singleton
    fun provideHeater(): Heater = ElectricHeater()
}

@Singleton
@Component(modules = [DripCoffeeModule::class])
interface CoffeeShop {
    fun maker(): CoffeeMaker
}