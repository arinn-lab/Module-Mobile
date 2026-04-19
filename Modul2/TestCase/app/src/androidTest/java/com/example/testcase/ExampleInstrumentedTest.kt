package com.example.nullsafetytest

import org.junit.Assert.assertEquals
import org.junit.Test

class NilaiMahasiswaTest {

    @Test
    fun testNilaiKosong() {
        val input = ""
        val nilai = input.toDoubleOrNull() ?: 0.0
        assertEquals(0.0, nilai, 0.0)
    }

    @Test
    fun testNilaiHuruf() {
        val input = "abc"
        val nilai = input.toDoubleOrNull() ?: 0.0
        assertEquals(0.0, nilai, 0.0)
    }

    @Test
    fun testNilaiValid() {
        val input = "85"
        val nilai = input.toDoubleOrNull() ?: 0.0
        assertEquals(85.0, nilai, 0.0)
    }

    @Test
    fun testNilaiDiatasSeratus() {
        val input = "110"
        val nilai = input.toDoubleOrNull() ?: 0.0
        val hasil = if (nilai > 100) 100.0 else nilai
        assertEquals(100.0, hasil, 0.0)
    }
}