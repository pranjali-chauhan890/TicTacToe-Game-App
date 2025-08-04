package com.v2v.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button[][] buttons = new Button[3][3];
    TextView textStatus, textPlayerX, textPlayerO;
    Button resetButton;

    boolean isXTurn = true;
    int roundCount = 0;
    int xScore = 0, oScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textStatus = findViewById(R.id.statusText);
        textPlayerX = findViewById(R.id.playerXScore);
        textPlayerO = findViewById(R.id.playerOScore);
        resetButton = findViewById(R.id.resetButton);

        // Initialize buttons by ID (button00, button01, ..., button22)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String btnID = "button" + i + j;
                int resID = getResources().getIdentifier(btnID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

                int finalI = i;
                int finalJ = j;

                buttons[i][j].setOnClickListener(v -> handleMove(buttons[finalI][finalJ]));
            }
        }

        resetButton.setOnClickListener(v -> resetBoard());
    }

    void handleMove(Button btn) {
        if (!btn.getText().toString().equals("")) return;

        btn.setText(isXTurn ? "X" : "O");
        roundCount++;

        if (checkWinner()) {
            if (isXTurn) {
                xScore++;
                textPlayerX.setText("Player X: " + xScore);
                showMessage("Player X wins!");
            } else {
                oScore++;
                textPlayerO.setText("Player O: " + oScore);
                showMessage("Player O wins!");
            }
            disableAll();
        } else if (roundCount == 9) {
            showMessage("It's a draw!");
        } else {
            isXTurn = !isXTurn;
            textStatus.setText("Turn: Player " + (isXTurn ? "X" : "O"));
        }
    }

    boolean checkWinner() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = buttons[i][j].getText().toString();

        // Rows and Columns
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2]))
                return true;
            if (!board[0][i].isEmpty() && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i]))
                return true;
        }

        // Diagonals
        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]))
            return true;
        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]))
            return true;

        return false;
    }

    void disableAll() {
        for (Button[] row : buttons)
            for (Button btn : row)
                btn.setEnabled(false);
    }

    void resetBoard() {
        for (Button[] row : buttons)
            for (Button btn : row) {
                btn.setText("");
                btn.setEnabled(true);
            }

        roundCount = 0;
        isXTurn = true;
        textStatus.setText("Turn: Player X");
    }

    void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        textStatus.setText(msg);
    }
}