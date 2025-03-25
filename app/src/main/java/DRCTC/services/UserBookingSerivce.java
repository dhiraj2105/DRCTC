package DRCTC.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

        // fetch user stores in DB user.json file
        File users = new File(USERS_PATH);

        ObjectMapper objectMapper = new ObjectMapper();
        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
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
    private void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(usersFile, userList);
    }

}
