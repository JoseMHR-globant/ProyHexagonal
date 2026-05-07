package com.pruebas.windsurf.common.facade.producto.get.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Concrete MapStruct binding of {@link GetProductosFacadeMapper}.
 *
 * <p>Empty body: MapStruct generates {@code GsGetProductosFacadeMapperImpl} from the abstract
 * methods declared on the parent interface. The Spring bean produced by MapStruct implements
 * both this interface and its parent, so callers can inject either type.
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GsGetProductosFacadeMapper extends GetProductosFacadeMapper {}
