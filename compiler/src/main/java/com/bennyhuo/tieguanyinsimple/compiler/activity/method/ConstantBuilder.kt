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

package com.bennyhuo.tieguanyinsimple.compiler.activity.method

import com.bennyhuo.tieguanyinsimple.compiler.activity.ActivityClass
import com.bennyhuo.tieguanyinsimple.compiler.utils.camelToUnderline
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier.*

class ConstantBuilder(private val activityClass: ActivityClass) {

    //public static final String REQUIRED_WORK_PLACE = "workPlace"
    fun build(typeBuilder: TypeSpec.Builder) {
        activityClass.fields.forEach { field ->
            typeBuilder.addField(FieldSpec.builder(String::class.java, field.prefix + field.name.camelToUnderline().toUpperCase(),
                    PUBLIC, STATIC, FINAL).initializer("\$S", field.name)
                    .build())
        }
    }

}