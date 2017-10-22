package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KeluargaService;
import com.example.service.LokasiService;
import com.example.service.PendudukService;

@Controller
public class PendudukContoller {
	@Autowired
	PendudukService pendudukDAO;

	@Autowired
	KeluargaService keluargaDAO;

	@Autowired
	LokasiService lokasiDAO;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/penduduk")
	public String selectPenduduk(@RequestParam(value = "nik", required = false, defaultValue = "0") String nik,
			Model model) {
		PendudukModel penduduk = pendudukDAO.selectPendudukByNIK(nik);

		if (penduduk != null) {
			KeluargaModel keluarga = keluargaDAO.selectKeluargaByID(penduduk.getId_keluarga());
			KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(keluarga.getId_kelurahan());
			KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
			KotaModel kota = lokasiDAO.selectKotaById(kecamatan.getId_kota());
			String[] tanggal_lhr = penduduk.getTanggal_lahir().split("-");
			String tanggal = tanggal_lhr[2];
			String bulan = tanggal_lhr[1];
			String tahun = tanggal_lhr[0];
			String tanggal_lahir = tanggal + "-" + bulan + "-" + tahun;
			model.addAttribute("tanggal_lahir", tanggal_lahir);
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			model.addAttribute("nik", nik);
			if (penduduk.getIs_wni() == 1) {
				model.addAttribute("kewarganegaraan", "WNI");

			} else {
				model.addAttribute("kewarganegaraan", "WNA");
			}

			if (penduduk.getIs_wafat() == 0) {
				model.addAttribute("wafat", "Hidup");

			} else {
				model.addAttribute("wafat", "Wafat");
			}
			return "view-penduduk";
		} else {
			model.addAttribute("nomor", nik);
			return "notfound";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/penduduk/tambah")
	public String formTambahPenduduk() {
		return "tambahpenduduk";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/penduduk/tambah")
	public String tambahPendudukSubmit(@RequestParam(value = "nama", required = false) String nama,
			@RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
			@RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
			@RequestParam(value = "golongan_darah", required = false) String golongan_darah,
			@RequestParam(value = "jenis_kelamin", required = false) int jenis_kelamin,
			@RequestParam(value = "agama", required = false) String agama,
			@RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
			@RequestParam(value = "pekerjaan", required = false) String pekerjaan,
			@RequestParam(value = "is_wni", required = false) int is_wni,
			@RequestParam(value = "is_wafat", required = false) int is_wafat,
			@RequestParam(value = "id_keluarga", required = false) String id_keluarga,
			@RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga,
			Model model) {
		KeluargaModel infoKeluarga = keluargaDAO.selectKeluargaByID(id_keluarga);
		KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(infoKeluarga.getId_kelurahan());
		KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
		String kodeKecamatan = kecamatan.getKode_kecamatan().substring(0, kecamatan.getKode_kecamatan().length() - 1);
		String tahun = tanggal_lahir.substring(2, 4);
		String bulan = tanggal_lahir.substring(5, 7);

		String tanggal = tanggal_lahir.substring(8, tanggal_lahir.length());

		if (jenis_kelamin == 1) {

			Integer parseTgl = Integer.parseInt(tanggal) + 40;
			tanggal = "" + parseTgl;
		}

		String nikTemp = kodeKecamatan + tanggal + bulan + tahun;
		int count = pendudukDAO.countNIK(nikTemp + "%");
		count += 1;
		String akhir = "" + count;
		int i = 4;
		while (i > akhir.length()) {
			akhir = "0" + akhir;
		}
		String nik = kodeKecamatan + tanggal + bulan + tahun + akhir;
		PendudukModel penduduk = new PendudukModel(nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni,
				id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat);
		pendudukDAO.tambahPenduduk(penduduk);
		model.addAttribute("penduduk", penduduk);
		return "success-add";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/penduduk/ubah/{nik}")
	public String formUpdatePenduduk(Model model, @PathVariable(value = "nik") String nik) {
		if (pendudukDAO.selectPendudukByNIK(nik) != null) {

			model.addAttribute("penduduk", pendudukDAO.selectPendudukByNIK(nik));
			return "update-penduduk";
		} else {
			model.addAttribute("nomor", nik);
			return "notfound";

		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/penduduk/ubah/{nik}")
	public String updatePenduduk(Model model, PendudukModel penduduk,
			@RequestParam(value = "id_keluarga_lama", required = false) String id_keluarga_lama,
			@RequestParam(value = "jenis_kelamin_lama", required = false) int jenis_kelamin_lama,
			@RequestParam(value = "tanggal_lahir_lama", required = false) String tanggal_lahir_lama) {

		if (!penduduk.getId_keluarga().equalsIgnoreCase(id_keluarga_lama)
				|| !penduduk.getTanggal_lahir().equalsIgnoreCase(tanggal_lahir_lama)
				|| jenis_kelamin_lama != penduduk.getJenis_kelamin()) {
			String nikBaru = "";
			KeluargaModel infoKeluarga = keluargaDAO.selectKeluargaByID(penduduk.getId_keluarga());
			KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(infoKeluarga.getId_kelurahan());
			KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
			String kodeKecamatan = kecamatan.getKode_kecamatan().substring(0,
					kecamatan.getKode_kecamatan().length() - 1);
			String tanggal = penduduk.getTanggal_lahir().substring(penduduk.getTanggal_lahir().length() - 2,
					penduduk.getTanggal_lahir().length());
			String tahun = penduduk.getTanggal_lahir().substring(2, 4);
			String bulan = penduduk.getTanggal_lahir().substring(5, 7);
			String tgl_lahir = "";
			if (penduduk.getJenis_kelamin() == 0) {
				tgl_lahir = tanggal + bulan + tahun;

			} else {
				Integer tgl = Integer.parseInt(tanggal) + 40;
				tgl_lahir = "" + tgl + bulan + tahun;
			}

			nikBaru = kodeKecamatan + tgl_lahir;
			int count = pendudukDAO.countNIK(nikBaru + "%");
			count += 1;
			String akhir = "" + count;
			int i = 4;
			while (i > akhir.length()) {
				akhir = "0" + akhir;
			}
			nikBaru += akhir;
			int id = pendudukDAO.selectIdByNik(penduduk.getNik());
			model.addAttribute("nik", penduduk.getNik());
			pendudukDAO.updatePenduduk(penduduk);
			pendudukDAO.updatePendudukNIK(nikBaru, id);
			return "success-update";
		} else {
			model.addAttribute("nik", penduduk.getNik());
			pendudukDAO.updatePenduduk(penduduk);
			return "success-update";
		}

	}

	@RequestMapping(method=RequestMethod.POST, value="/penduduk/mati")
	public String ubahStatusKematian(@RequestParam(value = "nik", required = false) String nik, RedirectAttributes redirectAttributes) {
		String id_keluarga = pendudukDAO.selectPendudukByNIK(nik).getId_keluarga();
		pendudukDAO.updateStatusKematianByNik(nik);
		
		String nkk_keluarga = keluargaDAO.selectKeluargaByID(id_keluarga).getNomor_kk();
		int jml_anggota = keluargaDAO.selectKeluargaByNKK(nkk_keluarga).getPendudukDiKeluarga().size();
		int jml_anggota_mati = pendudukDAO.countPendudukMatiDiKeluarga(id_keluarga);
		if (jml_anggota == jml_anggota_mati) {
			keluargaDAO.updateIsberlaku(id_keluarga);
		}
		redirectAttributes.addFlashAttribute("flag_sukses", true);
		
		return "redirect:/penduduk?nik="+nik;
	}

	@RequestMapping("/penduduk/cari")
	public String formCariDataPenduduk(Model model, @RequestParam(value = "kt", required = false) String kt,
			@RequestParam(value = "kc", required = false) String kc,
			@RequestParam(value = "kl", required = false) String kl) {

		List<KotaModel> kotas = lokasiDAO.selectAllKota();
		model.addAttribute("kotas", kotas);
		if (kt != null) {
			String nama_kt = lokasiDAO.selectKotaById(kt).getNama_kota();
			model.addAttribute("nama_kota", nama_kt);
			model.addAttribute("id_kota", kt);
			model.addAttribute("flag_kt", true);
			model.addAttribute("flag_kota", true);
			if (kc == null) {
				List<KecamatanModel> kecamatans = lokasiDAO.selectAllKecamatanById(kt);
				model.addAttribute("kecamatans", kecamatans);
			}
			if (kc != null) {
				String nama_kec = lokasiDAO.selectKecamatanById(kc).getNama_kecamatan();
				model.addAttribute("nama_kecamatan", nama_kec);
				model.addAttribute("id_kecamatan", kc);
				model.addAttribute("flag_kc", true);
				model.addAttribute("flag_kec", true);
				if (kl == null) {
					System.out.println(lokasiDAO.selectKecamatanById(kc).getNama_kecamatan());
					List<KelurahanModel> kelurahans = lokasiDAO.selectAllKelurahanById(kc);
					model.addAttribute("kelurahans", kelurahans);
				}
			}

		}
		if (kl != null) {
			String nama_kel = lokasiDAO.selectKelurahanById(kl).getNama_kelurahan();
			model.addAttribute("nama_kelurahan", nama_kel);
			String nama_kt = lokasiDAO.selectKotaById(kt).getNama_kota();
			model.addAttribute("nama_kota", nama_kt);
			String nama_kec = lokasiDAO.selectKecamatanById(kc).getNama_kecamatan();
			model.addAttribute("nama_kecamatan", nama_kec);
			model.addAttribute("pendudukTua", pendudukDAO.selectPendudukTertua(kl));
			model.addAttribute("pendudukMuda", pendudukDAO.selectPendudukTermuda(kl));
			List<PendudukModel> listPenduduk = pendudukDAO.selectAllPendudukByIdKelurahan(kl);
			model.addAttribute("listPenduduk", listPenduduk);
			return "success-cari-data";
		}

		return "cari-data";
	}

}
