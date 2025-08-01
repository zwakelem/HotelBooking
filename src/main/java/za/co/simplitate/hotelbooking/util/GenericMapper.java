package za.co.simplitate.hotelbooking.util;

import za.co.simplitate.hotelbooking.dtos.RoomTO;
import za.co.simplitate.hotelbooking.dtos.UserTO;
import za.co.simplitate.hotelbooking.entities.Room;
import za.co.simplitate.hotelbooking.entities.User;
import za.co.simplitate.hotelbooking.enums.UserRole;

public class GenericMapper {

    private GenericMapper() {}

    public static UserTO mapToUserTO(User user) {
        return new UserTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),
                user.getPassword(), user.getPhoneNumber(), user.getRole().toString(), user.isActive(),
                user.getCreatedAt());
    }

    public static User mapToUserTO(UserTO userTO) {
        return User.builder()
                .id(userTO.id())
                .email(userTO.email())
                .firstName(userTO.firstName())
                .lastName(userTO.lastName())
                .phoneNumber(userTO.phoneNumber())
                .role(UserRole.valueOf(userTO.role()))
                .isActive(userTO.isActive())
                .build();
    }

    public static RoomTO mapToRoomTO(Room room) {
        return new RoomTO(room.getId(), room.getRoomNumber(), room.getRoomType(), room.getPricePerNight(),
                room.getCapacity(), room.getDescription(), room.getImageUrl());
    }

    public static Room mapToRoom(RoomTO roomTO) {
        return Room.builder()
                .id(roomTO.id())
                .roomNumber(roomTO.roomNumber())
                .roomType(roomTO.roomType())
                .pricePerNight(roomTO.pricePerNight())
                .capacity(roomTO.capacity())
                .description(roomTO.description())
                .imageUrl(roomTO.imageUrl())
                .build();
    }

}
