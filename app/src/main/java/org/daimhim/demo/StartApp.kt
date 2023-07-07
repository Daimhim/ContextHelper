package org.daimhim.demo

import android.app.Application
import org.daimhim.container.ContextHelper

class StartApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ContextHelper.init(this)
    }
}