package com.devsuperior.bds02.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.exceptions.DataBaseException;
import com.devsuperior.bds02.exceptions.RegisterNotFoundException;
import com.devsuperior.bds02.repositories.CityRepository;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true )
	public List<CityDTO> findAll() {

		List<City> cities = new ArrayList<>();
		cities = cityRepository.findAll(Sort.by("name"));
		
		return cities.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public CityDTO insert(CityDTO cityDTO) {
		
		City city = new City();
		city.setName(cityDTO.getName());
		
		city = cityRepository.save(city);
		
		return new CityDTO(city);
	}

	public void delete(Long id) {
		try {
			this.cityRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new RegisterNotFoundException("Registro não encontrado: " + id);
		} catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Erro ao deletar: " + id);
		}
	}
}
