package com.example.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private val sensorManager by lazy { // 초기화 지연
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private lateinit var tiltView: TiltView

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) //화면이 꺼지지 않도록
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE //화면 가로모드 고정
        super.onCreate(savedInstanceState)

        tiltView = TiltView(this)
        setContentView(tiltView)

    }

    //액티비티 동작시만 센서 사용
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    //sensor 정밀도 변경시 호출
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    //senor value 변경시 호출
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            Log.d(
                "MainActivity",
                "onSensorChanged: x :" + "${event.values[0]}, y : ${event.values[1]}, z : ${event.values[2]}"
            )
            tiltView.onSensorEvent(event)
        }
    }
}
