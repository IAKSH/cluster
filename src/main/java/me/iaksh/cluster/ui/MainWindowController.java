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
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.oscillator.SquareOscillator;
import me.iaksh.cluster.core.oscillator.TriangleOscillator;
import me.iaksh.cluster.core.synthesizer.*;
import me.iaksh.cluster.core.export.*;
import me.iaksh.cluster.core.effects.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Function;

public class MainWindowController implements Initializable {

    public static class NoteRecord {
        private final SimpleFloatProperty noteFraction;
        private final SimpleBooleanProperty isDotted;
        private final SimpleIntegerProperty simpleScore;
        private final SimpleIntegerProperty octaveShift;
        private final SimpleIntegerProperty semitoneShift;
        private final SimpleStringProperty effectName;

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
        LINEAR_GRADIENT,
        NONE
    }

    public record EffectRecord(EffectType effectType) {
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
    public Button forceStopButton;
    public TableColumn squareAOrdinalColumn;
    public TableColumn squareBOrdinalColumn;
    public TableColumn triangleOrdinalColumn;
    public TableColumn noiseOrdinalColumn;
    public Canvas waveformDisplayCanvas;
    public MenuItem noiseEditMenuItem;
    public MenuItem noiseDeleteMenuItem;
    public MenuItem triangleDeleteMenuItem;
    public MenuItem triangleEditMenuItem;
    public MenuItem squareBDeleteMenuItem;
    public MenuItem squareBEditMenuItem;
    public MenuItem squareADeleteMenuItem;
    public MenuItem squareAEditMenuItem;

    private ObservableList<NoteRecord> squareARecords;
    private ObservableList<NoteRecord> squareBRecords;
    private ObservableList<NoteRecord> triangleRecords;
    private ObservableList<NoteRecord> noiseRecords;
    private ObservableList<EffectRecord> effectRecords;

    private ExpAttenuationEffect expAttenuationEffect = new ExpAttenuationEffect(4.0);
    private BPM packedBPM = new BPM(120);
    private SquareSynth squareSynth = new SquareSynth(packedBPM,expAttenuationEffect);
    private SteppedTriangleSynth steppedTriangleSynth = new SteppedTriangleSynth(packedBPM,expAttenuationEffect);
    private NoiseSynth noiseSynth = new NoiseSynth(packedBPM,expAttenuationEffect);
    private Player player;

    @FXML
    public void onForceStopButtonClick(ActionEvent actionEvent) {
        player.forceStop();
    }

    @FXML
    public void onSaveMenuItemClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存工程");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)","*.json"));
        File file = fileChooser.showSaveDialog(FXApplication.getInstance().getPrimaryStage());

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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)","*.json"));
        File file = fileChooser.showOpenDialog(FXApplication.getInstance().getPrimaryStage());

        if(file != null) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));

                JSONObject root = JSONObject.parseObject(content);

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
        FXApplication.getInstance().getAboutPageStage().show();
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

    private void playRecordsWithSynth(float volume,ObservableList<NoteRecord> records,Synthesizer synthesizer) {
        for(NoteRecord record : records) {
            if(record.getNoteFraction() == 0.0f) {
                synthesizer.setTimeSignature(record.getSimpleScore(),record.getOctaveShift());
            } else {
                synthesizer.setOctaveShift(record.getOctaveShift());
                synthesizer.setSemitoneShift(record.getSemitoneShift());
                synthesizer.setDotted(record.isIsDotted());
                synthesizer.setTimeFraction((int) (1.0f / record.getNoteFraction()));
                if(record.getSimpleScore() == 0) {
                    synthesizer.setScaleStep(1);
                    player.setPCM(synthesizer);
                    player.play(0.0f);
                } else {
                    synthesizer.setScaleStep(record.getSimpleScore());
                    player.setPCM(synthesizer);
                    player.play(volume);
                }
            }
        }
    }

    @FXML
    public void onPlayButtonClick(ActionEvent actionEvent) {
        float volume = (float) (volumeSlider.getValue() / 100.0f);
        int bpm = (int) (Math.floor(bpmSlider.getValue()));
        new Thread(() -> {
            pauseButton.setDisable(false);
            playButton.setDisable(true);
            volumeSlider.setDisable(true);
            bpmSlider.setDisable(true);
            squareAChannelCheckBox.setDisable(true);
            squareBChannelCheckBox.setDisable(true);
            triangleChannelCheckBox.setDisable(true);
            noiseChannelCheckBox.setDisable(true);

            // TODO
            packedBPM.setBpm(bpm);
            if(squareAChannelCheckBox.isSelected()) {;
                playRecordsWithSynth(volume,squareARecords,squareSynth);
            }
            if(squareBChannelCheckBox.isSelected()) {
                playRecordsWithSynth(volume,squareBRecords,squareSynth);
            }
            if(triangleChannelCheckBox.isSelected()) {
                playRecordsWithSynth(volume,triangleRecords,steppedTriangleSynth);
            }
            if(noiseChannelCheckBox.isSelected()) {
                playRecordsWithSynth(volume,noiseRecords,noiseSynth);
            }

            playButton.setDisable(false);
            volumeSlider.setDisable(false);
            bpmSlider.setDisable(false);
            squareAChannelCheckBox.setDisable(false);
            squareBChannelCheckBox.setDisable(false);
            triangleChannelCheckBox.setDisable(false);
            noiseChannelCheckBox.setDisable(false);
            pauseButton.setDisable(true);
            resetButton.setDisable(true);
        }).start();
    }

    @FXML
    public void onPauseButtonClick(ActionEvent actionEvent) {
        resetButton.setDisable(false);
        pauseButton.setDisable(true);
        player.pause();
    }

    @FXML
    public void onResetButtonClick(ActionEvent actionEvent) {
        pauseButton.setDisable(false);
        resetButton.setDisable(true);
        player.resume();
    }

    @FXML
    public void onExportButtonClick(ActionEvent actionEvent) {
        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("导出音频");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV files (*.wav)","*.wav"));
        File file = fileChooser.showSaveDialog(FXApplication.getInstance().getPrimaryStage());

        if(file != null) {
            int bpm = (int) (Math.floor(bpmSlider.getValue()));
            playButton.setDisable(true);
            volumeSlider.setDisable(true);
            bpmSlider.setDisable(true);
            squareAChannelCheckBox.setDisable(true);
            squareBChannelCheckBox.setDisable(true);
            triangleChannelCheckBox.setDisable(true);
            noiseChannelCheckBox.setDisable(true);

            // TODO
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
         */
    }

    @FXML
    public void onAddNoteButtonClick(ActionEvent actionEvent) {
        switch (getSelectedChannelId()) {
            case 0 -> addNoteRecord(squareARecords);
            case 1 -> addNoteRecord(squareBRecords);
            case 2 -> addNoteRecord(triangleRecords);
            case 3 -> addNoteRecord(noiseRecords);
        }
    }

    @FXML
    public void onEditNoteButtonClick(ActionEvent actionEvent) {
        FXApplication.getInstance().getNoteEditPageStage().show();
    }

    @FXML
    public void onDeleteNoteButtonClick(ActionEvent actionEvent) {
        ObservableList<NoteRecord> list;
        TableView view = switch (getSelectedChannelId()) {
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
        switch (getSelectedChannelId()) {
            case 0 -> addSectionRecord(squareARecords,true);
            case 1 -> addSectionRecord(squareBRecords,true);
            case 2 -> addSectionRecord(triangleRecords,true);
            case 3 -> addSectionRecord(noiseRecords,true);
        }
    }

    @FXML
    public void onSectionEndButton(ActionEvent actionEvent) {
        switch (getSelectedChannelId()) {
            case 0 -> addSectionRecord(squareARecords,false);
            case 1 -> addSectionRecord(squareBRecords,false);
            case 2 -> addSectionRecord(triangleRecords,false);
            case 3 -> addSectionRecord(noiseRecords,false);
        }
    }

    @FXML
    public void onVBPMLableClick(MouseEvent mouseEvent) {
        FXApplication.getInstance().getVbpmSettingPageStage().show();
    }

    private static Effect getEffect(NoteRecord record) {
        Effect effect = null;
        switch (record.getEffectName()) {
            case "EXP_GRADIENT" -> {
                ExpAttenuationEffect expAttenuationEffect = new ExpAttenuationEffect();
                expAttenuationEffect.setExpCoefficient(4.0f);
                effect = expAttenuationEffect;
            }
            case "LINEAR_GRADIENT" -> {
                effect = new LinearAttenuationEffect();
            }
        }
        return effect;
    }

    private void addSectionRecord(ObservableList<NoteRecord> list,boolean isStarting) {
        int[] info = getSectionInfo();
        // 临时这样：
        // noteFraction = 0则为Section标记
        // 此时isDotted表示起（true）止（false）
        list.add(new NoteRecord(0, isStarting, info[0], info[1], 0));
    }

    private int[] getSectionInfo() {
        int[] arr = new int[2];
        String[] parts = sectionInfoTextField.getText().split("/");
        arr[0] = Integer.parseInt(parts[0]);
        arr[1] = Integer.parseInt(parts[1]);
        return arr;
    }

    private void initExternUIUpdateThread() {
        // TODO: 绘制实时播放位置
        // TODO: 绘制实时波形图
        new Thread(() -> {
            try {
                while (FXApplication.getInstance().isRunning()) {
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

    private Callback<TableColumn<NoteRecord, String>, TableCell<NoteRecord, String>> getOrdinalCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<NoteRecord, String> call(TableColumn<NoteRecord, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        this.setText(null);
                        this.setGraphic(null);

                        if (!empty) {
                            int rowIndex = this.getIndex() + 1;
                            this.setText(String.valueOf(rowIndex));
                        }
                    }
                };
            }
        };
    }

    private void initSquareATableView() {
        squareAOrdinalColumn.setCellFactory(getOrdinalCellFactory());
        squareAFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        squareADottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        squareAScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        squareAOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        squareASemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        squareAEffectColumn.setCellValueFactory(new PropertyValueFactory<>("effectName"));
        squareATableView.setItems(squareARecords);
    }

    private void initSquareBTableView() {
        squareBOrdinalColumn.setCellFactory(getOrdinalCellFactory());
        squareBFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        squareBDottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        squareBScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        squareBOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        squareBSemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        squareBEffectColumn.setCellValueFactory(new PropertyValueFactory<>("effectName"));
        squareBTableView.setItems(squareBRecords);
    }

    private void initTriangleTableView() {
        triangleOrdinalColumn.setCellFactory(getOrdinalCellFactory());
        triangleFractionColumn.setCellValueFactory(new PropertyValueFactory<>("noteFraction"));
        triangleDottedColumn.setCellValueFactory(new PropertyValueFactory<>("isDotted"));
        triangleScoreColumn.setCellValueFactory(new PropertyValueFactory<>("simpleScore"));
        triangleOctaveColumn.setCellValueFactory(new PropertyValueFactory<>("octaveShift"));
        triangleSemitoneColumn.setCellValueFactory(new PropertyValueFactory<>("semitoneShift"));
        triangleEffectColumn.setCellValueFactory(new PropertyValueFactory<>("effectName"));
        triangleTableView.setItems(triangleRecords);
    }

    private void initNoiseTableView() {
        noiseOrdinalColumn.setCellFactory(getOrdinalCellFactory());
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
        effectRecords.add(new EffectRecord(EffectType.LINEAR_GRADIENT));
        effectRecords.add(new EffectRecord(EffectType.NONE));
        effectChoiceBox.setItems(effectRecords);
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

    private float getNoteFractionInput() {
        float fraction = Float.parseFloat(noteFractionTextField.getText());
        if(fraction < 0.0f)
            throw new IllegalArgumentException(String.format("fraction can't be negative, given = %f",fraction));
        return fraction;
    }

    private boolean getNoteDottedInput() {
        return noteIsDottedCheckBox.isSelected();
    }

    private int getNoteSimpleScoreInput() {
        int score = Integer.parseInt(noteSimpleScoreTextField.getText());
        if(score < 0)
            throw new IllegalArgumentException(String.format("simple score can't be negative, given = %d",score));
        return score;
    }

    private int getNoteOctaveShiftInput() {
        return Integer.parseInt(noteOctaveShiftTextField.getText());
    }

    private int getSemitoneShiftInput() {
        return Integer.parseInt(noteSemitoneShiftTextField.getText());
    }

    private void addNoteRecord(ObservableList<NoteRecord> list) {
        float noteFraction = getNoteFractionInput();
        boolean isDotted = getNoteDottedInput();
        int simpleScore = getNoteSimpleScoreInput();
        int octaveShift = getNoteOctaveShiftInput();
        int semitoneShift = getSemitoneShiftInput();
        list.add(new NoteRecord(noteFraction, isDotted, simpleScore, octaveShift, semitoneShift,
                ((EffectRecord) effectChoiceBox.getSelectionModel().getSelectedItem()).effectType().toString()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initRecords();
        initAllTableViews();
        initEffectRecords();
        initExternUIUpdateThread();
        player = new Player();
    }

    public TableView getSelectedTableView() {
        return switch (getSelectedChannelId()) {
            case 0 -> squareATableView;
            case 1 -> squareBTableView;
            case 2 -> triangleTableView;
            case 3 -> noiseTableView;
            default ->
                    throw new IllegalStateException("Unexpected value: " + getSelectedChannelId());
        };
    }

    public int getSelectedChannelId() {
        return Integer.parseInt(channelTextField.getText());
    }

    public NoteRecord getSelectedNoteRecord() {
        return (NoteRecord) getSelectedTableView().getSelectionModel().getSelectedItem();
    }
}
