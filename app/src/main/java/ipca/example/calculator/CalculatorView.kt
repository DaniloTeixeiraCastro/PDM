package ipca.example.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ipca.example.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorView(
    modifier: Modifier = Modifier
) {

    var displayText by remember { mutableStateOf("0") }
    var userIsTyping by remember { mutableStateOf(false) }
    val brain by remember { mutableStateOf(CalculatorBrain()) }

    fun updateDisplay() {
        val result = brain.result
        displayText = if (result % 1.0 == 0.0) {
            result.toLong().toString()
        } else {
            result.toString()
        }
    }

    val onNumberClick: (String) -> Unit = { number ->
        if (userIsTyping) {
            if (!(number == "." && displayText.contains("."))) {
                displayText += number
            }
        } else {
            displayText = if (number == ".") "0." else number
            userIsTyping = true
        }
    }

    val onOperationClick: (String) -> Unit = { operation ->
        if (userIsTyping) {
            brain.setOperand(displayText.toDoubleOrNull() ?: 0.0)
            userIsTyping = false
        }
        brain.performOperation(operation)
        updateDisplay()
    }

    val onControlClick: (String) -> Unit = { control ->
        when (control) {
            "AC" -> {
                brain.performOperation("AC")
                displayText = "0"
                userIsTyping = false
            }
            "C" -> {
                if (displayText.length > 1) {
                    displayText = displayText.dropLast(1)
                } else {
                    displayText = "0"
                    userIsTyping = false
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            displayText,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.End,
            fontSize = TextUnit(value = 62f, type = TextUnitType.Sp),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )

        Row {
            CalculatorButton(label = "AC", onClick = onControlClick)
            CalculatorButton(label = "√", isOperation = true, onClick = onOperationClick)
            CalculatorButton(label = "%", isOperation = true, onClick = onOperationClick)
            CalculatorButton(
                label = "C",
                isOperation = true, // To keep the orange color
                onClick = onControlClick)
        }
        Row {
            CalculatorButton(label = "7", onClick = onNumberClick)
            CalculatorButton(label = "8", onClick = onNumberClick)
            CalculatorButton(label = "9", onClick = onNumberClick)
            CalculatorButton(label = "+", isOperation = true, onClick = onOperationClick)
        }
        Row {
            CalculatorButton(label = "4", onClick = onNumberClick)
            CalculatorButton(label = "5", onClick = onNumberClick)
            CalculatorButton(label = "6", onClick = onNumberClick)
            CalculatorButton(label = "-", isOperation = true, onClick = onOperationClick)
        }
        Row {
            CalculatorButton(label = "1", onClick = onNumberClick)
            CalculatorButton(label = "2", onClick = onNumberClick)
            CalculatorButton(label = "3", onClick = onNumberClick)
            CalculatorButton(label = "÷", isOperation = true, onClick = onOperationClick)
        }
        Row {
            CalculatorButton(label = "0", onClick = onNumberClick)
            CalculatorButton(label = ".", onClick = onNumberClick)
            CalculatorButton(label = "=", isOperation = true, onClick = onOperationClick)
            CalculatorButton(label = "×", isOperation = true, onClick = onOperationClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorViewPreview() {
    CalculatorTheme {
        CalculatorView()
    }
}
