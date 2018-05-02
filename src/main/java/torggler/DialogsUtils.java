package torggler;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by ZacznijProgramowac.
 * https://www.youtube.com/zacznijprogramowac
 * http://zacznijprogramowac.net/
 */
public class DialogsUtils {

    private static final ResourceBundle bundle = FxmlUtils.getResourceBundle();


    public static void dialogWarningDPApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.WARNING);
        informationAlert.setTitle(bundle.getString("warningDP.title"));
        informationAlert.setHeaderText(bundle.getString("warningDP.header"));
        informationAlert.setContentText(bundle.getString("warningDP.content"));
        informationAlert.showAndWait();
    }

    public static void dialogSaveApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("save.title"));
        informationAlert.setHeaderText(bundle.getString("save.header"));
        informationAlert.setContentText(bundle.getString("save.content"));
        informationAlert.showAndWait();
    }

    public static void dialogExportCSVApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("exportCsv.title"));
        informationAlert.setHeaderText(bundle.getString("exportCsv.header"));
        informationAlert.setContentText(bundle.getString("exportCsv.content"));
        informationAlert.showAndWait();
    }

    public static void dialogAboutApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("about.title"));
        informationAlert.setHeaderText(bundle.getString("about.header"));
        informationAlert.setContentText(bundle.getString("about.content"));
        informationAlert.showAndWait();
    }

    public static void infoApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("info.title"));
        informationAlert.setHeaderText(bundle.getString("info.header"));
        informationAlert.setContentText(bundle.getString("info.content"));
        informationAlert.showAndWait();
    }

    public static Optional<ButtonType> confirmationDialog() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(bundle.getString("exit.title"));
        confirmationDialog.setHeaderText(bundle.getString("exit.header"));
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result;
    }

    public static void errorDialog(String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("error.title"));
        errorAlert.setHeaderText(bundle.getString("error.header"));

        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }

    public static String editDialog(String value) {
        TextInputDialog dialog = new TextInputDialog(value);
        dialog.setTitle(bundle.getString("edit.title"));
        dialog.setHeaderText(bundle.getString("edit.header"));
        dialog.setContentText(bundle.getString("edit.content"));
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }


}
