package io.daonomic.schema.json.domain

import io.daonomic.schema.json.custom.Title

data class TitleKotlinTest(
    val noTitle: String,
    @Title("This is title")
    val withTitle: String
)