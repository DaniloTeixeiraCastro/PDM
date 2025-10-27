package ipca.example.calculator

import kotlin.math.sqrt

class CalculatorBrain {

    private var accumulator = 0.0
    private var pendingBinaryOperation: PendingBinaryOperation? = null

    private data class PendingBinaryOperation(val function: (Double, Double) -> Double, val firstOperand: Double)

    fun setOperand(operand: Double) {
        accumulator = operand
    }

    fun performOperation(symbol: String) {
        when (symbol) {
            "AC" -> {
                accumulator = 0.0
                pendingBinaryOperation = null
            }
            "√" -> accumulator = sqrt(accumulator) // Changed from "V"
            "%" -> accumulator = accumulator / 100.0
            "=" -> {
                if (pendingBinaryOperation != null) {
                    accumulator = pendingBinaryOperation!!.function(pendingBinaryOperation!!.firstOperand, accumulator)
                    pendingBinaryOperation = null
                }
            }
            else -> { // Binary operations
                val operation = getOperationFunction(symbol)
                if (operation != null){
                    pendingBinaryOperation = PendingBinaryOperation(operation, accumulator)
                }else{
                    // do nothing
                }

            }
        }
    }

    val result: Double
        get() = accumulator

    private fun getOperationFunction(symbol: String): ((Double, Double) -> Double)? {
        return when (symbol) {
            "+" -> { op1, op2 -> op1 + op2 }
            "-" -> { op1, op2 -> op1 - op2 }
            "×" -> { op1, op2 -> op1 * op2 }
            "÷" -> { op1, op2 -> op1 / op2 }
            else -> null
        }
    }
}