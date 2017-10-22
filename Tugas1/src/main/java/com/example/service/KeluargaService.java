package com.example.service;

import com.example.model.KeluargaModel;

public interface KeluargaService {
	KeluargaModel selectKeluargaByID(String id_keluarga);

	KeluargaModel selectKeluargaByNKK(String nkk);

	void tambahKeluarga(KeluargaModel keluarga);

	int countNKK(String nomor_kk);

	void updateKeluarga(KeluargaModel keluarga);

	void updateNkkById(String nomor_kk, String id);

	String selectIdKeluargaByNkk(String nkk);

	void updateIsberlaku(String id_keluarga);
}
