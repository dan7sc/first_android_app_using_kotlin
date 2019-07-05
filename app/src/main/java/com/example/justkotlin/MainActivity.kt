package com.example.justkotlin

//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//}

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import android.net.Uri
//import java.text.NumberFormat




/**
 * This app displays an order form to order coffee.
 */
class MainActivity : AppCompatActivity() {

    var quantity: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * This method is called when the order button is clicked.
     */
    fun increment(view: View) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show()
            // Exit this method early because there's nothing left to do
            return
        }
        quantity += 1
        displayQuantity(quantity)
    }

    /**
     * This method is called when the order button is clicked.
     */
    fun decrement(view: View) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show()
            // Exit this method early because there's nothing left to do
            return
        }
        quantity -= 1
        displayQuantity(quantity)
    }

    /**
     * This method is called when the order button is clicked.
     */
    fun submitOrder(view: View) {

        var nameField = findViewById<EditText>(R.id.name_field)
        var name: String = nameField.getText().toString()
//        Log.v("MainActivity", "Name ", name)

        // Figure out if the user wants whipped cream topping
        var whippedCreamCheckBox = findViewById<CheckBox>(R.id.whipped_cream_checkbox)
        var hasWhippedCream: Boolean =  whippedCreamCheckBox.isChecked()
//        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream)

        // Figure out if the user wants chocolate topping
        var chocolateCheckBox = findViewById<CheckBox>(R.id.chocolate_checkbox)
        var hasChocolate: Boolean =  chocolateCheckBox.isChecked()
//        Log.v("MainActivity", "Has whipped cream: " + hasChocolate)

        var price: Int = calculatePrice(hasWhippedCream, hasChocolate)
        var priceMessage: String = createOrderSummary(name, price, hasWhippedCream, hasChocolate)
        displayMessage(priceMessage)

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Kotlin order for " + name)
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    /**
     * This method calculates the price of the order
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is wheter or not the user wants chocolate topping
     * @return total price
     */
    private fun calculatePrice(addWhippedCream: Boolean, addChocolate: Boolean): Int {
        // Price of 1 cup of coffee
        var basePrice: Int = 5

        // Add $1 if the user wants whipped cream
        if (addWhippedCream) {
            basePrice += 1
        }

        // Add $2 if the user wants chocolate
        if (addChocolate) {
            basePrice += 2
        }

        // Calculate the total order price by multiplying by quantity
        return quantity * basePrice
    }

    /**
     * This method creates summary of the order
     *
     * @param name of the customer
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
    private fun createOrderSummary(name: String, price: Int, addWhippedCream: Boolean, addChocolate: Boolean): String {
        var priceMessage: String = "Name: " + name
        priceMessage += "\nAdd whipped cream? " + addWhippedCream
        priceMessage += "\nAdd chocolate? " + addChocolate
        priceMessage += "\nQuantity: " + quantity
        priceMessage += "\nTotal: $" + price
        priceMessage += "\nThank you!"
        return priceMessage
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private fun displayQuantity(number: Int) {
        val quantityTextView = findViewById(R.id.quantity_text_view) as TextView
        quantityTextView.setText("" + number)
    }

//    /**
//     * This method displays the given price on the screen.
//     */
//    private fun displayPrice(number: Int) {
//        val orderSummaryTextView = findViewById(R.id.order_summary_text_view) as TextView
////        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number))
//        orderSummaryTextView.setText(number)
//    }

    /**
     * This method displays the given text on the screen.
     */
    private fun displayMessage(message: String) {
        val orderSummaryTextView = findViewById<View>(R.id.order_summary_text_view) as TextView
        orderSummaryTextView.text = message
    }
}
