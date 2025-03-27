package DRCTC.services;

import DRCTC.entities.Train;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {

    private final List<Train> trainList;
    private static final String TRAIN_DB_PATH = "../localDB/trains.json";


    // default constructor
    public TrainService() throws IOException{
        File trains = new File(TRAIN_DB_PATH);
        ObjectMapper objectMapper = new ObjectMapper();
        trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }

    // search trains
    public List<Train> searchTrains(String source, String destination){
        return trainList.stream().filter(train -> validTrain(train,source,destination)).toList();
    }

    // add trains
    public void addTrain(Train newTrain){
        // check if a train with same trainId already exists
        Optional<Train> existingTrain =
                trainList.stream()
                        .filter(train1 -> train1.getTrainId().
                                equalsIgnoreCase(newTrain.getTrainId())).findFirst();

        if(existingTrain.isPresent()){
            updateTrain(newTrain);
        }else{
            trainList.add(newTrain);
            saveTrainListToFile();
        }
    }

    // updateTrain
    public void updateTrain(Train updatedTrain){
        // find the index of the train with the same trainId
        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId()))
                .findFirst();

        if(index.isPresent()){
            // if found, replace the exsisting train with updated one
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainListToFile();
        }else{
            addTrain(updatedTrain);
        }
    }

    // save tainlist
    private void saveTrainListToFile() {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(TRAIN_DB_PATH), trainList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // validate trains
    private boolean validTrain(Train train, String source, String destination){
        List<String> stationOrder = train.getStations();

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }
}
