package DRCTC.services;

import DRCTC.entities.Train;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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


    // validate trains
    private boolean validTrain(Train train, String source, String destination){
        List<String> stationOrder = train.getStations();

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }
}
