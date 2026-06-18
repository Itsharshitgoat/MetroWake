package com.metrowake.app.data.metro

import com.metrowake.app.data.local.StationEntity

object MetroDataInitializer {

    // Simple subset of Delhi Metro data for demonstration purposes.
    // In a real app, this would be a full comprehensive JSON loaded from assets.
    val initialStations = listOf(
        // Yellow Line
        StationEntity("samaypur_badli_yellow", "Samaypur Badli", "Yellow", "rohin_sector_18_yellow", null),
        StationEntity("rohin_sector_18_yellow", "Rohini Sector 18, 19", "Yellow", "haiderpur_yellow", "samaypur_badli_yellow"),
        StationEntity("haiderpur_yellow", "Haiderpur Badli Mor", "Yellow", "jahangirpuri_yellow", "rohin_sector_18_yellow"),
        StationEntity("jahangirpuri_yellow", "Jahangirpuri", "Yellow", "adarsh_nagar_yellow", "haiderpur_yellow"),
        StationEntity("adarsh_nagar_yellow", "Adarsh Nagar", "Yellow", "azadpur_yellow", "jahangirpuri_yellow"),
        StationEntity("azadpur_yellow", "Azadpur", "Yellow", "model_town_yellow", "adarsh_nagar_yellow", isInterchange = true),
        StationEntity("model_town_yellow", "Model Town", "Yellow", "gtb_nagar_yellow", "azadpur_yellow"),
        StationEntity("gtb_nagar_yellow", "GTB Nagar", "Yellow", "vishwavidyalaya_yellow", "model_town_yellow"),
        StationEntity("vishwavidyalaya_yellow", "Vishwavidyalaya", "Yellow", "vidhan_sabha_yellow", "gtb_nagar_yellow"),
        StationEntity("vidhan_sabha_yellow", "Vidhan Sabha", "Yellow", "civil_lines_yellow", "vishwavidyalaya_yellow"),
        StationEntity("civil_lines_yellow", "Civil Lines", "Yellow", "kashmere_gate_yellow", "vidhan_sabha_yellow"),
        StationEntity("kashmere_gate_yellow", "Kashmere Gate", "Yellow", "chandni_chowk_yellow", "civil_lines_yellow", isInterchange = true),
        StationEntity("chandni_chowk_yellow", "Chandni Chowk", "Yellow", "chawri_bazar_yellow", "kashmere_gate_yellow"),
        StationEntity("chawri_bazar_yellow", "Chawri Bazar", "Yellow", "new_delhi_yellow", "chandni_chowk_yellow"),
        StationEntity("new_delhi_yellow", "New Delhi", "Yellow", "rajiv_chowk_yellow", "chawri_bazar_yellow", isInterchange = true),
        StationEntity("rajiv_chowk_yellow", "Rajiv Chowk", "Yellow", "patel_chowk_yellow", "new_delhi_yellow", isInterchange = true),
        StationEntity("patel_chowk_yellow", "Patel Chowk", "Yellow", "central_secretariat_yellow", "rajiv_chowk_yellow"),
        StationEntity("central_secretariat_yellow", "Central Secretariat", "Yellow", "udyog_bhawan_yellow", "patel_chowk_yellow", isInterchange = true),
        StationEntity("udyog_bhawan_yellow", "Udyog Bhawan", "Yellow", "lok_kalyan_marg_yellow", "central_secretariat_yellow"),
        StationEntity("lok_kalyan_marg_yellow", "Lok Kalyan Marg", "Yellow", "jor_bagh_yellow", "udyog_bhawan_yellow"),
        StationEntity("jor_bagh_yellow", "Jor Bagh", "Yellow", "ina_yellow", "lok_kalyan_marg_yellow"),
        StationEntity("ina_yellow", "Dilli Haat - INA", "Yellow", "aiims_yellow", "jor_bagh_yellow", isInterchange = true),
        StationEntity("aiims_yellow", "AIIMS", "Yellow", "green_park_yellow", "ina_yellow"),
        StationEntity("green_park_yellow", "Green Park", "Yellow", "hauz_khas_yellow", "aiims_yellow"),
        StationEntity("hauz_khas_yellow", "Hauz Khas", "Yellow", "malviya_nagar_yellow", "green_park_yellow", isInterchange = true),
        StationEntity("malviya_nagar_yellow", "Malviya Nagar", "Yellow", "saket_yellow", "hauz_khas_yellow"),
        StationEntity("saket_yellow", "Saket", "Yellow", "qutab_minar_yellow", "malviya_nagar_yellow"),
        StationEntity("qutab_minar_yellow", "Qutab Minar", "Yellow", "chhattarpur_yellow", "saket_yellow"),
        StationEntity("chhattarpur_yellow", "Chhattarpur", "Yellow", "sultanpur_yellow", "qutab_minar_yellow"),
        StationEntity("sultanpur_yellow", "Sultanpur", "Yellow", "ghitorni_yellow", "chhattarpur_yellow"),
        StationEntity("ghitorni_yellow", "Ghitorni", "Yellow", "arjan_garh_yellow", "sultanpur_yellow"),
        StationEntity("arjan_garh_yellow", "Arjan Garh", "Yellow", "guru_dronacharya_yellow", "ghitorni_yellow"),
        StationEntity("guru_dronacharya_yellow", "Guru Dronacharya", "Yellow", "sikandarpur_yellow", "arjan_garh_yellow"),
        StationEntity("sikandarpur_yellow", "Sikandarpur", "Yellow", "mg_road_yellow", "guru_dronacharya_yellow"),
        StationEntity("mg_road_yellow", "MG Road", "Yellow", "iffco_chowk_yellow", "sikandarpur_yellow"),
        StationEntity("iffco_chowk_yellow", "IFFCO Chowk", "Yellow", "huda_city_centre_yellow", "mg_road_yellow"),
        StationEntity("huda_city_centre_yellow", "Millennium City Centre Gurugram", "Yellow", null, "iffco_chowk_yellow"),

        // Blue Line (Sample intersecting at Rajiv Chowk)
        StationEntity("rk_ashram_blue", "RK Ashram Marg", "Blue", "rajiv_chowk_blue", null),
        StationEntity("rajiv_chowk_blue", "Rajiv Chowk", "Blue", "barakhamba_blue", "rk_ashram_blue", isInterchange = true),
        StationEntity("barakhamba_blue", "Barakhamba Road", "Blue", "mandi_house_blue", "rajiv_chowk_blue"),
        StationEntity("mandi_house_blue", "Mandi House", "Blue", "supreme_court_blue", "barakhamba_blue", isInterchange = true),
        StationEntity("supreme_court_blue", "Supreme Court", "Blue", null, "mandi_house_blue")
    )
}
