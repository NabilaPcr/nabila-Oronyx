package com.example.bila_oronyx.pertemuan3

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.ActivityRegisterBinding // Pastikan nama layout sesuai
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

        // 2. Setup DatePicker untuk Tanggal Lahir
        binding.inputTanggalLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val dpd = DatePickerDialog(this, { _, y, m, d ->
                binding.inputTanggalLahir.setText("$d/${m + 1}/$y")
                binding.tilTanggalLahir.error = null // Hapus error saat sudah diisi
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            dpd.show()
        }

        // 3. Logika Tombol Registrasi (Validasi & Simpan)
        binding.btnReg.setOnClickListener {
            val nama = binding.inputNama.text.toString().trim()
            val tgl = binding.inputTanggalLahir.text.toString().trim()
            val user = binding.inputUser.text.toString().trim()
            val pass = binding.inputPassword.text.toString().trim()
            val conf = binding.inputConfirmPassword.text.toString().trim()
            val genderId = binding.rgGender.checkedRadioButtonId

            var isValid = true

            // Validasi Input (Soal 2: Gunakan error selain Toast)
            if (nama.isEmpty()) { binding.tilNama.error = "Nama wajib diisi"; isValid = false }
            else { binding.tilNama.error = null }

            if (tgl.isEmpty()) { binding.tilTanggalLahir.error = "Pilih tanggal"; isValid = false }
            else { binding.tilTanggalLahir.error = null }

            if (user.isEmpty()) { binding.tilUser.error = "Username kosong"; isValid = false }
            else { binding.tilUser.error = null }

            if (pass.isEmpty()) { binding.tilPassword.error = "Password kosong"; isValid = false }
            else { binding.tilPassword.error = null }

            if (conf != pass) { binding.tilConfirmPassword.error = "Password tidak cocok"; isValid = false }
            else { binding.tilConfirmPassword.error = null }

            if (genderId == -1) {
                Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            if (isValid) {
                // Simpan ke SharedPreferences
                val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("nama", nama)
                editor.putString("username", user)
                editor.putString("password", pass)
                editor.apply()

                Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke LoginOronyx
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}