package com.jetbrains.handson.mpp.mobile

import platform.UIKit.UIDevice

actual fun platformName(): String {
    return "Running native iOS with kotlin " +  UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion
}
