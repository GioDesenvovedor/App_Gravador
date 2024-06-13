package com.example.appmidia.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmidia.model.AudioRecorder

class AudioViewModel: ViewModel() {

    private var audioRecorder : AudioRecorder? = null //Instância do AudioRecorder



    // Inicia a gravação de áudio
    fun startRecording(outputFilePath: String){
        audioRecorder = AudioRecorder(outputFilePath) //Cria uma nova instância de AudioRecorder
        audioRecorder?.startRecording() // Chama o método do modelo para iniciar a gravação
    }

    // Para a gravação de áudio
    fun stopRecording(){
        audioRecorder?.stopRecording() // Chama o método do modelo para parar a gravação
    }


}