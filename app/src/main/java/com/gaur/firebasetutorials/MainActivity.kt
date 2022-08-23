package com.gaur.firebasetutorials

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gaur.firebasetutorials.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!


    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSignUp.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.edEmail.requestFocus()
                }
                password.isEmpty() || password.length <= 6 -> {
                    binding.edPassword.requestFocus()
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        this.makeToast("complete")
                    }
                        .addOnCanceledListener {
                            this.makeToast("event cancel")
                        }.addOnSuccessListener {
                            this.makeToast(it.user?.email.toString())
                        }
                }
            }

        }

        binding.btnUserLogout.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
            } else {
                this.makeToast("user already logout")
            }
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.edEmail.requestFocus()
                }
                password.isEmpty() || password.length <= 6 -> {
                    binding.edPassword.requestFocus()
                }
                else -> {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        this.makeToast("complete")
                    }
                        .addOnCanceledListener {
                            this.makeToast("event cancel")
                        }.addOnSuccessListener {
                            this.makeToast(it.user?.email.toString())
                        }
                }
            }
        }


    }
}

fun Context.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
