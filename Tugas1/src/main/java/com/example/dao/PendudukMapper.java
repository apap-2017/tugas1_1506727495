package com.example.dao;


import java.util.List;

import org.apache.ibatis.annotations.*;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	
	@Select("select * from penduduk where nik = #{nik}")
	@Results(value = {
    		@Result(property="nik", column="nik"),
    		@Result(property="nama", column="nama"), 
    		@Result(property="tempat_lahir", column="tempat_lahir"), 
    		@Result(property="tanggal_lahir", column="tanggal_lahir"),
    		@Result(property="is_wni", column="is_wni"),
    		@Result(property="agama", column="agama"),
    		@Result(property="pekerjaan", column="pekerjaan"),
    		@Result(property="status_perkawinan", column="status_perkawinan"),
    		@Result(property="golongan_darah", column="golongan_darah"),
    		@Result(property="is_wafat", column="is_wafat"),
    		@Result(property="id_keluarga", column="id_keluarga"),
    		@Result(property="tanggal_lahir", column="tanggal_lahir")
    		
    })
    PendudukModel selectPendudukByNIK (@Param("nik") String nik);
	
	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, "
			+ "jenis_kelamin, is_wni, id_keluarga, "
			+ "agama, pekerjaan, status_perkawinan,"
			+ "status_dalam_keluarga, golongan_darah, is_wafat)"
			+ " VALUES (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, "
			+ "#{jenis_kelamin}, #{is_wni}, #{id_keluarga}, "
			+ "#{agama}, #{pekerjaan}, #{status_perkawinan}, "
			+ "#{status_dalam_keluarga}, #{golongan_darah},  #{is_wafat})")
	void tambahPenduduk(PendudukModel penduduk);
	
	@Select("select count(*) from penduduk where nik like #{nik}")
	int countNIK(String nik);

	@Update("UPDATE penduduk SET nama = #{nama}, tempat_lahir = #{tempat_lahir}, "
			+ "tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, "
			+ "is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, "
			+ "pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, "
			+ "status_dalam_keluarga = #{status_dalam_keluarga}, "
			+ "golongan_darah = #{golongan_darah} WHERE nik = #{nik}")
	void updatePenduduk(PendudukModel penduduk);
	
	@Select("select id from penduduk where nik = #{nik}")
	int selectIdByNik(String nik);

	@Update("UPDATE penduduk SET nik = #{nik} where id = #{id}")
	void updatePendudukNIK(@Param("nik") String nik,@Param("id") int id);

	@Update ("update penduduk set is_wafat = 1 where nik = #{nik}")
	void updateStatusKematianByNik(@Param("nik") String nik);

	@Select("select count(*) from penduduk "
			+ "where is_wafat = '1' and id_keluarga = #{id_keluarga}")
	int countPendudukMatiDiKeluarga(String id_keluarga);

	@Select("SELECT * FROM penduduk p, keluarga k, kelurahan kl where kl.id = #{id_kelurahan} "
			+ "and kl.id = k.id_kelurahan and p.id_keluarga = k.id")
	List<PendudukModel> selectAllPendudukByIdKelurahan(String id_kelurahan);

	@Select("SELECT * FROM penduduk p, keluarga k, kelurahan kl where kl.id = #{id_kelurahan} "
			+ "and kl.id = k.id_kelurahan and p.id_keluarga = k.id order by p.tanggal_lahir ASC LIMIT 1")
	PendudukModel selectPendudukTertua(String id_kelurahan);

	@Select("SELECT * FROM penduduk p, keluarga k, kelurahan kl where kl.id = #{id_kelurahan} "
			+ "and kl.id = k.id_kelurahan and p.id_keluarga = k.id order by p.tanggal_lahir DESC LIMIT 1")
	PendudukModel selectPendudukTermuda(String id_kelurahan);

	
}
