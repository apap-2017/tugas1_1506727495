package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.example.model.KelurahanModel;
import com.example.model.KecamatanModel;
import com.example.model.KotaModel;

@Mapper
public interface LokasiMapper {

	@Select("select * from kelurahan where id = #{id_kelurahan}")
	@Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="id_kecamatan", column="id_kecamatan"), 
    		@Result(property="kode_kelurahan", column="kode_kelurahan"), 
    		@Result(property="nama_kelurahan", column="nama_kelurahan"),
    		@Result(property="kode_pos", column="kode_pos")
    		
    })
	KelurahanModel selectKelurahanById(String id_kelurahan);

	@Select("select * from kecamatan where id = #{id_kecamatan}")
	@Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="id_kota", column="id_kota"), 
    		@Result(property="kode_kecamatan", column="kode_kecamatan"), 
    		@Result(property="nama_kecamatan", column="nama_kecamatan")
    })
	KecamatanModel selectKecamatanById(String id_kecamatan);

	@Select("select * from kota where id = #{id_kota}")
	@Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="kode_kota", column="kode_kota"), 
    		@Result(property="nama_kota", column="nama_kota")
    })
	KotaModel selectKotaById(String id_kota);
	
	@Select("select * from kelurahan where nama_kelurahan = #{nama_kelurahan}")
	@Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="id_kecamatan", column="id_kecamatan"), 
    		@Result(property="kode_kelurahan", column="kode_kelurahan"), 
    		@Result(property="nama_kelurahan", column="nama_kelurahan"),
    		@Result(property="kode_pos", column="kode_pos")
    		
    })
	KelurahanModel selectKelurahanByName(String nama_kelurahan);
	
	@Select("select * from kecamatan where nama_kecamatan = #{nama_kecamatam}")
	@Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="id_kota", column="id_kota"), 
    		@Result(property="kode_kecamatan", column="kode_kecamatan"), 
    		@Result(property="nama_kecamatan", column="nama_kecamatan")
    })
	KecamatanModel selectKecamatanByName(String nama_kecamatan);

	@Select("select * from kelurahan where id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectAllKelurahan(String id_kecamatan);

	@Select("select * from kecamatan where id_kota = #{id_kota}")
	List<KecamatanModel> selectAllKecamatan(String id_kota);

	@Select("select * from kota")
	List<KotaModel> selectAllKota();


}
