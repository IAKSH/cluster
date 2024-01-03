package me.iaksh.cluster.ui;

import javafx.application.Platform;
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
import me.iaksh.cluster.core.mixer.Exporter;
import me.iaksh.cluster.core.mixer.NESLikeSynthesizer;
import me.iaksh.cluster.core.notation.EqualTempNote;
import me.iaksh.cluster.core.notation.Note;
import me.iaksh.cluster.core.notation.Section;
import me.iaksh.cluster.core.player.Player;
import me.iaksh.cluster.core.waveform.effect.Effect;
import me.iaksh.cluster.core.waveform.effect.ExpGradientEffect;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

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

    public Label bpmDisplayLabel;
    public Slider bpmSlider;
    public Label volumeDisplayLabel;
    public Slider volumeSlider;
    public Button playButton;
    public Button pauseButton;
    public Button resetButton;
    public Button exportButton;
    public TextField channelTextField;
    public TextField noteIdTextField;
    public TextField noteFractionTextField;
    public TextField noteSimpleScoreTextField;
    public TextField noteOctaveShiftTextField;
    public TextField noteSemitoneShiftTextField;
    public TextField exportPathTextField;
    public CheckBox noteIsDottedCheckBox;
    public Button addNoteButton;
    public Button editNoteButton;
    public Button deleteNoteButton;
    public TextField sectionInfoTextField;
    public Button sectionBeginButton;
    public Button sectionEndButton;
    public CheckBox squareAChannelCheckBox;
    public CheckBox squareBChannelCheckBox;
    public CheckBox triangleChannelCheckBox;
    public CheckBox noiseChannelCheckBox;
    public TableColumn squareAIdColumn;
    public TableColumn squareAScoreColumn;
    public TableColumn squareADottedColumn;
    public TableColumn squareAOctaveColumn;
    public TableColumn squareBIdColumn;
    public TableColumn squareBScoreColumn;
    public TableColumn squareBDottedColumn;
    public TableColumn squareBOctaveColumn;
    public TableColumn triangleIdColumn;
    public TableColumn triangleScoreColumn;
    public TableColumn triangleDottedColumn;
    public TableColumn triangleOctaveColumn;
    public TableColumn noiseIdColumn;
    public TableColumn noiseScoreColumn;
    public TableColumn noiseDottedColumn;
    public TableColumn noiseOctaveColumn;
    public TableColumn squareAFractionColumn;
    public TableColumn squareBFractionColumn;
    public TableColumn triangleFractionColumn;
    public TableColumn noiseFractionColumn;
    public TableColumn noiseSemitoneColumn;
    public TableColumn triangleSemitoneColumn;
    public TableColumn squareBSemitoneColumn;
    public TableColumn squareASemitoneColumn;
    public TableView squareATableView;
    public TableView squareBTableView;
    public TableView triangleTableView;
    public TableView noiseTableView;

    private static boolean labelUpdateThreadShouldRunning = true;
    private ObservableList<NoteRecord> squareARecords;
    private ObservableList<NoteRecord> squareBRecords;
    private ObservableList<NoteRecord> triangleRecords;
    private ObservableList<NoteRecord> noiseRecords;

    @FXML
    public void onPlayButtonClick(ActionEvent actionEvent) {
        float volume = (float) (volumeSlider.getValue() / 100.0f);
        int bpm = (int) (Math.floor(bpmSlider.getValue()));
        new Thread(() -> {
            playButton.setDisable(true);
            volumeSlider.setDisable(true);
            bpmSlider.setDisable(true);
            new Player(volume).play(new NESLikeSynthesizer(bpm).genWaveform(genAllSections()));
            playButton.setDisable(false);
            volumeSlider.setDisable(false);
            bpmSlider.setDisable(false);
        }).start();
    }

    @FXML
    public void onPauseButtonClick(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    public void onResetButtonClick(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    public void onExportButtonClick(ActionEvent actionEvent) {
        int bpm = (int) (Math.floor(bpmSlider.getValue()));
        new Thread(() -> {
            playButton.setDisable(true);
            volumeSlider.setDisable(true);
            bpmSlider.setDisable(true);
            Exporter exporter = new NESLikeSynthesizer(bpm);
            exporter.saveWaveform(exportPathTextField.getText(),exporter.genWaveform(genAllSections()));
            playButton.setDisable(false);
            volumeSlider.setDisable(false);
            bpmSlider.setDisable(false);
        }).start();
    }

    @FXML
    public void onAddNoteButtonClick(ActionEvent actionEvent) {
        validateNoteInputArgs();
        switch (Integer.parseInt(channelTextField.getText())) {
            case 0 -> addNoteRecord(squareARecords);
            case 1 -> addNoteRecord(squareBRecords);
            case 2 -> addNoteRecord(triangleRecords);
            case 3 -> addNoteRecord(noiseRecords);
        }
    }

    @FXML
    public void onEditNoteButtonClick(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    public void onDeleteNoteButtonClick(ActionEvent actionEvent) {
        validateNoteInputArgs();
        ObservableList<NoteRecord> list = null;
        TableView view = switch (Integer.parseInt(channelTextField.getText())) {
            case 0 -> {
                list = squareARecords;
                yield squareATableView;
            }
            case 1 -> {
                list = squareBRecords;
                yield squareBTableView;
            }
            case 2 -> {
                list = triangleRecords;
                yield triangleTableView;
            }
            case 3 -> {
                list = noiseRecords;
                yield noiseTableView;
            }
            default -> throw new RuntimeException("this should never happen");
        };
        list.remove(view.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onSectionBeginButton(ActionEvent actionEvent) {
        validateNoteInputArgs();
        switch (Integer.parseInt(channelTextField.getText())) {
            case 0 -> addSectionRecord(squareARecords,true);
            case 1 -> addSectionRecord(squareBRecords,true);
            case 2 -> addSectionRecord(triangleRecords,true);
            case 3 -> addSectionRecord(noiseRecords,true);
        }
    }

    @FXML
    public void onSectionEndButton(ActionEvent actionEvent) {
        validateNoteInputArgs();
        switch (Integer.parseInt(channelTextField.getText())) {
            case 0 -> addSectionRecord(squareARecords,false);
            case 1 -> addSectionRecord(squareBRecords,false);
            case 2 -> addSectionRecord(triangleRecords,false);
            case 3 -> addSectionRecord(noiseRecords,false);
        }
    }

    private ExpGradientEffect tempEffect = new ExpGradientEffect();
    private ArrayList<Section> genSectionsFromRecords(ObservableList<NoteRecord> records) {
        tempEffect.setExpCoefficient(4.0f);
        ArrayList<Section> sections = new ArrayList<>();
        for(NoteRecord record : records) {
            if(record.getNoteFraction() == 0.0f) {
                sections.add(new Section(record.getSimpleScore(),record.getOctaveShift()));
            } else {
                sections.get(sections.size() - 1).getNotes().add(new EqualTempNote(
                        record.getNoteFraction(),
                        record.isIsDotted(),
                        record.getSimpleScore(),
                        record.getOctaveShift(),
                        record.getSemitoneShift(),
                        tempEffect
                ));
            }
        }
        return sections;
    }

    private ArrayList<ArrayList<Section>> genAllSections() {
        ArrayList<ArrayList<Section>> notes = new ArrayList<>();
        notes.add(genSectionsFromRecords(squareARecords));
        notes.add(genSectionsFromRecords(squareBRecords));
        notes.add(genSectionsFromRecords(triangleRecords));
        notes.add(genSectionsFromRecords(noiseRecords));
        return notes;
    }

    private void addSectionRecord(ObservableList<NoteRecord> list,boolean isStarting) {
        int id = Integer.parseInt(noteIdTextField.getText());
        int[] info = getSectionInfo();
        // 临时这样：
        // noteFraction = 0则为Section标记
        // 此时isDotted表示起（true）止（false）
        list.add(new NoteRecord(id,0,isStarting,info[0],info[1],0));
    }

    private int[] getSectionInfo() {
        validateSectionInputArgs();
        int[] arr = new int[2];
        String[] parts = sectionInfoTextField.getText().split("/");
        arr[0] = Integer.parseInt(parts[0]);
        arr[1] = Integer.parseInt(parts[1]);
        return arr;
    };

    private void validateSectionInputArgs() {
        //TODO
    }

    private void initLabelUpdateThread() {
        new Thread(() -> {
            try {
                while (labelUpdateThreadShouldRunning) {
                    String bpmStr = String.valueOf((int) Math.floor(bpmSlider.getValue()));
                    String volumeStr = String.valueOf((int) Math.floor(volumeSlider.getValue()));
                    Platform.runLater(() -> {
                        bpmDisplayLabel.setText(bpmStr);
                        volumeDisplayLabel.setText(volumeStr);
                    });
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initSquareATableView() {
        squareAIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        squareAFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        squareADottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        squareAScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        squareAOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        squareASemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        squareATableView.setItems(squareARecords);
    }

    private void initSquareBTableView() {
        squareBIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        squareBFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        squareBDottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        squareBScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        squareBOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        squareBSemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        squareBTableView.setItems(squareBRecords);
    }

    private void initTriangleTableView() {
        triangleIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        triangleFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        triangleDottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        triangleScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        triangleOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        triangleSemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        triangleTableView.setItems(triangleRecords);
    }

    private void initNoiseTableView() {
        noiseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        noiseFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        noiseDottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        noiseScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        noiseOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        noiseSemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        noiseTableView.setItems(noiseRecords);
    }

    private void initAllTableViews() {
        try {
            initSquareATableView();
            initSquareBTableView();
            initTriangleTableView();
            initNoiseTableView();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initRecords() {
        squareARecords = FXCollections.observableArrayList();
        squareBRecords = FXCollections.observableArrayList();
        triangleRecords = FXCollections.observableArrayList();
        noiseRecords = FXCollections.observableArrayList();
    }

    private void addNoteRecord(ObservableList<NoteRecord> list) {
        int id = Integer.parseInt(noteIdTextField.getText());
        float noteFraction = Float.parseFloat(noteFractionTextField.getText());
        boolean isDotted = noteIsDottedCheckBox.isSelected();
        int simpleScore = Integer.parseInt(noteSimpleScoreTextField.getText());
        int octaveShift = Integer.parseInt(noteOctaveShiftTextField.getText());
        int semitoneShift = Integer.parseInt(noteSemitoneShiftTextField.getText());
        list.add(new NoteRecord(id,noteFraction,isDotted,simpleScore,octaveShift,semitoneShift));
    }

    private void validateNoteInputArgs() {
        // TODO
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initRecords();
        initAllTableViews();
        initLabelUpdateThread();
    }

    public static void setLabelUpdateThreadShouldRunning(boolean labelUpdateThreadShouldRunning) {
        MainWindow.labelUpdateThreadShouldRunning = labelUpdateThreadShouldRunning;
    }
}
