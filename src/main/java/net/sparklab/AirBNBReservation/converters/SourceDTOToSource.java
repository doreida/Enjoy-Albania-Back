package net.sparklab.AirBNBReservation.converters;

import net.sparklab.AirBNBReservation.dto.SourceDTO;

import net.sparklab.AirBNBReservation.model.Source;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SourceDTOToSource implements Converter<SourceDTO, Source> {


    @Override
    public Source convert(SourceDTO source) {
        if (source != null) {
            Source sourcesave = new Source();

            if (source.getId() != null) {
                sourcesave.setId(source.getId());
            }
            sourcesave.setSource(source.getSource());
            return sourcesave;
        }
        return null;
    }

}
