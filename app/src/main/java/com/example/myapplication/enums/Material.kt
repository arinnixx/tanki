package com.example.myapplication.enums

enum class Material(val tankCanGoThrough:Boolean) {
    EMPTY(true),
    BRICK(false),
    CONCRETE(false),
    GRASS(true),
}