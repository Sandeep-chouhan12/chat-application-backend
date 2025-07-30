package com.dollop.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.entity.Message;
import com.dollop.app.entity.MessageVisibility;
import com.dollop.app.entity.User;

public interface IMessageVisibilityRepository extends JpaRepository<MessageVisibility,String> {

	Optional<MessageVisibility> findByMessageAndUser(Message message, User user);

    List<MessageVisibility> findByUserAndVisibleFalse(User user);

}
