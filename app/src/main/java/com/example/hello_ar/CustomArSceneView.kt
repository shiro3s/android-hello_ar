package com.example.hello_ar

import android.content.Context
import android.util.AttributeSet
import io.github.sceneview.ar.ARSceneView

class CustomArSceneView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ARSceneView(context, attrs) {

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
