package com.r.peachpayment


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.exception.PaymentException
import com.oppwa.mobile.connect.payment.BrandsValidation
import com.oppwa.mobile.connect.payment.CheckoutInfo
import com.oppwa.mobile.connect.payment.ImagesRequest
import com.oppwa.mobile.connect.payment.PaymentParams
import com.oppwa.mobile.connect.payment.card.CardPaymentParams
import com.oppwa.mobile.connect.provider.Connect
import com.oppwa.mobile.connect.provider.ITransactionListener
import com.oppwa.mobile.connect.provider.OppPaymentProvider
import com.oppwa.mobile.connect.provider.Transaction
import com.r.peachpayment.databinding.ActivityPaymentBinding


class PaymentActivity : AppCompatActivity(), ITransactionListener {

    private lateinit var binding: ActivityPaymentBinding
    private var paymentParams: PaymentParams? = null
    private var paymentProvider: OppPaymentProvider? = null
    private var transactionListener: ITransactionListener? = null

    private var checkoutId: String? = null
    private var brand: String? = null
    private var number: String? = null
    private var holder: String? = null
    private var expiryMonth: String? = null
    private var expiryYear: String? = null
    private var cvv: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)

        transactionListener = this

        checkoutId = intent.getStringExtra("checkoutId")
        number = intent.getStringExtra("number")
        brand = intent.getStringExtra("brand")
        holder = intent.getStringExtra("holder")
        expiryMonth = intent.getStringExtra("expiryMonth")
        expiryYear = intent.getStringExtra("expiryYear")
        cvv = intent.getStringExtra("cvv")


        if (!CardPaymentParams.isNumberValid(number)) {
            Toast.makeText(this, "enter a valid card number ", Toast.LENGTH_SHORT).show()
        } else {
            paymentParams = CardPaymentParams(
                checkoutId, brand, number!!, holder, expiryMonth, expiryYear, cvv
            )
            // Set shopper result URL
            paymentParams!!.shopperResultUrl = "companyname://result"
        }


        try {
            paymentProvider = OppPaymentProvider(this, Connect.ProviderMode.TEST)
            val transaction = Transaction(paymentParams)
            paymentProvider!!.submitTransaction(transaction, transactionListener)
            paymentProvider!!.requestCheckoutInfo(checkoutId, transactionListener)
        } catch (ee: PaymentException) {
            Log.d("@@ee", ee.toString())
        }

        try {
            paymentProvider!!.requestCheckoutInfo(checkoutId, transactionListener)
        } catch (e: PaymentException) {
            Log.d("@@error", e.toString())
        }
    }

    override fun transactionFailed(transaction: Transaction, paymentError: PaymentError) {
        OpenPaymentController.setTransactionFailed(transaction, paymentError)
    }

    override fun transactionCompleted(transaction: Transaction) {
        OpenPaymentController.setTransactionCompleted(transaction)
    }


    override fun paymentConfigRequestSucceeded(checkoutInfo: CheckoutInfo) {
        OpenPaymentController.setPaymentConfigRequestSucceeded(checkoutInfo)

    }

    override fun paymentConfigRequestFailed(paymentError: PaymentError?) {
        OpenPaymentController.setPaymentConfigRequestFailed(paymentError)
    }

    override fun brandsValidationRequestSucceeded(brandsValidation: BrandsValidation?) {
        Log.d("@@brandsValidation", brandsValidation.toString())
    }

    override fun brandsValidationRequestFailed(paymentError: PaymentError?) {
        Log.d("@@paymentError", paymentError.toString())
    }

    override fun imagesRequestSucceeded(imagesRequest: ImagesRequest?) {
        Log.d("@@imagesRequest", imagesRequest.toString())
    }

    override fun imagesRequestFailed() {

    }


}