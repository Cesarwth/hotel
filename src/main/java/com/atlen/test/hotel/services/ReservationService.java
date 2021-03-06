package com.atlen.test.hotel.services;

import com.atlen.test.hotel.dtos.CancelationDataDto;
import com.atlen.test.hotel.dtos.JsonInputDataDto;
import com.atlen.test.hotel.model.Reservations;
import com.atlen.test.hotel.model.enums.ReservationStatus;
import com.atlen.test.hotel.repository.ReservationRepository;
import com.atlen.test.hotel.repository.RoomRepository;
import com.atlen.test.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReservationService extends ValidationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    Boolean flagUpdate = false;

    public List<Reservations> findAll() {
        return reservationRepository.findAll();
    }

    public void placeReservation(JsonInputDataDto jsonInputDataDto){
        runValidations(jsonInputDataDto, flagUpdate);
    }

    public void updateReservation(JsonInputDataDto jsonInputDataDto){
        flagUpdate = true;
        runValidations(jsonInputDataDto, flagUpdate);
        flagUpdate = false;
    }

    private void runValidations(JsonInputDataDto jsonInputDataDto, Boolean flagUpdate){
        super.getIdRoom();
        super.validateDateReservation(jsonInputDataDto);
        super.validateMaxDateReservation(jsonInputDataDto);
        super.validateAvailabilityRoom();
        super.verifyStayDays(jsonInputDataDto);
        super.validateAvailabilityDates(jsonInputDataDto, flagUpdate);
        super.saveBook(jsonInputDataDto);
    }

    public void cancelReservation(CancelationDataDto cancelationDataDto){
        reservationRepository.cancelReservation(cancelationDataDto.getPassportNumber(), String.valueOf(ReservationStatus.CANCELED));
    }

}
