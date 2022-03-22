package com.application.to_docompose.util

enum class Action {
    ADD, DELETE, UPDATE, DELETE_ALL, UNDO, NO_ACTION
}

fun String?.toAction(): Action {
    return when {
        this == "DELETE" -> Action.DELETE
        this == "ADD" -> Action.ADD
        this == "UPDATE" -> Action.UPDATE
        this == "UNDO" -> Action.UNDO
        this == "DELETE_ALL" -> Action.DELETE_ALL
        else -> Action.NO_ACTION
    }
}