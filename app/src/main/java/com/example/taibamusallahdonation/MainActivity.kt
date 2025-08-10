package com.example.taibamusallahdonation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.UUID

class MainActivity : AppCompatActivity() {

    // Define a CoroutineScope for payment loops
    private val paymentScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Map to store running payment jobs so we can cancel them if needed
    private val runningJobs = mutableMapOf<String, Job>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.button_1)
        val button5 = findViewById<Button>(R.id.button_5)
        val button10 = findViewById<Button>(R.id.button_10)
        val button20 = findViewById<Button>(R.id.button_20)
        val button50 = findViewById<Button>(R.id.button_50)

        button1.setOnClickListener { togglePaymentLoop("reader1", 1) }
        button5.setOnClickListener { togglePaymentLoop("reader2", 5) }
        button10.setOnClickListener { togglePaymentLoop("reader3", 10) }
        button20.setOnClickListener { togglePaymentLoop("reader4", 20) }
        button50.setOnClickListener { togglePaymentLoop("reader5", 50) }
    }

    /**
     * Start or stop the loop for a given reader
     */
    private fun togglePaymentLoop(readerId: String, amount: Int) {
        if (runningJobs.containsKey(readerId)) {
            // Stop existing loop
            runningJobs[readerId]?.cancel()
            runningJobs.remove(readerId)
            runOnUiThread {
                Toast.makeText(this, "Stopped loop for $readerId", Toast.LENGTH_SHORT).show()
            }
            Log.i("PaymentLoop", "Stopped loop for $readerId")
        } else {
            // Start a new loop
            val job = paymentScope.launch {
                while (isActive) {
                    try {
                        val idempotencyKey = UUID.randomUUID().toString()
                        Log.i("PaymentLoop", "Starting payment for $$amount on $readerId (key=$idempotencyKey)")

                        // Simulate API call delay
                        delay(1000)

                        // Mock success/failure simulation
                        val success = Math.random() > 0.2 // 80% success rate
                        if (success) {
                            Log.i("PaymentLoop", "Payment SUCCESS for $readerId ($$amount)")
                        } else {
                            throw Exception("Payment failed due to network error")
                        }

                    } catch (e: Exception) {
                        Log.e("PaymentLoop", "Error on $readerId: ${e.message}")
                        // Continue loop after short wait
                        delay(2000)
                    }
                    delay(3000) // wait before next payment attempt
                }
            }
            runningJobs[readerId] = job
            runOnUiThread {
                Toast.makeText(this, "Started loop for $readerId", Toast.LENGTH_SHORT).show()
            }
            Log.i("PaymentLoop", "Started loop for $readerId")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        paymentScope.cancel() // stop all loops when activity is destroyed
    }
}
