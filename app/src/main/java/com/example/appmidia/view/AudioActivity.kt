package com.example.appmidia.view

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.appmidia.databinding.ActivityAudioBinding
import com.example.appmidia.viewmodel.AudioViewModel
import java.io.IOException

class AudioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAudioBinding
    private val audioViewModel: AudioViewModel by viewModels()

    private lateinit var outputFilePath: String //// Caminho do arquivo de saída
    private var mediaPlayer: MediaPlayer? = null


    // Solicitando permissão
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permmission ->
            if (permmission[Manifest.permission.RECORD_AUDIO]
                == true &&
                permmission[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true){

            }else{
                Toast.makeText(this,"Permissão necessarias não concedidas",
                    Toast.LENGTH_LONG).show()
                finish()
            }

        }



    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAudioBinding.inflate(layoutInflater)


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        outputFilePath = "${externalCacheDir?.absolutePath}/outputfile.3gp" // Define o caminho do arquivo de saída

        gravar()
        parar()
        player()
    }




    private fun gravar() {
       binding.btnGravar.setOnClickListener {
           Toast.makeText(this,"Gravando",Toast.LENGTH_LONG).show()
           if (allPermissionsGranted()){
               audioViewModel.startRecording(outputFilePath)
           }else{
               requestRecordAudioPermission()
           }
       }
    }



    private fun allPermissionsGranted(): Boolean {

      return  ContextCompat.checkSelfPermission(this,
      Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
              && ContextCompat.checkSelfPermission(this,
          Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    }

    private fun requestRecordAudioPermission() {
        requestPermissionLauncher.launch(arrayOf(Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    private fun parar() {
        binding.btnParar.setOnClickListener {
            Toast.makeText(this,"Parando",Toast.LENGTH_LONG).show()
            audioViewModel.stopRecording()
        }
    }

    private fun player() {

        binding.btnPlayer.setOnClickListener {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                try {
                    setDataSource(outputFilePath)
                    prepare()
                    start()
                    setOnCompletionListener {
                        Toast.makeText(this@AudioActivity, "Reprodução concluída", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: IOException){
                    e.printStackTrace()
                    Toast.makeText(this@AudioActivity, "ERRO AO REPRODUZIR AUDIO", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    override fun onDestroy(){
        super.onDestroy()
        mediaPlayer?.release()
    }
}