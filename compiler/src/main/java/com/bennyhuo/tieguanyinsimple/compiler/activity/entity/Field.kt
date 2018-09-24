package com.bennyhuo.tieguanyinsimple.compiler.activity.entity

import com.sun.tools.javac.code.Symbol

open class Field(private val symbol: Symbol.VarSymbol): Comparable<Field>{

    val name = symbol.qualifiedName.toString()

    open val prefix = "REQUIRED_"

    val isPrivate = symbol.isPrivate

    val isPrimitive = symbol.type.isPrimitive

    override fun compareTo(other: Field): Int {
        return name.compareTo(other.name)
    }

    override fun toString(): String {
        return "$name:${symbol.type}"
    }
}