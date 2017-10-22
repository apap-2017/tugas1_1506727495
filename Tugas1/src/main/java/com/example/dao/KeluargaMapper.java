package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {

	@Select("select * from keluarga where id = #{id_keluarga}")
	@Results(value = {
    		@Result(property="nomor_kk", column="nomor_kk"), 
    		@Result(property="alamat", column="alamat"), 
    		@Result(property="RT", column="RT"),
    		@Result(property="RW", column="RW"),
    		@Result(property="id_kelurahan", column="id_kelurahan"),
    		@Result(property="is_tidak_berlaku", column="is_tidak_berlaku")
    		
    })
	KeluargaModel selectKeluargaByID(String id_keluarga);
	
	
	@Select("select * from keluarga where nomor_kk = #{nkk}")
	@Results(value = {
    	
    		@Result(property="nomor_kk", column="nomor_kk"), 
    		@Result(property="alamat", column="alamat"), 
    		@Result(property="RT", column="RT"),
    		@Result(property="RW", column="RW"),
    		@Result(property="id_kelurahan", column="id_kelurahan"),
    		@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
    		@Result(property="pendudukDiKeluarga",column="id", 
			javaType = List.class, 
			many=@Many(select="selectPenduduk"))
    		
    })
	KeluargaModel selectKeluargaByNKK(@Param("nkk") String nkk);
	
	@Select("select nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, "
			+ "is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, "
			+ "status_dalam_keluarga, golongan_darah, is_wafat from penduduk where id_keluarga = #{id}")
	List<PendudukModel> selectPenduduk (@Param("id") String id);

	
	@Insert("insert into keluarga(nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) "
			+ "values(#{nomor_kk}, #{alamat}, #{RT}, #{RW}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void tambahKeluarga(KeluargaModel keluarga);
	
	@Select("select count(*) from keluarga where nomor_kk like #{nomor_kk}")
	int countNKK(String nomor_kk);

	
	@Update("UPDATE keluarga SET alamat = #{alamat}, RT = #{RT}, "
			+ "RW = #{RW}, id_kelurahan = #{id_kelurahan}, "
			+ "is_tidak_berlaku = #{is_tidak_berlaku}  WHERE nomor_kk = #{nomor_kk}")
	void updateKeluarga(KeluargaModel keluarga);

	@Update("UPDATE keluarga set nomor_kk = #{nomor_kk} where id = #{id}")
	void updateNkkById(@Param("nomor_kk") String nomor_kk, @Param("id") String id);

	@Select("select id from keluarga where nomor_kk = #{nomor_kk_lama}")
	String selectIdKeluargaByNkk(@Param("nomor_kk_lama") String nomor_kk_lama);


	@Update("update keluarga set is_tidak_berlaku = '1' where id = #{id_keluarga}")
	void updateIsberlaku(String id_keluarga);
	
	
	
}
