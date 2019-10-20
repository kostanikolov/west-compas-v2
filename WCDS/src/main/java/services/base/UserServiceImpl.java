package services.base;

import models.entity.User;
import models.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import services.HashingService;
import services.UserService;
import services.UsersValidationService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final EntityManager entityManager;
    private final HashingService hashingService;
    private final ModelMapper mapper;
    private final UsersValidationService usersValidationService;

    @Inject
    public UserServiceImpl(
            EntityManager entityManager,
            HashingService hashingService,
            ModelMapper mapper,
            UsersValidationService usersValidationService) {

        this.entityManager = entityManager;
        this.hashingService = hashingService;
        this.mapper = mapper;
        this.usersValidationService = usersValidationService;
    }

    @Override
    public void register(String username, String email, String password, String confirmPassword) throws Exception {
        if (!this.usersValidationService.canCreateUser(username, email, password, confirmPassword)) {
            throw new Exception("User cannot be created!");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(this.hashingService.hash(password));

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public UserServiceModel login(String username, String password) {
        List<User> users = this.entityManager
                .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();

        if (users.isEmpty()) {
            return null;
        }

        User user = users.get(0);

        if (!user.getPassword().equals(this.hashingService.hash(password))) {
            return null;
        }

        return this.mapper.map(user, UserServiceModel.class);
    }
}
