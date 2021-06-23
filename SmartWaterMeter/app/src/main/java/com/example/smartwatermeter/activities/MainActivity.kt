package com.example.smartwatermeter.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.smartwatermeter.R
import com.example.smartwatermeter.databinding.ActivityMainBinding
import com.example.smartwatermeter.fragments.HomeFragmentDirections.Companion.actionHomeFragmentToLoginFragment
import com.example.smartwatermeter.hideKeyboard
import com.example.smartwatermeter.models.RequestModel
import com.example.smartwatermeter.service.SmartWaterApi
import com.example.smartwatermeter.service.responses.LoginResponse
import com.example.smartwatermeter.viewmodels.GameViewModel
import com.example.smartwatermeter.viewmodels.HomeViewModel
import com.google.android.material.textfield.TextInputEditText
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: GameViewModel
    private val channelId = "my_channel"
    private var notId = 123


    object StaticData {
        var loginData: LoginResponse? = null
        @SuppressLint("StaticFieldLeak")
        var mqttClient: MqttAndroidClient? = null
        var name: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        val appBarConfiguration = AppBarConfiguration(binding.bottomNav.menu)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = null

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        println("maindee")
        binding.btnLogout.setOnClickListener {
            navHostFragment.findNavController().navigate(actionHomeFragmentToLoginFragment())
            //sendNotification()
        }

        binding.btnAddfriend.setOnClickListener {
                var dialog = AlertDialog.Builder(navHostFragment.requireContext(),R.style.MaterialAlertDialog_color)
                var dialogView = layoutInflater.inflate(R.layout.custom_dialog2,null)
                var fId = dialogView.findViewById<TextInputEditText>(R.id.fId)
                var fNum = dialogView.findViewById<TextInputEditText>(R.id.fNum)
                dialog.setView(dialogView)
                dialog.setCancelable(true)
                dialog.setTitle("Request Info")
                dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int -> }
                dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                    hideKeyboard(this)
                    viewModel.addFriend(fId.text.toString(),"friends"+fNum.text.toString())
                }
                dialog.show()
        }

        createNotificationChannel()





    }




    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Watch, Water & World"
            val descText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId,name,importance).apply {
                description = descText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    fun sendNotification(message: String) {
        val intent = Intent(this,NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(this,0,intent,0)


        var builder = NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Watch, Water & World")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(notId,builder.build())
        }
    }


    fun setToolbarTitle(title: String) {
        binding.toolbarTitle.text = title
    }

    fun setVisibilities(visible: Boolean) {
        if(visible) {
            binding.bottomNav.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE
        } else {
            binding.bottomNav.visibility = View.GONE
            binding.toolbar.visibility = View.GONE
        }
    }

    fun setLogoutButtonVisibility(visible: Boolean) {
        if(visible)
            binding.btnLogout.visibility = View.VISIBLE
        else
            binding.btnLogout.visibility = View.GONE
    }

    fun setAddFriendButtonVisibility(visible: Boolean) {
        if(visible)
            binding.btnAddfriend.visibility = View.VISIBLE
        else
            binding.btnAddfriend.visibility = View.GONE
    }

    fun setLoading(visible: Boolean) {
        if(visible)
            binding.loadingProgress.root.visibility = View.VISIBLE
        else
            binding.loadingProgress.root.visibility = View.GONE
    }
}