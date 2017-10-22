package com.example.service;

import com.example.model.KeluargaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {

	@Autowired
	private KeluargaMapper keluargaMapper;

	@Override
	public KeluargaModel selectKeluargaByID(String id_keluarga) {
		log.info("select keluarga with ID Keluarga {}", id_keluarga);
		return keluargaMapper.selectKeluargaByID(id_keluarga);
	}

	@Override
	public KeluargaModel selectKeluargaByNKK(String nkk) {
		log.info("select keluarga with NKK {}", nkk);
		return keluargaMapper.selectKeluargaByNKK(nkk);
	}

	@Override
	public void tambahKeluarga(KeluargaModel keluarga) {
		keluargaMapper.tambahKeluarga(keluarga);
	}

	@Override
	public int countNKK(String nomor_kk) {
		log.info("hitung keluarga dengan nkk {}", nomor_kk);
		return keluargaMapper.countNKK(nomor_kk);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		keluargaMapper.updateKeluarga(keluarga);

	}

	@Override
	public String selectIdKeluargaByNkk(String nkk) {
		log.info("select id keluarga by nkk {}", nkk);
		return keluargaMapper.selectIdKeluargaByNkk(nkk);
	}

	@Override
	public void updateNkkById(String nomor_kk, String id) {
		keluargaMapper.updateNkkById(nomor_kk, id);

	}

	@Override
	public void updateIsberlaku(String id_keluarga) {
		keluargaMapper.updateIsberlaku(id_keluarga);
		
	}

}
