package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.NotificationDTO;
import com.example.api_recrutement.models.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDTO toNotificationDTO(Notification notification);

    Notification toNotification(NotificationDTO notificationDTO);
}
