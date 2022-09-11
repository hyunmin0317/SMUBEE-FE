package com.smu.sumubee

import java.io.Serializable

class Data(
    val title: String? = null,
    val category: String? = null,
    val date: String? = null,
    val status: String? = null,
    val checked: Boolean? = null
):Serializable