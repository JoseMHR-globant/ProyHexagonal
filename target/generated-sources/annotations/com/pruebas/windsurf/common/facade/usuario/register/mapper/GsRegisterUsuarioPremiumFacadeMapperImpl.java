package com.pruebas.windsurf.common.facade.usuario.register.mapper;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.common.facade.usuario.register.model.UsuarioPremiumCdo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-06T16:59:25+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class GsRegisterUsuarioPremiumFacadeMapperImpl implements GsRegisterUsuarioPremiumFacadeMapper {

    @Override
    public UsuarioPremiumCdo toCdo(UsuarioPremium usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioPremiumCdo.UsuarioPremiumCdoBuilder usuarioPremiumCdo = UsuarioPremiumCdo.builder();

        usuarioPremiumCdo.id( usuario.getId() );
        usuarioPremiumCdo.nombre( usuario.getNombre() );
        usuarioPremiumCdo.email( usuario.getEmail() );
        usuarioPremiumCdo.plan( usuario.getPlan() );
        usuarioPremiumCdo.fechaAlta( usuario.getFechaAlta() );
        usuarioPremiumCdo.estado( usuario.getEstado() );

        return usuarioPremiumCdo.build();
    }

    @Override
    public UsuarioPremium toDomain(UsuarioPremiumCdo cdo) {
        if ( cdo == null ) {
            return null;
        }

        UsuarioPremium.UsuarioPremiumBuilder usuarioPremium = UsuarioPremium.builder();

        usuarioPremium.id( cdo.getId() );
        usuarioPremium.nombre( cdo.getNombre() );
        usuarioPremium.email( cdo.getEmail() );
        usuarioPremium.plan( cdo.getPlan() );
        usuarioPremium.fechaAlta( cdo.getFechaAlta() );
        usuarioPremium.estado( cdo.getEstado() );

        return usuarioPremium.build();
    }
}
