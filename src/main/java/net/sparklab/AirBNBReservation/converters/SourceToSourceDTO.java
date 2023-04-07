package net.sparklab.AirBNBReservation.converters;



import net.sparklab.AirBNBReservation.dto.SourceDTO;
import net.sparklab.AirBNBReservation.model.Source;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SourceToSourceDTO implements Converter<Source, SourceDTO> {


    @Override
    public SourceDTO convert(Source source) {

        if (source != null) {
            SourceDTO sourceDTO = new SourceDTO();
            sourceDTO.setId(source.getId());
            sourceDTO.setSource(source.getSource());
            return sourceDTO;
        }
        return null;
    }
}
