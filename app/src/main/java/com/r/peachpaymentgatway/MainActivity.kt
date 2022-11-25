package com.r.peachpaymentgatway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.braintreepayments.cardform.view.CardForm
import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.payment.CheckoutInfo
import com.oppwa.mobile.connect.provider.Transaction
import com.r.peachpayment.OpenPaymentController
import com.r.peachpayment.PaymentCallbacks
import com.r.peachpaymentgatway.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), PaymentCallbacks {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.cardForm.cardRequired(true).expirationRequired(true).cvvRequired(true)
            .cardholderName(CardForm.FIELD_REQUIRED).postalCodeRequired(false)
            .mobileNumberRequired(false).actionLabel("Purchase").setup(this)

        binding.PayNowBtn.setOnClickListener {
            val intent = Intent()

//           https://peachpayments.docs.oppwa.com/tutorials/integration-guide#getStatus
//           place you checkout id or you can test with api via shared url
            intent.putExtra("checkoutId", "DFAB290B1DF4E78C14DD14B62A49294C.uat01-vm-tx04")
            intent.putExtra("number", binding.cardForm.cardNumber)
            intent.putExtra("brand", "")
            intent.putExtra("holder", binding.cardForm.cardholderName)
            intent.putExtra("expiryMonth", binding.cardForm.expirationMonth)
            intent.putExtra("expiryYear", binding.cardForm.expirationYear)
            intent.putExtra("cvv", binding.cardForm.cvv)

            OpenPaymentController(this, intent, this)

        }

    }


    override fun paymentConfigRequestSucceeded(p0: CheckoutInfo?) {
        val resourcePath = p0!!.resourcePath
        Log.d("@@checkoutInfo", resourcePath.toString())
    }

    override fun paymentConfigRequestFailed(p0: PaymentError?) {
        Log.d("@@paymentError", p0.toString())

    }

    override fun transactionCompleted(p0: Transaction?) {
        Log.d("@@transactionCompleted", p0.toString())
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.putExtra("paymentStatus", 1)
        startActivity(intent)
    }

    override fun transactionFailed(p0: Transaction?, p1: PaymentError?) {
        Log.d("@@transactionFailed", p0.toString())
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.putExtra("paymentStatus", 2)
        startActivity(intent)
    }


}