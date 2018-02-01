package com.chooser.nti.ntichooser

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val TAG = "myMessage"
    private var snackText: TextView? = null
    private var drinkText: TextView? = null
    private var priceText: TextView? = null
    private var dbHandler: MyDBHandler? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val randomizeButton = findViewById<Button>(R.id.randomizeButton)
        dbHandler = MyDBHandler(this, null, null, 1)
        val mjölkSwitch = findViewById<Switch>(R.id.mjölkSwitch)
        val nutSwitch = findViewById<Switch>(R.id.nutSwitch)
        val glutenSwitch = findViewById<Switch>(R.id.glutenSwitch)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        randomizeButton.setOnClickListener{

            try {

                dbHandler!!.createDataBase()

            } catch (ioe: IOException) {

                Log.i(TAG, "Unable to create database!")

            }

            val switchStateMjölk = mjölkSwitch.isChecked
            val switchStateNut = nutSwitch.isChecked
            val switchStateGluten = glutenSwitch.isChecked

            val list = dbHandler!!.getSnacks(switchStateMjölk, switchStateNut, switchStateGluten)
            val snacks = Arrays.copyOf<String, Any>(list.toTypedArray(), list.size, Array<String>::class.java)

            val list2 = dbHandler!!.getDrinks(switchStateMjölk, switchStateNut, switchStateGluten)
            val drinks = Arrays.copyOf<String, Any>(list2.toTypedArray(), list2.size, Array<String>::class.java)


            snackText = findViewById<TextView>(R.id.snackText)
            drinkText = findViewById<TextView>(R.id.drinkText)
            priceText = findViewById<TextView>(R.id.priceBox)
            val rand = Random()  //Set random class

            val theSnack = snacks[rand.nextInt(snacks.size)] //The final snack that you'll receive
            val theDrink = drinks[rand.nextInt(drinks.size)]//The final drink that you'll receive

            //while theSnack == lastSnack {
            //    val theSnack = snacks[rand.nextInt(snacks.size)]
            //}


            val handler = @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    val randomNum = rand.nextInt(snacks.size)
                    val snack = snacks[randomNum]
                    snackText!!.text = snack

                    val snackPrice = dbHandler!!.getPriceSnack(snack)

                    val randomNum2 = rand.nextInt(drinks.size)
                    val drink = drinks[randomNum2]
                    drinkText!!.text = drink

                    val drinkPrice = dbHandler!!.getPriceDrink(drink)

                    val price = drinkPrice + snackPrice
                    val stringPrice = Integer.toString(price)
                    priceText!!.text = stringPrice + "kr"
                }
            }

            val handlerFinal = @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    snackText!!.text = theSnack
                    drinkText!!.text = theDrink
                    Log.i(TAG, "Successful replacement of " + theSnack + " and " + theDrink)
                }
            }

            //test

            val r = Runnable {
                for (i in 0..49) {
                    handler.sendEmptyMessage(0)
                    try {
                        Thread.sleep(50)
                    } catch (ie: InterruptedException) {
                        Log.i(TAG, "Thread interrupted")
                    }
                }
                handlerFinal.sendEmptyMessage(0)
            }

            val myThread = Thread(r)
            myThread.start()

            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("yy-MM-dd")
            val formatted = current.format(formatter)
            val finalSnack = "'" + theSnack + "', "
            val finalDrink = "'" + theDrink + "', "
            val date = "'" + formatted + "'"
            dbHandler!!.addToHistorik(finalSnack, finalDrink, date)



        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
