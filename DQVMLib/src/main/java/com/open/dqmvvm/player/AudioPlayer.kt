package com.open.dqmvvm.player

import android.media.*
import android.media.AudioAttributes.CONTENT_TYPE_MUSIC
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.dqmvvm.R
import kotlinx.android.synthetic.main.activity_audio_player.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@Route(path="/lib/audio")
class AudioPlayer : AppCompatActivity() {

    private lateinit var audioRecord : AudioRecord
    private val DEFAULT_SOURCE = MediaRecorder.AudioSource.MIC
    private val DEFAULT_SAMPLE_RATE = 44100
    private val DEFAULT_CHANNEL_CONFIG: Int = android.media.AudioFormat.CHANNEL_IN_STEREO
    private val DEFAULT_AUDIO_FORMAT: Int = android.media.AudioFormat.ENCODING_PCM_16BIT
    var buffer:ByteArray?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)
        startRecord.setOnClickListener{
            initAudioRecord()
        }
        stopRecord.setOnClickListener{
            stopRecord()
        }
        startPlay.setOnClickListener {
        }
    }

    private fun initAudioRecord() {
        val mMinBufferSize = AudioRecord.getMinBufferSize(DEFAULT_SAMPLE_RATE,DEFAULT_CHANNEL_CONFIG,DEFAULT_AUDIO_FORMAT)
        audioRecord = AudioRecord(DEFAULT_SOURCE,DEFAULT_SAMPLE_RATE,DEFAULT_CHANNEL_CONFIG,DEFAULT_AUDIO_FORMAT,mMinBufferSize)
        audioRecord.startRecording()
        runBlocking (Dispatchers.IO){
            while (true){
                buffer = ByteArray(mMinBufferSize)
                val read = audioRecord.read(buffer!!, 0, mMinBufferSize)
                if (read != AudioRecord.ERROR_INVALID_OPERATION){
                    initAudioTrack()
                }
            }
        }
    }

    private fun stopRecord(){
        audioRecord.startRecording()
        audioRecord.release()
    }

    private fun initAudioTrack() {
        val minBufferSize = AudioTrack.getMinBufferSize(DEFAULT_SAMPLE_RATE,DEFAULT_CHANNEL_CONFIG,DEFAULT_AUDIO_FORMAT)
        val audioTrack = AudioTrack(AudioAttributes.Builder().setContentType(CONTENT_TYPE_MUSIC).build(),AudioFormat.Builder().build(),minBufferSize,AudioTrack.MODE_STREAM,AudioManager.AUDIO_SESSION_ID_GENERATE)
        audioTrack.write(buffer!!,0,minBufferSize)
        audioTrack.play()
    }
}
