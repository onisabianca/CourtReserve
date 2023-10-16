package com.example.court_reserve_backend.service;

import com.example.court_reserve_backend.dto.Credentials;
import com.example.court_reserve_backend.dto.ReservationDto;
import com.example.court_reserve_backend.dto.SubscriptionDto;
import com.example.court_reserve_backend.dto.UserDto;
import com.example.court_reserve_backend.entity.User;
import com.example.court_reserve_backend.exception.AddressNotFoundException;
import com.example.court_reserve_backend.exception.PriceNotFoundException;
import com.example.court_reserve_backend.exception.UserAlreadyExists;
import com.example.court_reserve_backend.exception.UserNotFoundException;
import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RepositoryFactory factory;

    @Transactional
    public UserDto insert (String username, String password, String email, String firstName, String lastName, int phoneNumber, Long idRole, Long idAddress) throws NoSuchAlgorithmException {
        List<User> listUsers= factory.createUserRepository().findAll();
        boolean existentUser=false;

        for (User userN: listUsers)
        {
            if(Objects.equals(userN.getUsername(), username) || Objects.equals(userN.getEmail(), email))
            {
                existentUser=true;
            }
        }

        if(!existentUser) {

            User addedUser = new User();
            addedUser.setUsername(username);
            addedUser.setPassword(password);
            addedUser.setEmail(email);
            addedUser.setFirstName(firstName);
            addedUser.setLastName(lastName);
            addedUser.setPhoneNumber(phoneNumber);
            addedUser.setRole(factory.createRoleRepository().getRoleById(idRole));
            addedUser.setAddress(factory.createAddressRepository().findById(idAddress).orElseThrow(AddressNotFoundException::new));

            return UserDto.userDtoFromUser(factory.createUserRepository().save(addedUser));
        }
        else
        {
            throw new UserAlreadyExists();
        }
    }

    @Transactional
    public UserDto findById(Long id)
    {
        UserDto found = UserDto.userDtoFromUser(factory.createUserRepository().findById(id).orElseThrow(UserNotFoundException::new));
        findUserSubscriptionSet(found);
        findUserReservationSet(found);
        return found;
    }

    @Transactional
    public void remove(Long id)
    {
        User user=factory.createUserRepository().findById(id).orElseThrow(UserNotFoundException::new);
        factory.createUserRepository().remove(user);
    }

    @Transactional
    public UserDto update (Long id, String username, String password, String email, String firstName, String lastName, int phoneNumber, Long idRole, Long idAddress)
    {
        User updateUser = factory.createUserRepository().findById(id).orElseThrow(UserNotFoundException::new);

        updateUser.setUsername(username);
        updateUser.setPassword(password);
        updateUser.setEmail(email);
        updateUser.setFirstName(firstName);
        updateUser.setLastName(lastName);
        updateUser.setPhoneNumber(phoneNumber);
        updateUser.setRole(factory.createRoleRepository().getRoleById(idRole));
        updateUser.setAddress(factory.createAddressRepository().findById(idAddress).orElseThrow(AddressNotFoundException::new));

        return UserDto.userDtoFromUser(factory.createUserRepository().save(updateUser));
    }

    @Transactional
    public List<UserDto> findAll()
    {
        System.out.println("a");
        List<UserDto> users = factory.createUserRepository().findAll().stream().map(UserDto::userDtoFromUser).collect(Collectors.toList());
        for (UserDto u: users) {
            findUserSubscriptionSet(u);
            findUserReservationSet(u);
        }
        return users;
    }

    private void findUserSubscriptionSet(UserDto u) {
        List<SubscriptionDto> subscriptions = factory.createSubscriptionRepository().findByUserId(u.getId()).stream().map(SubscriptionDto::subscriptionDtoFromSubscription).collect(Collectors.toList());
        Set<SubscriptionDto> userSet = new HashSet<>();
        userSet.addAll(subscriptions);
        u.setSubscriptions(userSet);
    }

    private void findUserReservationSet(UserDto u) {
        List<ReservationDto> reservations = factory.createReservationRepository().findByUserId(u.getId()).stream().map(ReservationDto::reservationDtoFromReservation).collect(Collectors.toList());
        Set<ReservationDto> userSet = new HashSet<>();
        userSet.addAll(reservations);
        u.setReservations(userSet);
    }

    @Transactional
    public UserDto login(Credentials credentials)
    {
        List<User> listUsers= factory.createUserRepository().findAll();
        User foundUser=new User();

        for (User userN: listUsers)
        {
            if(Objects.equals(userN.getUsername(), credentials.getUsername()) && Objects.equals(userN.getPassword(), credentials.getPassword()))
            {
                foundUser=userN;
            }
        }

        return UserDto.userDtoFromUser(factory.createUserRepository().findById(foundUser.getId()).orElseThrow(UserNotFoundException::new));
    }
}
