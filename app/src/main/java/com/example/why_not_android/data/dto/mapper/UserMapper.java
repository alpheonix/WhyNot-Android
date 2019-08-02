package com.example.why_not_android.data.dto.mapper;

import com.example.why_not_android.data.Models.User;
import com.example.why_not_android.data.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static List<User> map(List<UserDTO> userDTOList) {
        List<User> userList = new ArrayList<>();
        for (UserDTO userDTO : userDTOList) {
            userList.add(map(userDTO));
        }
        return userList;
    }

    private static User map(UserDTO userDTO) {
        User user = new User();
        user.set_id(userDTO.get_id());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setBirthdate(userDTO.getBirthdate());
        user.setBanned(userDTO.getBanned());
        user.setBio(userDTO.getBio());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setIsDeleted(userDTO.getIsDeleted());
        user.setPhoto(userDTO.getPhoto());
        return user;
    }
}
