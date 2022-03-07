package com.example.focusondatastore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.focusondatastore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var focusDataStore: FocusDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doAutoLogin()

        binding.btnLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.edtEmail.text) -> {
                    binding.edtEmail.error = "Enter Email"
                }
                TextUtils.isEmpty(binding.edtPwd.text) -> {
                    binding.edtPwd.error = "Enter Password"
                }
                else -> {
                    doLoginAndStoreDataInDataStore()
                }
            }
        }
    }

    private fun doAutoLogin() {
        lifecycleScope.launch {
            binding.edtEmail.setText(focusDataStore.email.first())
            binding.edtPwd.setText(focusDataStore.password.first())
        }
    }

    private fun doLoginAndStoreDataInDataStore() {
        lifecycleScope.launch {
            focusDataStore.setEmail(binding.edtEmail.text.toString())
            focusDataStore.setPassword(binding.edtPwd.text.toString())
        }
        startActivity(Intent(this,HomeActivity::class.java))
    }
}