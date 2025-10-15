# MovieAAp

TMDb (The Movie Database) API'sini kullanarak popüler, vizyondaki ve en yüksek puanlı filmleri listeleyen, arama ve detay görüntüleme özelliklerine sahip Jetpack Compose ile geliştirilmiş modern bir Android film uygulamasıdır.

## 📱 Ekran Görüntüleri

| Ana Sayfa (Koyu Mod) | Arama Ekranı | Film Detay Sayfası |
| :---: |:---:|:---:|
| `[Ana sayfa ekran görüntüsü]` | `[Arama ekranı görüntüsü]` | `[Detay ekranı görüntüsü]` |

## ✨ Özellikler

- **Film Kategorileri**: Vizyondakiler (Now Playing), Popüler (Popular), En Yüksek Puanlılar (Top Rated) ve Yakında Gelecekler (Upcoming) filmlerini listeleme.
- **Tümünü Göster**: Her kategori için tüm filmlerin listelendiği ayrı bir sayfa.
- **Film Detayları**: Tıklanan filmin afişi, konusu, puanı, çıkış tarihi ve türleri gibi detaylı bilgilerini gösterme.
- **Film Arama**: Filmleri ismine göre arama ve sonuçları listeleme.
- **Modern Arayüz**: Tamamen Jetpack Compose ile oluşturulmuş reaktif ve modern kullanıcı arayüzü.
- **Tema Desteği**: Kullanıcının cihaz ayarlarına veya uygulama içinden seçimine göre Açık/Koyu mod desteği.
- **Çoklu Dil Desteği (Localization)**: Türkçe ve İngilizce dil desteği.

## 🛠️ Mimarisi ve Kullanılan Teknolojiler

Uygulama, Google'ın tavsiye ettiği modern Android uygulama geliştirme prensiplerine uygun olarak geliştirilmiştir.

### Mimari

- **MVVM (Model-View-ViewModel)**: Sorumlulukların ayrılması (SoC) prensibine uygun olarak, arayüz (View), arayüz mantığı (ViewModel) ve veri (Model/Repository) katmanları birbirinden ayrılmıştır.
- **Repository Pattern**: ViewModel'in doğrudan veri kaynaklarına (API) erişimini engelleyerek, veri mantığını merkezileştirir. Bu yapı, gelecekte yerel veritabanı (caching) eklemeyi kolaylaştırır.
- **Tek Aktivite Mimarisi (Single-Activity Architecture)**: Uygulama, tek bir ana `Activity` içerir ve ekranlar arası geçişler **Navigation Compose** ile yönetilir.

### Kullanılan Kütüphaneler

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Modern ve deklaratif bir şekilde kullanıcı arayüzü oluşturmak için kullanıldı.
- **[Kotlin Coroutines & Flow](https://kotlinlang.org/docs/coroutines-guide.html)**: Asenkron işlemleri (API çağrıları) ve reaktif veri akışlarını yönetmek için kullanıldı.
- **[ViewModel (Jetpack)](https://developer.android.com/topic/libraries/architecture/viewmodel)**: Arayüze ait verileri, ekran döndürme gibi konfigürasyon değişikliklerinden etkilenmeyecek şekilde saklamak ve yönetmek için kullanıldı.
- **[Retrofit 2](https://square.github.io/retrofit/)**: TMDb API'sine güvenli ve verimli bir şekilde ağ istekleri yapmak için kullanıldı.
- **[Gson](https://github.com/google/gson)**: API'den gelen JSON verilerini Kotlin veri sınıflarına (DTO) dönüştürmek için kullanıldı.
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)**: Uygulama içindeki ekranlar (Composable'lar) arası geçişleri yönetmek için kullanıldı.
- **[Coil](https://coil-kt.github.io/coil/)**: Film afişlerini URL'den verimli bir şekilde yüklemek, önbelleğe almak ve göstermek için kullanılan Kotlin tabanlı bir resim yükleme kütüphanesidir.
- **[DataStore](https://developer.android.com/topic/libraries/architecture/datastore)**: Kullanıcının tema (Açık/Koyu mod) tercihini asenkron ve güvenli bir şekilde cihaza kaydetmek için kullanıldı.

## 🏗️ Geliştirme Sırasındaki Tasarım Kararları

- **DTO ve UI Modellerinin Ayrılması**: API'den gelen veri yapıları (DTO - Data Transfer Object) ile arayüzde kullanılan veri modelleri (`Movie`, `MovieDetail`) birbirinden ayrılmıştır. Bu sayede, gelecekte API'de yaşanacak bir değişiklik doğrudan arayüzü etkilemez ve uygulama daha esnek hale gelir.
- **State Hoisting & Event Lifting**: Composable fonksiyonların durumlarını (state) yukarı taşıyarak ve olayları (event) yukarı bildirerek daha basit, yeniden kullanılabilir ve test edilebilir hale getirilmesi hedeflenmiştir. Örneğin, `HomeScreen` navigasyon mantığını bilmez, sadece "bir butona tıklandığını" `AppNavigation`'a bildirir.
- **Genel Liste Sayfası (`MovieListScreen`)**: Her kategori ("Popüler", "Vizyondakiler" vb.) için ayrı ayrı ekranlar oluşturmak yerine, hangi kategoriyi göstereceğini parametre olarak alan tek bir genel ekran tasarlanmıştır. Bu, kod tekrarını büyük ölçüde önlemiştir.
- **Yerelleştirme (`Localization`)**: Kullanıcıya gösterilen tüm metinler (`String`), kodun içine doğrudan yazılmak yerine `strings.xml` kaynak dosyalarına taşınmıştır. Bu sayede yeni diller eklemek son derece kolaylaşmıştır.

## 🔑 API Anahtarı

Bu proje, film verilerini çekmek için [TMDb API](https://www.themoviedb.org/documentation/api)'sini kullanmaktadır. Projeyi kendi bilgisayarınızda çalıştırmak için bir API anahtarına ihtiyacınız olacaktır.

**DİKKAT:** Projede API anahtarı `TmdbApiService` içinde doğrudan tanımlanmıştır. Bu, geliştirme kolaylığı için yapılmış olup **güvenli bir yöntem değildir.** Gerçek bir projede, anahtar `local.properties` dosyasına eklenmeli ve `build.gradle` üzerinden güvenli bir şekilde okunmalıdır.

## 🚀 Kurulum ve Çalıştırma

Projeyi kendi bilgisayarınızda çalıştırmak için aşağıdaki adımları izleyin:

1.  Bu repoyu klonlayın:
    ```bash
    git clone [REPO_URL]
    ```
2.  Android Studio'da projeyi açın.
3.  `data/TmdbApiService.kt` dosyasını açın ve `API_KEY` sabitini kendi TMDb API anahtarınızla değiştirin.
    ```kotlin
    private const val API_KEY = "SENIN_API_KEYIN_BURAYA_GELECEK"
    ```
4.  Projeyi senkronize edin (Sync Project with Gradle Files) ve çalıştırın.

