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

package com.bennyhuo.tieguanyinsimple.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bennyhuo.tieguanyinsimple.annotations.Builder;
import com.bennyhuo.tieguanyinsimple.annotations.Optional;
import com.bennyhuo.tieguanyinsimple.annotations.Required;


@Builder
public class UserActivity extends Activity {

    @Required
    String name;

    @Required
    int age;

    @Optional
    String title;

    @Optional
    String company;

    @Optional
    String workPlace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ((TextView)findViewById (R.id.nameView)).setText(name);
        ((TextView)findViewById (R.id.ageView)).setText(String.valueOf(age));
        ((TextView)findViewById (R.id.titleView)).setText(title);
        ((TextView)findViewById (R.id.companyView)).setText(company);
        ((TextView)findViewById (R.id.workPlaceView)).setText(workPlace);
    }
}
