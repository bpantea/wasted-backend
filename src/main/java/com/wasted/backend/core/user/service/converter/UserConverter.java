package com.wasted.backend.core.user.service.converter;

        import com.wasted.backend.core.user.api.dtos.GoogleUserDto;
        import com.wasted.backend.core.user.domain.User;
        import org.mapstruct.Mapper;
        import org.mapstruct.MappingTarget;
        import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {
    User convert(GoogleUserDto googleUserDto, @MappingTarget User user);
}
