package com.example.ram.appointment

data class DataOfPurposeCard(
    var purpose: String?=null,
    var isSelected: Boolean, // when radiobutton is click/selected
    var requirements: String?= null,
    var isExpandable: Boolean = false,
    var arrowSign:String? = null
)
