package com.experts.dada

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.experts.dada.databinding.FragmentSettingProfileBinding
import java.util.Calendar

class SettingProfileFragment : Fragment() {

    lateinit var binding: FragmentSettingProfileBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingProfileBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        loadProfileData()

        binding.profileCloseIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.profileBirthValueTv.setOnClickListener {
            showDatePickerDialog()
        }

        binding.profileSaveTv.setOnClickListener {
            saveProfileData()
        }

        binding.profileGenderManTv.setOnClickListener {
            selectGender(isMale = true)
        }
        binding.profileGenderWomanTv.setOnClickListener {
            selectGender(isMale = false)
        }

        return binding.root
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val birthDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            binding.profileBirthValueTv.text = birthDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun saveProfileData() {
        val nickname = binding.profileNicknameEt.text.toString()
        val gender = if (binding.profileGenderManTv.isSelected) "Male" else "Female"
        val birthDate = binding.profileBirthValueTv.text.toString()

        with(sharedPreferences.edit()) {
            putString("nickname", nickname)
            putString("gender", gender)
            putString("birthday", birthDate)
            apply()
        }

        // 데이터 저장 후 피드백
        Toast.makeText(requireContext(), "프로필이 저장되었습니다.", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }

    private fun loadProfileData() {
        val nickname = sharedPreferences.getString("nickname", "")
        val gender = sharedPreferences.getString("gender", "Male")
        val birthDate = sharedPreferences.getString("birthDate", "")

        binding.profileNicknameEt.setText(nickname)
        if(birthDate?.isEmpty() != true)   binding.profileBirthValueTv.text = birthDate
        else    binding.profileBirthValueTv.text = "2000-01-01"

        if (gender == "Male") {
            selectGender(isMale = true)
        } else {
            selectGender(isMale = false)
        }
    }

    private fun selectGender(isMale: Boolean) {
        if (isMale) {
            binding.profileGenderManTv.setTextColor(resources.getColor(R.color.main_blue))
            binding.profileGenderWomanTv.setTextColor(resources.getColor(R.color.dark_grey))
            binding.profileGenderManTv.isSelected = true
            binding.profileGenderWomanTv.isSelected = false
        } else {
            binding.profileGenderManTv.setTextColor(resources.getColor(R.color.dark_grey))
            binding.profileGenderWomanTv.setTextColor(resources.getColor(R.color.main_blue))
            binding.profileGenderManTv.isSelected = false
            binding.profileGenderWomanTv.isSelected = true
        }
    }
}