package net.sparklab.AirBNBReservation.services;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.converters.ReservationDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationToReservationDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.exceptions.EntityExistsException;
import net.sparklab.AirBNBReservation.exceptions.NotValidFileException;
import net.sparklab.AirBNBReservation.model.Reservation;
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


    public Page<ReservationDTO> findAll(int pageNumber,int pageSize, String sortBy, String sortDir) {

        Sort sort = Sort.by(sortBy).descending();
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        List<ReservationDTO> reservationDTOList = reservationRepository.findAll(pageable).stream().map(reservation ->
                toReservationDTO.convert(reservation)).collect(Collectors.toList());

        Page<ReservationDTO> reservationDTOPage = new PageImpl<>(reservationDTOList, pageable, reservationDTOList.size());

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

        try {
        List<Reservation> reservationList = reservations.stream().map(reservationDTO -> toReservation.convert(reservationDTO)).collect(Collectors.toList());
            reservationRepository.saveAll(reservationList);
        }
        catch (EntityExistsException e){
            reader.close();
            inputStream.close();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Record not saved successfully", HttpStatus.BAD_REQUEST);

        }
        reader.close();
        inputStream.close();
        return new ResponseEntity<>(reservations.size() + " records saved successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> saveOrUpdate(ReservationDTO reservationDTO) {
        try {
            if (reservationRepository.existsByConfirmationCode(reservationDTO.getConfirmCode()) && reservationDTO.getId() == null) {
                return new ResponseEntity<>("There is already a reservation with this confirmation code", HttpStatus.BAD_REQUEST);
            } else {
                reservationRepository.save(toReservation.convert(reservationDTO));
                return new ResponseEntity<>("Record saved successfully", HttpStatus.OK);
            }
        } catch (NotValidFileException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Record not saved with error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
