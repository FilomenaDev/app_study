package com.ao.co.tobe.appbuap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.ao.co.tobe.appbuap.databinding.ActivityMenuBinding
import com.ao.co.tobe.appbuap.payment.PaymentActivity
import com.ao.co.tobe.appbuap.search.SearchActivity


class MenuActivity : AppCompatActivity(),OnClickListener {
    private var _binding: ActivityMenuBinding? = null
    private  val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pay.setOnClickListener(this)
        binding.search.setOnClickListener(this)
        binding.fechoCobrador.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.pay ->{
               startActivity(Intent(this@MenuActivity,PaymentActivity::class.java))
            }

            R.id.search ->{
                startActivity(Intent(this@MenuActivity,SearchActivity::class.java))
            }
            R.id.fecho_cobrador ->{

            }
        }
    }
}