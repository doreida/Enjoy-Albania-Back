package net.sparklab.AirBNBReservation.services;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.converters.ReservationDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationToReservationDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Currency;

@AllArgsConstructor
@Service
public class ReservationService {

    public final ReservationRepository reservationRepository;
    public final ReservationToReservationDTO toReservationDTO;
    public final ReservationDTOToReservation toReservation;

    public List<ReservationDTO> findAll(){
        return reservationRepository.findAll().stream().map(reservation -> toReservationDTO.convert(reservation)).collect(Collectors.toList());
    }

    public List<ReservationDTO> uploadData(MultipartFile file) throws Exception {

        InputStream inputStream = file.getInputStream();

        Reader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));


        CsvToBean<ReservationDTO> csvToBean = new CsvToBeanBuilder(reader)
                .withType(ReservationDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();


        List<ReservationDTO> reservations = csvToBean.parse();
        List<Reservation> reservationList = reservations.stream().map(reservationDTO -> toReservation.convert(reservationDTO)).collect(Collectors.toList());

        reservationRepository.saveAll(reservationList);
        reader.close();
        inputStream.close();
        return reservations;
    }

//    public List<ReservationDTO> parseCsvFile(MultipartFile file) throws IOException, CsvValidationException {
//        List<ReservationDTO> csvDataList = new ArrayList<>();
//        InputStream inputStream = file.getInputStream();
//        Reader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
//
//        CSVParser parser = new CSVParserBuilder()
//                .withSeparator(',')
//                .withIgnoreQuotations(true)
//                .build();
//        CSVReader csvReader = new CSVReaderBuilder(reader)
//                .withCSVParser(parser)
//                .build();
//        String[] headers = csvReader.readNext(); // skip header row
//        String[] row;
//        while ((row = csvReader.readNext()) != null) {
//
//            String name = row[0];
//            String currencyString = row[1].replaceAll("\\$", ""); // remove $ sign
//            Currency currency = Currency.getInstance(currencyString);
//            ReservationDTO csvData = new ReservationDTO();
//
//            csvDataList.add(csvData);
//        }
//        return csvDataList;
//    }




}
