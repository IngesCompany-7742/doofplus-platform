package com.ingescompany.doofplus.platform.iam.application.internal.queryservices;

import com.ingescompany.doofplus.platform.iam.application.queryservices.UserQueryService;
import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetUserByEmailAddressQuery;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Application service for user query operations.
 *
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        log.debug("Querying user by id={}", query.userId());
        return userRepository.findById(query.userId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> handle(GetUserByEmailAddressQuery query) {
        log.debug("Querying user by emailAddress={}", query.emailAddress().value());
        return userRepository.findByEmailAddress(query.emailAddress());
    }
}
