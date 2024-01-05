package me.iaksh.cluster.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import me.iaksh.cluster.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class NoteEditPageController implements Initializable {
    public TextField channelTextField;
    public TextField noteFractionTextField;
    public TextField simpleScoreTextField;
    public TextField octaveTextureField;
    public TextField semitoneTextField;
    public CheckBox dottedCheckbox;
    public Button saveButton;

    private MainWindowController.NoteRecord lastSelectedNoteRecord;

    @FXML
    public void onSaveButtonClick(ActionEvent actionEvent) {
        MainWindowController.NoteRecord record = FXApplication.getInstance().getMainWindowController().getSelectedNoteRecord();
        record.setNoteFraction(Float.parseFloat(noteFractionTextField.getText()));
        record.setSimpleScore(Integer.parseInt(simpleScoreTextField.getText()));
        record.setOctaveShift(Integer.parseInt(octaveTextureField.getText()));
        record.setSemitoneShift(Integer.parseInt(semitoneTextField.getText()));
        record.setIsDotted(dottedCheckbox.isSelected());

        FXApplication.getInstance().getMainWindowController().getSelectedTableView().refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUpdateThread();
    }

    private void initUpdateThread() {
        new Thread(() -> {
            try {
                while (!FXApplication.getInstance().isClosing()) {
                    MainWindowController.NoteRecord currentSelectedRecord = FXApplication.getInstance().getMainWindowController().getSelectedNoteRecord();
                    if(currentSelectedRecord != null) {
                        if(lastSelectedNoteRecord != currentSelectedRecord) {
                            lastSelectedNoteRecord = currentSelectedRecord;
                            channelTextField.setText(String.valueOf(FXApplication.getInstance().getMainWindowController().getSelectedChannelId()));
                            noteFractionTextField.setText(String.valueOf(currentSelectedRecord.getNoteFraction()));
                            simpleScoreTextField.setText(String.valueOf(currentSelectedRecord.getSimpleScore()));
                            octaveTextureField.setText(String.valueOf(currentSelectedRecord.getOctaveShift()));
                            semitoneTextField.setText(String.valueOf(currentSelectedRecord.getSemitoneShift()));
                            dottedCheckbox.setSelected(currentSelectedRecord.isIsDotted());
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
