package com.example.myprofile.common

interface Symbols{

    fun setSymbol(course: String): String {

        return when (course) {
            CourseSymbols.GEL.name -> CourseSymbols.GEL.symbol
            CourseSymbols.USD.name -> CourseSymbols.USD.symbol
            CourseSymbols.EUR.name -> CourseSymbols.EUR.symbol
            else -> CourseSymbols.RUB.symbol
        }
    }
}