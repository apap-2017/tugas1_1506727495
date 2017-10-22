package com.example.service;

import java.util.List;

import com.example.model.PendudukModel;

public interface PendudukService {

	PendudukModel selectPendudukByNIK(String nik);

	void tambahPenduduk(PendudukModel penduduk);

	int countNIK(String nik);

	void updatePendudukNIK(String nik, int id);

	int selectIdByNik(String nik);

	void updatePenduduk(PendudukModel penduduk);

	void updateStatusKematianByNik(String nik);

	int countPendudukMatiDiKeluarga(String id_keluarga);

	List<PendudukModel> selectAllPendudukByIdKelurahan(String id_kelurahan);

	PendudukModel selectPendudukTertua(String id_kelurahan);

	PendudukModel selectPendudukTermuda(String id_kelurahan);
}
