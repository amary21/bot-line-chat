package com.amary.bot.petani.pintar.line.petanipintar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineSignatureValidator;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.DatetimePickerAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.container.FlexContainer;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.objectmapper.ModelObjectMapper;
import com.linecorp.bot.model.profile.UserProfileResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    @Autowired
    @Qualifier("lineMessagingClient")
    private LineMessagingClient lineMessagingClient;

    @Autowired
    @Qualifier("lineSignatureValidator")
    private LineSignatureValidator lineSignatureValidator;

    @RequestMapping(value="/webhook", method= RequestMethod.POST)
    public ResponseEntity<String> callback(
            @RequestHeader("X-Line-Signature") String xLineSignature,
            @RequestBody String eventsPayload)
    {
        try {
//            if (!lineSignatureValidator.validateSignature(eventsPayload.getBytes(), xLineSignature)) {
//                throw new RuntimeException("Invalid Signature Validation");
//            }

            ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
            EventsModel eventsModel = objectMapper.readValue(eventsPayload, EventsModel.class);

            eventsModel.getEvents().stream().filter(event -> event instanceof MessageEvent).map(event -> (MessageEvent) event).forEach(messageEvent -> {
                TextMessageContent textMessageContent = (TextMessageContent) messageEvent.getMessage();
                if (textMessageContent.getText().toLowerCase().equals("hai")) {
                    replyText(messageEvent.getReplyToken(), "Hai juga");
                }
                if (textMessageContent.getText().toLowerCase().equals("hallo")
                        || textMessageContent.getText().toLowerCase().equals("halo")) {
                    replyText(messageEvent.getReplyToken(), "Hallo juga");
                }

                //menu utama
                if (textMessageContent.getText().toLowerCase().equals("menu")) {
                    menuUtama(messageEvent.getReplyToken());
                }

                //menu harga pasar
                if (textMessageContent.getText().toLowerCase().equals("harga pasar")){
                    menuHargaPasar(messageEvent.getReplyToken());
                }

                    //list harga panen
                if (textMessageContent.getText().toLowerCase().equals("harga panen")){
                    listHargaPanen(messageEvent.getReplyToken());
                }
                if (textMessageContent.getText().toLowerCase().equals("cek harga padi super kering")){
                    replyText(messageEvent.getReplyToken(),"Harga panen padi 'super kering' saat ini yakni\n" +
                            "Rp 5.000 /1kg");
                }
                if (textMessageContent.getText().toLowerCase().equals("cek harga padi kering")){
                    replyText(messageEvent.getReplyToken(),"Harga panen padi 'kering' saat ini yakni\n" +
                            "Rp 4.800 /1kg");
                }
                if (textMessageContent.getText().toLowerCase().equals("cek harga padi super basah")){
                    replyText(messageEvent.getReplyToken(),"Harga panen padi 'super basah' saat ini yakni\n" +
                            "Rp 4.500 /1kg");
                }
                if (textMessageContent.getText().toLowerCase().equals("cek harga padi basah")){
                    replyText(messageEvent.getReplyToken(),"Harga panen padi 'basah' saat ini yakni\n" +
                            "Rp 4.200 /1kg");
                }

                    //list harga benih
                if (textMessageContent.getText().toLowerCase().equals("harga benih")){
                    listHargaBenih(messageEvent.getReplyToken());
                }
                        //harga
                if (textMessageContent.getText().toLowerCase().equals("cek harga varietas pw8")){
                    replyText(messageEvent.getReplyToken(),"Harga benih Varietas PW8\n" +
                            "Rp. 60.000");
                }
                if (textMessageContent.getText().toLowerCase().equals("cek harga sertani 13")){
                    replyText(messageEvent.getReplyToken(),"Harga benih SERTANI 13\n" +
                            "Rp. 76.000");
                }
                if (textMessageContent.getText().toLowerCase().equals("cek harga inpari 33")){
                    replyText(messageEvent.getReplyToken(),"Harga benih Inpari 33\n" +
                            "Rp. 125.000");
                }
                        //deskripsi
                if (textMessageContent.getText().toLowerCase().equals("deskripsi padi varietas pw8")){
                    replyText(messageEvent.getReplyToken(),"Varietas PW8\n" +
                            "---------------------------------------------\n" +
                            "padi unggul varietas PW8(PANDAN WALI)\n" +
                            "persilangan Cr 04 X SBK. umur tanaman 110 - 115 hss.\n" +
                            "tinggi tanaman 100-110 cm.\n" +
                            "anakan produktif 25-40 batang.\n" +
                            "telinga daun tidak berwarna.\n" +
                            "daun bendera tegak. malai panjang dan mudah berisi.\n" +
                            "jumlah permalai 250 - 350 butir.\n" +
                            "gabah kuning bersih. \n" +
                            "kerontokan sedang. \n" +
                            "tanaman kuat dan responsif thd pemupukan tinggi.\n" +
                            "potensi hasil 12 - 14 ton. \n" +
                            "bobog1000 butir 26 grm. \n" +
                            "kadar amilosa 23%.\n" +
                            "tekstur nasi pulen.");
                }
                if (textMessageContent.getText().toLowerCase().equals("deskripsi sertani 13")){
                    replyText(messageEvent.getReplyToken(),"SERTANI 13\n" +
                            "----------------------------------------------------\n" +
                            "Umur tanaman 115-125 hari\n" +
                            "Tinggi tanaman 110-115 cm\n" +
                            "Jumlah bulir per malai 300-350\n" +
                            "Kualitas Super (Premium Quality)\n" +
                            "Potensi anakan produktif 30\n" +
                            "Potensi hasil 13 ton/ha");
                }
                if (textMessageContent.getText().toLowerCase().equals("deskripsi inpari 33")){
                    replyText(messageEvent.getReplyToken(),"Inpari 33\n" +
                            "--------------------------------------------------------\n" +
                            "Golongan : Cere\n" +
                            "Umur tanaman : 107 hari setelah sebar\n" +
                            "Bentuk tanaman : Tegak\n" +
                            "Tinggi tanaman : 93 cm\n" +
                            "Daun bendera : Tegak\n" +
                            "Bentuk gabah : Panjang ramping\n" +
                            "Warna gabah : Kuning bersih\n" +
                            "Kerontokan : Sedang\n" +
                            "Kerebahan : Agak tahan\n" +
                            "Tekstur nasi : Sedang\n" +
                            "- Kadar amilosa : 23,42 %\n" +
                            "- Berat 1000 butir : 28,6 gram\n" +
                            "- Rata-rata hasil : 6,6 ton/ha GKG\n" +
                            "- Potensi hasil : 9,8 ton/ha GKG");
                }

                    //list harga obat
                if (textMessageContent.getText().toLowerCase().equals("harga obat - obatan")){
                    listHargaObat(messageEvent.getReplyToken());
                }
                        //harga
                if (textMessageContent.getText().toLowerCase().equals("cek harga spontan")){
                    replyText(messageEvent.getReplyToken(),"Harga obat Spontan 500 ml\n" +
                            "Rp. 70.000");
                }
                if (textMessageContent.getText().toLowerCase().equals("cek harga insektisida stuntman")){
                    replyText(messageEvent.getReplyToken(),"Harga obat Insektisida Stuntman 500 ml\n" +
                            "Rp. 85.000");
                }
                        //deskripsi
                if (textMessageContent.getText().toLowerCase().equals("deskripsi spontan")){
                    replyText(messageEvent.getReplyToken(),"SPONTAN 400SL adalah insektisida racun kontak dan lambung berbentuk pekatan yang dapat diemulsikan bewarna kuning jernih untuk mengendalikan hama:\n" +
                            "\n" +
                            "- penggerek batang (Tryporyza incertulas)\n" +
                            "- Wereng coklat (Nilaparvata lugens)\n" +
                            "- Hama putih (Nymphula depunctalis) \n" +
                            "- Lalat daun (Hydrellia philipina)\n" +
                            "- Hama putih palsu (Cnaphalocrosis medinalis) pada tanaman padi\n" +
                            "- Lalat bibit (Ophiomya phaseoli) \n" +
                            "- Penggulung daun (Lamprosema indicata) pada tanaman kedelai\n" +
                            "- Lalat pengorok daun (Liriomyza huidobrensis) pada tanaman kentang \n" +
                            "- Belalang (Locusta migratoria) pada tanaman jagung\n" +
                            "- Sexava nubila pada tanaman kelapa");
                }
                if (textMessageContent.getText().toLowerCase().equals("deskripsi insektisida stuntman")){
                    replyText(messageEvent.getReplyToken(),"Keunggulan dari stuntman 500SL\n" +
                            "Bahan aktif dimehypo tinggi (dimehypo 500gr / l) sehingga hama kontrol lebih cepat, tepat dan powerfulWorking sebagai Poison Kontak, alam lambung dan systemicOvicidal kelompok telur HPBP, HPP, hopper dan kelas LepidopteraIn sesuai dengan IPM metode SL, karena SL aman erhadap Stuntman 500 musuh alami, lebah dan fishNon Pestisida karsinogenik");
                }
                //menu laporan sekitar
                if (textMessageContent.getText().toLowerCase().equals("laporan sekitar")){
                    menuLaporanSekitar(messageEvent.getReplyToken());
                }
                if (textMessageContent.getText().toLowerCase().equals("cuaca")){
                    laporanCuaca(messageEvent.getReplyToken());
                }
                if (textMessageContent.getText().toLowerCase().equals("perairan")){
                    laporanAir(messageEvent.getReplyToken());
                }

                //menu solusi penyakit
                if (textMessageContent.getText().toLowerCase().equals("solusi penyakit padi")){
                    menuSolusiPenyakitPadi(messageEvent.getReplyToken());
                }
                if (textMessageContent.getText().toLowerCase().equals("wereng")){
                    solusiWereng(messageEvent.getReplyToken());
                }
                if (textMessageContent.getText().toLowerCase().equals("bercak daun")){
                    solusiBercak(messageEvent.getReplyToken());
                }
                if (textMessageContent.getText().toLowerCase().equals("fusarium")){
                    solusiFusarium(messageEvent.getReplyToken());
                }

                //confirmasi
                if (textMessageContent.getText().toLowerCase().equals("ya, saya ingin mencari informasi lain")){
                    menuUtama(messageEvent.getReplyToken());
                }
                if (textMessageContent.getText().toLowerCase().equals("tidak, terima kasih")){
                    replyText(messageEvent.getReplyToken(), "Terima kasih telah menggunakan Bot Petani Pintar");
                }
                else {
                    ConfirmTemplate confirmTemplate = new ConfirmTemplate(
                            "Apakah anda ingin mencari informasi lain?",
                            new MessageAction("Ya", "Ya, saya ingin mencari informasi lain"),
                            new MessageAction("Tidak", "Tidak, terima kasih")
                    );

                    List<Message> msgArray = new ArrayList<>();
                    msgArray.add(new StickerMessage("1", "1"));
                    msgArray.add(new TextMessage("Mohon maaf, informasi yang anda cari belum tersedia"));
                    msgArray.add(new TemplateMessage("Confirm alt text", confirmTemplate));
                    ReplyMessage replyMessage = new ReplyMessage(messageEvent.getReplyToken(), msgArray);
                    reply(replyMessage);

                }
            });

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/pushmessage/{id}/{message}", method=RequestMethod.GET)
    public ResponseEntity<String> pushmessage(
            @PathVariable("id") String userId,
            @PathVariable("message") String textMsg
    ){
        TextMessage textMessage = new TextMessage(textMsg);
        PushMessage pushMessage = new PushMessage(userId, textMessage);
        push(pushMessage);

        return new ResponseEntity<String>("Push message:"+textMsg+"\nsent to: "+userId, HttpStatus.OK);
    }

    @RequestMapping(value="/multicast", method=RequestMethod.GET)
    public ResponseEntity<String> multicast(){
        String[] userIdList = {
                "U206d25c2ea6bd87c17655609xxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"};
        Set<String> listUsers = new HashSet<String>(Arrays.asList(userIdList));
        if(listUsers.size() > 0){
            String textMsg = "Ini pesan multicast";
            sendMulticast(listUsers, textMsg);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> profile(
            @PathVariable("id") String userId
    ){
        UserProfileResponse profile = getProfile(userId);

        if (profile != null) {
            String profileName = profile.getDisplayName();
            TextMessage textMessage = new TextMessage("Hello, " + profileName);
            PushMessage pushMessage = new PushMessage(userId, textMessage);
            push(pushMessage);

            return new ResponseEntity<String>("Hello, "+profileName, HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    private void reply(ReplyMessage replyMessage) {
        try {
            lineMessagingClient.replyMessage(replyMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void replyText(String replyToken, String messageToUser){
        TextMessage textMessage = new TextMessage(messageToUser);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, textMessage);
        reply(replyMessage);
    }

    private void menuUtama(String replyToken){
        String imageUrl = "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/asian_farmers_drawing_design_with_rice_field_27220.jpg";
        ButtonsTemplate buttonsTemplate = new ButtonsTemplate(
                imageUrl,
                "Menu Utama",
                "Silahkan pilih menu yang ingin anda cari",
                Arrays.asList(
                        new MessageAction("Harga Pasar",
                                "Harga Pasar"),
                        new MessageAction("Laporan Sekitar",
                                "Laporan Sekitar"),
                        new MessageAction("Solusi Penyakit Padi",
                                "Solusi Penyakit Padi")
                ));
        TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, templateMessage);
        reply(replyMessage);
    }

    private void menuHargaPasar(String replyToken){
        String imageUrl = "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/poor-clipart-poor-farmer-3.jpg";
        ButtonsTemplate buttonsTemplate = new ButtonsTemplate(
                imageUrl,
                "Harga Pasar",
                "berikut daftar menu harga pasar tanaman padi",
                Arrays.asList(
                        new MessageAction("Harga Panen",
                                "Harga Panen"),
                        new MessageAction("Harga Benih",
                                "Harga Benih"),
                        new MessageAction("Harga obat - obatan",
                                "Harga obat - obatan")
                ));
        TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, templateMessage);
        reply(replyMessage);
    }

    private void menuSolusiPenyakitPadi(String replyToken) {
        String imageUrl = "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/Wereng-Hijau-Nephotettix-spp-Unsurtani.jpg";
        ButtonsTemplate buttonsTemplate = new ButtonsTemplate(
                imageUrl,
                "Solusi Penyakit Padi",
                "berikut beberapa solusi penyakit pada tanaman padi",
                Arrays.asList(
                        new MessageAction("Wereng",
                                "Wereng"),
                        new MessageAction("Bercak Daun",
                                "Bercak Daun"),
                        new MessageAction("Fusarium",
                                "Fusarium")
                ));
        TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, templateMessage);
        reply(replyMessage);
    }

    private void menuLaporanSekitar(String replyToken) {
        String imageUrl = "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/farmer-planting-clipart.png";
        ButtonsTemplate buttonsTemplate = new ButtonsTemplate(
                imageUrl,
                "Laporan Sekitar",
                "Laporan pertanian disekitar anda",
                Arrays.asList(
                        new MessageAction("Cuaca",
                                "Cuaca"),
                        new MessageAction("Perairan",
                                "Perairan")
                ));
        TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, templateMessage);
        reply(replyMessage);
    }

    private void solusiWereng(String replyToken){
        TextMessage textMessage = new TextMessage("Cara membasmi hama wereng secara alami yaitu dengan memberikan nutrisi yang cukup pada tanaman padi. Dengan memberikan nutrisi yang cukup dapat dipastikan bahwa tanaman padi tidak akan tersec\n" +
                "Agar tanaman padi tidak kelebihan unsur hara makro & mikro dan selalu tercukupi kebutuhan nutrisi, untuk itu diperlukan makhluk hidup yang bisa mengontrol jumlah kebutuhan nutrisi yang diperlukan tanaman. Makhluk hidup tersebut adalah bakteri (mikroba).\n" +
                "Bakteri tanah mengontrol pasokan harian nutrisi pada kondisi dingin atau panas yang ekstrim atau jika tanah tersebut terendam air, bakteri melambat, dan memperlambat pelepasan nitrogen.");
        ReplyMessage replyMessage = new ReplyMessage(replyToken, textMessage);
        reply(replyMessage);
    }

    private void solusiBercak(String replyToken){
        TextMessage textMessage = new TextMessage("Selama ini pengendalian penyakit bercak daun cercospora hanya dapat dilakukan dengan penyemprotan fungisida. Pengendalian dengan 3 kali penyemprotan yaitu pada fase anakan maksimum, awal pembungaan dan awal pengisian dengan fungisida benomil, mankozeb, carbendazim, atau difenoconazol dengan dosis 1 cc per 1 liter air, dengan volume semprot 500 liter per ha, dapat menekan perkembangan penyakit bercak daun cercospora dan menekan kehilangan hasil padi sampai dengan 30 persent ");
        ReplyMessage replyMessage = new ReplyMessage(replyToken, textMessage);
        reply(replyMessage);
    }

    private void solusiFusarium(String replyToken){
        TextMessage textMessage = new TextMessage("Untuk mengobati busuk akar karena cendawan maka anda bisa menggunakan Fungisida berbahan aktif Difekonazol (Score atau Amistar Top) sekitar 4 hari sekali sambil diselingi fungisida kontak berbahan aktif propineb atau tembaga hidroksida. Jika tidak kunjung membaik maka ganti fungisida sistemik dengan bahan aktif karbendazim.\n" +
                "Jika terjadi busuk akar akibat serangan bakteri patogen, maka yang digunakan adalah bakterisida berbahan aktif streptomicyn mix dengan tembaga hidroksida. Penyemprotan dilakukan 2 hari sekali. Jangan lupa untuk menggunakan pelekat terutama jika menyemprotkan dimusim hujan agar bahan aktif tidak segera luntur.\n" +
                "Bakterisida berbahan aktif streptomicyn akan bekerja secara sistemik dengan masuk dan menyebar kedalam jaringan tanaman dan mematikan setiap bakteri dari dalam jaringan tanaman");
        ReplyMessage replyMessage = new ReplyMessage(replyToken, textMessage);
        reply(replyMessage);
    }

    private void laporanCuaca(String replyToken){
        TextMessage textMessage = new TextMessage("laporan cuaca di sekitar anda saat ini diperkirakan cerah berawan.\n" +
                "- temprature 21 derajat Celcius\n" +
                "- suhu minimum 22 derajat Celcius\n" +
                "- suhu maksimum 32 derajat\n" +
                "- kelembapan 90 persen\n" +
                "- kecepatan angin 19 km/jam");
        ReplyMessage replyMessage = new ReplyMessage(replyToken, textMessage);
        reply(replyMessage);
    }

    private void laporanAir(String replyToken){
        TextMessage textMessage = new TextMessage("laporan perairan di sekitar anda diperkirakan lancar tanpa ada kendala\n" +
                "- perairan sungai lancar\n" +
                "- perairan irigasi lancar\n" +
                "- cuaca cerah berawan\n" +
                "- tidak ada kekeringan");
        ReplyMessage replyMessage = new ReplyMessage(replyToken, textMessage);
        reply(replyMessage);
    }

    private void listHargaPanen(String replyToken){
        CarouselTemplate carouselTemplate = new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(
                                "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/superkering.jpg",
                                null,
                                "Super Kering",
                                Arrays.asList(
                                new MessageAction("cek harga",
                                        "cek harga padi super kering")
                        )),
                        new CarouselColumn(
                                "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/kering.jpg",
                                null,
                                "Kering",
                                Arrays.asList(
                                new MessageAction("cek harga",
                                        "cek harga padi kering")
                        )),
                        new CarouselColumn(
                                "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/superbasah.jpg",
                                null,
                                "Super Basah",
                                Arrays.asList(
                                new MessageAction("cek harga",
                                        "cek harga padi super basah")
                        )),
                        new CarouselColumn(
                                "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/basah.jpg",
                                null,
                                "Basah",
                                Arrays.asList(
                                new MessageAction("cek harga",
                                        "cek harga padi basah")
                        ))
                ));
        TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, templateMessage);
        reply(replyMessage);
    }

    private void listHargaBenih(String replyToken) {
        CarouselTemplate carouselTemplate = new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(
                                "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/Varietas%20PW8.jpg",
                                null,
                                "Varietas PW8",
                                Arrays.asList(
                                        new MessageAction("cek harga",
                                                "cek harga Varietas PW8"),
                                        new MessageAction("deskripsi",
                                                "deskripsi padi Varietas PW8")
                                )),
                        new CarouselColumn(
                                "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/SERTANI%2013.jpg",
                                null,
                                "SERTANI 13",
                                Arrays.asList(
                                        new MessageAction("cek harga",
                                                "cek harga SERTANI 13"),
                                        new MessageAction("deskripsi",
                                                "deskripsi SERTANI 13")
                                )),
                        new CarouselColumn(
                                "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/Inpari%2033.jpg",
                                null,
                                "Inpari 33",
                                Arrays.asList(
                                        new MessageAction("cek harga",
                                                "cek harga Inpari 33"),
                                        new MessageAction("deskripsi",
                                                "deskripsi Inpari 33")
                                ))
                ));
        TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, templateMessage);
        reply(replyMessage);
    }

    private void listHargaObat(String replyToken) {
        CarouselTemplate carouselTemplate = new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(
                                "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/spontan.jpg",
                                null,
                                "SPONTAN 500 Ml",
                                Arrays.asList(
                                        new MessageAction("cek harga",
                                                "cek harga SPONTAN"),
                                        new MessageAction("deskripsi",
                                                "deskripsi SPONTAN")
                                )),
                        new CarouselColumn(
                                "https://raw.githubusercontent.com/amary21/bot-line-chat/master/img/stuntman.jpg",
                                null,
                                "Insektisida STUNTMAN 500 SL 500ml",
                                Arrays.asList(
                                        new MessageAction("cek harga",
                                                "cek harga Insektisida STUNTMAN"),
                                        new MessageAction("deskripsi",
                                                "deskripsi Insektisida STUNTMAN")
                                ))
                ));
        TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, templateMessage);
        reply(replyMessage);
    }

    private void push(PushMessage pushMessage){
        try {
            lineMessagingClient.pushMessage(pushMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMulticast(Set<String> sourceUsers, String txtMessage){
        TextMessage message = new TextMessage(txtMessage);
        Multicast multicast = new Multicast(sourceUsers, message);

        try {
            lineMessagingClient.multicast(multicast).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private UserProfileResponse getProfile(String userId){
        try {
            return lineMessagingClient.getProfile(userId).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}