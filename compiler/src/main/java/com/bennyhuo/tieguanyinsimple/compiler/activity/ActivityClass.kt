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