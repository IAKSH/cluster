package me.iaksh.cluster.ui;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import me.iaksh.cluster.Main;
import me.iaksh.cluster.core.mixer.NESLikeSynthesizer;
import me.iaksh.cluster.core.notation.EqualTempNote;
import me.iaksh.cluster.core.notation.Section;
import me.iaksh.cluster.core.player.Player;
import me.iaksh.cluster.core.waveform.effect.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    public class NoteRecord {
        private SimpleFloatProperty noteFraction;
        private SimpleBooleanProperty isDotted;
        private SimpleIntegerProperty simpleScore;
        private SimpleIntegerProperty octaveShift;
        private SimpleIntegerProperty semitoneShift;
        private SimpleStringProperty effectName;

        public NoteRecord(float noteFraction, boolean isDotted, int simpleScore, int octaveShift, int semitoneShift) {
            this.noteFraction = new SimpleFloatProperty(noteFraction);
            this.isDotted = new SimpleBooleanProperty(isDotted);
            this.simpleScore = new SimpleIntegerProperty(simpleScore);
            this.octaveShift = new SimpleIntegerProperty(octaveShift);
            this.semitoneShift = new SimpleIntegerProperty(semitoneShift);
            this.effectName = new SimpleStringProperty("null");
        }

        public NoteRecord(float noteFraction, boolean isDotted, int simpleScore, int octaveShift, int semitoneShift,String effectName) {
            this.noteFraction = new SimpleFloatProperty(noteFraction);
            this.isDotted = new SimpleBooleanProperty(isDotted);
            this.simpleScore = new SimpleIntegerProperty(simpleScore);
            this.octaveShift = new SimpleIntegerProperty(octaveShift);
            this.semitoneShift = new SimpleIntegerProperty(semitoneShift);
            this.effectName = new SimpleStringProperty(effectName);
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

        public String getEffectName() {
            return effectName.get();
        }

        public boolean isIsDotted() {
            return isDotted.get();
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

        public void setEffectName(String effectName) {
            this.effectName.set(effectName);
        }
    }

    public enum EffectType {
        EXP_GRADIENT,
        REV_EXP_GRADIENT,
        LINEAR_GRADIENT,
        REV_LINEAR_GRADIENT,
        NONE
    }

    public class EffectRecord {
        private final EffectType effectType;

        public EffectRecord(EffectType effectType) {
            this.effectType = effectType;
        }

        public EffectType getEffectType() {
            return effectType;
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
    public TextField noteFractionTextField;
    public TextField noteSimpleScoreTextField;
    public TextField noteOctaveShiftTextField;
    public TextField noteSemitoneShiftTextField;
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
    public TableColumn squareAScoreColumn;
    public TableColumn squareADottedColumn;
    public TableColumn squareAOctaveColumn;
    public TableColumn squareBScoreColumn;
    public TableColumn squareBDottedColumn;
    public TableColumn squareBOctaveColumn;
    public TableColumn triangleScoreColumn;
    public TableColumn triangleDottedColumn;
    public TableColumn triangleOctaveColumn;
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
    public MenuItem saveMenuItem;
    public MenuItem loadMenuItem;
    public MenuItem aboutMenuItem;
    public TableColumn squareAEffectColumn;
    public TableColumn squareBEffectColumn;
    public TableColumn triangleEffectColumn;
    public TableColumn noiseEffectColumn;
    public ChoiceBox effectChoiceBox;

    private static boolean labelUpdateThreadShouldRunning = true;
    private ObservableList<NoteRecord> squareARecords;
    private ObservableList<NoteRecord> squareBRecords;
    private ObservableList<NoteRecord> triangleRecords;
    private ObservableList<NoteRecord> noiseRecords;
    private ObservableList<EffectRecord> effectRecords;

    @FXML
    public void onSaveMenuItemClick(ActionEvent actionEvent) {
        // TODO: 限定后缀名为json

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存工程");
        File file = fileChooser.showSaveDialog(Main.primaryStage);

        if(file != null) {
            JSONObject root = new JSONObject();
            root.put("bpm",Math.floor(bpmSlider.getValue()));
            root.put("volume",Math.floor(volumeSlider.getValue()));
            root.put("version","SNAPSHOT");

            JSONObject channels = new JSONObject();
            JSONObject squareA = new JSONObject();
            JSONObject squareB = new JSONObject();
            JSONObject triangle = new JSONObject();
            JSONObject noise = new JSONObject();

            squareA.put("enable",squareAChannelCheckBox.isSelected());
            squareA.put("records",squareARecords);

            squareB.put("enable",squareBChannelCheckBox.isSelected());
            squareB.put("records",squareBRecords);

            triangle.put("enable",triangleChannelCheckBox.isSelected());
            triangle.put("records",triangleRecords);

            noise.put("enable",noiseChannelCheckBox.isSelected());
            noise.put("records",noiseRecords);

            channels.put("square_a",squareA);
            channels.put("square_b",squareB);
            channels.put("triangle",triangle);
            channels.put("noise",noise);
            root.put("channels",channels);

            FileWriter writer;
            try {
                writer = new FileWriter(file);
                writer.write("");
                writer.write(root.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onLoadMenuItemClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("载入工程");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON",".json"));
        File file = fileChooser.showOpenDialog(Main.primaryStage);

        if(file != null) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                JSONObject root = JSONObject.parseObject(content);

                // TODO: validate all data from json
                // TODO: validate version from json

                bpmSlider.setValue(root.getDoubleValue("bpm"));
                volumeSlider.setValue(root.getDoubleValue("volume"));
                JSONObject channels = root.getJSONObject("channels");

                JSONObject squareA = channels.getJSONObject("square_a");
                squareAChannelCheckBox.setSelected(squareA.getBooleanValue("enable"));
                squareARecords.clear();
                squareARecords.addAll(JSON.parseArray(squareA.getJSONArray("records").toJSONString(),NoteRecord.class));

                JSONObject squareB = channels.getJSONObject("square_b");
                squareBChannelCheckBox.setSelected(squareB.getBooleanValue("enable"));
                squareBRecords.clear();
                squareBRecords.addAll(JSON.parseArray(squareB.getJSONArray("records").toJSONString(),NoteRecord.class));

                JSONObject triangle = channels.getJSONObject("triangle");
                triangleChannelCheckBox.setSelected(triangle.getBooleanValue("enable"));
                triangleRecords.clear();
                triangleRecords.addAll(JSON.parseArray(triangle.getJSONArray("records").toJSONString(),NoteRecord.class));

                JSONObject noise = channels.getJSONObject("noise");
                noiseChannelCheckBox.setSelected(noise.getBooleanValue("enable"));
                noiseRecords.clear();
                noiseRecords.addAll(JSON.parseArray(noise.getJSONArray("records").toJSONString(),NoteRecord.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onAboutMenuItemClick(ActionEvent actionEvent) {
        // TODO: show about window
    }

    @FXML
    public void onSquareATableViewClick(MouseEvent mouseEvent) {
        channelTextField.setText("0");
    }

    @FXML
    public void onSquareBTableViewClick(MouseEvent mouseEvent) {
        channelTextField.setText("1");
    }

    @FXML
    public void onTriangleTableViewClick(MouseEvent mouseEvent) {
        channelTextField.setText("2");
    }

    @FXML
    public void onNoiseTableViewClick(MouseEvent mouseEvent) {
        channelTextField.setText("3");
    }

    @FXML
    public void onPlayButtonClick(ActionEvent actionEvent) {
        float volume = (float) (volumeSlider.getValue() / 100.0f);
        int bpm = (int) (Math.floor(bpmSlider.getValue()));
        new Thread(() -> {
            playButton.setDisable(true);
            volumeSlider.setDisable(true);
            bpmSlider.setDisable(true);
            squareAChannelCheckBox.setDisable(true);
            squareBChannelCheckBox.setDisable(true);
            triangleChannelCheckBox.setDisable(true);
            noiseChannelCheckBox.setDisable(true);
            NESLikeSynthesizer synthesizer = new NESLikeSynthesizer(bpm);

            if(!squareAChannelCheckBox.isSelected())
                synthesizer.setDisableChannel(0,true);
            if(!squareBChannelCheckBox.isSelected())
                synthesizer.setDisableChannel(1,true);
            if(!triangleChannelCheckBox.isSelected())
                synthesizer.setDisableChannel(2,true);
            if(!noiseChannelCheckBox.isSelected())
                synthesizer.setDisableChannel(3,true);

            new Player(volume).play(synthesizer.genWaveform(genAllSections()));
            playButton.setDisable(false);
            volumeSlider.setDisable(false);
            bpmSlider.setDisable(false);
            squareAChannelCheckBox.setDisable(false);
            squareBChannelCheckBox.setDisable(false);
            triangleChannelCheckBox.setDisable(false);
            noiseChannelCheckBox.setDisable(false);
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("导出音频");
        File file = fileChooser.showSaveDialog(Main.primaryStage);

        if(file != null) {
            int bpm = (int) (Math.floor(bpmSlider.getValue()));
            playButton.setDisable(true);
            volumeSlider.setDisable(true);
            bpmSlider.setDisable(true);
            squareAChannelCheckBox.setDisable(true);
            squareBChannelCheckBox.setDisable(true);
            triangleChannelCheckBox.setDisable(true);
            noiseChannelCheckBox.setDisable(true);
            NESLikeSynthesizer synthesizer = new NESLikeSynthesizer(bpm);

            if(!squareAChannelCheckBox.isSelected())
                synthesizer.setDisableChannel(0,true);
            if(!squareBChannelCheckBox.isSelected())
                synthesizer.setDisableChannel(1,true);
            if(!triangleChannelCheckBox.isSelected())
                synthesizer.setDisableChannel(2,true);
            if(!noiseChannelCheckBox.isSelected())
                synthesizer.setDisableChannel(3,true);

            synthesizer.saveWaveform(file.getPath(),synthesizer.genWaveform(genAllSections()));
            playButton.setDisable(false);
            volumeSlider.setDisable(false);
            bpmSlider.setDisable(false);
            squareAChannelCheckBox.setDisable(false);
            squareBChannelCheckBox.setDisable(false);
            triangleChannelCheckBox.setDisable(false);
            noiseChannelCheckBox.setDisable(false);
        }
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

    private ArrayList<Section> genSectionsFromRecords(ObservableList<NoteRecord> records) {
        ArrayList<Section> sections = new ArrayList<>();
        for(NoteRecord record : records) {
            if(record.getNoteFraction() == 0.0f) {
                sections.add(new Section(record.getSimpleScore(),record.getOctaveShift()));
            } else {
                if(record.getEffectName().equals("None")) {
                    sections.get(sections.size() - 1).getNotes().add(new EqualTempNote(
                            record.getNoteFraction(),
                            record.isIsDotted(),
                            record.getSimpleScore(),
                            record.getOctaveShift(),
                            record.getSemitoneShift()
                    ));
                } else {
                    Effect effect = null;
                    switch (record.getEffectName()) {
                        case "EXP_GRADIENT" -> {
                            ExpGradientEffect expGradientEffect = new ExpGradientEffect();
                            expGradientEffect.setExpCoefficient(4.0f);
                            effect = expGradientEffect;
                        }
                        case "REV_EXP_GRADIENT" -> {
                            ReverseExpGradientEffect expGradientEffect = new ReverseExpGradientEffect();
                            expGradientEffect.setExpCoefficient(4.0f);
                            effect = expGradientEffect;
                        }
                        case "LINEAR_GRADIENT" -> {
                            effect = new LinearGradientEffect();
                        }
                        case "REV_LINEAR_GRADIENT" -> {
                            effect = new ReverseLinearGradientEffect();
                        }
                    }
                    sections.get(sections.size() - 1).getNotes().add(new EqualTempNote(
                            record.getNoteFraction(),
                            record.isIsDotted(),
                            record.getSimpleScore(),
                            record.getOctaveShift(),
                            record.getSemitoneShift(),
                            effect
                    ));
                }
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
        int[] info = getSectionInfo();
        // 临时这样：
        // noteFraction = 0则为Section标记
        // 此时isDotted表示起（true）止（false）
        list.add(new NoteRecord(0,isStarting,info[0],info[1],0));
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
        squareAFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        squareADottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        squareAScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        squareAOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        squareASemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        squareAEffectColumn.setCellValueFactory(new PropertyValueFactory<>("effectName"));
        squareATableView.setItems(squareARecords);
    }

    private void initSquareBTableView() {
        squareBFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        squareBDottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        squareBScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        squareBOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        squareBSemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        squareBEffectColumn.setCellValueFactory(new PropertyValueFactory<>("effectName"));
        squareBTableView.setItems(squareBRecords);
    }

    private void initTriangleTableView() {
        triangleFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        triangleDottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        triangleScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        triangleOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        triangleSemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        triangleEffectColumn.setCellValueFactory(new PropertyValueFactory<>("effectName"));
        triangleTableView.setItems(triangleRecords);
    }

    private void initNoiseTableView() {
        noiseFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        noiseDottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        noiseScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        noiseOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        noiseSemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        noiseEffectColumn.setCellValueFactory(new PropertyValueFactory<>("effectName"));
        noiseTableView.setItems(noiseRecords);
    }

    private void initEffectRecords() {
        effectRecords = FXCollections.observableArrayList();
        effectRecords.add(new EffectRecord(EffectType.EXP_GRADIENT));
        effectRecords.add(new EffectRecord(EffectType.REV_EXP_GRADIENT));
        effectRecords.add(new EffectRecord(EffectType.LINEAR_GRADIENT));
        effectRecords.add(new EffectRecord(EffectType.REV_LINEAR_GRADIENT));
        effectRecords.add(new EffectRecord(EffectType.NONE));
        effectChoiceBox.setItems(effectRecords);
        /*
        effectChoiceBox.setConverter(new StringConverter<EffectRecord>() {
            @Override
            public String toString(EffectRecord object) {
                return object.getEffectType().toString();
            }

            @Override
            public EffectRecord fromString(String string) {
                System.out.println(string);
                return null;
            }
        });
         */
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
        float noteFraction = Float.parseFloat(noteFractionTextField.getText());
        boolean isDotted = noteIsDottedCheckBox.isSelected();
        int simpleScore = Integer.parseInt(noteSimpleScoreTextField.getText());
        int octaveShift = Integer.parseInt(noteOctaveShiftTextField.getText());
        int semitoneShift = Integer.parseInt(noteSemitoneShiftTextField.getText());
        list.add(new NoteRecord(noteFraction,isDotted,simpleScore,octaveShift,semitoneShift,
                ((EffectRecord)effectChoiceBox.getSelectionModel().getSelectedItem()).getEffectType().toString()));
    }

    private void validateNoteInputArgs() {
        // TODO
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initRecords();
        initAllTableViews();
        initEffectRecords();
        initLabelUpdateThread();

        // 因为还没做，所以先直接禁用了
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
    }

    public static void setLabelUpdateThreadShouldRunning(boolean labelUpdateThreadShouldRunning) {
        MainWindow.labelUpdateThreadShouldRunning = labelUpdateThreadShouldRunning;
    }
}
