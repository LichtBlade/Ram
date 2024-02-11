package com.example.ram.appointment

data class DataOfPurposeCard(
    val purpose: String?=null,
    val isSelected: Boolean?= null,
    val requirements: String?= null,
    var isExpandable: Boolean = false
)
