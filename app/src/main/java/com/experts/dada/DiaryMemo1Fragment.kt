package com.experts.dada

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.experts.dada.databinding.FragmentDiaryMemo1Binding

class DiaryMemo1Fragment : Fragment() {
    lateinit var binding: FragmentDiaryMemo1Binding

    // 이미지 파일 경로
    var uri : Uri? = null

    // GestureDetector 설정
    private lateinit var gestureDetector: GestureDetector

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMemo1Binding.inflate(inflater, container,false)

        binding.memo1Tv.visibility = View.VISIBLE
        binding.memo1Iv.visibility = View.VISIBLE
        binding.memo1EyebodyIv.visibility = View.GONE

        // 갤러리 버튼 누르면 갤러리 이미지 선택해서 가져오게 하기
        binding.memo1Iv.setOnClickListener {
            getFromAlbum()
        }

        // 이미지 수정도 가능
        binding.memo1EyebodyIv.setOnClickListener {
            getFromAlbum()
        }

        // GestureDetector 초기화
        gestureDetector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                // 이미지 두 번 클릭 시 삭제
                deleteImage()
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                // 이미지 꾹 누르면 삭제
                deleteImage()
            }
        })

        // 이미지 뷰에 터치 이벤트 설정
        binding.memo1EyebodyIv.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event) // 제스처 이벤트 처리
            false
        }

        return binding.root
    }

    // 갤러리 이미지 선택해서 가져오기
    private fun getFromAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"     // 모든 이미지
        // 이미지 선택하면 응답 102 보내기
        startActivityForResult(intent, 102) // 102 말고 다른 숫자도 가능
    }

    // 읍답 받은 액티비티
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 이미지 잘 선택했으면
        when(requestCode) {
            102 -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        uri = data?.data    // 선택한 이미지의 주소
                        // 사용자가 이미지를 선택했으면(null이 아니면)
                        uri?.let {
                            // Glide로 이미지 로드
                            Glide.with(requireActivity())
                                .load(it) // 이미지 URI를 Glide에 전달
                                .into(binding.memo1EyebodyIv) // 지정된 ImageView에 로드

                            // 기존 이미지나 텍스트 뷰는 숨기고, 선택한 이미지만 보이도록 설정
                            binding.memo1Tv.visibility = View.GONE
                            binding.memo1Iv.visibility = View.GONE
                            binding.memo1EyebodyIv.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    // 이미지 삭제
    private fun deleteImage() {
        // 이미지와 텍스트 숨기기
        uri = null
        binding.memo1EyebodyIv.visibility = View.GONE
        binding.memo1Tv.visibility = View.VISIBLE
        binding.memo1Iv.visibility = View.VISIBLE
    }
}