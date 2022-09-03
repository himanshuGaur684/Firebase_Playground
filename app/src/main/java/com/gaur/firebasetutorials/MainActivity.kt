package com.gaur.firebasetutorials

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gaur.firebasetutorials.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!


    private val db: FirebaseFirestore by lazy { Firebase.firestore }

    private val TITLE = "title"
    private val DESCRIPTION = "description"
    private val COLLECTION = "notes"
    private val DOCUMENT = "inner_notes"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            save()
        }
        binding.btnRead.setOnClickListener {
            read()
        }
        binding.btnUpdate.setOnClickListener {
            update()
        }
        binding.btnDelete.setOnClickListener {
            delete()
        }

    }


    private fun delete() {
        db.collection(COLLECTION).document(DOCUMENT).delete().addOnSuccessListener {
            this.makeToast("deleted")
        }.addOnFailureListener {
            this.makeToast("error occurred $it")
        }
    }

    private fun update() {
        db.collection(COLLECTION).document(DOCUMENT)
//             also update with update function
//            .update(mutableMapOf<String,Any>(TITLE to "titles"))
            .set(mutableMapOf(TITLE to "titles"), SetOptions.merge())
            .addOnSuccessListener {
                this.makeToast("updated")
            }.addOnFailureListener {
                this.makeToast("error occurred $it")
            }
    }

    private fun read() {
        db.collection(COLLECTION).document(DOCUMENT).get().addOnSuccessListener {
            val title = it.get(TITLE)
            val desc = it.get(DESCRIPTION)
            binding.tvRead.text = "Title: $title\nDescription : $desc"
        }.addOnFailureListener {
            this.makeToast("Error occurred $it")
        }
    }

    private fun save() {
        val title = binding.edTitle.text.toString()
        val desc = binding.edDescription.text.toString()
        val map = mutableMapOf<String, String>()
        map.put(TITLE, title)
        map.put(DESCRIPTION, desc)

//        create unique id for your document
//        db.collection(COLLECTION).document().set(map)
//        or
//        db.collection(COLLECTION).add(map)

        db.collection(COLLECTION).document(DOCUMENT).set(map)
            .addOnSuccessListener {
                this.makeToast("Successfully saved.")
            }
            .addOnFailureListener {
                this.makeToast("Error Occurred $it")
            }
    }
}

fun Context.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
