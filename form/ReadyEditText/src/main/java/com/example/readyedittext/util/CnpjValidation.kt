package com.example.readyedittext.util

import java.util.*

class CnpjValidation {

    companion object {
        fun isACNPJValid(CNPJ: String): Boolean {
            if (CNPJ == "00000000000000" ||
                CNPJ == "11111111111111" ||
                CNPJ == "22222222222222" ||
                CNPJ == "33333333333333" ||
                CNPJ == "44444444444444" ||
                CNPJ == "55555555555555" ||
                CNPJ == "66666666666666" ||
                CNPJ == "77777777777777" ||
                CNPJ == "88888888888888" ||
                CNPJ == "99999999999999" ||
                CNPJ.length != 14
            ) return false
            val dig13: Char
            val dig14: Char
            var sm: Int
            var i: Int
            var r: Int
            var num: Int
            var peso: Int

            return try {
                sm = 0
                peso = 2
                i = 11
                while (i >= 0) {

                    num = (CNPJ[i].toInt() - 48)
                    sm += num * peso
                    peso += 1
                    if (peso == 10) peso = 2
                    i--
                }
                r = sm % 11
                dig13 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()

                sm = 0
                peso = 2
                i = 12
                while (i >= 0) {
                    num = (CNPJ[i].toInt() - 48)
                    sm += num * peso
                    peso += 1
                    if (peso == 10) peso = 2
                    i--
                }
                r = sm % 11
                dig14 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()

                dig13 == CNPJ[12] && dig14 == CNPJ[13]
            } catch (erro: InputMismatchException) {
                false
            }
        }
    }
}