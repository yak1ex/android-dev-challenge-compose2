/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.media.RingtoneManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androiddevchallenge.ui.theme.MyTheme

@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {
    private val _counter = MutableLiveData(0)
    private val _active = MutableLiveData(false)
    private val counter : LiveData<Int> = _counter
    private val active : LiveData<Boolean> = _active
    private val handler = Handler(Looper.getMainLooper())
    private fun play() {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(this, uri)
        ringtone.play()
    }
    private val incrementCounter = object : Runnable {
        override fun run() {
            if(_active.value!!) {
                if (_counter.value!! > 0) {
                    _counter.value = _counter.value!! - 1
                }
                if (_counter.value!! == 0) {
                    _active.value = false
                    play()
                }
                handler.postDelayed(this, 1000)
            }
        }
    }
    private fun start() {
        if (!_active.value!!) {
            _active.value = true
            handler.postDelayed(incrementCounter, 1000)
        }
    }
    private fun pause() {
        if (_active.value!!) {
            _active.value = false
        }
    }
    private fun set(newCounter : Int) {
        if (!_active.value!!) {
            _counter.value = newCounter
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(counter, onUpdate = { set(it) }, active, onStart = { start() }, onPause = { pause() } )
            }
        }
    }
}

// Start building your app here!
@ExperimentalAnimationApi
@Composable
fun MyApp(
    counter_ : LiveData<Int> = MutableLiveData(0),
    onUpdate : (Int) -> Unit = {},
    active_ : LiveData<Boolean> = MutableLiveData(false),
    onStart : () -> Unit = {},
    onPause : () -> Unit = {}
) {
    val typography = MaterialTheme.typography
    val counter : Int by counter_.observeAsState(0)
    val active : Boolean by active_.observeAsState(false)
    Surface(color = MaterialTheme.colors.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(16.dp))
            AnimatedText(counter.toString())
            Slider(
                counter.toFloat(),
                onValueChange = { value -> onUpdate(value.toInt()) },
                enabled = !active,
                valueRange = 0f..180f,
                steps = 179
            )
            Spacer(Modifier.size(16.dp))
            Row {
                Button(
                    onClick = onStart
                ) {
                    Text(
                        "Start",
                        style = typography.h4
                    )
                }
                Spacer(Modifier.size(16.dp))
                Button(
                    onClick = onPause
                ) {
                    Text(
                        "Pause",
                        style = typography.h4
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedText(text: String) {
    val typography = MaterialTheme.typography
    var prevText by remember { mutableStateOf("") }
    var flip by remember { mutableStateOf(false) }
    flip = if (text == prevText) flip else !flip
    Crossfade(
        flip,
        animationSpec = tween(800)
    ) {
        if(it) {
            Text(
                text,
                style = typography.h2
            )
        } else {
            Text(
                text,
                style = typography.h2,
                color = Red
            )
        }
    }
    prevText = text
}

@ExperimentalAnimationApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@ExperimentalAnimationApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
