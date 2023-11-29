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
import com.myloginfbrd.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")


        binding.btnLogin.setOnClickListener{
            val loginusername = binding.email.text.toString() // take info from text field
            val loginPassword = binding.password.text.toString()
            if (loginusername.isNotEmpty() && loginPassword.isNotEmpty()){
                loginUser(loginusername,loginPassword) // use function
            }else  {
                Toast.makeText(this@LoginActivity, "login has Failed! Don't leave fields empty!", Toast.LENGTH_SHORT).show()
                // Error
            }
        }
        binding.registerRedirect.setOnClickListener{
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java)) // Redirect him to login
            finish()
        }

    }

    private fun loginUser(username: String, password: String,){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        val userData = userSnapshot.getValue(UserData::class.java)

                        if(userData != null && userData.password == password){
                            Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, MainActivity()::class.java))
                            finish()
                            return
                        }
                        else {
                            Toast.makeText(this@LoginActivity, "Login Failed! Check credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}