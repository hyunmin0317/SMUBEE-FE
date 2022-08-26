package com.example.sumubi

import java.io.Serializable

class Subject(
    val name: String? = null,
    val prof: String? = null,
    val code: String? = null,
    val course_status: Int? = null,
    val assign_status: Int? = null
):Serializable