package com.example.hello_ar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.node.ModelNode



class MainActivity : AppCompatActivity() {
    private lateinit var arSceneView: CustomArSceneView
    private lateinit var modelLoader: ModelLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arSceneView = findViewById(R.id.arSceneView)
        modelLoader = arSceneView.modelLoader

        arSceneView.setOnTouchListener {view, motionEvent ->
            if(motionEvent.action == android.view.MotionEvent.ACTION_UP) {
                val frame = arSceneView.frame
                val hitResult = frame?.hitTest(motionEvent)?.firstOrNull {
                    hit-> hit.trackable is com.google.ar.core.Plane && (hit.trackable as com.google.ar.core.Plane).isPoseInPolygon(hit.hitPose)
                }
                hitResult?.let { hit ->
                    val anchor = hit.createAnchor()
                    val anchorNode = AnchorNode(engine = arSceneView.engine, anchor= anchor)

                    modelLoader.loadModelAsync(fileLocation = "models/box.glb") {
                        modelInstance -> modelInstance?.let {
                            val modelNode = ModelNode(
                                modelInstance = it.instance,
                                autoAnimate = false,
                                scaleToUnits = 0.5f,
                            )
                            anchorNode.addChildNode(modelNode)
                            arSceneView.addChildNode(anchorNode)
                        }
                    }
                }
                view.performClick()
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
