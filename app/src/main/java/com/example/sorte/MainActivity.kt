package com.example.sorte

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)


        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

       //banco de dados preferencias
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)
        result?.let {
            txtResult.text = "Ultima aposta: $it"
        }


        btnGenerate.setOnClickListener {

            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        if (text.isEmpty()){
            Toast.makeText(this,"informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

           val qtd = text.toInt()

            if (qtd < 6 || qtd > 15) {
                Toast.makeText(this, "informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
                return
            }
                val numbers = mutableSetOf<Int>()
                val random = Random()

                while(true) {
                    val number = random.nextInt(60) // 0...59
                    numbers.add(number + 1)

                    if ( numbers.size == qtd) {
                        break
                    }
                }

                txtResult.text = numbers.joinToString(" - ")

                val editor = prefs.edit()
                editor.putString("result", txtResult.text.toString())
                editor.apply()

    }

}
