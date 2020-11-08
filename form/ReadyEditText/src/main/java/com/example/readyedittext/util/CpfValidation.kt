package com.example.readyedittext.util

import java.util.*

class CpfValidation {

    companion object {
        fun isAValidCPF(CPF: String): Boolean {

            if (CPF == "00000000000" ||
                CPF == "11111111111" ||
                CPF == "22222222222" ||
                CPF == "33333333333" ||
                CPF == "44444444444" ||
                CPF == "55555555555" ||
                CPF == "66666666666" ||
                CPF == "77777777777" ||
                CPF == "88888888888" ||
                CPF == "99999999999" ||
                CPF.length != 11
            ) return false

            val dig10: Char
            val dig11: Char
            var sm: Int
            var i: Int
            var r: Int
            var num: Int
            var peso: Int

            return try {
                sm = 0
                peso = 10
                i = 0
                while (i < 9) {
                    num = (CPF[i].toInt() - 48)
                    sm += num * peso
                    peso -= 1
                    i++
                }
                r = 11 - sm % 11
                dig10 =
                    if (r == 10 || r == 11) '0' else (r + 48).toChar()


                sm = 0
                peso = 11
                i = 0
                while (i < 10) {
                    num = (CPF[i].toInt() - 48)
                    sm += num * peso
                    peso -= 1
                    i++
                }
                r = 11 - sm % 11
                dig11 = if (r == 10 || r == 11) '0' else (r + 48).toChar()

                dig10 == CPF[9] && dig11 == CPF[10]
            } catch (erro: InputMismatchException) {
                false
            }
        }
    }
}