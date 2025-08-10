package com.example.taibamusallahdonation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.`activity_main`)

        val button1 = findViewById<Button>(R.id.button_1)
        val button5 = findViewById<Button>(R.id.button_5)
        val button10 = findViewById<Button>(R.id.button_10)
        val button20 = findViewById<Button>(R.id.button_20)
        val button50 = findViewById<Button>(R.id.button_50)

        button1.setOnClickListener { simulateMockReaderTap(1) }
        button5.setOnClickListener { simulateMockReaderTap(5) }
        button10.setOnClickListener { simulateMockReaderTap(10) }
        button20.setOnClickListener { simulateMockReaderTap(20) }
        button50.setOnClickListener { simulateMockReaderTap(50) }
    }

    private fun simulateMockReaderTap(amount: Int) {
        Log.i("MockReader", "Simulated reader tap for \$$amount")

        // Generate an idempotency key for this payment attempt
        val idempotencyKey = UUID.randomUUID().toString()
        Log.i("MockReader", "Processing payment: amount=\$$amount, idempotencyKey=$idempotencyKey")

        // Simulate payment delay (e.g. communicating with reader)
        Handler(Looper.getMainLooper()).postDelayed({
            // Simulate a successful payment response
            Log.i("MockReader", "Payment successful for \$$amount with key $idempotencyKey")
        }, 1500)
    }
}
