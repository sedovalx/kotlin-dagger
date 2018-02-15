package com.github.sedovalx.sandbox.kotlinDagger

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named


interface Cabin {
    fun doorsOpen()
    fun doorsClose()
}

class HatchbackCabin : Cabin {
    override fun doorsOpen() {
        println("Hatchback doors open")
    }

    override fun doorsClose() {
        println("Hatchback doors close")
    }
}

class SportCabin() : Cabin {
    override fun doorsOpen() {
        println("Doors slide up")
    }

    override fun doorsClose() {
        println("Doors slide down")
    }
}

interface Engine {
    fun start()
    fun stop()
}

class PetrolEngine : Engine {
    override fun start() {
        println("Brrrrrr... Ready")
    }

    override fun stop() {
        println("...Stopped")
    }
}

class ElectricEngine : Engine {
    override fun start() {
        println("... Ready")
    }

    override fun stop() {
        println("... Stopped")
    }
}

interface Transmission {
    fun drive()
    fun reverse()
    fun halt()
}

class ManualTransmission : Transmission {
    override fun drive() {
        println("First gear switched on")
    }

    override fun reverse() {
        println("Reverse gear switched on")
    }

    override fun halt() {
        println("Neutral gear switched on")
    }
}

class AutoTransmission : Transmission {
    override fun drive() {
        println("Drive switched on")
    }

    override fun reverse() {
        println("Reverse switched on")
    }

    override fun halt() {
        println("Parking")
    }
}

data class Driver(val name: String)

abstract class Car(val cabin: Cabin, val engine: Engine, val transmission: Transmission) {
    fun run(driver: Driver) {
        cabin.doorsOpen()
        println("${driver.name} gets in")
        cabin.doorsClose()
        engine.start()
        transmission.drive()
        transmission.reverse()
        transmission.halt()
        engine.stop()
    }
}

class Hatchback @Inject constructor(
    @Named("hatchback") cabin: Cabin,
    @Named("electric") engine: Engine,
    @Named("auto") transmission: Transmission
): Car(cabin, engine, transmission)

class Sportcar @Inject constructor(
    @Named("sport") cabin: Cabin,
    @Named("petrol") engine: Engine,
    @Named("manual") transmission: Transmission
): Car(cabin, engine, transmission)

@Module
class CheapCarPartsModule {
    @Provides
    @Named("hatchback")
    fun provideCabin(): Cabin = HatchbackCabin()

    @Provides
    @Named("petrol")
    fun provideEngine(): Engine = PetrolEngine()

    @Provides
    @Named("manual")
    fun provideTransmission(): Transmission = ManualTransmission()
}

@Module
class SportCarPartsModule {
    @Provides
    @Named("sport")
    fun provideCabin(): Cabin = SportCabin()

    @Provides
    @Named("electric")
    fun provideEngine(): Engine = ElectricEngine()

    @Provides
    @Named("auto")
    fun provideTransmission(): Transmission = AutoTransmission()
}

@Component(modules = [CheapCarPartsModule::class, SportCarPartsModule::class])
interface CarShop {
    fun sportcar(): Sportcar
    fun hatchback(): Hatchback
}