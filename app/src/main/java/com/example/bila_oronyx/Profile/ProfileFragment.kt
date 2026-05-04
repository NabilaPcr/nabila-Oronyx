package com.example.bila_oronyx.Profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnInstagram.setOnClickListener {
            openUrl("https://www.instagram.com/me.nblzhr?igsh=MWswMTkweGl4Yzc5aQ==")
        }

        binding.btnLinkedin.setOnClickListener {
            openUrl("https://www.linkedin.com/in/nabila-azzahra-b339123a0?utm_source=share_via&utm_content=profile&utm_medium=member_android")
        }

        binding.btnGithub.setOnClickListener {
            openUrl("https://github.com/NabilaPcr")
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
