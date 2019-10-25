
package edu.gatech.seclass.sdpencryptor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class SDPEncryptorActivity extends AppCompatActivity {

    // Create variables
    private EditText textMessageInput;
    private EditText textKeyPhrase;
    private EditText textShiftNumber;
    private TextView textCipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables with related screen fields
        textMessageInput = (EditText)findViewById(R.id.messageInput);
        textKeyPhrase = (EditText)findViewById(R.id.keyPhrase);
        textShiftNumber = (EditText)findViewById(R.id.shiftNumber);
        textShiftNumber.setText("0");
        textCipher = (TextView)findViewById(R.id.cipherText);
    }

    public void handleClick(View view) {
        /* Determines the correctness of the user inputs on the app. If every
        * input is correct, then produces the encrypted message when the user
        * clicks the ENCRYPT button */

        boolean isInputValid = true;

        // Tests for certain situations by producing error messages next to
        // the user input fields
        if (TextUtils.isEmpty(textMessageInput.getText().toString())) {
            textCipher.setText("");
            textMessageInput.setError("Message must contain letters");
            isInputValid = false;
        }
        else if (TextUtils.isDigitsOnly(textMessageInput.getText().toString())) {
            textCipher.setText("");
            textMessageInput.setError("Message must contain letters");
            isInputValid = false;
        }

        if (TextUtils.isEmpty(textKeyPhrase.getText().toString())) {
            textCipher.setText("");
            textKeyPhrase.setError("Key Phrase must contain only letters");
            isInputValid = false;
        }
        else if (!(textKeyPhrase.getText().toString().matches("^[a-zA-Z]+$"))) {
            textCipher.setText("");
            textKeyPhrase.setError("Key Phrase must contain only letters");
            isInputValid = false;
        }

        String strShiftNumber = textShiftNumber.getText().toString();
        int intShiftNumber = Integer.parseInt(strShiftNumber);

        if (!(intShiftNumber >= 1)) {
            textCipher.setText("");
            textShiftNumber.setError("Shift Number must be >= 1");
            isInputValid = false;
        }

        // If everything looks good, it produces the encrypted message
        if (view.getId() == R.id.encryptButton && isInputValid) {
            String encryptedMessage;
            String rotatedMessage;
            String shiftNumber = textShiftNumber.getText().toString();

            String message = textMessageInput.getText().toString();
            String key = textKeyPhrase.getText().toString();
            encryptedMessage = vigenereCipherEncrypt(message, key);
            rotatedMessage = rotateEncryptedMessage(encryptedMessage, shiftNumber);
            textCipher.setText(rotatedMessage);
        }
    }

    private String vigenereCipherEncrypt(String message, String key) {
        /* Encrypts the message by determining which letters are upper&lower
        * case as well as appending the encrypted message with the numbers and
        * other special characters such as space without changing them */

        // Creates and initializes variables
        StringBuilder encryptedMessage = new StringBuilder();
        char[] ch = message.toCharArray();
        key = key.toUpperCase();
        char nowUpper;
        char temp;
        char nowLower;

        // Go thru the message by checking for the upper&lower case letters and
        // encrypt the message by using the Vigenere Cipher formula
        for (int i = 0, j = 0; i < message.length(); i++) {
            if (Character.isLetter(ch[i])) {
                if (Character.isLowerCase(ch[i])) {
                    nowUpper = Character.toUpperCase(ch[i]);
                    temp = (char) ((nowUpper + key.charAt(j) - 2 * 'A') % 26 + 'A');
                    nowLower = Character.toLowerCase(temp);
                    encryptedMessage.append(nowLower);
                    j = ++j % key.length();
                }
                else {
                    encryptedMessage.append((char) ((ch[i] + key.charAt(j) - 2 * 'A') % 26 + 'A'));
                    j = ++j % key.length();
                }
            }
            else if ((Character.isSpaceChar(ch[i]))) {
                encryptedMessage.append(" ");
                j = ++j % key.length();
            }
            else {
                encryptedMessage.append(ch[i]);
                j = ++j % key.length();
            }
        }
        return encryptedMessage.toString();
    }

    private String rotateEncryptedMessage(String encryptedMessage, String shiftNumber) {
        /* Rotates the encrypted message by shifting the letters to the end by
        * determining their new location with the given shift number */

        // Creates and initializes variables
        String rotateEncryptedMessage = "";
        int intShiftNumber = Integer.parseInt(shiftNumber);
        String toBeRotated = encryptedMessage + encryptedMessage;
        char[] ch = toBeRotated.toCharArray();
        int newShiftNumber;

        // Checks whether the message is empty before determining the new shift
        // number by using MOD
        if ((encryptedMessage.length() >= 1) && (intShiftNumber > encryptedMessage.length())) {
            newShiftNumber = intShiftNumber % encryptedMessage.length();
        }
        else {
            newShiftNumber = intShiftNumber;
        }

        // Goes thru the char Array which has the duplicate message value to
        // determine the beginning of the shift character
        for (int i = 0; i < ch.length; i++) {
            if (i >= newShiftNumber && i < (newShiftNumber + encryptedMessage.length())) {
                rotateEncryptedMessage += ch[i];
            }
            else
                continue;
        }
        return rotateEncryptedMessage;
    }
}

