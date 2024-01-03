package me.iaksh.cluster.ui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import me.iaksh.cluster.core.notation.EqualTempNote;
import me.iaksh.cluster.core.notation.Note;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SectionViewController implements Initializable {

    public class NoteRecord {
        private SimpleIntegerProperty id;
        private SimpleFloatProperty noteFraction;
        private SimpleBooleanProperty isDotted;
        private SimpleIntegerProperty simpleScore;
        private SimpleIntegerProperty octaveShift;
        private SimpleIntegerProperty semitoneShift;

        public NoteRecord(int id, float noteFraction, boolean isDotted, int simpleScore, int octaveShift, int semitoneShift) {
            this.id = new SimpleIntegerProperty(id);
            this.noteFraction = new SimpleFloatProperty(noteFraction);
            this.isDotted = new SimpleBooleanProperty(isDotted);
            this.simpleScore = new SimpleIntegerProperty(simpleScore);
            this.octaveShift = new SimpleIntegerProperty(octaveShift);
            this.semitoneShift = new SimpleIntegerProperty(semitoneShift);
        }

        public int getId() {
            return id.get();
        }

        public float getNoteFraction() {
            return noteFraction.get();
        }

        public int getOctaveShift() {
            return octaveShift.get();
        }

        public int getSemitoneShift() {
            return semitoneShift.get();
        }

        public int getSimpleScore() {
            return simpleScore.get();
        }

        public boolean isIsDotted() {
            return isDotted.get();
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public void setIsDotted(boolean isDotted) {
            this.isDotted.set(isDotted);
        }

        public void setNoteFraction(float noteFraction) {
            this.noteFraction.set(noteFraction);
        }

        public void setOctaveShift(int octaveShift) {
            this.octaveShift.set(octaveShift);
        }

        public void setSemitoneShift(int semitoneShift) {
            this.semitoneShift.set(semitoneShift);
        }

        public void setSimpleScore(int simpleScore) {
            this.simpleScore.set(simpleScore);
        }
    }

    @FXML
    public TableView<NoteRecord> notesTableView;
    @FXML
    public TableColumn colId;
    @FXML
    public TableColumn colSimpleScore;
    @FXML
    public TableColumn colIsDotted;
    @FXML
    public TableColumn colOctaveShift;
    @FXML
    public TableColumn colSemitoneShift;
    @FXML
    public TableColumn colNoteFraction;
    @FXML
    public MenuItem MenuItemLoadSectionFromFile;
    @FXML
    public MenuItem MenuItemAddNote;
    @FXML
    public MenuItem MenuItemDeleteNote;
    @FXML
    public TextField NoteFractionInputArea;
    @FXML
    public TextField SimpleScoreInputArea;
    @FXML
    public TextField DotInputArea;
    @FXML
    public TextField OctaveShiftInputArea;
    @FXML
    public TextField SemitoneShiftinputArea;
    @FXML
    public Button AddNoteButton;
    @FXML
    public TextField idInputArea;

    private ObservableList<NoteRecord>[] datas;
    private ObservableList<NoteRecord> currentData;

    public void switchData(int i) {
        // TODO: 检查i的取值
        currentData = datas[i];
        notesTableView.setItems(currentData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datas = new ObservableList[4];
        for(int i = 0;i < datas.length;i++)
            datas[i] = FXCollections.observableArrayList();
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNoteFraction.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
            colIsDotted.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
            colSimpleScore.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
            colOctaveShift.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
            colSemitoneShift.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addNote() {
        // TODO: 验证下面用的几个输入框的内容是否合法，类型是否正确
        // 注：id从1开始

        int id = Integer.parseInt(idInputArea.getText());
        currentData.add(new NoteRecord(id,
                Float.parseFloat(NoteFractionInputArea.getText()),
                Boolean.parseBoolean(DotInputArea.getText()),
                Integer.parseInt(SimpleScoreInputArea.getText()),
                Integer.parseInt(OctaveShiftInputArea.getText()),
                Integer.parseInt(SemitoneShiftinputArea.getText())));

        idInputArea.setText(String.valueOf(id + 1));
    }

    public void onMenuItemLoadSectionFromFile(ActionEvent actionEvent) {
    }

    public void onMenuItemAddNote(ActionEvent actionEvent) {
        addNote();
    }

    public void onMenuItemDeleteNote(ActionEvent actionEvent) {
        currentData.remove(notesTableView.getSelectionModel().getSelectedItem());
    }

    public void onAddNoteButton(ActionEvent actionEvent) {
        addNote();
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        for(NoteRecord record : currentData) {
            notes.add(new EqualTempNote(
                    record.getNoteFraction(),
                    record.isIsDotted(),
                    record.getSimpleScore(),
                    record.getOctaveShift(),
                    record.getSemitoneShift()
                    ));
        }
        return notes;
    }
}
