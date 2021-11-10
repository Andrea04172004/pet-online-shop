package com.sazonov.mainonlineshop.mapper;

import com.sazonov.mainonlineshop.dto.AttachmentDto;
import com.sazonov.mainonlineshop.shopentity.AttachmentEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AttachmentMapper {

    public Function<AttachmentEntity, AttachmentDto> attachmentFromEntityToDto = p -> getAttachmentDto(p);
    public Function<AttachmentDto, AttachmentEntity> attachmentFromDtoToEntity = p -> getAttachmentEntity(p);

    public <A, R> List<R> collectionToList(Collection<A> collection, Function<A, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    public AttachmentEntity getAttachmentEntity(AttachmentDto attachmentDto){

        return AttachmentEntity.builder()
                .id(attachmentDto.getId())
                .name(attachmentDto.getName())
                .created(attachmentDto.getCreated())
                .content(attachmentDto.getContent())
                .build();

    }


    public AttachmentDto getAttachmentDto(AttachmentEntity attachmentEntity){

        return AttachmentDto.builder()
                .id(attachmentEntity.getId())
                .name(attachmentEntity.getName())
                .created(attachmentEntity.getCreated())
                .content(attachmentEntity.getContent())
                .build();

    }


}