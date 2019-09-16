package com.example.democommon.models

data class Paging(
        var page : Int = 1,
        var perPage : Int = 10,
        var reachEnd: Boolean = false
) {
    fun reset() {
        page = 1
        reachEnd = false
    }

    constructor() : this(1, 10, false)
}