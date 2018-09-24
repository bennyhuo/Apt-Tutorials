package com.bennyhuo.tieguanyinsimple.compiler.activity.entity

import com.bennyhuo.aptutils.types.asTypeMirror
import com.bennyhuo.tieguanyinsimple.annotations.Optional
import com.sun.tools.javac.code.Symbol.VarSymbol
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeKind.BOOLEAN

class OptionalField(symbol: VarSymbol): Field(symbol) {

    var defaultValue: Any? = null
        private set

    override val prefix = "OPTIONAL_"

    init {
        val optional = symbol.getAnnotation(Optional::class.java)
        when(symbol.type.kind){
            BOOLEAN -> defaultValue = optional.booleanValue
            TypeKind.BYTE, TypeKind.SHORT, TypeKind.INT, TypeKind.LONG, TypeKind.CHAR
                -> defaultValue = optional.intValue
            TypeKind.FLOAT, TypeKind.DOUBLE -> defaultValue = optional.floatValue
            else -> if(symbol.type == String::class.java.asTypeMirror()){
                defaultValue = """"${optional.stringValue}""""
            }
        }
    }

    override fun compareTo(other: Field): Int {
        return if(other is OptionalField){
            super.compareTo(other)
        }else {
            1
        }
    }

}