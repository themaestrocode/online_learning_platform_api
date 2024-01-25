package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Creator;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;

public interface CreatorService {

    Creator registerCreator(UserModel userModel);
}
