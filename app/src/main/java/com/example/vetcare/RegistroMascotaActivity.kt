package com.example.vetcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vetcare.databinding.ActivityRegistroMascotaBinding
import com.google.firebase.firestore.FirebaseFirestore

class RegistroMascotaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroMascotaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_mascota)
        binding = ActivityRegistroMascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider: String) {
        title = "Registro de las Mascotas"
        binding.completeRegister.setOnClickListener() {
            val homeIntent = Intent(this, HomeActivity::class.java).apply {
                putExtra("email", email)
                putExtra("provider", provider)
            }
            startActivity(homeIntent)
            FirebaseFirestore.getInstance().collection("pets").document(
                //se haria la logica para registrar esta mascto
            )
        }
    }
}