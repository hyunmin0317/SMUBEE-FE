package com.sangmyung.sumubi

import java.io.Serializable

class Plan(
    val id: Int? = null,
    val title: String? = null,
    val content: String? = null,
    val date: String? = null,
    val course: String? = null,
    val code: String? = null,
    val category: String? = null,
    val status: String? = null,
    val checked: Boolean? = null
):Serializable