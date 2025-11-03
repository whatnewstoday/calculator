package vn.edu.hust.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstNumber = 0.0
    private var lastResult = 0.0
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        // --- Lấy các nút số ---
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        ).map { findViewById<Button>(it) }

        // Gắn sự kiện nhập số
        numberButtons.forEach { btn ->
            btn.setOnClickListener {
                if (isNewInput) {
                    currentInput = ""
                    isNewInput = false
                }
                currentInput += btn.text
                tvResult.text = currentInput
            }
        }

        // --- Các nút phép toán ---
        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { setOperator("x") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { setOperator("/") }

        // --- Nút dấu bằng ---
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }

        // --- Nút CE: xóa toàn bộ ---
        findViewById<Button>(R.id.btnCE).setOnClickListener {
            firstNumber = 0.0
            operator = ""
            currentInput = ""
            tvResult.text = "0"
        }

        // --- Nút C: xóa nhập hiện tại ---
        findViewById<Button>(R.id.btnC).setOnClickListener {
            currentInput = ""
            tvResult.text = "0"
        }

        // --- Nút BS: xóa 1 ký tự ---
        findViewById<Button>(R.id.btnBS).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                tvResult.text = if (currentInput.isEmpty()) "0" else currentInput
            }
        }

        // --- Nút đổi dấu ---
        findViewById<Button>(R.id.btnSign).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                if (currentInput.startsWith("-")) {
                    currentInput = currentInput.drop(1)
                } else {
                    currentInput = "-$currentInput"
                }
                tvResult.text = currentInput
            }
        }

        // --- Nút dấu chấm ---
        findViewById<Button>(R.id.btnDot).setOnClickListener {
            if (!currentInput.contains(".")) {
                if (currentInput.isEmpty()) currentInput = "0"
                currentInput += "."
                tvResult.text = currentInput
            }
        }
    }

    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toDouble()
            operator = op
            currentInput = ""
        }
    }

    private fun calculateResult() {
        if (operator.isNotEmpty() && currentInput.isNotEmpty()) {
            val secondNumber = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "x" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                else -> 0.0
            }
            lastResult = result
            tvResult.text = result.toString()
            currentInput = result.toString()
            operator = ""
            isNewInput = true
        }
    }
}