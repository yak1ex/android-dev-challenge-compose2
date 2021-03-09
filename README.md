# Tiny Simple Countdown Timer

![Workflow result](https://github.com/yak1ex/android-dev-challenge-compose2/workflows/Check/badge.svg)

## :scroll: Description

An almost minimal countdown timer with crossfade, for "Android Dev Challenge: Week 2 - Countdown timer".

## :bulb: Motivation and Context

Although this is my first, series, Kotlin and Jetpack Compose products, I don't, or can't, use `View::invalidate()` and `Timer` for countdown in order to follow Compose data flow model. I used `Handler` and `LiveData`. Probably, it is better to separate `Model` from `Activity`, though.

## :camera_flash: Screenshots

<img src="/results/screenshot_1.png" width="260">&emsp;<img src="/results/screenshot_2.png" width="260"><br>
<video><source src="/results/movie.mp4" type="video/mp4"></video>

## License
```
Copyright 2020 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```