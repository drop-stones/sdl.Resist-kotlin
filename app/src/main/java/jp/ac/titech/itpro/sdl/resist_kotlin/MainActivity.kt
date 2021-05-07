package jp.ac.titech.itpro.sdl.resist_kotlin

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(), SensorEventListener {
    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var rotationView: RotationView
    private lateinit var manager: SensorManager
    private lateinit var gyroscope: Sensor

    private val Nano2Sec: Float = 1.0f / 1000000000.0f
    private var omegaZ: Double = 0.0
    private var prevTimestamp: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rotationView = findViewById(R.id.rotation_view)

        manager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (manager == null) {
            Toast.makeText(this, R.string.toast_no_sensor_manager, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        gyroscope = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroscope == null) {
            Toast.makeText(this, R.string.toast_no_gyroscope, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        manager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        manager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val elapsedSec: Float = if (prevTimestamp == 0.0f) {
            0.0f
        } else {
            (event.timestamp - prevTimestamp) * Nano2Sec
        }
        val vz: Float = event.values[2]
        omegaZ += vz * elapsedSec
        prevTimestamp = event.timestamp.toFloat()
        rotationView.setDirection(omegaZ)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do nothing
    }
}