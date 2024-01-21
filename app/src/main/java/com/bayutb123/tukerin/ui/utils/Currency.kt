package com.bayutb123.tukerin.ui.utils

object Currency {
    fun convertIntToRupiah(price: Long): String {
        val priceString = price.toString()
        val result = StringBuilder()
        var counter = 0
        for (i in priceString.length - 1 downTo 0) {
            result.append(priceString[i])
            counter++
            if (counter == 3 && i != 0) {
                result.append(".")
                counter = 0
            }
        }
        return "Rp ${result.reverse()}"
    }

    fun displayLongAsRupiah(price: String): String {
        val priceString = price.replace("[^0-9]".toRegex(), "")
        val result = StringBuilder()
        var counter = 0
        for (i in priceString.length - 1 downTo 0) {
            result.append(priceString[i])
            counter++
            if (counter == 3 && i != 0) {
                result.append(".")
                counter = 0
            }
        }
        return result.reverse().toString()
    }

    fun reverseRupiahToLong(price: String): String {
        val priceString = price.replace(".", "")
        return priceString.substring(3).toLong().toString()
    }
}
