package com.jaus.albertogiunta.giftimewaster.utils

object Sources {

    private val genAdjs = listOf("cute", "fun", "funny", "adorable", "lovely", "sweet", "")

    private val catAdjs = mutableListOf<String>()
    private val catSyn = listOf("cat", "kitten")
    private var catMix = mutableListOf<String>()

    private val pandaAdjs = mutableListOf("eat", "cub")
    private val pandaSyn = listOf("panda")
    private var pandaMix = mutableListOf<String>()

    private val dogAdjs = mutableListOf("puppy")
    private val dogSyn = listOf("dog")
    private var dogMix = mutableListOf<String>()

    fun initialize() {

        if (!catMix.isEmpty() || !pandaMix.isEmpty() || !dogMix.isEmpty()) return

        dogAdjs.addAll(genAdjs)
        catAdjs.addAll(genAdjs)
        pandaAdjs.addAll(genAdjs)

        buildCatMix()
        buildDogMix()
        buildPandaMix()
    }

    fun newTagCombination(): String = when ("panda") {
        "cat" -> buildCombination(catMix)
        "dog" -> buildCombination(dogMix)
        "panda" -> buildCombination(pandaMix)
        else -> buildCombination(catMix)
    }

    fun stringToTags(str: String): String {
        val hash = " #"
        val separator = "-"
        return "$hash${str.replace(separator, hash)}"
    }

    private fun buildCombination(mix: List<String>): String {
        if (mix.isEmpty()) throw Exception("Sources is not initialized")
        else return mix.random()
    }

    private fun buildCatMix() {
        catMix = catSyn.permuteWith(catAdjs)
        catMix.add("kitten-gorgeous")
    }

    private fun buildPandaMix() {
        pandaMix = pandaSyn.permuteWith(pandaAdjs)
    }

    private fun buildDogMix() {
        dogMix = dogSyn.permuteWith(dogAdjs)
    }
}

fun List<String>.random(): String = this[(Math.random() * this.size).toInt()]

fun List<String>.permuteWith(permutee: List<String>): MutableList<String> {
    val mix = mutableListOf<String>()
    this.forEach { p1 -> permutee.forEach { p2 -> if (p2.isBlank()) mix.add(p1) else mix.add("$p1-$p2") } }
    return mix
}