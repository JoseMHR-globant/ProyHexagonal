package com.pruebas.windsurf.postusuariopremium.controller.mapper;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.postusuariopremium.controller.model.request.UsuarioPremiumRequestDto;
import com.pruebas.windsurf.postusuariopremium.controller.model.response.UsuarioPremiumResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-06T16:59:25+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class GsPostUsuarioPremiumControllerMapperImpl implements GsPostUsuarioPremiumControllerMapper {

    @Override
    public UsuarioPremium toDomain(UsuarioPremiumRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        UsuarioPremium.UsuarioPremiumBuilder usuarioPremium = UsuarioPremium.builder();

        usuarioPremium.nombre( dto.getNombre() );
        usuarioPremium.email( dto.getEmail() );
        usuarioPremium.plan( dto.getPlan() );

        return usuarioPremium.build();
    }

    @Override
    public UsuarioPremiumResponseDto toDto(UsuarioPremium domain) {
        if ( domain == null ) {
            return null;
        }

        UsuarioPremiumResponseDto.UsuarioPremiumResponseDtoBuilder usuarioPremiumResponseDto = UsuarioPremiumResponseDto.builder();

        usuarioPremiumResponseDto.id( domain.getId() );
        usuarioPremiumResponseDto.nombre( domain.getNombre() );
        usuarioPremiumResponseDto.email( domain.getEmail() );
        usuarioPremiumResponseDto.plan( domain.getPlan() );
        usuarioPremiumResponseDto.fechaAlta( domain.getFechaAlta() );
        usuarioPremiumResponseDto.estado( domain.getEstado() );

        return usuarioPremiumResponseDto.build();
    }
}
