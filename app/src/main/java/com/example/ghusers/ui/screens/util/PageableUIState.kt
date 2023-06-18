package com.example.ghusers.ui.screens.util

import kotlin.math.min


abstract class PageableUIState<T>(private val itemsPerPage: Int = 10) {
    abstract val data: List<T>
    abstract val currentPage: Int
    val hasPrevPage: Boolean
        get() = currentPage > 1
    val hasNextPage: Boolean
        get() = data.size > currentPage * itemsPerPage

    fun pagedDataView() = if (data.size < itemsPerPage) data else
        data.subList(
            (currentPage - 1) * itemsPerPage,
            min(currentPage * itemsPerPage, data.size)
        )
}