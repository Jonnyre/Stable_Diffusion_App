package com.lenhujori.stablediffusion

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    val imageBitmap = MutableLiveData<Bitmap?>()
    val isLoading = MutableLiveData<Boolean>()

    fun generateImage(prompt: String) {
        val randomSeed = Random.nextInt()
        val requestBody = RequestBody(
            prompt = prompt,
            negative_prompt = "blurry, low quality",
            styles = listOf("photorealistic"),
            seed = randomSeed,
            subseed = -1,
            subseed_strength = 0f,
            seed_resize_from_h = -1,
            seed_resize_from_w = -1,
            sampler_name = "Euler a",
            scheduler = "Karras",
            batch_size = 1,
            n_iter = 1,
            steps = 500,
            cfg_scale = 7f,
            width = 512,
            height = 512,
            restore_faces = true,
            tiling = false,
            do_not_save_samples = false,
            do_not_save_grid = false,
            eta = 0f,
            denoising_strength = 0f,
            s_min_uncond = 0f,
            s_churn = 0f,
            s_tmax = 0f,
            s_tmin = 0f,
            s_noise = 0f,
            override_settings = emptyMap(),
            override_settings_restore_afterwards = true,
            refiner_checkpoint = "",
            refiner_switch_at = 0f,
            disable_extra_networks = false,
            firstpass_image = "",
            comments = emptyMap(),
            enable_hr = false,
            firstphase_width = 0,
            firstphase_height = 0,
            hr_scale = 2f,
            hr_upscaler = "",
            hr_second_pass_steps = 0,
            hr_resize_x = 0,
            hr_resize_y = 0,
            hr_checkpoint_name = "",
            hr_sampler_name = "",
            hr_scheduler = "",
            hr_prompt = "",
            hr_negative_prompt = "",
            force_task_id = "",
            sampler_index = "Euler",
            script_name = "",
            script_args = emptyList(),
            send_images = true,
            save_images = false,
            alwayson_scripts = emptyMap(),
            infotext = "Generated with Stable Diffusion"
        )

        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.generateImage(requestBody)
                if (response.images.isNotEmpty()) {
                    val base64String = response.images[0]
                    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    imageBitmap.value = bitmap
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}