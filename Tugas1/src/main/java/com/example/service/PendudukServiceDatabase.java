package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService{
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Override
	public PendudukModel selectPendudukByNIK(String nik) {
		log.info ("select penduduk with nik {}", nik);
		return pendudukMapper.selectPendudukByNIK (nik);
	}

	@Override
	public void tambahPenduduk(PendudukModel penduduk) {
		pendudukMapper.tambahPenduduk(penduduk);
	}

	@Override
	public int countNIK(String nik) {
		log.info("hitung penduduk dengan nik {}", nik);
		return pendudukMapper.countNIK(nik);
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		pendudukMapper.updatePenduduk(penduduk);
	}

	@Override
	public int selectIdByNik(String nik) {
		log.info ("select id penduduk with nik {}", nik);
		return pendudukMapper.selectIdByNik(nik);
	}

	@Override
	public void updatePendudukNIK(String nik, int id) {
		pendudukMapper.updatePendudukNIK(nik,id);
		
	}

	@Override
	public void updateStatusKematianByNik(String nik) {
		pendudukMapper.updateStatusKematianByNik(nik);
		
	}

	@Override
	public int countPendudukMatiDiKeluarga(String id_keluarga) {
		log.info("hitung penduduk yang mati di keluarga dengan id {}", id_keluarga);
		return pendudukMapper.countPendudukMatiDiKeluarga(id_keluarga);
	}

	@Override
	public List<PendudukModel> selectAllPendudukByIdKelurahan(String id_kelurahan) {
		log.info("select all penduduk with id kelurahan {}", id_kelurahan);
		return pendudukMapper.selectAllPendudukByIdKelurahan(id_kelurahan);
	}

	@Override
	public PendudukModel selectPendudukTertua(String id_kelurahan) {
		log.info("select penduduk tertua dengan id kelurahan {}", id_kelurahan);
		return pendudukMapper.selectPendudukTertua(id_kelurahan);
	}

	@Override
	public PendudukModel selectPendudukTermuda(String id_kelurahan) {
		log.info("select penduduk termuda dengan id kelurahan {}", id_kelurahan);
		return pendudukMapper.selectPendudukTermuda(id_kelurahan);
	}


	
	

}
