package com.rp.authenticationsystem.service;

import com.rp.authenticationsystem.model.User;

public interface IUserService extends ICrudService<User, Long> {

	void signUp(User user);
}
