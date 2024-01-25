package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Creator;
import com.themaestrocode.onlinelearningplatform.api.security.Role;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.repository.CreatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreatorServiceImpl implements CreatorService {

    @Autowired
    private CreatorRepo creatorRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Creator registerCreator(UserModel userModel) {

        Creator creator = new Creator();
        creator.setFirstName(userModel.getFirstName());
        creator.setLastName(userModel.getLastName());
        creator.setEmail(userModel.getEmail());
        creator.setPassword(passwordEncoder.encode(userModel.getPassword()));
        creator.setPhoneNo(userModel.getPhoneNo());
        creator.setRole(Role.CREATOR);

        return creatorRepo.save(creator);
    }
}
