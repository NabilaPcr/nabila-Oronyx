package com.example.bila_oronyx.Home.pertemuan3

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.ActivityRegisterBinding
import java.util.Calendar

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Spinner Agama
        val listAgama = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Khonghucu")
        val adapterAgama = ArrayAdapter(this, android.R.layout.simple_spinner_item, listAgama)
        adapterAgama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAgama.adapter = adapterAgama

        // 2. Setup DatePicker
        binding.inputTanggalLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val dpd = DatePickerDialog(this, { _, y, m, d ->
                binding.inputTanggalLahir.setText("$d/${m + 1}/$y")
                binding.tilTanggalLahir.error = null
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            dpd.show()
        }

        // 3. Tombol Registrasi
        binding.btnReg.setOnClickListener {
            val nama = binding.inputNama.text.toString().trim()
            val tgl = binding.inputTanggalLahir.text.toString().trim()
            val user = binding.inputUser.text.toString().trim()
            val pass = binding.inputPassword.text.toString().trim()
            val conf = binding.inputConfirmPassword.text.toString().trim()
            val genderId = binding.rgGender.checkedRadioButtonId
            val agama = binding.spinnerAgama.selectedItem.toString()

            var isValid = true

            // Validasi
            if (nama.isEmpty()) {
                binding.tilNama.error = "Nama wajib diisi"
                isValid = false
            } else {
                binding.tilNama.error = null
            }

            if (tgl.isEmpty()) {
                binding.tilTanggalLahir.error = "Pilih tanggal"
                isValid = false
            } else {
                binding.tilTanggalLahir.error = null
            }

            if (user.isEmpty()) {
                binding.tilUser.error = "Username kosong"
                isValid = false
            } else {
                binding.tilUser.error = null
            }

            if (pass.isEmpty()) {
                binding.tilPassword.error = "Password kosong"
                isValid = false
            } else if (pass.length < 6) {
                binding.tilPassword.error = "Password minimal 6 karakter"
                isValid = false
            } else {
                binding.tilPassword.error = null
            }

            if (conf != pass) {
                binding.tilConfirmPassword.error = "Password tidak cocok"
                isValid = false
            } else {
                binding.tilConfirmPassword.error = null
            }

            if (genderId == -1) {
                Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            if (isValid) {
                // Simpan ke SharedPreferences
                val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                val editor = sharedPref.edit()

                editor.putString("nama", nama)
                editor.putString("tanggal_lahir", tgl)
                editor.putString("username", user)
                editor.putString("password", pass)
                editor.putString("agama", agama)

                val selectedGender = if (genderId == binding.rbLaki.id) "Laki-laki" else "Perempuan"
                editor.putString("jenis_kelamin", selectedGender)
                editor.putBoolean("isLoggedIn", false)
                editor.apply()

                Toast.makeText(this, "Registrasi Berhasil! Silakan Login", Toast.LENGTH_SHORT).show()

                // Kembali ke Login
                val intent = Intent(this, LoginOronyx::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }
        }

        // ======== LINK KE LOGIN ========
        binding.tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginOronyx::class.java))
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}