package com.pruebas.windsurf.getlistaproductos.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Concrete MapStruct binding of {@link GetListaProductosControllerMapper}.
 *
 * <p>Empty body: MapStruct generates {@code GsGetListaProductosControllerMapperImpl} from the
 * abstract methods declared on the parent interface. The Spring bean produced by MapStruct
 * implements both this interface and its parent, so callers can inject either type.
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GsGetListaProductosControllerMapper extends GetListaProductosControllerMapper {}
