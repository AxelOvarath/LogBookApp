package com.myloginfbrd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.myloginfbrd.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference // required for connection database to refer to database items
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance() // initialize database
        databaseReference = firebaseDatabase.reference.child("users") // users is database name

        binding.btnRegister.setOnClickListener{
            val signupUsername = binding.email.text.toString() // take info from text field
            val signupPassword = binding.password.text.toString()
            println(signupUsername + signupPassword)

            if (signupUsername.isNotEmpty() && signupPassword.isNotEmpty()){
                signupUser(signupUsername,signupPassword) // use function
            }else  {
                Toast.makeText(this@SignupActivity, "Signup Failed! Don't leave fields empty!", Toast.LENGTH_SHORT).show()
                // Error
            }
        }
        binding.loginRedirect.setOnClickListener{
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java)) // Redirect him to login
            finish()
        }
    }


    private fun signupUser(username: String, password: String){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { // database snapshot checks to see if data exists
                if (!dataSnapshot.exists()){ // if username doesn't exist
                    val id = databaseReference.push().key // id gets created
                    val userData = UserData(id,username,password)
                    databaseReference.child(id!!).setValue(userData) // id will unique
                    Toast.makeText(this@SignupActivity, "Signup Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java)) // will cause auto login
                    finish()
                } else {
                    Toast.makeText(this@SignupActivity, "User Already exists", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignupActivity, "Database Error: $databaseError", Toast.LENGTH_SHORT).show()
            }

        } )



    }
}