package com.example.myrecipe

import org.junit.Test


import org.junit.Assert.assertEquals
import com.example.myrecipe.model.CookingStep
import com.example.myrecipe.serialization.parseCookingStep
import com.example.myrecipe.serialization.json

class CookingStepJsonTest {
    @Test
    fun testRoundTrip() {
        val original = CookingStep(order = 1, text = "Посолить")
        val json = original.json
        val restored = parseCookingStep(json)
        assertEquals(original, restored)


    }
}