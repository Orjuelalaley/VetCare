package com.example.vetcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.vetcare.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        title = "Registro"
        binding.registroButtonID.setOnClickListener {
            if (binding.email.text.isNotEmpty() && binding.loginPassword.editText?.text.toString().isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.loginPassword.editText?.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        FirebaseFirestore.getInstance().collection("users")
                            .document(it.result?.user?.uid ?: "")
                            .set(
                                hashMapOf("name" to binding.name.text.toString(),
                                    "email" to binding.email.text.toString(),
                                    "address" to binding.address.text.toString(),
                                    "phone" to binding.phone.text.toString(),
                                    "user" to binding.user.text.toString(),
                                    "password" to binding.loginPassword.editText?.text.toString())
                            )
                        Toast.makeText(this, "Usuario creado", Toast.LENGTH_LONG).show()
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            } else {
                showAlert()
            }
        }
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error en la autenticación del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType){
        val homeIntent: Intent = Intent(this, HomeActivity::class.java).apply{
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}