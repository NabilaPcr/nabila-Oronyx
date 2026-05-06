package com.example.bila_oronyx.pertemuan3

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.MainActivity
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.ActivityLoginOronyxBinding
import java.util.Calendar

class LoginOronyx : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOronyxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginOronyxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Spinner Agama
        val listAgama = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Khonghucu")
        val adapterAgama = ArrayAdapter(this, android.R.layout.simple_spinner_item, listAgama)
        adapterAgama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAgama.adapter = adapterAgama

        // 2. Setup DatePicker untuk Tanggal Lahir
        binding.inputTanggalLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, yearSelected, monthOfYear, dayOfMonth ->
                binding.inputTanggalLahir.setText("$dayOfMonth/${monthOfYear + 1}/$yearSelected")
            }, year, month, day)
            dpd.show()
        }

        // 3. Logika Tombol Registrasi
        binding.btnReg.setOnClickListener {
            // 1. Ambil nilai dari inputan
            val nama = binding.inputNama.text.toString().trim()
            val tglLahir = binding.inputTanggalLahir.text.toString().trim()
            val username = binding.inputUser.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()
            val confirmPass = binding.inputConfirmPassword.text.toString().trim()

            val genderId = binding.rgGender.checkedRadioButtonId
            val agama = binding.spinnerAgama.selectedItem.toString()

            var isError = false

            if (nama.isEmpty()) {
                binding.inputNama.error = "Nama tidak boleh kosong"
                isError = true
            }

            if (tglLahir.isEmpty()) {
                binding.inputTanggalLahir.error = "Tanggal lahir harus diisi"
                isError = true
            }

            if (genderId == -1) {
                isError = true
            }

            if (username.isEmpty()) {
                binding.inputUser.error = "Username wajib diisi"
                isError = true
            }

            // 3. Validasi Password & Confirm Password
            if (password.isEmpty()) {
                binding.inputPassword.error = "Password tidak boleh kosong"
                isError = true
            } else if (password.length < 6) {
                binding.inputPassword.error = "Password minimal 6 karakter"
                isError = true
            }

            if (confirmPass != password) {
                binding.inputConfirmPassword.error = "Konfirmasi password tidak cocok!"
                isError = true
            }

            // 4. Jika semua valid (isError tetap false)
            if (!isError) {
                // Simpan ke SharedPreferences (SP)
                val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                val editor = sharedPref.edit()

                editor.putString("nama", nama)
                editor.putString("tanggal_lahir", tglLahir)
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putString("agama", agama)
                // Ambil teks dari radio button yang dipilih
                val selectedGender = if (genderId == binding.rbLaki.id) "Laki-laki" else "Perempuan"
                editor.putString("jenis_kelamin", selectedGender)

                editor.apply()

                // Pindah ke Halaman Utama atau Login
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}