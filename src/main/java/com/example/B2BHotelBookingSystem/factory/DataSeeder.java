package com.example.demo.factory;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    // تزریق تمام ریپازیتوری‌های مورد نیاز
    private final HotelRepository hotelRepository;
    private final AgencyRepository agencyRepository;
    private final ContractRepository contractRepository;
    private final RateRepository rateRepository;
    private final RoomRepository roomRepository;

    public DataSeeder(HotelRepository hotelRepository,
                      AgencyRepository agencyRepository,
                      ContractRepository contractRepository,
                      RateRepository rateRepository,
                      RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.agencyRepository = agencyRepository;
        this.contractRepository = contractRepository;
        this.rateRepository = rateRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional // تضمین یکپارچگی تراکنش و جلوگیری از LazyInitializationException
    public void run(String... args) throws Exception {

        Random random = new Random();
        List<Hotel> hotels = new ArrayList<>();
        List<Agency> agencies = new ArrayList<>();

        // ۱. ایجاد ۱۰ آژانس (چون مستقل هستند اول این‌ها را ذخیره می‌کنیم)
        for (int i = 1; i <= 10; i++) {
            Agency agency = new Agency();
            agency.setName("آژانس " + i);
            agency.setAddress("آدرس آژانس " + i);
            agencies.add(agencyRepository.save(agency));
        }

        // ۲. ایجاد ۱۰ هتل و اتاق‌هایشان
        for (int i = 1; i <= 10; i++) {
            Hotel hotel = new Hotel();
            hotel.setName("هتل " + i);
            hotel.setStar(random.nextInt(5) + 1);

            // ذخیره هتل برای گرفتن ID
            Hotel savedHotel = hotelRepository.save(hotel);

            // اضافه کردن ۵ اتاق به هر هتل
            for (int j = 1; j <= 5; j++) {
                Room room = new Room();
                room.setTitle("اتاق " + j + " هتل " + i);
                room.setMainCapacity(2);
                room.setActive(true);
                savedHotel.addRoom(room); // مدیریت دوطرفه رابطه
                roomRepository.save(room); // ذخیره صریح اتاق
            }
            hotels.add(savedHotel);
        }

        // ۳. ایجاد قرارداد و نرخ (Rate)
        for (Hotel hotel : hotels) {
            // ایجاد ۱ قرارداد برای هر هتل
            Contract contract = new Contract();
            contract.setTitle("قرارداد سال ۲۰۲۵ - " + hotel.getName());
            contract.setHotel(hotel);
            Contract savedContract = contractRepository.save(contract);

            // ایجاد ۱ نرخ برای این هتل و قرارداد
            Rate rate = new Rate();
            rate.setHotel(hotel);
            rate.setContract(savedContract);
            rate.setFrom(LocalDateTime.now());
            rate.setTo(LocalDateTime.now().plusMonths(6));
            rate.setDiscount(10.0f + random.nextFloat() * 20); // تخفیف تصادفی بین ۱۰ تا ۳۰

            // مهم: اول Rate را ذخیره می‌کنیم تا خطا برطرف شود
            Rate savedRate = rateRepository.save(rate);

            // حالا این نرخ ذخیره شده را به ۲ آژانس تصادفی وصل می‌کنیم
            for (int k = 0; k < 2; k++) {
                Agency randomAgency = agencies.get(random.nextInt(agencies.size()));

                // برقراری رابطه Many-to-Many
                randomAgency.addRate(savedRate);

                // ذخیره آژانس برای آپدیت شدن جدول واسط (Join Table)
                agencyRepository.save(randomAgency);
            }
        }

        System.out.println("✅ دیتای فیک با موفقیت و رعایت ترتیب روابط ذخیره شد.");
    }
}