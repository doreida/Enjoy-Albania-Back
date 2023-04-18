package net.sparklab.AirBNBReservation.services;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.converters.FilterDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationToReservationDTO;
import net.sparklab.AirBNBReservation.dto.FilterDTO;
import net.sparklab.AirBNBReservation.dto.ReportDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.exceptions.NotFoundException;
import net.sparklab.AirBNBReservation.exceptions.NotValidFileException;
import net.sparklab.AirBNBReservation.model.Guest;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.model.Source;
import net.sparklab.AirBNBReservation.repositories.SourceRepository;
import net.sparklab.AirBNBReservation.specifications.ReservationSpecification;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.repositories.ReservationRepository;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ReservationService {

    public final ReservationRepository reservationRepository;
    public final ReservationToReservationDTO toReservationDTO;
    public final ReservationDTOToReservation toReservation;
    public final FilterDTOToReservation filterDTOToReservation;
    public final GuestRepository guestRepository;
    private final SourceRepository sourceRepository;
    private final ReportService reportService;

    public Page<ReservationDTO> findAll(FilterDTO filterDTO){

        Sort sort = Sort.by(filterDTO.getSortBy()).descending();
        if (filterDTO.getSortDir().equalsIgnoreCase("asc")) {
            sort = Sort.by(filterDTO.getSortBy()).ascending();
        }

        Pageable pageable = PageRequest.of(filterDTO.getPageNo(), filterDTO.getPageSize(), sort);

        Reservation filter = filterDTOToReservation.convert(filterDTO);

        ReservationSpecification specification = new ReservationSpecification(filterDTO,filter);

        Page<Reservation> reservationsPage = reservationRepository.findAll(specification, pageable);

        return new PageImpl<>(reservationsPage.stream().map(toReservationDTO::convert).collect(Collectors.toList()), pageable, reservationsPage.getTotalElements());

    }


    public ResponseEntity<?> uploadData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        try {
            CsvToBean<ReservationDTO> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(ReservationDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<ReservationDTO> reservations = csvToBean.parse();


            Source source = sourceRepository.findSourcesBySource("AirBNBReservation");
            List<Reservation> reservationList = reservations.stream()
                    .map(reservationDTO -> toReservation.convert(reservationDTO))
                    .filter(reservation -> reservation != null)
                    .peek(reservation -> reservation.setSource(source))
                    .collect(Collectors.toList());

            List<Guest> guestList = reservationList.stream().map(reservation -> reservation.getGuest()).collect(Collectors.toList());

            if (guestList.size() != 0) {
                guestRepository.saveAll(guestList);
            }
            if (reservationList.size() != 0) {
                reservationRepository.saveAll(reservationList);
            }
            reader.close();
            inputStream.close();
            return new ResponseEntity<>("Reservations saved: " + reservationList.size(), HttpStatus.OK);

        }catch (Exception e){
            reader.close();
            inputStream.close();
            return new ResponseEntity<>("Error uploading the file",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> saveOrUpdate(ReservationDTO reservationDTO) {
        try {
            if (reservationRepository.existsByConfirmationCode(reservationDTO.getConfirmationCode()) && reservationDTO.getId() == null) {
                return new ResponseEntity<>("There is already a reservation with this confirmation code", HttpStatus.BAD_REQUEST);
            } else {
                Reservation reservation = toReservation.convert(reservationDTO);
                guestRepository.save(reservation.getGuest());
                reservationRepository.save(reservation);
                if (reservationDTO.getId()==null) {
                    return new ResponseEntity<>("Record saved successfully", HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("Record updated successfully", HttpStatus.OK);
                }
            }
        } catch (NotValidFileException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
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
        return new ResponseEntity<>("Reservation deleted", HttpStatus.OK);
    }


    public ReservationDTO findById(String id) {
        Long parseId;
        try {
            parseId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Reservation id: \"" + id + "\" can't be parsed!");
        }
        return toReservationDTO.convert(reservationRepository.findById(parseId).orElseThrow(() -> new NotFoundException("Record with id: " + id + " not found!")));

    }

    public ResponseEntity<?> findReport(FilterDTO filterDTO) {


        Reservation filter = filterDTOToReservation.convert(filterDTO);
        ReservationSpecification specification = new ReservationSpecification(filterDTO,filter);
        List<Reservation> reservations = reservationRepository.findAll(specification);
        if(reservations.size()<=0){
            return new ResponseEntity<>("There is no reservation with those specifications.Please enter other specifications ",HttpStatus.BAD_REQUEST);
        }
        ReportDTO reportDTO=new ReportDTO();
        reportDTO.setAvg_Guest(reportService.avgGuests(reservations)+" guests");
        reportDTO.setAvg_Anticipation(reportService.avgAnticipation(reservations)+" days");
        reportDTO.setAvg_Length_Stay(reportService.avgLength_Stay(reservations)+" days");
        reportDTO.setPercentage_of_reservations_with_children(reportService.percentage_reservation_with_children(reservations)+"%");
        reportDTO.setPercentage_of_reservations_with_infants(reportService.percentageOfReservationsWithInfants(reservations)+"%");
        if(filterDTO.getListing()!=null&& filterDTO.getStart()!=null&& filterDTO.getEnd()!=null)
        {
            reportDTO.setPercentage_of_occupancy_listing_between_dates(reportService.percentageOfOccupancyPerListingAndDates(reservations, LocalDate.parse(filterDTO.getStart(), DateTimeFormatter.ofPattern("d/M/uuuu")),LocalDate.parse(filterDTO.getEnd(), DateTimeFormatter.ofPattern("d/M/uuuu")),filterDTO.getListing())+"%");
        }

        return new ResponseEntity(reportDTO,HttpStatus.OK);
    }




}
