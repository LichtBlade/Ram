package com.example.ram.appointment

data class DataOfPurposeCard(
    var purpose: String?=null,
    var isSelected: Boolean = false, // when radiobutton is click/selected
    var requirements: String?= null,
    var isExpandable: Boolean = false
)
