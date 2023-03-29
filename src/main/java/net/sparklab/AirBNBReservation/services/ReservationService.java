package net.sparklab.AirBNBReservation.services;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.converters.FilterDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationToReservationDTO;
import net.sparklab.AirBNBReservation.dto.FilterDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.exceptions.NotValidFileException;
import net.sparklab.AirBNBReservation.model.Guest;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.specifications.ReservationSpecification;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.repositories.ReservationRepository;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ReservationService {

    public final ReservationRepository reservationRepository;
    public final ReservationToReservationDTO toReservationDTO;
    public final ReservationDTOToReservation toReservation;
    public final FilterDTOToReservation filterDTOToReservation;
    public final GuestRepository guestRepository;


    public Page<ReservationDTO> findAll(FilterDTO filterDTO){

        Sort sort = Sort.by(filterDTO.getSortBy()).descending();
        if (filterDTO.getSortDir().equalsIgnoreCase("asc")) {
            sort = Sort.by(filterDTO.getSortBy()).ascending();
        }
        Pageable pageable = PageRequest.of(0, filterDTO.getPageSize(), sort);

        Reservation filter = filterDTOToReservation.convert(filterDTO);

        ReservationSpecification specification = new ReservationSpecification(filterDTO,filter);

        List<ReservationDTO> reservationDTOList = reservationRepository.findAll(specification).stream().map(reservation ->
                toReservationDTO.convert(reservation)).collect(Collectors.toList());

        int total = reservationDTOList.size();
        int offset = (int) pageable.getOffset();

        List<ReservationDTO> content = reservationDTOList.subList(offset, Math.min(offset + filterDTO.getPageSize(), total));

        Page<ReservationDTO> reservationDTOPage = new PageImpl<>(content, pageable, reservationDTOList.size());

        return reservationDTOPage;
    }


    public ResponseEntity<?> uploadData(MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();
        Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        CsvToBean<ReservationDTO> csvToBean = new CsvToBeanBuilder(reader)
                .withType(ReservationDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List<ReservationDTO> reservations = csvToBean.parse();

        List<Reservation> reservationList = reservations.stream().map(reservationDTO -> toReservation.convert(reservationDTO))
                .filter(reservation -> reservation!=null).collect(Collectors.toList());

        List<Guest> guestList = reservationList.stream().map(reservation -> reservation.getGuest()).collect(Collectors.toList());

        if (guestList.size()!=0){
            guestRepository.saveAll(guestList);
        }
        if (reservationList.size()!=0) {
            reservationRepository.saveAll(reservationList);
        }
        reader.close();
        inputStream.close();
        return new ResponseEntity<>("Reservations saved: " + reservationList.size(), HttpStatus.OK);
    }

    public ResponseEntity<?> saveOrUpdate(ReservationDTO reservationDTO) {
        try {
            if (reservationRepository.existsByConfirmationCode(reservationDTO.getConfirmationCode()) && reservationDTO.getId() == null) {
                return new ResponseEntity<>("There is already a reservation with this confirmation code", HttpStatus.BAD_REQUEST);
            } else {
                Reservation reservation = toReservation.convert(reservationDTO);
                guestRepository.save(reservation.getGuest());
                reservationRepository.save(reservation);
                return new ResponseEntity<>("Record saved successfully", HttpStatus.OK);
            }
        } catch (NotValidFileException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Record not saved with error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> delete(String id){
        Long parseId;
        try {
            parseId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Id: " + id + " cannot be parsed");
        }
        reservationRepository.deleteById(parseId);
        return new ResponseEntity("Task deleted", HttpStatus.OK);
    }


}
