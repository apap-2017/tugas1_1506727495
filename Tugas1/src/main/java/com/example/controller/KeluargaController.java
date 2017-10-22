package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KeluargaService;
import com.example.service.LokasiService;
import com.example.service.PendudukService;

@Controller
public class KeluargaController {
	@Autowired
	PendudukService pendudukDAO;

	@Autowired
	KeluargaService keluargaDAO;

	@Autowired
	LokasiService lokasiDAO;

	@RequestMapping("/keluarga")
	public String findKeluargaByNKK(@RequestParam(value = "nkk", required = false, defaultValue = "0") String nkk,
			Model model) {
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNKK(nkk);

		if (keluarga != null) {
			KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(keluarga.getId_kelurahan());
			KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
			KotaModel kota = lokasiDAO.selectKotaById(kecamatan.getId_kota());
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			return "view-keluarga";
		} else {
			model.addAttribute("nomor", nkk);
			return "notfound-keluarga";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/keluarga/tambah")
	public String formTambahKeluarga() {
		return "tambahkeluarga";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/keluarga/tambah")
	public String tambahPendudukSubmit(@RequestParam(value = "alamat", required = false) String alamat,
			@RequestParam(value = "RT", required = false) String RT,
			@RequestParam(value = "RW", required = false) String RW,
			@RequestParam(value = "nama_kelurahan", required = false) String nama_kelurahan,
			@RequestParam(value = "nama_kecamatan", required = false) String nama_kecamatan,
			@RequestParam(value = "nama_kota", required = false) String nama_kota, Model model) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date2 = dateFormat.format(date);
		KelurahanModel kelurahan = lokasiDAO.selectKelurahanByNama(nama_kelurahan);
		String idKelurahan = kelurahan.getId();
		KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
		String kodeKecamatan = kecamatan.getKode_kecamatan().substring(0, kecamatan.getKode_kecamatan().length() - 1);
		Integer tahun = Integer.parseInt(date2.substring(2, 4));
		Integer bulan = Integer.parseInt(date2.substring(5, 7));
		Integer tanggal = Integer.parseInt(date2.substring(8, date2.length()));

		String nkkTemp = kodeKecamatan + tanggal + bulan + tahun;
		int count = keluargaDAO.countNKK(nkkTemp + "%");
		count += 1;
		String akhir = "" + count;
		int i = 4;
		while (i > akhir.length()) {
			akhir = "0" + akhir;
		}
		String nkk = nkkTemp + akhir;
		KeluargaModel keluarga = new KeluargaModel(nkk, alamat, RT, RW, idKelurahan, 0, null);
		model.addAttribute("keluarga", keluarga);
		keluargaDAO.tambahKeluarga(keluarga);
		return "success-addKeluarga";
	}

	@RequestMapping(method=RequestMethod.GET, value="/keluarga/ubah/{nomor_kk}")
	public String formUpdateKeluargak(Model model, @PathVariable(value = "nomor_kk") String nomor_kk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNKK(nomor_kk);
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(keluarga.getId_kelurahan());
			String nama_kelurahan = kelurahan.getNama_kelurahan();
			KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
			String nama_kecamatan = kecamatan.getNama_kecamatan();
			KotaModel kota = lokasiDAO.selectKotaById(kecamatan.getId_kota());
			String nama_kota = kota.getNama_kota();
			model.addAttribute("kelurahan", nama_kelurahan);
			model.addAttribute("kecamatan", nama_kecamatan);
			model.addAttribute("kota", nama_kota);
			return "update-keluarga";
		} else {
			model.addAttribute("nomor", nomor_kk);
			return "notfound-keluarga";

		}
	}

	@RequestMapping(method=RequestMethod.POST, value="/keluarga/ubah/{nomor_kk}")
	public String updatePenduduk(@PathVariable(value = "nomor_kk") String nomor_kk,
			Model model, KeluargaModel keluarga,
			@RequestParam(value = "nama_kecamatan_lama", required = false) String nama_kecamatan_lama,
			@RequestParam(value = "nama_kelurahan_lama", required = false) String nama_kelurahan_lama,
			@RequestParam(value = "nama_kota_lama", required = false) String nama_kota_lama,
			@RequestParam(value = "nomor_kk_lama", required = false) String nomor_kk_lama,
			@RequestParam(value = "nama_kelurahan", required = false) String nama_kelurahan,
			@RequestParam(value = "nama_kecamatan", required = false) String nama_kecamatan,
			@RequestParam(value = "nama_kota", required = false) String nama_kota) {
		if (!nama_kelurahan.equalsIgnoreCase(nama_kelurahan_lama)
				|| !nama_kecamatan.equalsIgnoreCase(nama_kecamatan_lama)
				|| !nama_kota.equalsIgnoreCase(nama_kota_lama)) {
			String nkkBaru = "";
			String kode_kecamatan = lokasiDAO.selectKecamatanByNama(nama_kecamatan).getKode_kecamatan().substring(0, 6);

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();

			String date2 = dateFormat.format(date);
			String tahun = date2.substring(2, 4);
			String bulan = date2.substring(5, 7);
			String tanggal = date2.substring(8, date2.length());
			String tgl_skrg = tanggal + bulan + tahun;
			nkkBaru = kode_kecamatan + tgl_skrg;

			int count = keluargaDAO.countNKK(nkkBaru + "%");
			count += 1;

			String akhir = "" + count;
			int i = 4;
			while (i > akhir.length()) {
				akhir = "0" + akhir;
			}
			nkkBaru = nkkBaru + akhir;
			String id_keluarga = keluargaDAO.selectIdKeluargaByNkk(nomor_kk_lama);
			List<PendudukModel> anggota = keluargaDAO.selectKeluargaByNKK(nomor_kk_lama).getPendudukDiKeluarga();
			for (int j = 0; j < anggota.size(); j++) {

				PendudukModel penduduk = anggota.get(j);

				String tgl = penduduk.getTanggal_lahir();
				String tahunlhr = tgl.substring(2, 4);
				String bulanlhr = tgl.substring(5, 7);
				String tanggallhr = tgl.substring(8, date2.length());
				String tgllhr = tanggallhr + bulanlhr + tahunlhr;
				String nikBaru = kode_kecamatan + tgllhr;
				int count1 = pendudukDAO.countNIK(nikBaru + "%");
				count1 += 1;

				String akhir1 = "" + count1;
				int i1 = 4;
				while (i1 > akhir1.length()) {
					akhir1 = "0" + akhir1;
				}
				nikBaru = nikBaru + akhir1;
				String nikLama = penduduk.getNik();
				int idPenduduk = pendudukDAO.selectIdByNik(nikLama);
				pendudukDAO.updatePendudukNIK(nikBaru, idPenduduk);
			}

			model.addAttribute("nomor_kk", nomor_kk_lama);
			String id_kelurahan = lokasiDAO.selectKelurahanByNama(nama_kelurahan).getId();
			keluarga.setId_kelurahan(id_kelurahan);
			keluargaDAO.updateKeluarga(keluarga);
			keluargaDAO.updateNkkById(nkkBaru, id_keluarga);

			return "success-update-keluarga";
		} else {
			model.addAttribute("nomor_kk", nomor_kk_lama);
			String id_kelurahan = lokasiDAO.selectKelurahanByNama(nama_kelurahan).getId();
			keluarga.setId_kelurahan(id_kelurahan);
			keluargaDAO.updateKeluarga(keluarga);
			return "success-update-keluarga";
		}

	}

}
