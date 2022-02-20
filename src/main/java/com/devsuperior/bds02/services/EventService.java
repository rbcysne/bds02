package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.exceptions.RegisterNotFoundException;
import com.devsuperior.bds02.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO eventDTO) {

		Event event = new Event();

		try {
			event = this.eventRepository.getOne(id);
			this.copyDtoToEntity(eventDTO, event);

			event = this.eventRepository.save(event);
		} catch (EntityNotFoundException e) {
			throw new RegisterNotFoundException("Registro não encontrado: " + id);
		}

		return new EventDTO(event);
	}

	private void copyDtoToEntity(EventDTO eventDTO, Event event) {

		event.setName(eventDTO.getName());
		event.setDate(eventDTO.getDate());
		event.setUrl(eventDTO.getUrl());
		event.setCity(new City(eventDTO.getCityId(), null));

	}
}
