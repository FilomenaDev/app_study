package com.ao.co.tobe.appbuap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ao.co.tobe.appbuap.databinding.AuthenticationActivityBinding


class AuthenticationActivity: AppCompatActivity() {
    private var _binding: AuthenticationActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = AuthenticationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener { startActivity(Intent(this@AuthenticationActivity,MenuActivity::class.java)) }
    }
}