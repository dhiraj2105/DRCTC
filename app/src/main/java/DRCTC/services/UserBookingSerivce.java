package DRCTC.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import DRCTC.entities.Train;
import DRCTC.entities.User;
import DRCTC.util.UserServiceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class UserBookingSerivce
{
    // We are creating a global user here ( user is an entity ) so that we can store
    // the user state for e.g. Login state , to use User globally
    private User user;

    // we will fetch users and store in this list
    private List<User> userList;

    // file path
    private static final String USERS_PATH = "../localdb/users.json";

    // user constructor
    public UserBookingSerivce(User user) throws IOException
    {
        this.user = user;
        loadUser();
    }

    // default constructor of this class that loads user from file and load to userList
    public  UserBookingSerivce() throws  IOException{
        loadUser();
    }

    public List<User> loadUser() throws IOException{
        File users = new File(USERS_PATH);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    // Login User
    public  Boolean loginUser(){

        Optional<User> foundUser = userList.stream().filter(user1 ->{
            return user1.getName().equals(user.getName()) &&
                    UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();

        return foundUser.isPresent();
    }

    // Signup user
    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    // function to save user list to a file
    // we are serializing user here
    private void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(usersFile, userList);
    }

    // json to object | class => deserialize
    // object to json => serialize

    // fetch ticket bookings of user
    public  void fetchBooking(){
        user.printTickets();
    }

    // cancel booking
    public  Boolean cancelBooking(String ticketId){
        return Boolean.FALSE;
    }

    // get trains
    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        }catch (IOException e){
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }
    public Boolean bookTrainSeat(Train train,int row, int seat){
        try{
         TrainService trainService = new TrainService();
         List<List<Integer>> seats = train.getSeats();
         if(row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()){
             if(seats.get(row).get(seat) == 0){
                 seats.get(row).set(seat, 1);
                 train.setSeats(seats);
                 trainService.addTrain(train);
             }else{return  false;}
         }else{
             return false;
         }
        }catch (IOException e){
            return Boolean.FALSE;
        }
    }
}
