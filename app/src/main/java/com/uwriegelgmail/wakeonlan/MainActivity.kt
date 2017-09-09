package com.uwriegelgmail.wakeonlan

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val awaker = findViewById<Button>(R.id.awaker)
        awaker.setOnClickListener {
            wake()
        }
    }

    private fun wake() {
        try
        {
            val faden = Thread()
            {
                try
                {
                    val serverSocket = DatagramSocket(11000)
                    serverSocket.setBroadcast(true)
                    serverSocket.connect(InetAddress.getByName("192.168.178.255"), 11000)

                    val sendData = ByteArray(1024)
                    val capitalizedSentence = "Das kommt vom lieben Androiden"
                    //sendData = capitalizedSentence.getBytes();

                    var counter = 0
                    for(y in 0..5)
                        sendData[counter++]= 0xFF.toByte()
                        // now repeat MAC 16 times
                    for(y in 0..15)
                    {
                        sendData[counter++]= 0xD4.toByte()
                        sendData[counter++]= 0x3D.toByte()
                        sendData[counter++]= 0x7E.toByte()
                        sendData[counter++]= 0x4E.toByte()
                        sendData[counter++]= 0x09.toByte()
                        sendData[counter++]= 0x6B.toByte()
                    }
                    val sendPacket = DatagramPacket(sendData, sendData.size)
                    serverSocket.send(sendPacket)
                    serverSocket.close()
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                }
            }
            faden.start()
        }
        catch (e: Exception)
        {
            e.printStackTrace();
        }

    }
}
