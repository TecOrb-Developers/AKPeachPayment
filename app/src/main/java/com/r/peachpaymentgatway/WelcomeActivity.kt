package com.r.peachpaymentgatway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.r.peachpaymentgatway.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding
    var paymentStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        paymentStatus = intent.getIntExtra("paymentStatus", 0)

        binding.btnPayment.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        when (paymentStatus) {
            1 -> {
                binding.paymentStatus.text = "Payment has been done"
            }
            2 -> {
                binding.paymentStatus.text = "Payment has been failed"
            }
            else -> {
                binding.paymentStatus.text = ""
            }
        }
    }
}