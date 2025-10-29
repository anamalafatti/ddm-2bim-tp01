package br.unisanta.tp05_sqlite_room.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.unisanta.tp05_sqlite_room.databinding.ActivityRegisterBinding
import br.unisanta.tp05_sqlite_room.data.model.User
import br.unisanta.tp05_sqlite_room.viewmodel.UserViewModel
import br.unisanta.tp05_sqlite_room.viewmodel.UserViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull() ?: 0
            val phone = binding.etPhone.text.toString().trim()
            val course = binding.etCourse.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Nome, e-mail e senha são obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(
                name = name,
                email = email,
                password = password,
                age = age,
                phone = phone,
                course = course
            )

            viewModel.insert(user) { id ->
                runOnUiThread {
                    Toast.makeText(this, "Registrado com ID: $id", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
