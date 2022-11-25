package com.r.peachpayment

import android.content.Context
import android.content.Intent
import com.oppwa.mobile.connect.payment.CheckoutInfo
import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.provider.Transaction

class OpenPaymentController(callbacks: PaymentCallbacks?, intent: Intent, context: Context) {
    init {
        Companion.callbacks = callbacks
        val intent1 = Intent(context, PaymentActivity::class.java)
        intent1.putExtra("checkoutId", intent.getStringExtra("checkoutId"))
        intent1.putExtra("number", intent.getStringExtra("number"))
        intent1.putExtra("brand", intent.getStringExtra("brand"))
        intent1.putExtra("holder", intent.getStringExtra("holder"))
        intent1.putExtra("expiryMonth", intent.getStringExtra("expiryMonth"))
        intent1.putExtra("expiryYear", intent.getStringExtra("expiryYear"))
        intent1.putExtra("cvv", intent.getStringExtra("cvv"))
        context.startActivity(intent1)
        intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    }


    companion object {
        private var callbacks: PaymentCallbacks? = null
        fun setPaymentConfigRequestSucceeded(p0: CheckoutInfo?) {
            if (callbacks != null) {
                callbacks!!.paymentConfigRequestSucceeded(p0)
            }
        }

        fun setPaymentConfigRequestFailed(p0: PaymentError?) {
            if (callbacks != null) {
                callbacks!!.paymentConfigRequestFailed(p0)
            }
        }

        fun setTransactionCompleted(p0: Transaction?) {
            if (callbacks != null) {
                callbacks!!.transactionCompleted(p0)
            }
        }

        fun setTransactionFailed(p0: Transaction?, p1: PaymentError?) {
            if (callbacks != null) {
                callbacks!!.transactionFailed(p0, p1)
            }
        }
    }
}