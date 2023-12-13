package com.example.individualproject.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.individualproject.R;
import com.example.individualproject.models.CheckOutModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class CheckoutActivity extends AppCompatActivity {

    EditText editTextName, editTextAddress, editTextPayment, editTextContactNumber, editTextCVV, editTextCardNumber  ;
    Spinner spinnerShipping;
    TextView textViewOrderSummary;
    Button btnConfirmPurchase;

//    public static  final String clientKey = "Aa0KUw9tMMrjE7c_w7QbGLcbDzuHEFOOdkq6eVw0iWb07XRH9rv3fAupyh0c4Mt_Lg5CDsKKlLoJ6UFM";
//    public  static  final int PAYPAL_REQUEST_CODE = 123;
//
////    paypal configuration object
//    private static PayPalConfiguration config = new PayPalConfiguration()
//        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
//        .clientId(clientKey);
//
//    private EditText amountEdt;
//    private TextView paymentTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
//        editTextPayment = findViewById(R.id.editTextPayment);
        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        editTextContactNumber = findViewById(R.id.editTextContactNumber);
        editTextCVV = findViewById(R.id.editTextCVV);
        spinnerShipping = findViewById(R.id.spinnerShipping);
        textViewOrderSummary = findViewById(R.id.textViewOrderSummary);
        btnConfirmPurchase = findViewById(R.id.btnConfirmPurchase);

        // Set up a click listener for the Confirm Purchase button
        btnConfirmPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate form fields
                if (validateForm()) {
                    // Perform purchase logic here
                    performPurchase();
                }
            }
        });
    }

    // Validation logic for form fields
    private boolean validateForm() {
        // Check if the name, address, and payment fields are not empty
        if (editTextName.getText().toString().trim().isEmpty() ||
                editTextAddress.getText().toString().trim().isEmpty() ||
                editTextPayment.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Additional validation for contact number, card number, and CVV
        if (!isValidContactNumber(editTextContactNumber.getText().toString().trim())) {
            Toast.makeText(this, "Invalid contact number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidCardNumber(editTextCardNumber.getText().toString().trim())) {
            Toast.makeText(this, "Invalid card number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidCVV(editTextCVV.getText().toString().trim())) {
            Toast.makeText(this, "Invalid CVV", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Additional validation can be added based on your requirements

        return true;
    }

    // Example validation for a 10-digit contact number
    private boolean isValidContactNumber(String contactNumber) {
        return contactNumber.matches("\\d{10}");
    }

    // Example validation for a 16-digit card number
    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }

    // Example validation for a 3-digit CVV
    private boolean isValidCVV(String cvv) {
        return cvv.matches("\\d{3}");
    }

    private void performPurchase() {
        // Get the values from the form fields
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String paymentDetails = editTextPayment.getText().toString().trim();
        String shippingMethod = spinnerShipping.getSelectedItem().toString(); // Assuming you want to store the selected shipping method

        // Create a data object
        CheckOutModel checkOutModel = new CheckOutModel(name, address, paymentDetails, shippingMethod);

        // Add the data to the "checkOut" collection
        FirebaseFirestore.getInstance()
                .collection("checkOut")
                .add(checkOutModel)
                .addOnSuccessListener(documentReference -> {
                    Log.d("CheckOutActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    Toast.makeText(this, "Purchase successful", Toast.LENGTH_SHORT).show();

                    // Optionally, you can navigate to the MainActivity
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckOutActivity", "Error adding document", e);
                    Toast.makeText(this, "Error processing purchase", Toast.LENGTH_SHORT).show();
                });
    }


//        amountEdt = findViewById(R.id.idEdtAmount);
//        Button makePaymentBtn = findViewById(R.id.idBtnPay);
//        paymentTV = findViewById(R.id.idTVStatus);
//
//        makePaymentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getPayment();
//            }
//        });
//    }
//
//    private void getPayment() {
//
//        // Getting the amount from editText
//        String amount = amountEdt.getText().toString();
//
//        // Creating a paypal payment on below line.
//        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Course Fees",
//                PayPalPayment.PAYMENT_INTENT_SALE);
//
//        // Creating Paypal Payment activity intent
//        Intent intent = new Intent(this, PaymentActivity.class);
//
//        //putting the paypal configuration to the intent
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        // Putting paypal payment to the intent
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//
//        // Starting the intent activity for result
//        // the request code will be used on the method onActivityResult
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        check if the results if is from paypal
//        if (requestCode == PAYPAL_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//
//                if (confirm != null) {
//                    try {
//                        // Getting the payment details
//                        String paymentDetails = confirm.toJSONObject().toString(4);
//                        // on below line we are extracting json response and displaying it in a text view.
//                        JSONObject payObj = new JSONObject(paymentDetails);
//                        String payID = payObj.getJSONObject("response").getString("id");
//                        String state = payObj.getJSONObject("response").getString("state");
//                        paymentTV.setText("Payment " + state + "\n with payment id is " + payID);
//                    } catch (JSONException e) {
//                        // handling json exception on below line
//                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
//                    }
//                } else if (resultCode == Activity.RESULT_CANCELED) {
//                    Log.i("paymentExample", "The user canceled.");
//                } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
//                    Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
//                }
//            }
//        }
//    }

}