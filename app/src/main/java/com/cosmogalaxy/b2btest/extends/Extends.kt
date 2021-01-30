package com.cosmogalaxy.b2btest.extends

fun Double.formatNum(digits: Int = 4) = String.format("%.${digits}f", this)