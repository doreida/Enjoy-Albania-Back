package net.sparklab.AirBNBReservation.services;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.converters.ReservationToReservationDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.dto.UploadDTO;
import net.sparklab.AirBNBReservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReservationService {

    public final ReservationRepository reservationRepository;
    public final ReservationToReservationDTO toReservationDTO;

    public List<ReservationDTO> findAll(){
        return reservationRepository.findAll().stream().map(reservation -> toReservationDTO.convert(reservation)).collect(Collectors.toList());
    }

    public List<UploadDTO> uploadData(MultipartFile file) throws Exception {

        InputStream inputStream = file.getInputStream();

        Reader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));


        CsvToBean<UploadDTO> csvToBean = new CsvToBeanBuilder(reader)
                .withType(UploadDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();


        List<UploadDTO> reservations = csvToBean.parse();


//        reservationRepository.saveAll(reservations);

        inputStream.close();
        return reservations;
    }
}
