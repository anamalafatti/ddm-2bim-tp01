package br.unisanta.tp05_sqlite_room.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.unisanta.tp05_sqlite_room.databinding.ActivityMainBinding
import br.unisanta.tp05_sqlite_room.viewmodel.UserViewModel
import br.unisanta.tp05_sqlite_room.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(application)
    }

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recebe o ID do usuário vindo do LoginActivity
        userId = intent.getIntExtra("USER_ID", -1)

        if (userId == -1) {
            Toast.makeText(this, "Usuário inválido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Carrega as informações do usuário
        lifecycleScope.launch {
            val user = viewModel.getById(userId)
            user?.let {
                with(binding) {
                    etName.setText(it.name)
                    etEmail.setText(it.email)
                    etPhone.setText(it.phone)
                    etCourse.setText(it.course)
                    etAge.setText(it.age.toString())
                }
            }
        }

        // Atualiza as informações do usuário
        binding.btnUpdate.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val course = binding.etCourse.text.toString().trim()
            val ageText = binding.etAge.text.toString().trim()
            val age = ageText.toIntOrNull()

            if (age == null) {
                Toast.makeText(this, "Idade inválida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = viewModel.getById(userId)
                if (user != null) {
                    val updatedUser = user.copy(
                        name = name,
                        email = email,
                        phone = phone,
                        course = course,
                        age = age
                    )
                    viewModel.update(updatedUser)
                    Toast.makeText(this@MainActivity, "Informações atualizadas!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
