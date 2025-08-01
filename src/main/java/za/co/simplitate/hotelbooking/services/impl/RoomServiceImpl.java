package za.co.simplitate.hotelbooking.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.simplitate.hotelbooking.dtos.Response;
import za.co.simplitate.hotelbooking.dtos.RoomTO;
import za.co.simplitate.hotelbooking.entities.Room;
import za.co.simplitate.hotelbooking.enums.RoomType;
import za.co.simplitate.hotelbooking.exceptions.InvalidBookingStateException;
import za.co.simplitate.hotelbooking.exceptions.NotFoundException;
import za.co.simplitate.hotelbooking.repositories.RoomsRepository;
import za.co.simplitate.hotelbooking.services.RoomService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    public static final String SUCCESS = "success";

    private final RoomsRepository roomsRepository;

    private final ModelMapper modelMapper;

    private static final String  IMAGE_DIR = System.getProperty("user.dir") + "/product-image";

    @Override
    public Response addRoom(RoomTO roomTO, MultipartFile imageFile) {
        Room roomEntity = modelMapper.map(roomTO, Room.class);
        if(imageFile != null) {

            String imagePath = null;
            try {
                imagePath = saveImage(imageFile);
            } catch (Exception e) {
                log.warn("addRoom: could not add room image");
            }
            roomEntity.setImageUrl(imagePath);
        }

        roomsRepository.save(roomEntity);
        return Response.builder()
                .status(201)
                .message("Room successfully added.")
                .build();
    }

    @Override
    public Response updateRoom(RoomTO roomTO, MultipartFile imageFile) {
        Room existingRoom = roomsRepository.findById(roomTO.id())
                .orElseThrow(() -> new NotFoundException("Room does not exist"));

        if(imageFile != null && !imageFile.isEmpty()) {
            String imagePath = "";
            try {
                imagePath = saveImage(imageFile);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
            existingRoom.setImageUrl(imagePath);
        }

        //TODO add validation for room attribute

        roomsRepository.save(existingRoom);
        return Response.builder()
                .status(204)
                .message("Room successfully updated.")
                .build();
    }

    @Override
    public Response getAllRooms() {
        List<Room> roomList = roomsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<RoomTO> roomTOList = modelMapper.map(roomList, new TypeToken<List<RoomTO>>() {}.getType());
        return Response.builder()
                .status(200)
                .message(SUCCESS)
                .rooms(roomTOList)
                .build();
    }

    @Override
    public Response getRoomById(Long roomId) {
        Room existingRoom = roomsRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException(String.format("Room with id=%d not found", roomId)));
        RoomTO roomTO = modelMapper.map(existingRoom, RoomTO.class);
        return Response.builder()
                .status(200)
                .message(SUCCESS)
                .room(roomTO)
                .build();
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Room existingRoom = roomsRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room does not exist"));
        roomsRepository.delete(existingRoom);
        return Response.builder()
                .status(204)
                .message("room deleted successfully")
                .build();
    }

    @Override
    public Response getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, RoomType roomType) {
        // validate dates
        validateDates(checkInDate, checkOutDate);
        List<Room> roomList = roomsRepository.findAvailableRooms(checkInDate, checkOutDate, roomType);
        List<RoomTO> roomTOList = modelMapper.map(roomList, new TypeToken<List<RoomTO>>() {}.getType());
        return Response.builder()
                .status(200)
                .message(SUCCESS)
                .rooms(roomTOList)
                .build();
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        return Arrays.asList(RoomType.values());
    }

    @Override
    public Response searchRoom(String input) {
        List<Room> roomList = roomsRepository.findRoomByDescriptionEqualsIgnoreCase(input);
        List<RoomTO> roomTOList = modelMapper.map(roomList, new TypeToken<List<RoomTO>>() {}.getType());
        return Response.builder()
                .status(200)
                .message(SUCCESS)
                .rooms(roomTOList)
                .build();
    }

    private String saveImage(MultipartFile imageFile) throws Exception {
        if (!imageFile.getContentType().startsWith("image/")) {
            log.error("saveImage: not an image");
           throw new IllegalArgumentException("Only image file allowed!!");
        }

        // create directory if it doesn't exist
        File dir = new File(IMAGE_DIR);
        if(!dir.exists()) {
            if (dir.mkdir()) {
                log.debug("Directory created");
            } else {
                log.warn("Could not create directory");
            }
        }

        //generate unique filename for image
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        String imagePath = IMAGE_DIR + fileName;

        try {
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile);
        } catch (IOException | IllegalArgumentException ex) {
            log.error("saveImage: error while saving an image");
            throw new IllegalArgumentException(ex.getMessage());
        }

        return imagePath;
    }

    private static void validateDates(LocalDate checkInDate, LocalDate checkOutDate) {

        if(checkInDate != null && checkOutDate != null) {
            if(checkInDate.isBefore(LocalDate.now())) {
                throw new InvalidBookingStateException("Check IN date must be before today");
            }

            if(checkOutDate.isBefore(checkInDate)) {
                throw new InvalidBookingStateException("Check OUT date must be before check IN date");
            }

            if(checkInDate.isEqual(checkOutDate)) {
                throw new InvalidBookingStateException("Check IN date cannot be equal to check OUT date");
            }
        }
    }

}
