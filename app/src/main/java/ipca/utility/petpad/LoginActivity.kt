package ipca.utility.petpad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val registertext: TextView = findViewById(R.id.textViewRegisterNow)

        val firebaseAuth = Firebase.auth
        if (firebaseAuth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
        }

        registertext.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.buttonLogin)

        loginButton.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email: EditText = findViewById(R.id.editTextEmail)
        val password: EditText = findViewById(R.id.editTextPassword)





        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG)
                .show()
            return
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()


        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext, "Success.",
                        Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    baseContext, "Authentication failed. ${it.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}