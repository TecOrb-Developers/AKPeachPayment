package com.r.peachpayment

import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.payment.CheckoutInfo
import com.oppwa.mobile.connect.provider.Transaction

interface PaymentCallbacks {
    fun paymentConfigRequestSucceeded(p0: CheckoutInfo?)
    fun paymentConfigRequestFailed(p0: PaymentError?)
    fun transactionCompleted(p0: Transaction?)
    fun transactionFailed(p0: Transaction?, p1: PaymentError?)
}