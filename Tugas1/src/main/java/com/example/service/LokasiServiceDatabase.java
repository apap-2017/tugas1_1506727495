package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.LokasiMapper;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LokasiServiceDatabase implements LokasiService{
	@Autowired
	private LokasiMapper lokasiMapper;
	
	@Override
	public KelurahanModel selectKelurahanById(String id_kelurahan) {
		log.info("select kelurahan by id kelurahan {}", id_kelurahan);
		return lokasiMapper.selectKelurahanById(id_kelurahan);
	}

	@Override
	public KecamatanModel selectKecamatanById(String id_kecamatan) {
		log.info("select kecamatan by id kecamatan {}", id_kecamatan);
		return lokasiMapper.selectKecamatanById(id_kecamatan);
	}

	@Override
	public KotaModel selectKotaById(String id_kota) {
		log.info("select kota by id kota {}", id_kota);
		return lokasiMapper.selectKotaById(id_kota);
	}

	@Override
	public KelurahanModel selectKelurahanByNama(String nama_kelurahan) {
		log.info("select kelurahan by name {}", nama_kelurahan);
		return lokasiMapper.selectKelurahanByName(nama_kelurahan);
	}

	@Override
	public KecamatanModel selectKecamatanByNama(String nama_kecamatan) {
		log.info("select kecamatan by name {}", nama_kecamatan);
		return lokasiMapper.selectKecamatanByName(nama_kecamatan);
	}

	@Override
	public List<KelurahanModel> selectAllKelurahanById(String id_kecamatan) {
		log.info("select all kelurahan by id kecamatan {}", id_kecamatan);
		return lokasiMapper.selectAllKelurahan(id_kecamatan);
	}

	@Override
	public List<KecamatanModel> selectAllKecamatanById(String id_kota) {
		log.info("select all kecamatan by id kota {}", id_kota);
		return lokasiMapper.selectAllKecamatan(id_kota);
	}

	@Override
	public List<KotaModel> selectAllKota() {
		log.info("select all kota");
		return lokasiMapper.selectAllKota();
	}

}
