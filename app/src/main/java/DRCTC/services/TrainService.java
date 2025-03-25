package DRCTC.services;

import DRCTC.entities.Train;

import java.io.IOException;
import java.util.List;

public class TrainService {

    private List<Train> trainList;


    public TrainService() throws IOException{
        System.out.println("Train service");
    }

    public List<Train> searchTrains(String source, String destination){
        return trainList;
    }
}
