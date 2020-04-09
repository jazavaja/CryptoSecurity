/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secure;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField plain;
    @FXML
    private TextField password;
    @FXML
    private TextArea result;

    @FXML
    private void clearPlain(ActionEvent event) {
        plain.setText("");
    }

    @FXML
    private void pastePlain(ActionEvent event) {
        plain.setText(paste());
        
    }

    @FXML
    private void clearPass(ActionEvent event) {
        password.setText("");
    }


    @FXML
    private void copyResult(ActionEvent event) {
        copy(result.getText());
    }

    @FXML
    private void encrypt(ActionEvent event) {
        if (plain.getText().equals("")) {
            alert("Empty plain", "Please Enter in TextField Plain");
        } else if (password.getText().equals("")) {
            alert("Empty Password", "Please Enter in TextField password");
        } else {
            final String strToEncrypt = plain.getText();
            final String strPssword = password.getText();
            AES.setKey(strPssword);
            AES.encrypt(strToEncrypt);
            String u = AES.getEncryptedString();
            result.setText(u);
        }
    }

    @FXML
    private void decrypt(ActionEvent event) {
        if (plain.getText().equals("")) {
            alert("Empty plain", "Please Enter in TextField Plain");
        } else if (password.getText().equals("")) {
            alert("Empty Password", "Please Enter in TextField password");
        } else {
            final String strToEncrypt = plain.getText();
            final String strPssword = password.getText();
            AES.setKey(strPssword);
            try {
                AES.decrypt(strToEncrypt);
                String u = AES.getDecryptedString();
                result.setText(u);
            } catch (Exception ex) {
                alert("Password Incorrect", "Your password For decrypt is incorrect please Try Agian with Another password");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void alert(String textTitle, String textContent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(textTitle);
        alert.setContentText(textContent);
        alert.showAndWait();
    }

    public void copy(String string) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(string);

        clipboard.setContent(content);
    }

    public String paste() {
        Clipboard c = Clipboard.getSystemClipboard();
        return c.getString();
    }
}
