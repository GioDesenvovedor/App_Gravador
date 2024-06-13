package com.example.appmidia.model

import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import java.io.IOException
import java.nio.file.Path

class AudioRecorder(private val outputFilePath: String) {

    private var mediaRecorder: MediaRecorder? = null // Instância do MediaRecorder para gravação


    fun startRecording(){
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)// Define o microfone como fonte de áudio
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)// Define o formato de saída
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)// Define o codificador de áudio
            setOutputFile(outputFilePath)  // Define o arquivo de saída

            try {
                prepare() // Prepara o MediaRecorder para gravação
                start()   // Inicia a gravação
                Log.i("AudioRecorder", "Gravação iniciada")

            }catch (e: IOException){
                Log.e("AudioRecorder", "Falha iniciada ${e.message}")
                e.printStackTrace()
            }
        }
    }

    // Para a gravação de áudio
    fun stopRecording(){
        mediaRecorder?.apply {
            stop() // Para a gravação
            release() // Libera os recursos do MediaRecorder
            Log.i("AudioRecorder", "gravação parada")
        }
        mediaRecorder = null // Reseta a instância do MediaRecorder
    }

}