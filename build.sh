#!/bin/sh
clear && \
    ant debug && \
    adb -s emulator-$1 install -r bin/Spuge-debug.apk && \
    adb -s emulator-$1 logcat