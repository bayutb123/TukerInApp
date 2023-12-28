package com.bayutb123.tukerin.ui.utils

class Currency {
    companion object {
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
    }
}