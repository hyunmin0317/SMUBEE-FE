package com.sangmyung.sumubi

import java.io.Serializable

class Announce(
    val number: Int? = null,
    val title: String? = null,
    val pinned: Boolean? = null,
    val created_date: String? = null,
    val campus: String? = null,
    val views: Int? = null,
    val more_link: String? = null
):Serializable