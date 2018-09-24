package com.bennyhuo.tieguanyinsimple.compiler.activity

import com.bennyhuo.aptutils.types.packageName
import com.bennyhuo.aptutils.types.simpleName
import com.bennyhuo.tieguanyinsimple.compiler.activity.entity.Field
import java.util.*
import javax.lang.model.element.Modifier.ABSTRACT
import javax.lang.model.element.TypeElement

class ActivityClass(val typeElement: TypeElement) {

    val simpleName: String = typeElement.simpleName()
    val packageName: String = typeElement.packageName()

    val fields = TreeSet<Field>()

    val isAbstract = typeElement.modifiers.contains(ABSTRACT)

    val builder = ActivityClassBuilder(this)

    val isKotlin = typeElement.getAnnotation(META_DATA) != null

    override fun toString(): String {
        return "$packageName.$simpleName[${fields.joinToString()}]"
    }

    companion object {
        val META_DATA = Class.forName("kotlin.Metadata") as Class<Annotation>
    }
}