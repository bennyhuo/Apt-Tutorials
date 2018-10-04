/*
 * Copyright (C) 2018 Bennyhuo.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package com.bennyhuo.tieguanyinsimple.compiler.activity.entity

import com.bennyhuo.aptutils.types.asJavaTypeName
import com.bennyhuo.aptutils.types.asKotlinTypeName
import com.sun.tools.javac.code.Symbol

open class Field(private val symbol: Symbol.VarSymbol): Comparable<Field>{

    val name = symbol.qualifiedName.toString()

    open val prefix = "REQUIRED_"

    val isPrivate = symbol.isPrivate

    val isPrimitive = symbol.type.isPrimitive

    override fun compareTo(other: Field): Int {
        return name.compareTo(other.name)
    }

    fun asJavaTypeName() = symbol.type.asJavaTypeName()
    open fun asKotlinTypeName() = symbol.type.asKotlinTypeName()

    override fun toString(): String {
        return "$name:${symbol.type}"
    }
}