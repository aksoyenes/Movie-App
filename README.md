MovieAAp: Teknik Detaylar
Bu belge, MovieAAp projesinin geliştirilmesi sırasında kullanılan temel teknolojileri ve alınan önemli mimari kararları özetlemektedir.

 Kullanılan Kütüphaneler ve Teknolojiler
Jetpack Compose: Modern ve deklaratif bir şekilde kullanıcı arayüzü oluşturmak için kullanıldı.

Kotlin Coroutines & Flow: Asenkron işlemleri (API çağrıları) ve reaktif veri akışlarını yönetmek için kullanıldı.

ViewModel (Jetpack): Arayüze ait verileri, ekran döndürme gibi konfigürasyon değişikliklerinden etkilenmeyecek şekilde saklamak ve yönetmek için kullanıldı.

Retrofit 2: TMDb API'sine güvenli ve verimli bir şekilde ağ istekleri yapmak için kullanıldı.

Gson: API'den gelen JSON verilerini Kotlin veri sınıflarına (DTO) dönüştürmek için kullanıldı.

Navigation Compose: Uygulama içindeki ekranlar (Composable'lar) arası geçişleri yönetmek için kullanıldı.

Coil: Film afişlerini URL'den verimli bir şekilde yüklemek, önbelleğe almak ve göstermek için kullanılan Kotlin tabanlı bir resim yükleme kütüphanesidir.

DataStore: Kullanıcının tema (Açık/Koyu mod) tercihini asenkron ve güvenli bir şekilde cihaza kaydetmek için kullanıldı.

 Geliştirme Sırasındaki Tasarım Kararları
DTO ve UI Modellerinin Ayrılması: API'den gelen veri yapıları (DTO - Data Transfer Object) ile arayüzde kullanılan veri modelleri (Movie, MovieDetail) birbirinden ayrılmıştır. Bu sayede, gelecekte API'de yaşanacak bir değişiklik doğrudan arayüzü etkilemez ve uygulama daha esnek hale gelir.

State Hoisting & Event Lifting: Composable fonksiyonların durumlarını (state) yukarı taşıyarak ve olayları (event) yukarı bildirerek daha basit, yeniden kullanılabilir ve test edilebilir hale getirilmesi hedeflenmiştir. Örneğin, HomeScreen navigasyon mantığını bilmez, sadece "bir butona tıklandığını" AppNavigation'a bildirir.

Genel Liste Sayfası (MovieListScreen): Her kategori ("Popüler", "Vizyondakiler" vb.) için ayrı ayrı ekranlar oluşturmak yerine, hangi kategoriyi göstereceğini parametre olarak alan tek bir genel ekran tasarlanmıştır. Bu, kod tekrarını büyük ölçüde önlemiştir.

Yerelleştirme (Localization): Kullanıcıya gösterilen tüm metinler (String), kodun içine doğrudan yazılmak yerine strings.xml kaynak dosyalarına taşınmıştır. Bu sayede yeni diller eklemek son derece kolaylaşmıştır.

ViewModel ve Sorumlulukların Ayrılması: ViewModel'ler, arayüz metinlerinden (örneğin "Popüler Filmler") habersiz olacak şekilde tasarlanmıştır. Bunun yerine, arayüze metnin kaynak kimliğini (R.string.popular) iletirler. Bu, ViewModel'in tamamen arayüzden bağımsız ve test edilebilir olmasını sağlar.



