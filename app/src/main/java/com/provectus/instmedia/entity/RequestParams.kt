package com.provectus.instmedia.entity

enum class RequestParams(val title: String) {
    ID("id"),
    USERNAME("username"),
    TIMESTAMP("timestamp"),
    MEDIA_TYPE("media_type"),
    CAPTION("caption"),
    MEDIA_URL("media_url"),
    CHILDREN("children")
}